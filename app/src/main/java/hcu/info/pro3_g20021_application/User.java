package hcu.info.pro3_g20021_application;

public class User {
    private String name;
    private String studentId;
    private String idm;
    private int Balance;

    public User(String name, String studentId, String idm, int Balance) {
        this.name = name;
        this.studentId = studentId;
        this.idm = idm;
        this.Balance = Balance;
    }
    public String getName() { return name; }
    public String getStudentId() { return studentId; }
    public String getIdm() { return idm; }
    public int getBalance() { return Balance; }
    public void setBalance(int Balance) { this.Balance = Balance; }
}
