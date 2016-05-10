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

    private String user_fname = "user_fname",
            user_gender = "user_gender",
            user_email = "user_email",
            user_phone = "user_phone",
            user_lname = "user_lname",
            deviceToken = "device_token_pref",
            appVersion = "app_version_pref",
            hotel_Name = "hotel_Name",
            checkout = "checkout";

    private String is_task_completed = "is_task_completed";



    public AppPreferences(Context context) {
        this.appSharedpref = context.getSharedPreferences(SKILL_PREFS, Activity.MODE_PRIVATE);
        this.prefEditor = appSharedpref.edit();
    }

    public void clearPref() {
        prefEditor.clear().commit();
    }



    public String getUser_lname() {
        return appSharedpref.getString(user_lname, "");
    }

    public void setUser_lname(String _user_lname) {
        prefEditor.putString(user_lname, _user_lname).commit();
    }

    public String getUser_email() {
        return appSharedpref.getString(user_email, "");
    }

    public void setUser_email(String _user_email) {
        prefEditor.putString(user_email, _user_email).commit();
    }

    public String getUser_gender() {
        return appSharedpref.getString(user_gender, "");
    }

    public void setUser_gender(String _user_gender) {
        prefEditor.putString(user_gender, _user_gender).commit();
    }

    public String getUser_fname() {
        return appSharedpref.getString(user_fname, "");
    }

    public void setUser_fname(String _user_fname) {
        prefEditor.putString(user_fname, _user_fname).commit();
    }

    public boolean getIs_task_completed() {
        return appSharedpref.getBoolean(String.valueOf(is_task_completed), false);
    }

    public void setIs_task_completed(Boolean _is_task_completed) {
        prefEditor.putBoolean(String.valueOf(is_task_completed), _is_task_completed).commit();
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
}