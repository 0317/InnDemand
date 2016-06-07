package demand.inn.com.inndemand.constants;

/**
 * Created by akash
 */
public class HotelData {

    String title, desc;

    public HotelData() {
    }

    public HotelData(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
