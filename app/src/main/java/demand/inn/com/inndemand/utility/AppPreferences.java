package demand.inn.com.inndemand.utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by akash
 */
public class AppPreferences {

    public static final String SKILL_PREFS = "inn_demand_prefs";

    private SharedPreferences appSharedpref;
    private SharedPreferences.Editor prefEditor;

    private String customer_Id = "customer_Id",
            checkin_Id = "checkin_Id",
            restaurant_Id = "restaurant_Id",
            bar_Id = "bar_Id",
            user_gender = "user_gender",
            user_bday = "user_bday",
            user_lname = "user_lname",
            user_gname = "user_gname",
            user_gemail = "user_gemail",
            user_gpicture = "user_gpicture",
            google_gender = "google_gender",
            google_bday = "google_bday",
            google_location = "google_location",
            deviceToken = "device_token_pref",
            appVersion = "app_version_pref",
            checkout = "checkout",
            user_fbemail = "user_fbemail",
            user_fbname = "user_fbname",
            user_fbpic = "user_fbpic",
            fb_Token = "fb_Token",
            g_Token = "g_Token",
            hotel_id = "hotel_id",
            hotel_latitude = "hotel_latitude",
            hotel_longitude  ="hotel_longitude",
            itemName = "itemName",
            itemDesc = "itemDesc",
            price = "price",
            category = "category",
            restaurant_food = "restaurant_food",
            save_data = "save_data",
            total_cash = "total_cash",
            total_items = "total_items";

    //Check whether Hotel provides mentioned facilities/not
    private String bar = "bar",
                    spa = "spa",
                    restaurant = "restaurant",
                    barList = "barList";

    //Check timings for the services mentioned
    private String fm_bar = "fm_bar",
                    fm_spa = "fm_spa",
                    fm_restaurant = "fm_restaurant",
                    fm_service = "fm_service";

    //Check availability for the services mentioned
    private String room_service = "roomservice",
                    beverage = "beverage",
                    bed_tea = "bed_tea",
                    wake_up = "wake_up",
                    bell_boy = "bell_boy",
                    room_clean = "room_clean",
                    laundry = "laundry",
                    cab = "cab",
                    bathroom = "bathroom",
                    bath_towel = "bath_towel",
                    bath_toiletries = "bath_toiletries",
                    bath_maintenance = "bath_maintenance",
                    water = "water",
                    ice_bucket = "ice_bucket",
                    soda = "soda",
                    glass = "glass",
                    tea = "tea",
                    coffee = "coffee";

    //Location Saves
    private String currentLocation = "currentLocation",
                    destination = "destination";

    //Registration ID for GCM
    private String reg_ID = "reg_ID";

    private String is_task_completed = "is_task_completed",
            hotel_check = "hotel_check",
            is_In_Hotel = "is_In_Hotel",
            check_list = "check_list",
            facebook_logged_In = "facebook_logged_In",
            google_logged_In = "google_logged_IN";




    public AppPreferences(Context context) {
        this.appSharedpref = context.getSharedPreferences(SKILL_PREFS, Activity.MODE_PRIVATE);
        this.prefEditor = appSharedpref.edit();
    }

    public void singleCache(Context context){
        this.appSharedpref = context.getSharedPreferences(SKILL_PREFS, Activity.MODE_PRIVATE);
        this.prefEditor = appSharedpref.edit();
    }

    public void clearPref() {
        prefEditor.clear().commit();
    }

    public Boolean getHotel_check() {
        return appSharedpref.getBoolean(hotel_check, false);
    }

    public void setHotel_check(boolean _hotel_check) {
        prefEditor.putBoolean(hotel_check, _hotel_check).commit();
    }

    public String getReg_ID() {
        return appSharedpref.getString(reg_ID, "");
    }

    public void setReg_ID(String _reg_ID) {
        prefEditor.putString(reg_ID, _reg_ID).commit();
    }

    public String getFb_Token() {
        return appSharedpref.getString(fb_Token, "");
    }

    public void setFb_Token(String _fb_Token) {
        prefEditor.putString(fb_Token, _fb_Token).commit();
    }

    public String getCustomer_Id() {
        return appSharedpref.getString(customer_Id, "");
    }

    public void setCustomer_Id(String _customer_Id) {
        prefEditor.putString(customer_Id, _customer_Id).commit();
    }

    public String getCheckin_Id() {
        return appSharedpref.getString(checkin_Id, "");
    }

    public void setCheckin_Id(String _checkin_Id) {
        prefEditor.putString(checkin_Id, _checkin_Id).commit();
    }

    public String getRestaurant_Id() {
        return appSharedpref.getString(restaurant_Id, "");
    }

