package demand.inn.com.inndemand.constants;

/**
 * Created by akash on 28/6/16.
 */
public class OrderData {

    String title, desc, price, day;

    public OrderData(){

    }

    public OrderData(String title, String desc, String price, String day){
        this.title = title;
        this.desc = desc;
        this.price = price;
        this.day = day;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
