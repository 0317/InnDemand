package demand.inn.com.inndemand.constants;

/**
 * Created by akash
 */
public class FragmentData {

    String title;
    int id;

    public FragmentData(){

    }

    public FragmentData(String title){
        this.title = title;
    }

    public FragmentData(String title, int id){
        this.title = title;
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
