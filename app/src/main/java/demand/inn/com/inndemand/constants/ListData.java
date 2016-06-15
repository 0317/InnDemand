package demand.inn.com.inndemand.constants;

/**
 * Created by akash
 */
public class ListData {

    String title, status;

    public ListData() {
    }

    public ListData(String title,String status) {
        this.title = title;
        this.status = status;
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
