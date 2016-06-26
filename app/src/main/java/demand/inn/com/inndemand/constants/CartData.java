package demand.inn.com.inndemand.constants;

/**
 * Created by akash
 */
public class CartData {

    private String name, desc, rupees;
    private int count;

    public CartData() {
    }

    public CartData(String name, String desc, String rupees) {
        this.name = name;
        this.desc = desc;
        this.rupees = rupees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
