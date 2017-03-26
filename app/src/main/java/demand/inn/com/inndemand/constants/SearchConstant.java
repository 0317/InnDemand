package demand.inn.com.inndemand.constants;

/**
 * Created by akash
 */
public class SearchConstant {

    String title;
    String name;
    String desc;
    String amount;
    String rating;

    public SearchConstant(){

    }

    public SearchConstant(String title, String name, String desc, String amount, String rating){
        this.title = title;
        this.name = name;
        this.desc = desc;
        this.amount = amount;
        this.rating = rating;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
