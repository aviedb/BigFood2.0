package men.ngopi.aviedb.android_java_md_boilerplate;

public class User {
    private int id;
    private String name;
    private String email;
    private Company company;

    public User(int id, String name, String email, Company company) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.company = company;
    }

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Company getCompany() {
        return company;
    }
}
