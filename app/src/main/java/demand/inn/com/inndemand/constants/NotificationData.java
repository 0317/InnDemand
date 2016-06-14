package demand.inn.com.inndemand.constants;

/**
 * Created by akash
 */

public class NotificationData {

    private String title, details;

    public NotificationData() {
    }

    public NotificationData(String title, String details) {
        this.title = title;
        this.details = details;

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
}
