package demand.inn.com.inndemand.Helper;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

import demand.inn.com.inndemand.DashBoard;
import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.constants.Config;
import demand.inn.com.inndemand.constants.ListData;
import demand.inn.com.inndemand.database.DBHelper;
import demand.inn.com.inndemand.model.ResturantDataModel;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.volleycall.AppController;

/**
 * Created by akash
 */
public class Loader extends AppCompatActivity {

    //Utility Class Area
    NetworkUtility nu;
    AppPreferences prefs;

    //String Area to Call Hotels Details(Name. Address, etc)
    String hotelName;
    String address;
    String restaurant_image;

    //public String to get Translated language response
    public String destinationString = "";

    //List Class to Add Resturants in the list
    private List<ListData> restaurantData;

    //DATABASE CLASS AREA
    DBHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loader_layout);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);
        db = new DBHelper(this);

        /**
         * Loader Area to load APIs (Hotel Details)
         * */
        if(nu.isConnectingToInternet()) {
            makeJsonObjectRequestHotel();
            restaurantList();
            inclusion();
        }else{
            networkClick();
        }

        /**
         * Handler Class handling Loader screen without any invention of APIs call
         * Handler Class to handle the Splash Loader for 3 seconds
         * After 3 seconds it fires the Intent to next mentioned screen
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent(Loader.this, DashBoard.class);
                startActivity(in);
                finish();
            }
        },3000 /* 3sec delay*/);
    }


    /**
     * Method to make json object get call and fetch details from Server by sending Hotel ID to
     * server
     * Loads details of the hotel to show at the bottom of the screen
     * */

    private void makeJsonObjectRequestHotel() {

        JSONObject obj = new JSONObject();

        try {
            obj.put("hotel_id", prefs.getHotel_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Api Data", obj.toString());

        postJsonDataHotel(Config.innDemand+"hotels/details/", obj.toString());
    }

    /*
     * API call for Different Restaurants list shown when User clicks on the
     * Restaurant Option on the screen
     */
    public void restaurantList(){
        JSONObject obj = new JSONObject();

        try {
            obj.put("hotel_id", prefs.getHotel_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Api Hotel Data", obj.toString());

        postJsonRestaurant(Config.innDemand+"restaurant/details/", obj.toString());
    }

    //API call for Inclusion for Room Services n Restaurant or overall Hotel
    public void inclusion(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("hotel_id", prefs.getHotel_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Api Hotel Data", obj.toString());

        postJsonDataInclusion(Config.innDemand+"inclusion/details/", obj.toString());
    }

    /*
    * Network Response getting all details of Hotel
    * Details: Picture, name, address, location, contact number etc
    * Values set to show options like: If Restaurant/Bar/Spa is there or not
    */
    public void postJsonDataHotel(String url, String userData) {

        RequestQueue mRequestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();

        final String requestBody = userData;

        System.out.println("inside post json data=====" + requestBody);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("yohaha=success===" + response);
                        try {
                            JSONObject object = new JSONObject(response);

                            hotelName = object.getString("name");
                            String location = object.getString("location");
                            String latitude = object.getString("latitude");
                            String longitude = object.getString("longitude");
                            address = object.getString("address");
                            final String number = object.getString("contact_number");
                            Boolean service = object.getBoolean("has_restaurant_service");
                            restaurant_image = object.getString("restaurant_image");
                            Boolean room_service = object.getBoolean("has_room_service");
                            String room_img = object.getString("room_service_image");
                            Boolean bar_service = object.getBoolean("has_bar_service");
                            String bar_img = object.getString("bar_image");
                            Boolean spa_service = object.getBoolean("has_spa_service");
                            String spa_img = object.getString("spa_image");
                            String about_hotel = object.getString("about_hotel");


                            String htName = new getTraslatedString().execute(prefs.getLocaleset(),
                                    hotelName).get();
                            String htAddress = new getTraslatedString().execute(prefs.getLocaleset(),
                                    hotelName).get();

                            prefs.setHotel_name(htName);
                            prefs.setHotel_address(htAddress);
                            prefs.setHotel_dp(restaurant_image);
                            prefs.setHotel_latitude(latitude);
                            prefs.setHotel_longitude(longitude);

                            Log.d("Name_", hotelName);
                            Log.d("Address_", address);
                            Log.d("Image", restaurant_image);
                            Log.d("Contact", number);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Loader.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return String.format("application/json; charset=utf-8");
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            requestBody, "utf-8");
                    return null;
                }
            }
        };
//        mRequestQueue.add(stringRequest);
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

        /*
        * Method to get response of list of Restaurant in the hotel
        * Saving name of restaurant & show in the list
        * Saving ID of restaurant for further usage
        * Setting status (getting in response) to check which restaurant to Hide/Show
        * through Adapter Class
    */
    public void postJsonRestaurant(String url, String userData){
        RequestQueue mRequestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();

        final String requestBody = userData;

        System.out.println("inside post json data=====" + requestBody);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("yohaha=restautant=success===" + response);


                        JSONArray array = null;
                        try {
                            array = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < array.length(); i++) {
                            try {
                                JSONObject object = array.getJSONObject(i);

                                String restaurantId = object.getString("id");
                                String rest_name = object.getString("name");
                                String hotel = object.getString("hotel");
                                String status = object.getString("status");

                                String htName = new getTraslatedString().execute
                                            (prefs.getLocaleset(), rest_name).get();

                                db.insertData(new ListData(restaurantId, htName, status));

//                                ListData data = new ListData(restaurantId, htName, status);
//                                        restaurantData.add(data);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(HotelDetails.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return String.format("application/json; charset=utf-8");
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            requestBody, "utf-8");
                    return null;
                }
            }
        };
//        mRequestQueue.add(stringRequest);
        AppController.getInstance().addToRequestQueue(stringRequest);
    }


    /*
    *Network Call to set Inclusion whether the hotel is having the facilities
    *(Restaurant, Bar, Services, Spa)
    *Bollean value to set/get whether to show/not
    *Boolean value to show which room service is available in the hotel
    *Room services value are also mentioned(Beverages/Bath Essentials/Bed tea/coffee)
    */
    public void postJsonDataInclusion(String url, String userData){
        RequestQueue mRequestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();

        final String requestBody = userData;

        System.out.println("inside post inclusion json data=====" + requestBody);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("yohaha=inclusion=success===" + response);


                        JSONObject object = null;
                        try {
                            object = new JSONObject(response);

                            Boolean bar = object.getBoolean("bar");
                            Boolean spa = object.getBoolean("spa");
                            Boolean restaurant = object.getBoolean("restaurant");
                            Boolean room_service = object.getBoolean("room_service");
                            Boolean room_clean = object.getBoolean("room_clean");
                            Boolean laundry = object.getBoolean("laundry");
                            Boolean cab = object.getBoolean("cab");
                            Boolean bath_ess = object.getBoolean("bathroom_essentials");
                            Boolean bathroom_essentials_towels =
                                    object.getBoolean("bathroom_essentials_towels");
                            Boolean bathroom_essentials_toiletries =
                                    object.getBoolean("bathroom_essentials_toiletries");
                            Boolean bathroom_essentials_maintenance =
                                    object.getBoolean("bathroom_essentials_maintenance");
                            Boolean water = object.getBoolean("water");
                            Boolean mineral_water = object.getBoolean("mineral_water");
                            Boolean ice_bucket = object.getBoolean("ice_bucket");
                            Boolean soda = object.getBoolean("soda");
                            Boolean glass = object.getBoolean("glass");
                            Boolean wake_me_up = object.getBoolean("wake_me_up");
                            Boolean tea_coffee = object.getBoolean("tea_coffee");
                            Boolean tea = object.getBoolean("tea");
                            Boolean coffee = object.getBoolean("coffee");
                            Boolean bell_boy = object.getBoolean("bell_boy");

                            prefs.setBell_boy(bell_boy);
                            prefs.setCoffee(coffee);
                            prefs.setTea(tea);
                            prefs.setBed_tea(tea_coffee);
                            prefs.setWake_up(wake_me_up);
                            prefs.setBar(bar);
                            prefs.setSpa(spa);
                            prefs.setRestaurant(restaurant);
                            prefs.setRoom_service(room_service);
                            prefs.setRoom_clean(room_clean);
                            prefs.setLaundry(laundry);
                            prefs.setCab(cab);
                            prefs.setBathroom(bath_ess);
                            prefs.setBath_towel(bathroom_essentials_towels);
                            prefs.setBath_toiletries(bathroom_essentials_toiletries);
                            prefs.setBath_maintenance(bathroom_essentials_maintenance);
                            prefs.setBeverage(water);
                            prefs.setWater(mineral_water);
                            prefs.setIce_bucket(ice_bucket);
                            prefs.setSoda(soda);
                            prefs.setGlass(glass);


                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(HotelDetails.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return String.format("application/json; charset=utf-8");
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            requestBody, "utf-8");
                    return null;
                }
            }
        };
