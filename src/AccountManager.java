import java.io.*;
import java.util.ArrayList;

public class AccountManager {
    private final String FILENAME = "students.txt";
    private ArrayList<Student> students;

    public enum loginStates {
        USER_DOESNT_EXIST,
        INCORRECT_PASSWORD,
        SUCCESS
    }

    public AccountManager() {
        students = new ArrayList<>();
    }

    public void loadAccounts() {
        File file = new File(FILENAME);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) { e.printStackTrace(); }
        }

        try {
            try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
                String line = br.readLine();
                
                while (line != null) {
                    String[] parts = line.split(",");
                    
                    String studentID = parts[0];
                    String password = parts[1];
                    
                    Student student = new Student(studentID, password);
                    students.add(student);

                    line = br.readLine();
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void saveAccounts() {
        try (FileWriter fw = new FileWriter(FILENAME); BufferedWriter bw = new BufferedWriter(fw)) {
            
            for (Student sd : students) {
                bw.write(sd.getStudentId() + "," + sd.getPassword());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean register(String studentId, String password) {
        if (accountExists(studentId)) {
            return false;
        }

        Student newStudent = new Student(studentId, password);

        students.add(newStudent);
        saveAccounts();

        return true;
    }

    public loginStates login(String studentId, String password) {
        if (!accountExists(studentId)) {
            return loginStates.USER_DOESNT_EXIST;
        }
        
        for (Student sd : students) {
            if (sd.getStudentId().equals(studentId)) {
                if (sd.getPassword().equals(password)) {
                    return loginStates.SUCCESS;
                }
            }
            
        }
        return loginStates.INCORRECT_PASSWORD;
    }

    public boolean accountExists(String studentId) {
        for (Student sd : students) {
            if (sd.getStudentId().equals(studentId)) {
                return true;
            }
        }
        return false;
    }
}
