import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountManager {
    private Connection connection;

    private static final String URL = "jdbc:mariadb://localhost:3306/student_system";
    String user;
    String password;

    public enum loginStates {
        USER_DOESNT_EXIST,
        INCORRECT_PASSWORD,
        SUCCESS
    }
    public AccountManager() {
        loadEnv();
    }

    // Load Environment Variables and assign to DB_USER and DB_PASSWORD
    private void loadEnv() {
        try (BufferedReader br = new BufferedReader(new FileReader(".env"))) {
            String line = br.readLine();
            while (line != null) {
                String[] parts = line.split("=");
                System.out.println(parts[0] + "=" + parts[1]);
                if (parts.length == 2) {
                    System.setProperty(parts[0], parts[1]);
                }
                line = br.readLine();
            }
            user = System.getProperty("DB_USER");
            password = System.getProperty("DB_PASSWORD");
        } catch (Exception e) {
            e.printStackTrace();
        }

        createConnection(user, password);
    }

    private void createConnection(String user, String password) {
        try {
            connection = DriverManager.getConnection(
                URL,
                user,
                password
            );

            System.out.println("Connected!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean register(String studentId, String password) {
        String checkSql = "SELECT password FROM students WHERE student_id = ?";
        String insertSql = "INSERT INTO students (student_id, password) VALUES(?, ?)";

        
        try {
            PreparedStatement checkStmt = connection.prepareStatement(checkSql);
            checkStmt.setString(1, studentId);

            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                return false;
            }

            PreparedStatement insertStmt = connection.prepareStatement(insertSql);
            insertStmt.setString(1, studentId);
            insertStmt.setString(2, password);

            int rowsInserted = insertStmt.executeUpdate();

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public loginStates login(String studentId, String password) {
        String sql = "SELECT password FROM students WHERE student_id = ?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                return loginStates.USER_DOESNT_EXIST;
                }

                String dbPassword = rs.getString("password");

                if (dbPassword.equals(password)) {
                    return loginStates.SUCCESS;
                } else {
                    return loginStates.INCORRECT_PASSWORD;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loginStates.INCORRECT_PASSWORD;
    }
}
