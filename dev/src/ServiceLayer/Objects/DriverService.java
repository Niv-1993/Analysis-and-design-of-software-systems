package ServiceLayer.Objects;

public class DriverService {

    private int id;
    private String name;
    private int license;

    public DriverService(int id, String name, int license){
        this.id=id;
        this.name=name;
        this.license=license;
    }

    public void setName(String name) { this.name = name; }
    public void setId(int id) { this.id = id; }
    public void setLicense(int license) { this.license = license; }

    public String getName() { return name; }
    public int getId() { return id; }
    public int getLicense() { return license; }
}
