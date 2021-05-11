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

        String login = tf1_id.getText();

        String sql = "WITH appointment_for_current AS\n" +
                "         (\n" +
                "             SELECT *\n" +
                "             FROM (SELECT id as pid\n" +
                "                   FROM patient\n" +
                "                   WHERE user_ssn in (\n" +
                "                       SELECT ssn\n" +
                "                       FROM \"user\"\n" +
                "                                JOIN account on account_id = account.id\n" +
                "                       WHERE login = '" + login + "' AND gender = 'F')\n" +
                "                  ) as current_patient\n" +
                "                      JOIN appointment on for_patient_id = pid\n" +
                "         )\n" +
                "SELECT first_name, last_name\n" +
                "FROM \"user\"\n" +
                "WHERE ssn in (SELECT appointed_by_user_ssn\n" +
                "              FROM appointment_for_current\n" +
                "              WHERE date in (SELECT max(date) FROM appointment_for_current)\n" +
                ")\n" +
                "  AND ((left(first_name, 1) = 'M' AND left(last_name, 1) <> 'M') OR\n" +
                "       (left(first_name, 1) = 'L' AND left(last_name, 1) <> 'L') OR\n" +
                "       (left(first_name, 1) <> 'M' AND left(last_name, 1) = 'M') OR\n" +
                "       (left(first_name, 1) <> 'L' AND left(last_name, 1) = 'L'));\n";

        resultField.appendText(connector.query(sql));
        resultField.appendText("----End Of Query----\n");
    }

    @FXML
    void queryBtn2() {

        String date = tf2_id.getText();


        String sql = "SELECT ssn,\n" +
                "       first_name,\n" +
                "       last_name,\n" +
                "       to_char(DATE '2019-11-17'+ INTERVAL '1 day' * day_of_week, 'dy') as weekday,\n" +
                "       total,\n" +
                "       statistic_about_appointments.total / count_of_workweeks.amount_of_work_weeks :: double precision as average\n" +
                "FROM (\n" +
                "         SELECT appointed_by_user_ssn,\n" +
                "                day_of_week,\n" +
                "                count(date) as total\n" +
                "         FROM (\n" +
                "                  SELECT appointed_by_user_ssn, EXTRACT(dow FROM date) as day_of_week, date\n" +
                "                  FROM APPOINTMENT\n" +
                "                  WHERE date >= ('" + date + "'::date - INTERVAL '1 year')\n" +
                "              ) as doctor_ssn_dow_and_date\n" +
                "         GROUP BY appointed_by_user_ssn, day_of_week\n" +
                "     ) as statistic_about_appointments\n" +
                "         JOIN\n" +
                "     (\n" +
                "         SELECT distinct appointed_by_user_ssn, count(week) as amount_of_work_weeks\n" +
                "         FROM (\n" +
                "                  SELECT appointed_by_user_ssn, EXTRACT(week FROM date) as week\n" +
                "                  FROM appointment\n" +
                "                  WHERE date >= ('" + date + "'::date - INTERVAL '1 year')\n" +
                "              ) as doctor_works_in_week\n" +
                "         GROUP BY appointed_by_user_ssn\n" +
                "     ) as count_of_workweeks\n" +
                "     on count_of_workweeks.appointed_by_user_ssn = statistic_about_appointments.appointed_by_user_ssn\n" +
                "         JOIN \"user\" on count_of_workweeks.appointed_by_user_ssn = ssn\n" +
                "WHERE role = 'doctor'\n" +
                "ORDER BY ssn, day_of_week;\n";


        resultField.appendText(connector.query(sql));
        resultField.appendText("----End Of Query----\n");
    }

    @FXML
    void queryBtn3() {

        String date = tf3_id.getText();

        String sql = "-- Query 3\n" +
                "\n" +
                "SELECT first_name || ' ' || last_name as twice_a_week\n" +
                "FROM \"user\" JOIN\n" +
                "     (SELECT count(date) as num, user_ssn\n" +
                "      FROM patient JOIN appointment a on patient.id = a.for_patient_id\n" +
                "      WHERE EXTRACT('week' from date) = EXTRACT('week' from '" + date + " 00:00:01'::timestamp)\n" +
                "      GROUP BY patient.id) as week1 on ssn = week1.user_ssn\n" +
                "      JOIN\n" +
                "     (SELECT count(date) as num, user_ssn\n" +
                "      FROM patient JOIN appointment a on patient.id = a.for_patient_id\n" +
                "      WHERE EXTRACT('week' from date) = EXTRACT('week' from '" + date + " 00:00:01'::timestamp) - 1\n" +
                "      GROUP BY patient.id) as week2 on ssn = week2.user_ssn\n" +
                "      JOIN\n" +
                "     (SELECT count(date) as num, user_ssn\n" +
                "      FROM patient JOIN appointment a on patient.id = a.for_patient_id\n" +
                "      WHERE EXTRACT('week' from date) = EXTRACT('week' from '" + date + " 00:00:01'::timestamp) - 2\n" +
                "      GROUP BY patient.id) as week3 on ssn = week3.user_ssn\n" +
                "      JOIN\n" +
                "     (SELECT count(date) as num, user_ssn\n" +
                "      FROM patient JOIN appointment a on patient.id = a.for_patient_id\n" +
                "      WHERE EXTRACT('week' from date) = EXTRACT('week' from '" + date + " 00:00:01'::timestamp) - 3\n" +
                "      GROUP BY patient.id) as week4 on ssn = week4.user_ssn\n" +
                "WHERE week1.num >= 2 AND week1.num >= 2 AND week3.num >= 2 AND week4.num >= 2;\n";

        resultField.appendText(connector.query(sql));
        resultField.appendText("----End Of Query----\n");
    }

    @FXML
    void queryBtn4() {

        String date = tf4_id.getText();

        String sql = "WITH possible_charge AS (\n" +
                "    SELECT *\n" +
                "    FROM (\n" +
                "             SELECT 200\n" +
                "             UNION ALL\n" +
                "             SELECT 250\n" +
                "             UNION ALL\n" +
                "             SELECT 400\n" +
                "             UNION ALL\n" +
                "             SELECT 500\n" +
                "         ) as possible_charge(charge)\n" +
                ")\n" +
                "SELECT SUM(charge * amount_of_appointments) as income_in_rubles\n" +
                "FROM (\n" +
                "         SELECT age, amount_of_appointments\n" +
                "         FROM (\n" +
                "                  SELECT id as patient_id, age\n" +
                "                  FROM patient\n" +
                "                           JOIN \"user\" on patient.user_ssn = \"user\".ssn\n" +
                "              ) as age_of_patient\n" +
                "                  JOIN\n" +
                "              (\n" +
                "                  SELECT for_patient_id, count(app_id) as amount_of_appointments\n" +
                "                  FROM (\n" +
                "                           SELECT id as app_id, for_patient_id\n" +
                "                           FROM appointment\n" +
                "                           WHERE date >= ('" + date + "'::date - interval '1 month')\n" +
                "                       ) as appoinments_in_previous_month\n" +
                "                  GROUP BY for_patient_id\n" +
                "              ) as amount_of_appoinments_in_previous_month\n" +
                "              on patient_id = for_patient_id\n" +
                "     ) as age_and_amount_of_appointments_in_previous_month_of_patient\n" +
                "         CROSS JOIN possible_charge\n" +
                "WHERE (age < 50 AND amount_of_appointments < 3 AND charge = 200)\n" +
                "   OR (age < 50 AND amount_of_appointments >= 3 AND charge = 250)\n" +
                "   OR (age >= 50 AND amount_of_appointments < 3 AND charge = 400)\n" +
                "   OR (age >= 50 AND amount_of_appointments >= 3 AND charge = 500);\n";

        resultField.appendText(connector.query(sql));
        resultField.appendText("----End Of Query----\n");
    }

    @FXML
    void queryBtn5() {

        String date = tf5_id.getText();

        String sql = "SELECT ssn, first_name, last_name\n" +
                "FROM(\n" +
                "    SELECT ssn, first_name, last_name\n" +
                "    FROM(\n" +
                "        SELECT ssn, first_name, last_name, count(year) as amount_of_hard_working_years\n" +
                "        FROM(\n" +
                "            SELECT ssn, first_name, last_name, year\n" +
                "            FROM(\n" +
                "                SELECT ssn, first_name, last_name, year, count(for_patient_id) as patients_in_year\n" +
                "                FROM(\n" +
                "                    SELECT distinct ssn, first_name, last_name, EXTRACT(year from date) as year, for_patient_id\n" +
                "                    FROM \"user\" join appointment on appointed_by_user_ssn = ssn\n" +
                "                    WHERE role = 'doctor' AND EXTRACT(year from date) > EXTRACT(year from CURRENT_DATE) - 10\n" +
                "                ) as patients_visited_by_doctor_in_year\n" +
                "                GROUP BY ssn, first_name, last_name, year\n" +
                "            ) as amount_of_patient_visited_by_doctor_in_year\n" +
                "            WHERE patients_in_year >= 5\n" +
                "        ) as hard_working_years_of_doctor\n" +
                "        GROUP BY ssn, last_name, first_name\n" +
                "    ) as amount_of_ard_working_years_of_doctor\n" +
                "    WHERE amount_of_hard_working_years = 10\n" +
                ") as doctors_with_not_less_than_5_patients_per_year \n" +
                "JOIN \n" +
                "(\n" +
                "    SELECT ssn as ssn_of_doctor_with_100_patients\n" +
                "    FROM(\n" +
                "        SELECT ssn, count(for_patient_id) as total_patients\n" +
                "        FROM(\n" +
                "            SELECT distinct ssn, for_patient_id\n" +
                "            FROM \"user\" join appointment on appointed_by_user_ssn = ssn\n" +
                "            WHERE role = 'doctor' AND EXTRACT(year from date) > EXTRACT(year from CURRENT_DATE) - 10\n" +
                "        ) as patients_visited_by_doctor_ssn\n" +
                "        GROUP BY ssn\n" +
                "    ) as amount_of_patients_visited_by_doctor_ssn\n" +
                "    WHERE total_patients >= 100\n" +
                ") as doctors_with_not_less_than_100_patients \n" +
                "on ssn = ssn_of_doctor_with_100_patients";

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