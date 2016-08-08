package demand.inn.com.inndemand.model;

/**
 * Created by akash
 */

public class Translate {

    String title;
    public static String translate_url = "https://www.googleapis.com/language/translate/v2?" +
            "key=AIzaSyAK9Vu9g2vv4jsT0aljz5DFHiTqS9IKsBk&source=en";
    public static String translate_target = "&target=";
    public static String translate_value = "&q=";

    public Translate(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
