package Car_Rental_System.Models;

public class User {
    private int id;
    private String name;
    private String email;
    private String passwordHash;
    private int age;
    private boolean isCorporate;
    private boolean isAdmin;

    // Constructor, Getter/Setter'lar
    public User() {}

    public User(String name, String email, String passwordHash, int age, boolean isCorporate, boolean isAdmin) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.age = age;
        this.isCorporate = isCorporate;
        this.isAdmin = isAdmin;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCorporate() {
        return isCorporate;
    }

    public void setCorporate(boolean corporate) {
        isCorporate = corporate;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

}