    public void setRestaurant_Id(String _restaurant_Id) {
        prefEditor.putString(restaurant_Id, _restaurant_Id).commit();
    }

    public String getBar_Id() {
        return appSharedpref.getString(bar_Id, "");
    }

    public void setBar_Id(String _bar_Id) {
        prefEditor.putString(bar_Id, _bar_Id).commit();
    }

    public String getG_Token() {
        return appSharedpref.getString(g_Token, "");
    }

    public void setG_Token(String _g_Token) {
        prefEditor.putString(g_Token, _g_Token).commit();
    }

    public String getUser_lname() {
        return appSharedpref.getString(user_lname, "");
    }

    public void setUser_lname(String _user_lname) {
        prefEditor.putString(user_lname, _user_lname).commit();
    }

    public String getUser_gender() {
        return appSharedpref.getString(user_gender, "");
    }

    public void setUser_gender(String _user_gender) {
        prefEditor.putString(user_gender, _user_gender).commit();
    }

    public String getUser_fbemail() {
        return appSharedpref.getString(user_fbemail, "");
    }

    public void setUser_fbemail(String _user_fbemail) {
        prefEditor.putString(user_fbemail, _user_fbemail).commit();
    }

    public String getUser_fbname() {
        return appSharedpref.getString(user_fbname, "");
    }

    public void setUser_fbname(String _user_fbname) {
        prefEditor.putString(user_fbname, _user_fbname).commit();
    }

    public String getUser_fbpic() {
        return appSharedpref.getString(user_fbpic, "");
    }

    public void setUser_fbpic(String _user_fbpic) {
        prefEditor.putString(user_fbpic, _user_fbpic).commit();
    }

    public boolean getIs_task_completed() {
        return appSharedpref.getBoolean(String.valueOf(is_task_completed), false);
    }

    public void setIs_task_completed(Boolean _is_task_completed) {
        prefEditor.putBoolean(String.valueOf(is_task_completed), _is_task_completed).commit();
    }

    public Boolean getIs_In_Hotel() {
        return appSharedpref.getBoolean(String.valueOf(is_In_Hotel), false);
    }

    public void setIs_In_Hotel(boolean _is_In_Hotel) {
        prefEditor.putBoolean(String.valueOf(is_In_Hotel), _is_In_Hotel).commit();
    }

    public Boolean getCheck_list() {
        return appSharedpref.getBoolean(check_list, false);
    }

    public void setCheck_list(boolean _check_list) {
        prefEditor.putBoolean(check_list, _check_list).commit();
    }

    public boolean getFacebook_logged_In() {
        return appSharedpref.getBoolean(String.valueOf(facebook_logged_In), false);
    }

    public void setFacebook_logged_In(Boolean _facebook_logged_In) {
        prefEditor.putBoolean(String.valueOf(facebook_logged_In), _facebook_logged_In).commit();
    }

    public boolean getGoogle_logged_In() {
        return appSharedpref.getBoolean(String.valueOf(google_logged_In), false);
    }

    public void setGoogle_logged_In(Boolean _google_logged_In) {
        prefEditor.putBoolean(String.valueOf(google_logged_In), _google_logged_In).commit();
    }

    public String getDeviceToken()
    {
        return appSharedpref.getString(deviceToken, "");
    }


    public void setDeviceToken(String _deviceToken)
    {
        prefEditor.putString(deviceToken, _deviceToken).commit();
    }

    public int getAppVersion()
    {
        return appSharedpref.getInt(appVersion, Integer.MIN_VALUE);
    }

    public void setAppVersion(int _appVersion)
    {
        prefEditor.putInt(appVersion, _appVersion).commit();
    }

    public String getCheckout() {
        return appSharedpref.getString(checkout, "");
    }

    public void setCheckout(String _checkout) {
        prefEditor.putString(checkout, _checkout).commit();
    }

    public String getUser_bday() {
        return appSharedpref.getString(user_bday, "");
    }

    public void setUser_bday(String _user_bday) {
        prefEditor.putString(user_bday, _user_bday).commit();
    }

    public String getUser_gname() {
        return appSharedpref.getString(user_gname, "");
    }

    public void setUser_gname(String _user_gname) {
        prefEditor.putString(user_gname, _user_gname).commit();
    }

    public String getUser_gemail() {
        return appSharedpref.getString(user_gemail, "");
    }

    public void setUser_gemail(String _user_gemail) {
        prefEditor.putString(user_gemail, _user_gemail).commit();
    }


    public String getUser_gpicture() {
        return appSharedpref.getString(user_gpicture, "");
    }

    public void setUser_gpicture(String _user_gpicture) {
        prefEditor.putString(user_gpicture, _user_gpicture).commit();
    }

    public String getHotel_id() {
        return appSharedpref.getString(hotel_id, "");
    }

