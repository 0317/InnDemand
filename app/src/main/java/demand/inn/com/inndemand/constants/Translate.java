package demand.inn.com.inndemand.constants;

/**
 * Created by akash
 */

public class Translate {

    String id;
    String name;
    String type;

    public Translate(){

    }

    public Translate(String name, String type){
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
