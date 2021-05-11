package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import connect.DBConnect;


public class Controller {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchor1;

    @FXML
    public TextArea resultField;

    @FXML
    public TextArea queryField;

    @FXML
    private Button btn1;

    @FXML
    private TextField tf1_id;

    @FXML
    private TextField tf2_id;

    @FXML
    private TextField tf3_id;

    @FXML
    private TextField tf4_id;

    @FXML
    private TextField tf5_id;

    private DBConnect connector = getConnection();

    private DBConnect getConnection(){
        String address = "127.0.0.1";
        String dbname = "postgres";
        String user = "postgres";
        String pass = "postgres";

        try {
            Scanner scan = new Scanner(new File("config.cfg"));
            address = scan.nextLine().split("=")[1];
            dbname = scan.nextLine().split("=")[1];
            user = scan.nextLine().split("=")[1];
            pass = scan.nextLine().split("=")[1];
            System.out.println("Got configs correct!");
            System.out.println(String.format("Address: %s\nDB Name: %s\nUser: %s\nPass: %s\n"
                    , address, dbname, user, pass));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e){
            System.out.println("Failed to load configs, continue with default\n");
        }

        String url = "jdbc:postgresql://"+address+"/" + dbname;
        return new DBConnect(url, user, pass);
    }

    @FXML
    void btn1Action(ActionEvent event) {

        resultField.appendText(connector.query(queryField.getText()));
        resultField.appendText("----End Of Query----\n");
    }

    @FXML
    void queryBtn1() {

        String first_name = tf1_id.getText();

        String sql = "SELECT *\n" +
                     "FROM \"user\"\n" +
                     "WHERE first_name = '" + first_name + "';\n";

        resultField.appendText(connector.query(sql));
        resultField.appendText("----End Of Query----\n");
    }

    @FXML
    void queryBtn2() {
        String first_name = tf1_id.getText();
        String last_name = tf2_id.getText();
        String sql = "";
        if (first_name != null && !first_name.trim().isEmpty())
            sql = "SELECT *\n" +
                    "FROM \"user\"\n" +
                    "WHERE first_name = '" + first_name + "'" +
                    "AND last_name = '" + last_name + "';\n";
        else
            sql = "SELECT *\n" +
                  "FROM \"user\"\n" +
                  "WHERE last_name = '" + last_name + "';\n";

        resultField.appendText(connector.query(sql));
        resultField.appendText("----End Of Query----\n");
    }

    @FXML
    void queryBtn3() {

        String login = tf3_id.getText();
        String first_name = tf1_id.getText();
        String last_name = tf2_id.getText();
        String sql = "";
        if (first_name != null && !first_name.trim().isEmpty())
            if (last_name != null && !last_name.trim().isEmpty())
                sql = "SELECT *\n" +
                        "FROM \"user\"\n" +
                        "WHERE first_name = '" + first_name + "' " +
                        "AND last_name = '" + last_name + "' " +
                        "AND ssn = (SELECT id FROM account WHERE login = '" + login + "');\n";
            else
                sql = "SELECT *\n" +
                        "FROM \"user\"\n" +
                        "WHERE first_name = '" + first_name + "' " +
                        "AND ssn = (SELECT id FROM account WHERE login = '" + login + "');\n";
        else
            if (last_name != null && !last_name.trim().isEmpty())
                sql = "SELECT *\n" +
                        "FROM \"user\"\n" +
                        "WHERE last_name = '" + last_name + "' " +
                        "AND ssn = (SELECT id FROM account WHERE login = '" + login + "');\n";
            else
                sql = "SELECT *\n" +
                        "FROM \"user\"\n" +
                        "WHERE ssn = (SELECT id FROM account WHERE login = '" + login + "');\n";
        resultField.appendText(connector.query(sql));
        resultField.appendText("----End Of Query----\n");
    }

