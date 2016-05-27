package demand.inn.com.inndemand.constants;

/**
 * Created by Akash
 */
public class DessertData {

    private String title, name, rupees, details;

    public DessertData() {
    }

    public DessertData(String title, String name, String details, String rupees) {
        this.title = title;
        this.name = name;
        this.details = details;
        this.rupees = rupees;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRupees() {
        return rupees;
    }

    public void setRupees(String rupees) {
        this.rupees = rupees;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
