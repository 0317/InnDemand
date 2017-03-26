package demand.inn.com.inndemand.constants;

/**
 * Created by akash
 */
public class HotelData {

    String title, desc, screen_key;

    public HotelData() {
    }

    public HotelData(String title, String desc, String screen_key) {
        this.title = title;
        this.desc = desc;
        this.screen_key = screen_key;
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

    public String getScreen_key() {
        return screen_key;
    }

    public void setScreen_key(String screen_key) {
        this.screen_key = screen_key;
    }
}
