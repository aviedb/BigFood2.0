package men.ngopi.aviedb.android_java_md_boilerplate;

public class Company {
    private String name;
    private String catchPrase;
    private String bs;

    public Company(String name, String catchPrase, String bs) {
        this.name = name;
        this.catchPrase = catchPrase;
        this.bs = bs;
    }

    public String getName() {
        return name;
    }

    public String getCatchPrase() {
        return catchPrase;
    }

    public String getBs() {
        return bs;
    }
}
