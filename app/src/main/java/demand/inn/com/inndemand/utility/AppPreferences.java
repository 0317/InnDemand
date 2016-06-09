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
            user_gender = "user_gender",
            user_phone = "user_phone",
            user_picture = "user_picture",
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
            hotel_Name = "hotel_Name",
            checkout = "checkout",
            user_fbname = "user_fbname",
            user_fbemail = "user_fbemail",
            fb_Token = "fb_Token",
            g_Token = "g_Token",
            hotel_id = "hotel_id",
            save_data = "save_data";

    private String is_task_completed = "is_task_completed",
            facebook_logged_In = "facebook_logged_In",
            google_logged_In = "google_logged_IN";




    public AppPreferences(Context context) {
        this.appSharedpref = context.getSharedPreferences(SKILL_PREFS, Activity.MODE_PRIVATE);
        this.prefEditor = appSharedpref.edit();
    }

    public void clearPref() {
        prefEditor.clear().commit();
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

    public String getUser_fbname() {
        return appSharedpref.getString(user_fbname, "");
    }

    public void setUser_fbname(String _user_fbname) {
        prefEditor.putString(user_fbname, _user_fbname).commit();
    }

    public String getUser_fbemail() {
        return appSharedpref.getString(user_fbemail, "");
    }

    public void setUser_fbemail(String _user_fbemail) {
        prefEditor.putString(user_fbemail, _user_fbemail).commit();
    }

    public boolean getIs_task_completed() {
        return appSharedpref.getBoolean(String.valueOf(is_task_completed), false);
    }

    public void setIs_task_completed(Boolean _is_task_completed) {
        prefEditor.putBoolean(String.valueOf(is_task_completed), _is_task_completed).commit();
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

    public int getUser_phone() {
        return appSharedpref.getInt(user_phone, Integer.MIN_VALUE);
    }

    public void setUser_phone(int _user_phone) {
        prefEditor.putInt(user_phone, _user_phone).commit();
    }

    public String getCheckout() {
        return appSharedpref.getString(checkout, "");
    }

    public void setCheckout(String _checkout) {
        prefEditor.putString(checkout, _checkout).commit();
    }

    public String getHotel_Name() {
        return appSharedpref.getString(hotel_Name, "");
    }

    public void setHotel_Name(String _hotel_Name) {
        prefEditor.putString(hotel_Name, _hotel_Name).commit();
    }

    public String getUser_picture() {
        return appSharedpref.getString(user_picture, "");
    }

    public void setUser_picture(String _user_picture) {
        prefEditor.putString(user_picture, _user_picture).commit();
    }

    public String getUser_bday() {
        return appSharedpref.getString(user_bday, "");
    }

    public void setUser_bday(String _user_bday) {
        prefEditor.putString(user_bday, _user_bday).commit();
    }

    public static String getSkillPrefs() {
        return SKILL_PREFS;
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

    public String getGoogle_gender() {
        return appSharedpref.getString(google_gender, "");
    }

    public void setGoogle_gender(String _google_gender) {
        prefEditor.putString(google_gender, _google_gender).commit();
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
}