package demand.inn.com.inndemand.constants;

import android.widget.TextView;

/**
 * Created by akash
 */
public class FeedbackData {

    String title, desc, rate;

    public FeedbackData(String title, String desc, String rate){
        this.title = title;
        this.desc = desc;
        this.rate = rate;
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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
