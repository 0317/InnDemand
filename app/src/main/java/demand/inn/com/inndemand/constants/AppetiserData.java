package demand.inn.com.inndemand.constants;

/**
 * Created by akash
 */
public class AppetiserData {

    private String title, name, rupees;
    private int count;

    public AppetiserData() {
    }

    public AppetiserData(String title, String name, String rupees) {
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