    public void setHotel_id(String _hotel_id) {
        prefEditor.putString(hotel_id, _hotel_id).commit();
    }

    public int getGoogle_gender() {
        return appSharedpref.getInt(google_gender, 0);
    }

    public void setGoogle_gender(int _google_gender) {
        prefEditor.putInt(google_gender, _google_gender).commit();
    }

    public String getGoogle_bday() {
        return appSharedpref.getString(google_bday, "");
    }

    public void setGoogle_bday(String _google_bday) {
        prefEditor.putString(google_bday, _google_bday).commit();
    }

    public String getGoogle_location() {
        return appSharedpref.getString(google_location, "");
    }

    public void setGoogle_location(String _google_location) {
        prefEditor.putString(google_location, _google_location).commit();
    }

    public String getSave_data() {
        return appSharedpref.getString(save_data, "");
    }

    public void setSave_data(String _save_data) {
        prefEditor.putString(save_data, _save_data).commit();
    }

    public String getHotel_latitude() {
        return appSharedpref.getString(hotel_latitude, "");
    }

    public void setHotel_latitude(String _hotel_latitude) {
        prefEditor.putString(hotel_latitude, _hotel_latitude).commit();
    }

    public String getHotel_longitude() {
        return appSharedpref.getString(hotel_longitude, "");
    }

    public void setHotel_longitude(String _hotel_longitude) {
        prefEditor.putString(hotel_longitude, _hotel_longitude).commit();
    }

    public String getItemName() {
        return appSharedpref.getString(itemName, "");
    }

    public void setItemName(String _itemName) {
        prefEditor.putString(itemName, _itemName).commit();
    }

    public String getItemDesc() {
        return appSharedpref.getString(itemDesc, "");
    }

    public void setItemDesc(String _itemDesc) {
        prefEditor.putString(itemDesc, _itemDesc).commit();
    }

    public String getPrice() {
        return appSharedpref.getString(price, "");
    }

    public void setPrice(String _price) {
        prefEditor.putString(price, _price).commit();
    }

    public String getCategory() {
        return appSharedpref.getString(category, "");
    }

    public void setCategory(String _category) {
        prefEditor.putString(category, _category).commit();
    }

    public String getRestaurant_food() {
        return appSharedpref.getString(restaurant_food, "");
    }

    public void setRestaurant_food(String _restaurant_food) {
        prefEditor.putString(restaurant_food, _restaurant_food).commit();
    }


    /* ..................Location Data Details........................... */

    public String getCurrentLocation() {
        return appSharedpref.getString(currentLocation, "");
    }

    public void setCurrentLocation(String _currentLocation) {
        prefEditor.putString(currentLocation, _currentLocation).commit();
    }

    public String getDestination() {
        return appSharedpref.getString(destination, "");
    }

    public void setDestination(String _destination) {
        prefEditor.putString(destination, _destination).commit();
    }

    /* ..................Hotel Details........................... */

    public Boolean getBar() {
        return appSharedpref.getBoolean(bar, false);
    }

    public void setBar(boolean _bar) {
        prefEditor.putBoolean(bar, _bar).commit();
    }

    public Boolean getSpa() {
        return appSharedpref.getBoolean(spa, false);
    }

    public void setSpa(boolean _spa) {
        prefEditor.putBoolean(spa, _spa).commit();
    }

    public Boolean getRestaurant() {
        return appSharedpref.getBoolean(restaurant, false);
    }

    public void setRestaurant(boolean _restaurant) {
        prefEditor.putBoolean(restaurant, _restaurant).commit();


    }


     /* .................Timings Check Area....................... */

    public Boolean getFm_bar() {
        return appSharedpref.getBoolean(fm_bar, false);
    }

    public void setFm_bar(boolean _fm_bar) {
        prefEditor.putBoolean(fm_bar, _fm_bar).commit();
    }

    public Boolean getFm_spa() {
        return appSharedpref.getBoolean(fm_spa, false);
    }

    public void setFm_spa(boolean _fm_spa) {
        prefEditor.putBoolean(fm_spa, _fm_spa).commit();
    }

    public Boolean getFm_restaurant() {
        return appSharedpref.getBoolean(fm_restaurant, false);
    }

    public void setFm_restaurant(boolean _fm_restaurant) {
        prefEditor.putBoolean(fm_restaurant, _fm_restaurant).commit();
    }

    public Boolean getFm_service() {
        return appSharedpref.getBoolean(fm_service, false);
    }

    public void setFm_service(boolean _fm_service) {
        prefEditor.putBoolean(fm_service, _fm_service).commit();
    }

    /* .................Room Services Area....................... */

    public Boolean getRoom_service() {
        return appSharedpref.getBoolean(room_service, false);
    }

