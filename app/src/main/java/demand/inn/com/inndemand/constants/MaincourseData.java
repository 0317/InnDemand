package demand.inn.com.inndemand.constants;

/**
 * Created by akash
 */
public class MaincourseData {

    private String title, name, rupees;

    public MaincourseData() {
    }

    public MaincourseData(String title, String name, String rupees) {
        this.title = title;
        this.name = name;
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
}
