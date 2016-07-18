package demand.inn.com.inndemand.constants;

/**
 * Created by akash
 */
public class CartData {

    private String name, desc, rupees;
    String price;
    private int count;

    int userqty, productsaleprice;

    public CartData() {
    }

    public CartData(String name, String desc, String rupees) {
        this.name = name;
        this.desc = desc;
        this.rupees = rupees;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getUserqty() {
        return userqty;
    }

    public void setUserqty(int userqty) {
        this.userqty = userqty;
    }

    public int getProductsaleprice() {
        return productsaleprice;
    }

    public void setProductsaleprice(int productsaleprice) {
        this.productsaleprice = productsaleprice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRupees() {
        return rupees;
    }

    public void setRupees(String rupees) {
        this.rupees = rupees;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
