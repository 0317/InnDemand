package demand.inn.com.inndemand.model;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spanned;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;
import java.util.regex.Pattern;

import demand.inn.com.inndemand.utils.PicassoEx;

//9650275007;	

/**
 * All common methods are defined here as a public static.
 *
 */

public class Utils  {

    private static final int PERMISSION_LOCATION_REQUEST_CODE = 100;

    public static String destinationString = "";

    private static HashMap<String, String> mapLoc = new HashMap<String, String>();

    /**
     * return base url remove '/' char from last of the url
     *
     * @param url
     * @return
     */
    public static String getBaseUrl(String url) {
        String baseUrl = url.substring(0, url.length());

        return baseUrl;
        // return Constants.BASE_URL;
    }

    /**
     * Check particular node is array.
     *
     * @param jsonObject
     * @return true if particular node is Array.
     */
    public static boolean isJsonArray(JSONObject jsonObject, String key) {

        if (!jsonObject.isNull(key)) {

            try {
                jsonObject.getJSONArray(key);
                return true;
            } catch (JSONException e) {
                return false;
            }

        }
        return false;
    }

    /**
     * Check particular node is object.
     *
     * @param jsonObject
     * @return true if particular node is Object.
     */
    public static boolean isJsonObject(JSONObject jsonObject, String key) {

//        if (!jsonObject.isNull(key)) {
//
//            try {
//                jsonObject.getJSONObject(key);
//                return true;
//            } catch (JSONException e) {
//                return false;
//            }
//
//        }
        return false;
    }

    /**
     * Check particular node is having particular key.
     *
     * @param jsonObject
     * @return true if particular node is having key.
     */
    public static boolean isJsonKeyAvailable(JSONObject jsonObject, String key) {

        return jsonObject.has(key);

    }


