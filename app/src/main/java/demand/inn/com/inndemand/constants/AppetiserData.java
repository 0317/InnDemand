package demand.inn.com.inndemand.constants;

import java.util.ArrayList;

/**
 * Created by akash
 */
public class AppetiserData {

    private String title, name, rupees, details, food;
    int userqty, productsaleprice;
    private static int count = 0;

    public AppetiserData(String title, String name, String details, String rupees, String food) {
        this.title = title;
        this.name = name;
        this.details = details;
        this.rupees = rupees;
        this.food = food;
    }

    public AppetiserData(String title, String name, String details, String rupees, String food, int productsaleprice, int userqty) {
        this.title = title;
        this.name = name;
        this.details = details;
        this.rupees = rupees;
        this.food = food;
        this.productsaleprice = productsaleprice;
        this.userqty = userqty;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRupees() {
        return rupees;
    }

    public void setRupees(String rupees) {
        this.rupees = rupees;
    }

    public int getCount() {
        return count++;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }
}
