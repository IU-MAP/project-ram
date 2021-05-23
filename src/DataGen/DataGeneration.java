package DataGen;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class DataGeneration {
    private RandomPool femaleNamesPool;
    private RandomPool maleNamesPool;
    private RandomPool surnamesPool;
    private RandomPool medFactsPool;
    private RandomPool medNamesPool;
    private RandomPool nicknamePool;
    private RandomPool randomInfoPool;
    private RandomPool supplyPool;
    private RandomPool depNamesPool;
    private RandomPool roleNamesPool;
    private RandomPool addressPool;

    private class RandomPool {
        private ArrayList<String> pool = new ArrayList<>();

        RandomPool(String filename) throws FileNotFoundException {
            Scanner scan = new Scanner(new File(filename));
            while (scan.hasNext()) {
                pool.add(scan.nextLine());
            }
        }

        RandomPool(ArrayList<String> data) {
            this.pool = data;
        }

        public int size() {
            return pool.size();
        }

        public String getNext() {
            return pool.get(DataGeneration.nextInt(0, pool.size()));
        }

    }

    static final ArrayList<String> departmentNames = new ArrayList<String>(Arrays.asList(
            "Accident and emergency (A&E)", "Admissions", "Anesthetics", "Breast Screening"
            , "Burn Center (Burn Unit or Burns Unit)", "Cardiology", "Central Sterile Services Department (CSSD)"
            , "Chaplaincy", "Coronary Care Unit (CCU)", "Critical Care", "Diagnostic Imaging", "Discharge Lounge"
            , "Elderly services", "Finance Department", "Gastroenterology", "General Services", "General Surgery"
            , "Gynecology", "Haematology", "Health & Safety", "Intensive Care Unit (ICU)", "Human Resources"
            , "Infection Control", "Information Management", "Maternity", "Medical Records", "Microbiology", "Neonatal"
            , "Nephrology", "Neurology", "Nutrition and Dietetics", "Obstetrics/Gynecology", "Occupational Therapy"
            , "Oncology", "Ophthalmology", "Orthopaedics", "Otolaryngology (Ear, Nose, and Throat)", "Pain Management"
            , "Patient Accounts", "Patient Services", "Pharmacy", "Physiotherapy", "Purchasing & Supplies", "Radiology"
            , "Radiotherapy", "Renal", "Rheumatology", "Sexual Health", "Social Work", "Urology"
    ));

    private static final ArrayList<String> roleNames = new ArrayList<>(Arrays.asList(
            "patient", "doctor", "nurse", "manager", "bookkeeper", "intern", "sysadmin", "receptionist", "patient"
            , "patient", "patient", "patient", "patient", "patient", "patient", "patient", "patient", "patient", "patient"
            , "patient", "patient", "patient", "patient", "patient", "patient", "patient", "patient", "patient", "patient"
            , "patient", "patient", "patient", "patient", "patient", "patient", "patient", "patient", "patient", "patient"
            , "patient", "patient", "patient", "patient", "patient", "patient", "patient", "patient", "patient", "patient"
            , "patient", "patient", "patient", "patient", "patient", "patient", "patient", "patient", "patient", "patient"
            , "patient", "patient", "patient", "patient", "patient", "patient", "patient", "patient", "patient", "patient"
            , "patient", "patient", "patient", "patient", "patient", "patient", "patient", "patient", "patient", "patient"
            , "patient", "patient", "patient", "patient", "patient", "patient", "patient", "patient", "patient", "patient"
            , "patient", "patient", "patient", "patient", "patient", "patient", "patient", "patient", "patient", "patient"
    ));

    private static final ArrayList<String> addresses = new ArrayList<>(Arrays.asList(
            "Moscow, Luiz Street, 15", "Novo-Cheboksary, Broadway Street, 2"
            , "Rio De Janeiro, Universitetskaya, 1-1"
    ));

    public DataGeneration() throws FileNotFoundException {
        femaleNamesPool = new RandomPool("./src/data/NamesF.txt");
        maleNamesPool = new RandomPool("./src/data/NamesM.txt");
        surnamesPool = new RandomPool("./src/data/Surnames.txt");
        medFactsPool = new RandomPool("./src/data/MedicalFacts.txt");
        medNamesPool = new RandomPool("./src/data/Medicament.txt");
        nicknamePool = new RandomPool("./src/data/Nicknames.txt");
        randomInfoPool = new RandomPool("./src/data/RandomFacts.txt");
        supplyPool = new RandomPool("./src/data/Supplies.txt");
        depNamesPool = new RandomPool(departmentNames);
        roleNamesPool = new RandomPool(roleNames);
        addressPool = new RandomPool(addresses);
    }

    public static int nextInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public String getPassword() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";

        StringBuilder ans = new StringBuilder();
        int n = nextInt(8, 25);

        for (int i = 0; i < n; ++i) {
            ans.append(AlphaNumericString.charAt(nextInt(0, AlphaNumericString.length())));
        }
        return ans.toString();
    }

    public String getPhone() {
        StringBuilder result = new StringBuilder("+7");
        for (int i = 0; i < 10; ++i) {
            result.append(nextInt(0, 10));
        }
        assert result.length() <= 12;

        return result.toString();
    }

    public String getDepName() {
        return depNamesPool.getNext();
    }

    public String getGender(boolean isMale) {
        return isMale ? "M" : "F";
    }

    public String getRandFact() {
        return randomInfoPool.getNext();
    }

    public String getSupply() {
        return supplyPool.getNext();
    }

    public String getRole() {
        return roleNamesPool.getNext();
    }

    public String getNickname() {
        return nicknamePool.getNext();
    }

    public ArrayList<String> getNicksSubset(int amount){
        int begin = nextInt(0, nicknamePool.pool.size()-amount);
        return new ArrayList<String> (nicknamePool.pool.subList(begin, begin+amount));
    }

    public String getAddress() {
        return addressPool.getNext();
    }

    public String getFemaleName() {
        return femaleNamesPool.getNext();
    }

    public String getMedicalFact() {
        return medFactsPool.getNext();
    }

    public String getMedicament() {
        return medNamesPool.getNext();
    }

    public String getMaleName() {
        return maleNamesPool.getNext();
    }

    public String getSurname() {
        return surnamesPool.getNext();
    }

    public String getDateTime() {
        return (new Timestamp(ThreadLocalRandom.current().nextLong(942842538000L) + 631152000000L)).toString();
    }

    public String getDate() {
        Timestamp time = new Timestamp(ThreadLocalRandom.current().nextLong(942842538000L) + 631152000000L);
        return (new SimpleDateFormat("yyyy-MM-dd").format(new Date(time.getTime())));
    }

    public boolean getBool() {
        return ThreadLocalRandom.current().nextBoolean();
    }

}