    /**
     * Check particular node is having particular key.
     *
     * @param data
     * @return String if particular node is having key.
     */
    public static String computeHash(ArrayList<String> data) {
        StringBuffer stringBuffer = new StringBuffer();
        if (data != null && data.size() > 0) {
            for (String str : data) {
                if (str != null && !(str.equals("") || str.equals("blank"))) {
                    stringBuffer.append(str);
                }
                stringBuffer.append("|");
            }
        }
        if (stringBuffer.length() > 0) {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        System.out.println(stringBuffer.toString());
        return computeSHAHash(stringBuffer.toString());

    }

    public static String computeSHAHash(String message) {
        MessageDigest md = null;
        String SHAHash = "";
        try {
            md = MessageDigest.getInstance("SHA-512");

            md.update(message.getBytes());
            byte[] mb = md.digest();
            String out = "";
            for (int i = 0; i < mb.length; i++) {
                byte temp = mb[i];
                String s = Integer.toHexString(new Byte(temp));
                while (s.length() < 2) {
                    s = "0" + s;
                }
                s = s.substring(s.length() - 2);
                out += s;
            }
            SHAHash = out;
        } catch (Exception e) {

        }
        return SHAHash;
    }


    /**
     * Display Toast message.
     *
     * @param activity
     * @param msgId
     */
    public static void showToast(final Activity activity, final int msgId) {
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(activity, activity.getString(msgId),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void displayToast(final Activity activity, String message) {
        if (activity != null)
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

    /**
     * convert dp into pixel
     *
     * @param context
     * @param dpValue value in dp
     * @return value in pixel
     */
    public static int convertToPx(Context context, int dpValue) {

        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * Check user logged in or not.
     *
     * @param context
     * @return true if user is logged in.
     */
    public static boolean isLoggedIn(Context context) {
        boolean flag = false;
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (mPrefs.contains("login")) {
            flag = mPrefs.getBoolean("login", false);

        }
        return flag;
    }

    /**
     * @param text
     * @return text with underline
     */
    public static Spanned getUnderLine(String text) {
        return Html.fromHtml("<u>" + text + "</u>");
    }

    public static void showAlert(Context context, String title, String msg) {
        Builder builder = new Builder(context);
        builder.setPositiveButton("OK", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.setMessage(msg);
        alertDialog.setTitle(title);
        alertDialog.show();

    }

    /**
     * Check Internet connection is available or not.
     *
     * @param context is the {@link Context} of the {@link Activity}.
     * @return <b>true</b> is Internet connection is available.
     */
    public static boolean isInternetAvailable(Context context) {
        boolean isInternetAvailable = false;

        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager
                    .getActiveNetworkInfo();

            if (networkInfo != null && (networkInfo.isConnected())) {
                isInternetAvailable = true;
            }
        } catch (Exception exception) {
            // Do Nothing
        }

        return isInternetAvailable;
    }

	/*public static HashMap<String, String> getDefaultHeader(
            Request request) {

		HashMap<String, String> header = new HashMap<String, String>();
		header.put("X-ROCKET-MOBAPI-PLATFORM","application/android.rocket.SHOP_MOBILEAPI_STAGING-v1.0+json");
		header.put("X-ROCKET-MOBAPI-TOKEN", "b5467071c82d1e0d88a46f6e057dbb88");
		header.put("X-ROCKET-MOBAPI-VERSION", "1.0");
		header.put("AUTO-LOGIN-TOKEN", request.getApiToken());
        //header.put("X-USER-DEVICE-TYPE", "mobile");

		*//*
         * header.put("Username", "rocket"); header.put("Password",
		 * "LetsRockJabong2012");
		 *//*

		// if (MainActivity.sessionId != null) {
		// header.put("session[id]", MainActivity.sessionId);
		// }
		return header;
	}*/

    /**
     * This method append User Id and password in URL
     *
     * @return urlWithUserIdPwd
     */
    /*
     * public static String getURLWithUidPwd(String url,boolean isRequired) {
	 * 
	 * String urlWithUidPwd = ""; if(!isRequired) return url; if(
	 * url.indexOf("http://") == 0){ url = url.replaceAll("http://", "");
	 * urlWithUidPwd =
	 * urlWithUidPwd.concat("http://").concat(Constants.UID_PWD).concat(url);
	 * return urlWithUidPwd; } else if( url.indexOf("https://") == 0){ url =
	 * url.replaceAll("https://", ""); urlWithUidPwd =
	 * urlWithUidPwd.concat("https://").concat(Constants.UID_PWD).concat(url);
	 * return urlWithUidPwd; }
	 * 
	 * return url;
	 * 
	 * }
	 */
    public static void hideKeyboard(Context context) {
        try {
            InputMethodManager imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm != null) {
                imm.hideSoftInputFromWindow(((Activity) context)
                        .getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String removeDecimalIfAnyFromPrice(String price) {
        price = price.split("\\.")[0];
        return price;
    }

    public static String convertStringtoTwoDecimalPlaces(String price) {
        return String.format("%.2f", Float.parseFloat(price));
    }

    public static boolean checkEmailValidation(String email, String pattern) {
        Pattern PATTERN = Pattern.compile(pattern);
        return PATTERN.matcher(email).matches();
    }

    public static boolean checkMobileValidation(String email, String pattern) {
        Pattern PATTERN = Pattern.compile(pattern);
        return PATTERN.matcher(email).matches();
    }

    public static boolean checkJCValidation(String jabongCredits) {
        String pattern = "^\\d+(\\.\\d{1,2})?$";
        Pattern PATTERN = Pattern.compile(pattern);
        return PATTERN.matcher(jabongCredits).matches();
    }

    public static ArrayList<String> getJsonKeysFromObject(JSONObject jsonObj) {
        ArrayList<String> keys = new ArrayList<String>();
        JSONArray it = jsonObj.names();
        for (int i = 0; i < it.length(); i++) {
            try {
                keys.add(i, it.getString(i));
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return keys;

    }

    /**
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        View listItem;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            listItem = listAdapter.getView(i, null, listView);
            listItem.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.MATCH_PARENT));
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight() + Utils.convertToPx(listView.getContext(), 8);
        }

        LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void setListViewHeightBasedOnChildrenNew(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        View listItem;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            listItem = listAdapter.getView(i, null, listView);
            listItem.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.MATCH_PARENT));
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount())) + listView.getPaddingTop() + listView.getPaddingBottom();
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    /**
     * convert dp into pixel
     *
     * @param context
     * @param dpValue value in dp
     * @return value in pixel
     */
    public static float convertToPx(Context context, float dpValue) {

        final float scale = context.getResources().getDisplayMetrics().density;

        return (float) (dpValue * scale + 0.5f);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static void setGridViewHeightBasedOnChildren(GridView gridView, int moreHeight, int noOfCol) {


        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null || listAdapter.getCount() == 0) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        View gridItem = listAdapter.getView(0, null, gridView);
        gridItem.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.MATCH_PARENT));
        gridItem.measure(0, 0);
        totalHeight += gridItem.getMeasuredHeight() + Utils.convertToPx(gridItem.getContext(), 3);
        LayoutParams params = gridView.getLayoutParams();

        int noOfRow = (gridView.getAdapter().getCount() / noOfCol);
        noOfRow = noOfRow + ((gridView.getAdapter().getCount() % noOfCol) > 0 ? 1 : 0);
        totalHeight = totalHeight * noOfRow;
        params.height = (totalHeight + Utils.dpToPx(moreHeight));
        gridView.setLayoutParams(params);
        gridView.requestLayout();
    }


    public static String urlEncode(String str) {
        try {
            //return str.replaceAll(" ", "%20");
            //return str.replace(" ", "%20");
            //return URLEncoder.encode(str, "UTF-8");
            String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
            return Uri.encode(str, "UTF-8");
        } catch (Exception e) {
            throw new IllegalArgumentException("failed to encode", e);
        }
    }

    public static void makeCall(Context mContext, String phoneNo) {

        try {

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNo));
            mContext.startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(mContext, "Error in your phone call" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public static void sendEmail(Context context, String sendTo, String subject) {
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        /* Fill it with Data */
        System.out.println(sendTo);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{sendTo});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        /* Send it off to the Activity-Chooser */
        context.startActivity(Intent.createChooser(emailIntent, "Choose an Email client :"));
    }

    public static boolean isSimCardReadyForCall(Context context) {

        TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);  //gets the current TelephonyManager
        if (telMgr != null && telMgr.getSimState() == TelephonyManager.SIM_STATE_READY) {
            //the device has a sim card
            return true;
        } else {
            //no sim card available
            return false;
        }
    }


    public static boolean rateTheApp(Context context) {

        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        //Uri uri = Uri.parse("market://details?id=" +"com.killam.apartment");
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        boolean isAppAvailableinMarket = true;
        try {
            context.startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            isAppAvailableinMarket = false;

        }
        return isAppAvailableinMarket;
    }

    public static boolean shareWithFriends(Context context) {

        boolean isReadyToShare = true;
        try {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Printvenue";
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Printvenue Mobile App");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "Hey! I just discovered the Printvenue Mobile App. It's awesome! \n http://www.printvenue.com/mobile");
            context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
        } catch (Exception e) {
            isReadyToShare = false;

        }
        return isReadyToShare;
    }

    public static HashMap<String, String> getLocationParams(Context context) {
//        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//        if (locationManager != null) {
//            Location lastKnownLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if (lastKnownLocationGPS != null) {
//                return lastKnownLocationGPS;
//            } else {
//                Location loc =  locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
//                return loc;
//            }
//        } else {
//            return null;
//        }

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);


        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)context,Manifest.permission.ACCESS_FINE_LOCATION)){

                Toast.makeText(context,"GPS permission allows us to access location data. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();
                return mapLoc;
            } else {

                ActivityCompat.requestPermissions(
                        (Activity) context,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_LOCATION_REQUEST_CODE);

                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return mapLoc;
                }

            }



        }


        if(provider == null){
            provider = locationManager.getBestProvider(new Criteria(), false);
        }


        if(provider == null){
            mapLoc.put("city", "");
            mapLoc.put("region", "");

            return mapLoc;
        }else {

            Location locations = locationManager.getLastKnownLocation(provider);
            List<String> providerList = locationManager.getAllProviders();
            if (null != locations && null != providerList && providerList.size() > 0) {
                double longitude = locations.getLongitude();
                double latitude = locations.getLatitude();
                Geocoder geocoder = new Geocoder(context.getApplicationContext(), Locale.getDefault());
                try {
                    List<android.location.Address> listAddresses = geocoder.getFromLocation(latitude, longitude, 1);
                    if (null != listAddresses && listAddresses.size() > 0) {
                        if (listAddresses.get(0).getLocality() != null) {
                            mapLoc.put("city", listAddresses.get(0).getLocality());
                        }
                        if (listAddresses.get(0).getAdminArea() != null) {
                            mapLoc.put("region", listAddresses.get(0).getAdminArea());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return mapLoc;
        }

    }






    public void toggleKeyBoard(Activity context) {
        InputMethodManager inputManager = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /*   public static String getSessionId(Context context) {
           SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
           if (mPrefs.contains(Constants.SESSION_ID_KEY)) {
               return mPrefs.getString(Constants.SESSION_ID_KEY, "");

           }
           return "";
       }
   */


    public static String encrypt(String plainText, String encryptionKey, Context context) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] byteArr = cipher.doFinal(plainText.getBytes("UTF-8"));
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        String auto_login_token = Utils.urlEncode(Base64.encodeToString(byteArr, Base64.DEFAULT));
        prefsEditor.putString("auto_login", auto_login_token);
        prefsEditor.commit();
        return auto_login_token;
    }




    public static boolean isDebugMode() {
        return false;
    }

    public static void unbindDrawables(View view, boolean bRemoveAllViews) {
        if (view == null)
            return;
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof NetworkImageView) {
            ((NetworkImageView) view).setImageBitmap(null);
        }
        if (view instanceof ImageView) {
            PicassoEx.getPicasso(view.getContext().getApplicationContext()).cancelRequest((ImageView) view);
            ((ImageView) view).setImageBitmap(null);
        }
        if (view instanceof ViewGroup) {

            if (view.getBackground() != null) {
                view.getBackground().setCallback(null);
            }
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i), bRemoveAllViews);
            }
            if (!(view instanceof AdapterView)) {
                try {
                    if (bRemoveAllViews)
                        ((ViewGroup) view).removeAllViews();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static String getFormatedMessage(String message, String replacement) {
        return message.replaceAll("%C%", replacement);
    }

    public static byte[] getBody(HashMap<String, String> map) throws AuthFailureError {
        if (map != null && map.size() > 0) {
            return encodeParameters(map, "UTF-8");
        }
        return null;
    }

    public static byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            return encodedParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }

    public static String ordinal(int i) {
        String[] sufixes = new String[]{"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return "th";
            default:
                return sufixes[i % 10];
        }
    }


    public static String getMonth(String month) {

        String returnString = month;

        try {

            if (month.equalsIgnoreCase("01") || month.equalsIgnoreCase("1"))
                returnString = "January";
            else if (month.equalsIgnoreCase("02") || month.equalsIgnoreCase("2"))
                returnString = "February";
            else if (month.equalsIgnoreCase("03") || month.equalsIgnoreCase("3"))
                returnString = "March";
            else if (month.equalsIgnoreCase("04") || month.equalsIgnoreCase("4"))
                returnString = "April";
            else if (month.equalsIgnoreCase("05") || month.equalsIgnoreCase("5"))
                returnString = "May";
            else if (month.equalsIgnoreCase("06") || month.equalsIgnoreCase("6"))
                returnString = "June";
            else if (month.equalsIgnoreCase("07") || month.equalsIgnoreCase("7"))
                returnString = "July";
            else if (month.equalsIgnoreCase("08") || month.equalsIgnoreCase("8"))
                returnString = "August";
            else if (month.equalsIgnoreCase("09") || month.equalsIgnoreCase("9"))
                returnString = "September";
            else if (month.equalsIgnoreCase("10"))
                returnString = "October";
            else if (month.equalsIgnoreCase("11"))
                returnString = "November";
            else if (month.equalsIgnoreCase("12"))
                returnString = "December";
            else
                return returnString;


        } catch (Exception ex) {

        }

        return returnString;
    }


    public static String getShortMonth(String month) {

        String returnString = month;

        try {

            if (month.equalsIgnoreCase("01") || month.equalsIgnoreCase("1"))
                returnString = "Jan";
            else if (month.equalsIgnoreCase("02") || month.equalsIgnoreCase("2"))
                returnString = "Feb";
            else if (month.equalsIgnoreCase("03") || month.equalsIgnoreCase("3"))
                returnString = "Mar";
            else if (month.equalsIgnoreCase("04") || month.equalsIgnoreCase("4"))
                returnString = "Apr";
            else if (month.equalsIgnoreCase("05") || month.equalsIgnoreCase("5"))
                returnString = "May";
            else if (month.equalsIgnoreCase("06") || month.equalsIgnoreCase("6"))
                returnString = "Jun";
            else if (month.equalsIgnoreCase("07") || month.equalsIgnoreCase("7"))
                returnString = "Jul";
            else if (month.equalsIgnoreCase("08") || month.equalsIgnoreCase("8"))
                returnString = "Aug";
            else if (month.equalsIgnoreCase("09") || month.equalsIgnoreCase("9"))
                returnString = "Sep";
            else if (month.equalsIgnoreCase("10"))
                returnString = "Oct";
            else if (month.equalsIgnoreCase("11"))
                returnString = "Nov";
            else if (month.equalsIgnoreCase("12"))
                returnString = "Dec";
            else
                return returnString;


        } catch (Exception ex) {

        }

        return returnString;
    }

    public static String getCardExpireText(String expireDate) {
        String returnText = expireDate;
        if (expireDate.length() == 5)
            expireDate = "0" + expireDate;
        try {

            String month = expireDate.substring(0, 2);
            String date = expireDate.substring(2, 4);
            String year = expireDate.substring(4, expireDate.length());


            returnText = "Expire on " + date + ordinal(Integer.parseInt(date)) + " " + getMonth(month) + " 20" + year;


        } catch (Exception e) {

        }

        return returnText;
    }


    public static boolean isDebugMode(Context appContext, Context baseContext) {
        try {
            return (appContext.getPackageManager().getPackageInfo(
                    baseContext.getPackageName(), 0).applicationInfo.flags
                    & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return false;
    }





    public static String getDeviceDensityName(Context context) {

        switch (context.getResources().getDisplayMetrics().densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                return "ldpi";

            case DisplayMetrics.DENSITY_MEDIUM:
                return "mdpi";

            case DisplayMetrics.DENSITY_HIGH:
                return "hdpi";

            case DisplayMetrics.DENSITY_XHIGH:
                return "xhdpi";

            case DisplayMetrics.DENSITY_XXHIGH:
                return "xxhdpi";

            default:
                return "hdpi";

        }

    }

    public static int getCurrentDeviceOrientation(Context context) {
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        } else if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        } else {
            return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        }
    }


    public static void setHasEmbeddedTabs(Object inActionBar, final boolean inHasEmbeddedTabs) {
        // get the ActionBar class
        Class<?> actionBarClass = inActionBar.getClass();

        // if it is a Jelly Bean implementation (ActionBarImplJB), get the super class (ActionBarImplICS)
        if ("android.support.v7.app.ActionBarImplJB".equals(actionBarClass.getName())) {
            actionBarClass = actionBarClass.getSuperclass();
        }

        try {
            // try to get the mActionBar field, because the current ActionBar is probably just a wrapper Class
            // if this fails, no worries, this will be an instance of the native ActionBar class or from the ActionBarImplBase class
            final Field actionBarField = actionBarClass.getDeclaredField("mActionBar");
            actionBarField.setAccessible(true);
            inActionBar = actionBarField.get(inActionBar);
            actionBarClass = inActionBar.getClass();
        } catch (IllegalAccessException e) {
        } catch (IllegalArgumentException e) {
        } catch (NoSuchFieldException e) {
        }

        try {
            // now call the method setHasEmbeddedTabs, this will put the tabs inside the ActionBar
            // if this fails, you're on you own <img src="http://www.blogc.at/wp-includes/images/smilies/icon_wink.gif" alt=";-)" class="wp-smiley">
            final Method method = actionBarClass.getDeclaredMethod("setHasEmbeddedTabs", new Class[]{Boolean.TYPE});
            method.setAccessible(true);
            method.invoke(inActionBar, new Object[]{inHasEmbeddedTabs});
        } catch (NoSuchMethodException e) {
        } catch (InvocationTargetException e) {
        } catch (IllegalAccessException e) {
        } catch (IllegalArgumentException e) {
        }
    }




    public static String getCountryCode(Context paramContext) {
        String countryCode = "";
        TelephonyManager telephonyManager = (TelephonyManager) paramContext.getSystemService(Context.TELEPHONY_SERVICE);
        countryCode = telephonyManager.getSimCountryIso();
        if (countryCode.equalsIgnoreCase("")) {
            //countryCode = AdxTracker.INDIA_COUNTRY_CODE;
        }
        return countryCode;
    }







    /**
     * Utils method to change the server images
     */

    public static String changeImageUrl(String imageUrl, String parameterToBeReplaced) {

        String url1 = imageUrl.substring(imageUrl.lastIndexOf("-") + 1, imageUrl.length());
        url1 = url1.substring(0, url1.lastIndexOf("."));
        System.out.println(url1);
        imageUrl = imageUrl.replace(url1, parameterToBeReplaced);

        return imageUrl;
    }



    public static String getNetworkClass(Context context) {
        String networkTypeName = "";
        if (isConnectedWifi(context)) {
            return "Wi-fi";
        } else if (isConnectedMobile(context)) {
            TelephonyManager mTelephonyManager = (TelephonyManager)
                    context.getSystemService(Context.TELEPHONY_SERVICE);
            int networkType = mTelephonyManager.getNetworkType();

            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return "GPRS";
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return "EDGE";
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return "2G";
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return "3G";
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return "4G";
                default:
                    return "Unknown";
            }
        }
        return networkTypeName;
    }

    /**
     * Get the network info
     *
     * @param context
     * @return
     */
    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    public static boolean isConnectedWifi(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * Check if there is any connectivity to a mobile network
     *
     * @param context
     * @param
     * @return
     */
    public static boolean isConnectedMobile(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    public static String getDeviceUniqueIdentifier(Context context) {
       // A4S mA4S = Ad4PushEventTracker.getInstance(context);
        //return mA4S.getAndroidId();
        return "not set in code";
    }





    public static void putStringinPrefs(Context mContext, String mKey, String mVal) {
        SharedPreferences.Editor prefsEditorr = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
        try {
            prefsEditorr.putString(mKey, mVal.toString());
            prefsEditorr.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Method for creating standard url campaign params for  following cases
     * 1.If urlParams is in format c=campaignName
     * 2.If urlParams is in format utm=campaignName
     * 3.If urlParams is in format utm_campaign=campaignName
     * 4.If urlParams is campaignId
     * 5.If urlParams contains utm_campaign,utm_source,utm_medium keys
     *
     * It will return utm_campaign=campaignName&utm_source=campaignSource&utm_medium=campaignMedium
     *
     *
     *1
     * @param
     * @return
     */




    public static int getVersionNoFromString(String a_version) {
        int version = 0;
        if (a_version != null){
            try {
                version = Integer.parseInt(a_version.replace(".", ""));
            } catch (NumberFormatException e) {

            }
        }
        return version;
    }

    public static String getJson(Object o){
        Gson gson = new Gson();
        try {
            return gson.toJson(o);
        }catch (Exception e){
            return "";
        }
    }


    public static Integer getQuantity(Integer quantity, Boolean isAbsolutePricingEnabled){
        if (isAbsolutePricingEnabled){
            return 1;
        }
        return quantity;
    }

    /**
     *
     * @param sourceString  string from api response
     * @param sourceLang    en in our case
     * @param destinationLang user setting lang
     * @return
     */
    public String getTraslatedString(String sourceString, String sourceLang, String destinationLang){

        String trasRequest = "https://www.googleapis.com/language/translate/v2?key=AIzaSyAK9Vu9g2vv4jsT0aljz5DFHiTqS9IKsBk&source=en&target="+destinationLang+"&q="+sourceString;

        try {
            String responseString = executeHttpGet(trasRequest);

            JSONObject dataObj = getJsonObject(new JSONObject(responseString),"data");

            JSONArray translationArray = getJsonArray(dataObj,"translations");
            if(translationArray!=null && translationArray.length()>0){

                for (int i = 0; i <translationArray.length() ; i++) {
                    JSONObject jsonObject = translationArray.getJSONObject(i);
                    destinationString = getString(jsonObject,"translatedText");

                    Log.d("Check Responce", "Here: "+destinationString);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return destinationString;
    }


    public static String executeHttpGet(String url) throws Exception {


        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

        return  response.toString();

    }


    /**
     * get String from {@link JSONObject}.
     *
     * @param jsonObject
     * @param key
     * @return value
     */
    public static String getString(JSONObject jsonObject, String key) {
        try {
            if (jsonObject != null) {
                if (!jsonObject.isNull(key)) {
                    return jsonObject.getString(key);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * get {@link JSONObject}.
     *
     * @param jsonObject
     * @param key
     * @return
     */
    public static JSONObject getJsonObject(JSONObject jsonObject, String key) {

        try {
            if (jsonObject != null) {
                if (!jsonObject.isNull(key)) {
                    return jsonObject.getJSONObject(key);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get {@link JSONObject}.
     *
     * @param resString
     * @return
     */
    public static JSONObject getJsonObjectFromResponse(String resString) {

        try {
            if (resString != null) {
                if (resString.length()>0) {
                    return new JSONObject(resString);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * get {@link JSONArray}.
     *
     * @param jsonObject
     * @param key
     * @return
     */
    public static JSONArray getJsonArray(JSONObject jsonObject, String key) {

        try {
            if (jsonObject != null) {
                if (!jsonObject.isNull(key)) {
                    return jsonObject.getJSONArray(key);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}