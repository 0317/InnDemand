package demand.inn.com.inndemand.model;

/**
 * Created by akash
 */

public class Translate {

    String title;
    public static String translate_url = "https://www.googleapis.com/language/translate/v2?";
    public static String translate_key = "key=";
    public static String translate_source = "&source=";
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