    @FXML
    void queryBtn4() {

        String age = tf4_id.getText();
        String login = tf3_id.getText();
        String first_name = tf1_id.getText();
        String last_name = tf2_id.getText();
        String sql = "";
        if (login != null && !login.trim().isEmpty())
            if (first_name != null && !first_name.trim().isEmpty())
                if (last_name != null && !last_name.trim().isEmpty())
                    sql = "SELECT *\n" +
                            "FROM \"user\"\n" +
                            "WHERE first_name = '" + first_name + "' " +
                            "AND age = '" + age + "' " +
                            "AND last_name = '" + last_name + "' " +
                            "AND ssn = (SELECT id FROM account WHERE login = '" + login + "');\n";
                else
                    sql = "SELECT *\n" +
                            "FROM \"user\"\n" +
                            "WHERE first_name = '" + first_name + "' " +
                            "AND age = '" + age + "' " +
                            "AND ssn = (SELECT id FROM account WHERE login = '" + login + "');\n";
            else
                if (last_name != null && !last_name.trim().isEmpty())
                    sql = "SELECT *\n" +
                            "FROM \"user\"\n" +
                            "WHERE last_name = '" + last_name + "' " +
                            "AND age = '" + age + "' " +
                            "AND ssn = (SELECT id FROM account WHERE login = '" + login + "');\n";
                else
                    sql = "SELECT *\n" +
                            "FROM \"user\"\n" +
                            "WHERE age = '" + age + "' " +
                            "AND ssn = (SELECT id FROM account WHERE login = '" + login + "');\n";
        else
            if (first_name != null && !first_name.trim().isEmpty())
                if (last_name != null && !last_name.trim().isEmpty())
                    sql = "SELECT *\n" +
                        "FROM \"user\"\n" +
                        "WHERE first_name = '" + first_name + "' " +
                        "AND age = '" + age + "' " +
                        "AND last_name = '" + last_name + "';\n";
                else
                    sql = "SELECT *\n" +
                        "FROM \"user\"\n" +
                        "WHERE first_name = '" + first_name + "' " +
                        "AND age = '" + age + "';\n";
        else
            if (last_name != null && !last_name.trim().isEmpty())
                sql = "SELECT *\n" +
                    "FROM \"user\"\n" +
                    "WHERE last_name = '" + last_name + "' " +
                    "AND age = '" + age + "';\n";
            else
                sql = "SELECT *\n" +
                    "FROM \"user\"\n" +
                    "WHERE age = '" + age + "';\n";
        resultField.appendText(connector.query(sql));
        resultField.appendText("----End Of Query----\n");
    }