    public void setRoom_service(boolean _room_service) {
        prefEditor.putBoolean(room_service, _room_service).commit();
    }

    public Boolean getBeverage() {
        return appSharedpref.getBoolean(beverage, false);
    }

    public void setBeverage(boolean _beverage) {
        prefEditor.putBoolean(beverage, _beverage).commit();
    }

    public Boolean getBed_tea() {
        return appSharedpref.getBoolean(bed_tea, false);
    }

    public void setBed_tea(boolean _bed_tea) {
        prefEditor.putBoolean(bed_tea, _bed_tea).commit();
    }

    public Boolean getWake_up() {
        return appSharedpref.getBoolean(wake_up, false);
    }

    public void setWake_up(boolean _wake_up) {
        prefEditor.putBoolean(wake_up, _wake_up).commit();
    }

    public Boolean getBell_boy() {
        return appSharedpref.getBoolean(bell_boy, false);
    }

    public void setBell_boy(boolean _bell_boy) {
        prefEditor.putBoolean(bell_boy, _bell_boy).commit();
    }

    public Boolean getRoom_clean() {
        return appSharedpref.getBoolean(room_clean, false);
    }

    public void setRoom_clean(boolean _room_clean) {
        prefEditor.putBoolean(room_clean, _room_clean).commit();
    }

    public Boolean getLaundry() {
        return appSharedpref.getBoolean(laundry, false);
    }

    public void setLaundry(boolean _laundry) {
        prefEditor.putBoolean(laundry, _laundry).commit();
    }

    public Boolean getCab() {
        return appSharedpref.getBoolean(cab, false);
    }

    public void setCab(boolean _cab) {
        prefEditor.putBoolean(cab, _cab).commit();
    }

    public Boolean getBathroom() {
        return appSharedpref.getBoolean(bathroom, false);
    }

    public void setBathroom(boolean _bathroom) {
        prefEditor.putBoolean(bathroom, _bathroom).commit();
    }

    public Boolean getBath_towel() {
        return appSharedpref.getBoolean(bath_towel, false);
    }

    public void setBath_towel(boolean _bath_towel) {
        prefEditor.putBoolean(bath_towel, _bath_towel).commit();
    }

    public Boolean getBath_toiletries() {
        return appSharedpref.getBoolean(bath_toiletries, false);
    }

    public void setBath_toiletries(boolean _bath_toiletries) {
        prefEditor.putBoolean(bath_toiletries, _bath_toiletries).commit();
    }

    public Boolean getBath_maintenance() {
        return appSharedpref.getBoolean(bath_maintenance, false);
    }

    public void setBath_maintenance(boolean _bath_maintenance) {
        prefEditor.putBoolean(bath_maintenance, _bath_maintenance).commit();
    }

    public Boolean getWater() {
        return appSharedpref.getBoolean(water, false);
    }

    public void setWater(boolean _water) {
        prefEditor.putBoolean(water, _water).commit();
    }

    public Boolean getIce_bucket() {
        return appSharedpref.getBoolean(ice_bucket, false);
    }

    public void setIce_bucket(boolean _ice_bucket) {
        prefEditor.putBoolean(ice_bucket, _ice_bucket).commit();
    }

    public Boolean getSoda() {
        return appSharedpref.getBoolean(soda, false);
    }

    public void setSoda(boolean _soda) {
        prefEditor.putBoolean(soda, _soda).commit();
    }

    public Boolean getGlass() {
        return appSharedpref.getBoolean(glass, false);
    }

    public void setGlass(boolean _glass) {
        prefEditor.putBoolean(glass, _glass).commit();
    }

    public Boolean getTea() {
        return appSharedpref.getBoolean(tea, false);
    }

    public void setTea(boolean _tea) {
        prefEditor.putBoolean(tea, _tea).commit();
    }

    public Boolean getCoffee() {
        return appSharedpref.getBoolean(coffee, false);
    }

    public void setCoffee(boolean _coffee) {
        prefEditor.putBoolean(coffee, _coffee).commit();
    }

    public Boolean getBarList() {
        return appSharedpref.getBoolean(barList, false);
    }

    public void setBarList(boolean _barList) {
        prefEditor.putBoolean(barList, _barList).commit();
    }


//    Total cash for Restaurant/ Bar/ Spa

    public String getTotal_cash() {
        return appSharedpref.getString(total_cash, "");
    }

    public String getTotal_items() {
        return appSharedpref.getString(total_items, "");
    }

    public void setTotal_items(String _total_items) {
        prefEditor.putString(total_items, _total_items).commit();
    }

    public void setTotal_cash(String _total_cash) {
        prefEditor.putString(total_cash, _total_cash).commit();
    }
}