//        mRequestQueue.add(stringRequest);
        AppController.getInstance().addToRequestQueue(stringRequest);
    }


    /*
     * API call to translate data
     * Translation coding to Translate all the data coming from server in target language
     */
    public class getTraslatedString extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String[] target) {

            String trasRequest = "https://www.googleapis.com/language/translate/v2?" +
                    "key=AIzaSyAK9Vu9g2vv4jsT0aljz5DFHiTqS9IKsBk&source=en" +
                    "&target="+target[0]+"&q="+target[1];

            try {
                String responseString = executeHttpGet(trasRequest);

                JSONObject dataObj = getJsonObject(new JSONObject(responseString),"data");

                JSONArray translationArray = getJsonArray(dataObj,"translations");
                if(translationArray!=null && translationArray.length()>0){

                    for (int i = 0; i <translationArray.length() ; i++) {
                        JSONObject jsonObject = translationArray.getJSONObject(i);
                        destinationString = getString(jsonObject,"translatedText");
                        Log.d("Check Responce", "Here: "+destinationString);
                        System.out.print("Pls give "+destinationString);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.print("ExceptionGen: "+e);
            }
            return destinationString;
        }

        @Override
        protected void onPostExecute(String valuee) {
            Log.d("Check Responce", "Here: "+destinationString);

            Log.d("Check Responce", "There: "+valuee);
        }
    }

    public String executeHttpGet(String url) throws Exception {

        java.net.URL obj = new URL(url.replace(" ", "%20"));
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header

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
    public String getString(JSONObject jsonObject, String key) {
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
    public JSONObject getJsonObject(JSONObject jsonObject, String key) {

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
    public JSONObject getJsonObjectFromResponse(String resString) {

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
    public JSONArray getJsonArray(JSONObject jsonObject, String key) {

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

    /*
     *Custom pop-up for Internet Check (If connected or not)
     * When No internet connectivity a pop-up will arise by below method
     */
    public void networkClick(){
        // custom dialog
        final Dialog dialog = new Dialog(Loader.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.network);

        // set the custom dialog components - text, image and button
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Button checkout = (Button) dialog.findViewById(R.id.ok_click);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dialog.show();
    }
}
