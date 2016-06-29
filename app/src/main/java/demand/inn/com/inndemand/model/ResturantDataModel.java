package demand.inn.com.inndemand.model;

import android.util.Log;

/**
 * Created by akash on 26/6/16.
 */
public class ResturantDataModel {

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
    String name;
    String description;
    String category;
    String food;
    String subcategory;
    String price;

    int userqty, productsaleprice;

    public ResturantDataModel(){

    }

    public ResturantDataModel(int i){

    }

    public ResturantDataModel(String title, String name, String details, String rupees, String food) {
        this.name = title;
        this.name = name;
        this.description = details;
        this.price = rupees;
        this.food = food;
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
