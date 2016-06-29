package demand.inn.com.inndemand.constants;

/**
 * Created by akash
 */
public class FeedbackData {

    String title, desc, about;

    public FeedbackData(String title, String desc){
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


    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
