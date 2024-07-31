package hcu.info.pro3_g20021_application;

public class User {
    private String name;
    private String studentId;
    private String idm;
    private int prepaidBalance;

    public User(String name, String studentId, String idm, int prepaidBalance) {
        this.name = name;
        this.studentId = studentId;
        this.idm = idm;
        this.prepaidBalance = prepaidBalance;
    }
    public String getName() { return name; }
    public String getStudentId() { return studentId; }
    public String getIdm() { return idm; }
    public int getPrepaidBalance() { return prepaidBalance; }
    public void setPrepaidBalance(int prepaidBalance) { this.prepaidBalance = prepaidBalance; }
}
