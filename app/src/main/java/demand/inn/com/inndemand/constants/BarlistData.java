package demand.inn.com.inndemand.constants;

/**
 * Created by akash
 */
public class BarlistData {

    String id, title, status;

    public BarlistData() {
    }

    public BarlistData(String title,String status, String id) {
        this.title = title;
        this.status = status;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
