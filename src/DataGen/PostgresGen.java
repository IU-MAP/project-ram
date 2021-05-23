package DataGen;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class PostgresGen {
    private static final int initialID = 100;

    private DataGeneration gen;
    private ArrayList<Integer> departmentIDs = new ArrayList<>(30);
    private ArrayList<Integer> ambulanceIDs = new ArrayList<>(30);
    private ArrayList<Integer> accountIDs = new ArrayList<>(30);
    private ArrayList<Integer> userIDs = new ArrayList<>(30);
    private ArrayList<Integer> notificationIDs = new ArrayList<>(30);
    private ArrayList<Integer> patientIDs = new ArrayList<>(30);
    private ArrayList<Integer> requestIDs = new ArrayList<>(30);
    private ArrayList<Integer> chatIDs = new ArrayList<>(30);
    private ArrayList<Integer> web_pageIDs = new ArrayList<>(30);
    private ArrayList<Integer> usersWhoPatient = new ArrayList<>(1000);
    private ArrayList<Integer> usersWhoDoctor = new ArrayList<>(10);

    public PostgresGen() throws FileNotFoundException {
        this.gen = new DataGeneration();
    }

    public Integer getRandomID(ArrayList<Integer> list) {
        return list.get(DataGeneration.nextInt(0, list.size()));
    }

    /**
     * Generate sublist of IDs
     *
     * @param list of IDs of corresponding table
     * @param n    is amount of elements in sublist
     */
    public ArrayList<Integer> getSubsetIDs(ArrayList<Integer> list, int n) {
        ArrayList<Integer> local = new ArrayList<>(list);

        Collections.shuffle(local);
        return new ArrayList<Integer> (local.subList(0, n));
    }

    public int accountsAmount() {
        return accountIDs.size();
    }

    public String generate() {

        // Tables

        return "BEGIN;\n" + this.genDepartments() + "\n\n" +
                this.genAmbulance() + "\n\n" +
                this.genAccount() + "\n\n" +
                this.genUser() + "\n\n" +
                this.genNotification() + "\n\n" +
                this.genReport() + "\n\n" +
                this.genPatient() + "\n\n" +
                this.genMedical_report() + "\n\n" +
                this.genAppointment() + "\n\n" +
                this.genMedical_certificate() + "\n\n" +
                this.genInvoice() + "\n\n" +
                this.genRequest() + "\n\n" +
                this.genChat() + "\n\n" +
                this.genMessage() + "\n\n" +
                this.genSalary() + "\n\n" +
                this.genMedicament() + "\n\n" +
                this.genSupply() + "\n\n" +

                // Relations
                this.genIs_for() + "\n\n" +
                this.genRequired_request() + "\n\n" +
                this.genApproved_request() + "\n\n" +
                this.genMember_of_chat() + "\n\n" +
                this.genWeb_page() + "\n\n" +
                this.genContent() + "END;\n\n" + "";
    }

    public void generateToFile(String filename) {
        try {
            FileWriter write = new FileWriter(filename);
            write.write(this.generate());
            write.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String genContent() {
        StringBuilder result = new StringBuilder();

        int finish = DataGeneration.nextInt(10, 31);
        ArrayList<Integer> webpages = getSubsetIDs(web_pageIDs, web_pageIDs.size());

        for (int i = 1; i <= finish; ++i) {
            result.append(
                    String.format("INSERT INTO content VALUES (%d, %d, 'This is first draft of hospital`s website');\n"
                            , initialID + i, webpages.get(i % webpages.size()))
            );
        }

        return result.toString();
    }

    public String genWeb_page() {
        StringBuilder result = new StringBuilder();

        int finish = DataGeneration.nextInt(20, 31);
        ArrayList<Integer> users = getSubsetIDs(userIDs, userIDs.size());

        for (int i = 1; i <= finish; ++i) {
            result.append(
                    String.format("INSERT INTO web_page VALUES (%d, %d);\n"
                            , initialID + i, users.get(i % users.size()))
            );
            this.web_pageIDs.add(initialID + i);
        }

        return result.toString();
    }

    public String genMember_of_chat() {
        StringBuilder result = new StringBuilder();

        int finish = chatIDs.size();
        ArrayList<Integer> users = getSubsetIDs(userIDs, userIDs.size());
        ArrayList<Integer> chats = getSubsetIDs(chatIDs, chatIDs.size());

        for (int i = 1; i <= finish; ++i) {
            result.append(
                    String.format("INSERT INTO member_of_chat VALUES (%d, %d);\n"
                            , users.get(i % users.size()), chats.get(i % chats.size()))
            );
        }

        return result.toString();
    }

    public String genApproved_request() {
        StringBuilder result = new StringBuilder();

        int finish = DataGeneration.nextInt(10, 31);
        ArrayList<Integer> requests = getSubsetIDs(requestIDs, requestIDs.size());
        ArrayList<Integer> users = getSubsetIDs(userIDs, userIDs.size());

        for (int i = 1; i <= finish; ++i) {
            result.append(
                    String.format("INSERT INTO approved_request VALUES (%d, %d);\n"
                            , requests.get(i % requests.size()), users.get(i % users.size()))
            );
        }

        return result.toString();
    }

    public String genRequired_request() {
        StringBuilder result = new StringBuilder();

        int finish = DataGeneration.nextInt(10, 31);
        ArrayList<Integer> requests = getSubsetIDs(requestIDs, requestIDs.size());
        ArrayList<Integer> users = getSubsetIDs(userIDs, userIDs.size());

        for (int i = 1; i <= finish; ++i) {
            result.append(
                    String.format("INSERT INTO required_request VALUES (%d, %d);\n"
                            , requests.get(i % requests.size()), users.get(i % users.size()))
            );
        }

        return result.toString();
    }

    public String genIs_for() {
        StringBuilder result = new StringBuilder();

        int finish = DataGeneration.nextInt(10, 31);
        ArrayList<Integer> users = getSubsetIDs(userIDs, userIDs.size());

        for (int i = 1; i <= finish; ++i) {
            result.append(
                    String.format("INSERT INTO is_for VALUES (%d, %d);\n"
                            , users.get(i % users.size()), notificationIDs.get(i % notificationIDs.size()))
            );
        }

        return result.toString();
    }

    public String genSupply() {
        StringBuilder result = new StringBuilder();

        int finish = DataGeneration.nextInt(10, 800);

        for (int i = 1; i <= finish; ++i) {
            result.append(
                    String.format("INSERT INTO supply VALUES (%d, '%s', %d);\n"
                            , initialID + i
                            , gen.getSupply()
                            , departmentIDs.get(i % departmentIDs.size()))
            );
        }

        return result.toString();
    }

    public String genMedicament() {
        StringBuilder result = new StringBuilder();

        int finish = DataGeneration.nextInt(10, 256);

        for (int i = 1; i <= finish; ++i) {
            result.append(
                    String.format("INSERT INTO medicament VALUES (%d, '%s', '%s', %d);\n"
                            , initialID + i, gen.getPassword(), gen.getMedicament(), departmentIDs.get(i % departmentIDs.size()))
            );
        }

        return result.toString();
    }

    public String genMessage() {
        StringBuilder result = new StringBuilder();

        int finish = DataGeneration.nextInt(10, accountsAmount());
        ArrayList<Integer> users = getSubsetIDs(userIDs, finish);
        ArrayList<Integer> chats = getSubsetIDs(chatIDs, chatIDs.size());

        for (int i = 1; i <= finish; ++i) {
            result.append(
                    String.format("INSERT INTO message VALUES (%d, %d, %d, '%s');\n"
                            , initialID + i, chats.get(i % chats.size()), users.get(i % users.size()), gen.getMedicalFact())
            );
        }

        return result.toString();
    }

    public String genChat() {
        StringBuilder result = new StringBuilder();

        int finish = DataGeneration.nextInt(10, 31);

        for (int i = 1; i <= finish; ++i) {
            result.append(
                    String.format("INSERT INTO chat VALUES (%d, '%s');\n"
                            , initialID + i, gen.getRandFact())
            );
            this.chatIDs.add(initialID + i);
        }

        return result.toString();
    }

    public String genRequest() {
        StringBuilder result = new StringBuilder();

        int finish = DataGeneration.nextInt(10, 310);

        for (int i = 1; i <= finish; ++i) {
            result.append(
                    String.format("INSERT INTO request VALUES (%d, 'I need %s', %b);\n"
                            , initialID + i, gen.getMedicament(), gen.getBool())
            );
            this.requestIDs.add(initialID + i);
        }

        return result.toString();
    }

    public String genInvoice() {
        StringBuilder result = new StringBuilder();

        int finish = DataGeneration.nextInt(2, Math.min(accountsAmount(), 15));
        ArrayList<Integer> users = getSubsetIDs(userIDs, userIDs.size());
        ArrayList<Integer> patients = getSubsetIDs(patientIDs, patientIDs.size());

        for (int i = 1; i <= finish; ++i) {
            result.append(
                    String.format("INSERT INTO invoice VALUES (%d, %d, %d, '%d', 'visit to %s + %s');\n"
                            , initialID + i
                            , users.get(i % users.size())
                            , patients.get(i % patients.size())
                            , DataGeneration.nextInt(1000, 50000)
                            , gen.getRole()
                            , gen.getMedicament())
            );
        }

        return result.toString();
    }

    public String genMedical_certificate() {
        StringBuilder result = new StringBuilder();

        int finish = DataGeneration.nextInt(3, 6);
        ArrayList<Integer> users = getSubsetIDs(userIDs, finish);
        ArrayList<Integer> patients = getSubsetIDs(patientIDs, finish);

        for (int i = 1; i <= finish; ++i) {
            result.append(
                    String.format("INSERT INTO medical_certificate VALUES (%d, %d, %d, '%d days free from job');\n"
                            , initialID + i, users.get(i - 1), patients.get(i - 1), DataGeneration.nextInt(1, 1000))
            );
        }

        return result.toString();
    }

    public String genAppointment() {
        StringBuilder result = new StringBuilder();

        int finish = 5000;

        for (int i = 1; i <= finish; ++i) {
            result.append(
                    String.format("INSERT INTO appointment VALUES (%d, %d, %d, TIMESTAMP '%s');\n"
                            , initialID + i
                            , usersWhoDoctor.get(i % usersWhoDoctor.size())
                            , patientIDs.get(i % patientIDs.size())
                            , gen.getDateTime())
            );
        }

        return result.toString();
    }

    public String genMedical_report() {
        StringBuilder result = new StringBuilder();

        int finish = DataGeneration.nextInt(2, 10);
        ArrayList<Integer> users = getSubsetIDs(userIDs, finish);
        ArrayList<Integer> patients = getSubsetIDs(patientIDs, finish);

        for (int i = 1; i <= finish; ++i) {
            result.append(
                    String.format("INSERT INTO medical_report VALUES (%d, %d, %d, '%s');\n"
                            , initialID + i, users.get(i - 1), patients.get(i - 1), gen.getMedicalFact())
            );
        }

        return result.toString();
    }

    public String genPatient() {
        StringBuilder result = new StringBuilder();

        int finish = usersWhoPatient.size();

        for (int i = 1; i <= finish; ++i) {
            int id = initialID + i;

            result.append(
                    String.format("INSERT INTO patient VALUES (%d, %d, '%s', DATE '%s');\n"
                            , id
                            , usersWhoPatient.get(i - 1)
                            , gen.getMedicalFact()
                            , gen.getDate())
            );
            this.patientIDs.add(id);
        }

        assert usersWhoPatient.size() == patientIDs.size();
        return result.toString();
    }

    public String genReport() {
        StringBuilder result = new StringBuilder();

        int finish = DataGeneration.nextInt(6, accountsAmount());
        ArrayList<Integer> users = getSubsetIDs(userIDs, finish);

        for (int i = 1; i <= finish; ++i) {
            result.append(
                    String.format("INSERT INTO report VALUES (%d, %d, '%d for advertising to find new doctors');\n"
                            , initialID + i
                            , users.get(i - 1)
                            , DataGeneration.nextInt(200, 9999))
            );
        }

        return result.toString();
    }

    public String genNotification() {
        StringBuilder result = new StringBuilder();

        int finish = accountsAmount();
        ArrayList<Integer> users = getSubsetIDs(userIDs, finish);

        for (int i = 1; i <= finish; ++i) {
            result.append(
                    String.format("INSERT INTO notification VALUES (%d, %d, '%s');\n"
                            , initialID + i, users.get(i - 1), gen.getRandFact())
            );
            this.notificationIDs.add(initialID + i);
        }

        return result.toString();
    }

    public String genSalary() {
        StringBuilder result = new StringBuilder();

        int finish = DataGeneration.nextInt(10, accountsAmount());
        ArrayList<Integer> users = getSubsetIDs(userIDs, finish);

        for (int i = 1; i <= finish; ++i) {
            result.append(
                    String.format("INSERT INTO salary VALUES (%d, %d, %d);\n"
                            , initialID + i, DataGeneration.nextInt(20000, 9999999), users.get(i - 1))
            );
        }

        return result.toString();
    }

    public String genUser() {
        StringBuilder result = new StringBuilder();

        int finish = accountsAmount();
        ArrayList<Integer> accounts = getSubsetIDs(accountIDs, finish);

        for (int i = 1; i <= finish; ++i) {
            int id = initialID + i;

            boolean isMale = this.gen.getBool();
            String role = this.gen.getRole();
            if(role.equals("doctor") && usersWhoDoctor.size() >= 2){
                role = "patient";
            }

            if(role.equals("patient")){
                usersWhoPatient.add(id);
            }else if(role.equals("doctor")){
                usersWhoDoctor.add(id);
            }

            result.append(
                    String.format("INSERT INTO \"user\" VALUES (%d, '%s', '%s', '%s', '%s', %s, %s, '%s', '%s', %d, %d);\n"
                            , id
                            , isMale ? gen.getMaleName() : gen.getFemaleName()
                            , gen.getSurname()
                            , gen.getGender(isMale)
                            , role
                            , (role.equals("patient") ? "null" : getRandomID(departmentIDs))
                            , (role.equals("doctor") && DataGeneration.nextInt(0, 4) == 0) ? getRandomID(ambulanceIDs) : "null"
                            , gen.getAddress()
                            , gen.getPhone()
                            , DataGeneration.nextInt(18, 101)
                            , accounts.get(i - 1))
            );
            this.userIDs.add(id);
        }

        return result.toString();
    }

    public String genAccount() {
        StringBuilder result = new StringBuilder();

        int finish = 1000;
        ArrayList<String> nicknames = gen.getNicksSubset(finish);

        for (int i = 1; i <= finish; ++i) {
            result.append(
                    String.format("INSERT INTO account VALUES (%d, '%s', '%s');\n"
                            , initialID + i, nicknames.get(i-1), gen.getPassword())
            );
            this.accountIDs.add(initialID + i);
        }

        return result.toString();
    }

    public String genAmbulance() {
        StringBuilder result = new StringBuilder();

        int finish = DataGeneration.nextInt(10, 31);

        for (int i = 1; i <= finish; ++i) {
            result.append(
                    String.format("INSERT INTO ambulance VALUES (%d, %b);\n"
                            , initialID + i, gen.getBool())
            );
            this.ambulanceIDs.add(initialID + i);
        }

        return result.toString();
    }

    public String genDepartments() {
        StringBuilder result = new StringBuilder();

        int start = DataGeneration.nextInt(0, 31);
        int len = DataGeneration.nextInt(10, 21);
        for (int i = start; i < start + len; ++i) {
            result.append(
                    String.format("INSERT INTO department VALUES (%d, '%s', %d);\n"
                            , initialID + i - start + 1, DataGeneration.departmentNames.get(i)
                            , DataGeneration.nextInt(1000000, 99999999))
            );
            this.departmentIDs.add(initialID + i - start + 1);
        }

        return result.toString();
    }
}

