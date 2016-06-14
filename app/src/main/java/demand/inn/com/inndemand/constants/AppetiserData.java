package demand.inn.com.inndemand.constants;

import java.util.ArrayList;

/**
 * Created by akash
 */
public class AppetiserData {

    private String title, name, rupees, details;
    private static int count = 0;

    public AppetiserData() {
    }

    public AppetiserData(String title, String name, String details, String rupees) {
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

    public int getCount() {
        return count++;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

  /*  public static ArrayList<AppetiserData> createList(int counts) {
        ArrayList<AppetiserData> counter = new ArrayList<AppetiserData>();

        for (int i = 1; i <= counts; i++) {
            counter.add(new AppetiserData("", String.valueOf(count), String.valueOf(i <= counts/ 2) , ""));
        }

        return counter;
    }*/
}
