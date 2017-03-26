package demand.inn.com.inndemand.model;

import android.util.Log;

/**
 * Created by akash
 */

public class AppetiserData {

//    itemName = object.getString("name");
//    Log.d("API", "API na" + itemName);
//    itemDesc = object.getString("description");
//    category = object.getString("category");
//    Log.d("API", "API Ca" + category);
//    food = object.getString("food");
//
//    subCategory = object.getString("subcategory");
//    amount = object.getString("price");

    String id;
    String title;
    String name;
    String description;
    String category;
    String food;
    String subcategory;
    String price;
    String rating;

    int userqty, productsaleprice;

    public AppetiserData(){

    }

    public AppetiserData(String name, String description, String category, String price,
                         String categorys){
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.subcategory = categorys;
    }

    public AppetiserData(int i){

    }

    public AppetiserData(String title, String description, String rupees, String subcategory,
                              String food, String rating) {
        this.title = title;
        this.description = description;
        this.price = rupees;
        this.subcategory = subcategory;
        this.food = food;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getUserqty() {
        return userqty;
    }

    public void setUserqty(int userqty) {
        this.userqty = userqty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
