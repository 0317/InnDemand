package demand.inn.com.inndemand.constants;

/**
 * Created by akash
 */

public class NotificationData {

    private String title, details, type, service, place;

    public NotificationData() {
    }

    public NotificationData(String title, String details, String type, String service, String place) {
        this.title = title;
        this.details = details;
        this.type = type;
        this.service = service;
        this.place = place;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
