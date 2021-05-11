package connect;

import java.sql.*;
import java.util.concurrent.TimeUnit;

public class DBConnect {
    private String db_url;
    private String db_user;
    private String db_password;
    private Connection connection;

    public DBConnect(String db_url, String db_user, String db_password) {
        this.db_url = db_url;
        this.db_user = db_user;
        this.db_password = db_password;

        this.hold_invariant();
    }

    /**
     * The function guarantees that class will be connected to the database
     */
    private void hold_invariant(){
        boolean isConnected = false;

        while(!isConnected){
            try {
                Class.forName("org.postgresql.Driver");
                if(connection == null || !connection.isValid(5)){
                    this.connect();
                }
                isConnected = true;
            }catch (SQLException | ClassNotFoundException ex){
                isConnected = false;
                System.err.println("Error message: " + ex.getLocalizedMessage());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
            }

            if(!isConnected){
                System.err.println("DB connection failed, retrying");
            }
        }
    }

    private void connect() throws SQLException {
        // create a connection to the database
        this.connection = DriverManager.getConnection(this.db_url, this.db_user, this.db_password);
        System.err.println("Connected to DB successfully");
    }

    public String query(String sql){
        this.hold_invariant();
        StringBuilder result = new StringBuilder();

        try (Statement stmt = this.connection.createStatement()) {
            // create a new table
            String new_sql = sql.replaceAll("--.*?\n", " ").replace('\n', ' ');
            ResultSet resultSet = stmt.executeQuery(new_sql);
            System.err.println("Query succeed");
            int columnCount = resultSet.getMetaData().getColumnCount();
            for(int i = 0; i < columnCount; ++i){
                result.append(resultSet.getMetaData().getColumnName(i+1)).append(i == columnCount-1 ? "" : " | ");
            }
            result.append("\n");

            while (resultSet.next()) {
                for(int i = 1; i <= columnCount; ++i){
                    if (i > 1) result.append(", ");
                    result.append(resultSet.getString(i));
                }
                result.append("\n");
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return result.toString();
    }

}