    @FXML
    void queryBtn5() {
        String age = tf4_id.getText();
        String login = tf3_id.getText();
        String first_name = tf1_id.getText();
        String last_name = tf2_id.getText();
        String role = tf5_id.getText();
        String sql = "";
        if (age != null && !age.trim().isEmpty())
            if (login != null && !login.trim().isEmpty())
                if (first_name != null && !first_name.trim().isEmpty())
                    if (last_name != null && !last_name.trim().isEmpty())
                        sql = "SELECT *\n" +
                                "FROM \"user\"\n" +
                                "WHERE first_name = '" + first_name + "' " +
                                "AND age = '" + age + "' " +
                                "AND last_name = '" + last_name + "' " +
                                "AND role = '" + role + "' " +
                                "AND ssn = (SELECT id FROM account WHERE login = '" + login + "');\n";
                    else
                        sql = "SELECT *\n" +
                                "FROM \"user\"\n" +
                                "WHERE first_name = '" + first_name + "' " +
                                "AND age = '" + age + "' " +
                                "AND role = '" + role + "' " +
                                "AND ssn = (SELECT id FROM account WHERE login = '" + login + "');\n";
                else
                    if (last_name != null && !last_name.trim().isEmpty())
                        sql = "SELECT *\n" +
                                "FROM \"user\"\n" +
                                "WHERE last_name = '" + last_name + "' " +
                                "AND age = '" + age + "' " +
                                "AND role = '" + role + "' " +
                                "AND ssn = (SELECT id FROM account WHERE login = '" + login + "');\n";
                    else
                        sql = "SELECT *\n" +
                                "FROM \"user\"\n" +
                                "WHERE age = '" + age + "' " +
                                "AND role = '" + role + "' " +
                                "AND ssn = (SELECT id FROM account WHERE login = '" + login + "');\n";
            else
                if (first_name != null && !first_name.trim().isEmpty())
                    if (last_name != null && !last_name.trim().isEmpty())
                        sql = "SELECT *\n" +
                                "FROM \"user\"\n" +
                                "WHERE first_name = '" + first_name + "' " +
                                "AND age = '" + age + "' " +
                                "AND role = '" + role + "' " +
                                "AND last_name = '" + last_name + "';\n";
                    else
                        sql = "SELECT *\n" +
                                "FROM \"user\"\n" +
                                "WHERE first_name = '" + first_name + "' " +
                                "AND role = '" + role + "' " +
                                "AND age = '" + age + "';\n";
                else
                    if (last_name != null && !last_name.trim().isEmpty())
                        sql = "SELECT *\n" +
                                "FROM \"user\"\n" +
                                "WHERE last_name = '" + last_name + "' " +
                                "AND role = '" + role + "' " +
                                "AND age = '" + age + "';\n";
                    else
                        sql = "SELECT *\n" +
                                "FROM \"user\"\n" +
                                "WHERE age = '" + age + "' " +
                                "AND role = '" + role + "';\n";
        else
            if (login != null && !login.trim().isEmpty())
                if (first_name != null && !first_name.trim().isEmpty())
                    if (last_name != null && !last_name.trim().isEmpty())
                        sql = "SELECT *\n" +
                            "FROM \"user\"\n" +
                            "WHERE first_name = '" + first_name + "' " +
                            "AND last_name = '" + last_name + "' " +
                            "AND role = '" + role + "' " +
                            "AND ssn = (SELECT id FROM account WHERE login = '" + login + "');\n";
                    else
                        sql = "SELECT *\n" +
                            "FROM \"user\"\n" +
                            "WHERE first_name = '" + first_name + "' " +
                            "AND role = '" + role + "' " +
                            "AND ssn = (SELECT id FROM account WHERE login = '" + login + "');\n";
                else
                    if (last_name != null && !last_name.trim().isEmpty())
                        sql = "SELECT *\n" +
                        "FROM \"user\"\n" +
                        "WHERE last_name = '" + last_name + "' " +
                        "AND role = '" + role + "' " +
                        "AND ssn = (SELECT id FROM account WHERE login = '" + login + "');\n";
                    else
                        sql = "SELECT *\n" +
                        "FROM \"user\"\n" +
                        "AND role = '" + role + "' " +
                        "AND ssn = (SELECT id FROM account WHERE login = '" + login + "');\n";
            else
                if (first_name != null && !first_name.trim().isEmpty())
                    if (last_name != null && !last_name.trim().isEmpty())
                        sql = "SELECT *\n" +
                        "FROM \"user\"\n" +
                        "WHERE first_name = '" + first_name + "' " +
                        "AND role = '" + role + "' " +
                        "AND last_name = '" + last_name + "';\n";
                    else
                        sql = "SELECT *\n" +
                        "FROM \"user\"\n" +
                        "WHERE first_name = '" + first_name + "' " +
                        "AND role = '" + role + "';\n";
                else
                    if (last_name != null && !last_name.trim().isEmpty())
                        sql = "SELECT *\n" +
                            "FROM \"user\"\n" +
                            "WHERE last_name = '" + last_name + "' " +
                            "AND role = '" + role + "';\n";
                    else
                        sql = "SELECT *\n" +
                                "FROM \"user\"\n" +
                                "WHERE role = '" + role + "';\n";

        resultField.appendText(connector.query(sql));
        resultField.appendText("----End Of Query----\n");
    }

    @FXML
    void initialize() {
        assert anchor1 != null : "fx:id=\"anchor1\" was not injected: check your FXML file 'mainForm.fxml'.";
        assert btn1 != null : "fx:id=\"btn1\" was not injected: check your FXML file 'mainForm.fxml'.";
        assert queryField != null : "fx:id=\"txtArea1\" was not injected: check your FXML file 'mainForm.fxml'.";
        assert resultField != null : "fx:id=\"txtArea1\" was not injected: check your FXML file 'mainForm.fxml'.";
    }

    public void btnClean(ActionEvent actionEvent) {
        resultField.clear();
    }
}