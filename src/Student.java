public class Student {
    private String studentId;
    private String password;

    public Student(String studentId, String password) {
        this.studentId = studentId;
        this.password = password;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudentId() { return this.studentId; }
    public String getPassword() { return this.password; }

    public String toTxtLine() {
        return studentId + "," + password;
    }
}
