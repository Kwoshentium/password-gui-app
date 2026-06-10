import java.awt.*;
import javax.swing.*;

public class GUI {
    private final AccountManager accountManager;
    JFrame frame;
    JTextField idField;
    JPasswordField passwordField;

    public GUI() {
        accountManager = new AccountManager();

        buildGui();
    }

    private void buildGui() {
        frame = new JFrame("Student Database System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        createComponents();
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException |
         InstantiationException |
         IllegalAccessException |
         UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        frame.setVisible(true);
    }

    private void createComponents() {
        JLabel title = new JLabel("Student Account Database Saving & Login System");
        title.setFont(new Font("Sans Serif", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel centerPanel = new JPanel(new GridBagLayout());

        JPanel loginPanel = textPanel();
        loginPanel.setFont(new Font("Arial", Font.BOLD, 15));
        loginPanel.setPreferredSize(new Dimension(200, 140));

        JPanel mainPanel =  new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.add(title);
        mainPanel.add(loginPanel);

        centerPanel.add(mainPanel);

        frame.add(centerPanel);
    }

    private JPanel textPanel() {
        JPanel field = new JPanel();
        field.setLayout(new BoxLayout(field, BoxLayout.Y_AXIS));

        idField = new JTextField();
        passwordField = new JPasswordField();

        JLabel student = new JLabel("Student ID");
        student.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel password = new JLabel("Password");
        password.setAlignmentX(Component.CENTER_ALIGNMENT);

        idField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = buttons();

        field.add(student);
        field.add(idField);
        field.add(password);
        field.add(passwordField);
        field.add(Box.createVerticalStrut(20));
        field.add(buttonPanel);

        return field;
    }

    private JPanel buttons() {
        JPanel buttonPanel = new JPanel();

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.addActionListener((actionEvent) -> {
            String studentId = idField.getText();
            char[] password = passwordField.getPassword();
            String pass = new String(password);

            if (studentId.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Field is empty.");
                return;
            }

            AccountManager.loginStates result = accountManager.login(studentId, pass);
            String message = "";

            switch (result) {
                case AccountManager.loginStates.INCORRECT_PASSWORD:
                    message = "Incorrect password.";
                    idField.setText("");
                    passwordField.setText("");
                    break;
                case AccountManager.loginStates.USER_DOESNT_EXIST:
                    message = "User does not exist.";
                    idField.setText("");
                    passwordField.setText("");
                    break;
                case AccountManager.loginStates.SUCCESS:
                    message = "Login Success!";
                    break;
                default:
                    throw new AssertionError();
            }

            JOptionPane.showMessageDialog(null, message);
        });

        registerButton.addActionListener((actionEvent) -> {
            String studentId = idField.getText();
            char[] password = passwordField.getPassword();
            String pass = new String(password);

            if (studentId.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Field is empty.");
                return;
            }

            boolean result = accountManager.register(studentId, pass);
            String message = "";

            if (result) {
                message = "Registration successful."; 
            } else {
                message = "Registration failed: Account already exists.";
                idField.setText("");
                passwordField.setText("");
            }

            JOptionPane.showMessageDialog(null, message);
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        return buttonPanel;
    }
}