package demand.inn.com.inndemand.constants;

/**
 * Created by akash
 */

public class NotificationData {

    private String title, details, buttonText;

    public NotificationData() {
    }

    public NotificationData(String title, String details, String text) {
        this.title = title;
        this.details = details;
        this.buttonText = text;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }
}
