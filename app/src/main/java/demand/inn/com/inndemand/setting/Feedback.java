package demand.inn.com.inndemand.setting;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.adapter.CartAdapter;
import demand.inn.com.inndemand.adapter.FeedbackAdapter;
import demand.inn.com.inndemand.constants.Config;
import demand.inn.com.inndemand.constants.FeedbackData;
import demand.inn.com.inndemand.model.SimpleDividerItemDecoration;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.volleycall.AppController;

/**
 * Created by akash
 */

public class Feedback extends AppCompatActivity {

    //Utility Class Area
    NetworkUtility nu;
    AppPreferences prefs;

    //UI
    Toolbar toolbar;
    TextView name, desc, price, product_rating;
    Button submit;
    Button rating_one, rating_two, rating_three, rating_four, rating_five;
    EditText feedback;

    //Class call
    private List<FeedbackData> cardList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FeedbackAdapter adapter;

    FeedbackData data;

    //Other variables
    String itemName, itemDesc, itemPrice, itemRating;

    public String destinationString = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);

        itemName = getIntent().getStringExtra("itemname");
        itemDesc = getIntent().getStringExtra("itemdesc");
        itemPrice = getIntent().getStringExtra("itemprice");
        itemRating = getIntent().getStringExtra("itemrating");

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        try {
            String finalName = new getTraslatedString().execute(prefs.getLocaleset(),
                    itemName).get();

            toolbar.setTitle(finalName);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.mipmap.ic_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //UI Initialize
        name = (TextView) findViewById(R.id.product_title);
        desc = (TextView) findViewById(R.id.product_desc);
        product_rating = (TextView) findViewById(R.id.product_rating);
//        price = (TextView) findViewById(R.id.product_price);

        rating_one = (Button) findViewById(R.id.rating_one);
        rating_two = (Button) findViewById(R.id.rating_two);
        rating_three = (Button) findViewById(R.id.rating_three);
        rating_four = (Button) findViewById(R.id.rating_four);
        rating_five = (Button) findViewById(R.id.rating_five);

        feedback = (EditText) findViewById(R.id.product_feedback);

        try {
            String finalName = new getTraslatedString().execute(prefs.getLocaleset(),
                    itemName).get();
            String finalDesc = new getTraslatedString().execute(prefs.getLocaleset(),
                    itemDesc).get();
            String finalRating = new getTraslatedString().execute(prefs.getLocaleset(),
                    itemRating).get();

            name.setText(finalName);
            desc.setText(finalDesc);
            product_rating.setText(finalRating);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
//        price.setText(itemPrice);

//        ListItems in RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        cardList = new ArrayList<>();
        adapter = new FeedbackAdapter(this, cardList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Feedback.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerView.setAdapter(adapter);

        if(nu.isConnectingToInternet()){
            getFeedback();
        }else{
            networkClick();
        }

        data = new FeedbackData("Akash bariar", "Food is very good", "2");
        cardList.add(data);

        data = new FeedbackData("Jyotirmoy", "Taste is superb", "3");
        cardList.add(data);

        data = new FeedbackData("Vinay Ellore", "Omg, Its really awesome", "5");
        cardList.add(data);

        data = new FeedbackData("Sachin tendulkar", "Ok ok but not that good.", "3");
        cardList.add(data);


    }

    public void oneRating(View view){
        rating_one.setBackgroundResource(R.color.confirm_demand_click);
        rating_two.setBackgroundResource(R.color.theme_gray);
        rating_three.setBackgroundResource(R.color.theme_gray);
        rating_four.setBackgroundResource(R.color.theme_gray);
        rating_five.setBackgroundResource(R.color.theme_gray);
        prefs.setRating_value("1");
    }

    public void twoRating(View view){
        rating_one.setBackgroundResource(R.color.confirm_demand_click);
        rating_two.setBackgroundResource(R.color.confirm_demand_click);
        rating_three.setBackgroundResource(R.color.theme_gray);
        rating_four.setBackgroundResource(R.color.theme_gray);
        rating_five.setBackgroundResource(R.color.theme_gray);
        prefs.setRating_value("2");
    }

    public void threeRating(View view){
        rating_one.setBackgroundResource(R.color.confirm_demand_click);
        rating_two.setBackgroundResource(R.color.confirm_demand_click);
        rating_three.setBackgroundResource(R.color.confirm_demand_click);
        rating_four.setBackgroundResource(R.color.theme_gray);
        rating_five.setBackgroundResource(R.color.theme_gray);
        prefs.setRating_value("3");
    }

    public void fourRating(View view){
        rating_one.setBackgroundResource(R.color.confirm_demand_click);
        rating_two.setBackgroundResource(R.color.confirm_demand_click);
        rating_three.setBackgroundResource(R.color.confirm_demand_click);
        rating_four.setBackgroundResource(R.color.confirm_demand_click);
        rating_five.setBackgroundResource(R.color.theme_gray);
        prefs.setRating_value("4");
    }

    public void fiveRating(View view){
        rating_one.setBackgroundResource(R.color.confirm_demand_click);
        rating_two.setBackgroundResource(R.color.confirm_demand_click);
        rating_three.setBackgroundResource(R.color.confirm_demand_click);
        rating_four.setBackgroundResource(R.color.confirm_demand_click);
        rating_five.setBackgroundResource(R.color.confirm_demand_click);

        prefs.setRating_value("5");
    }

    public void submitFeedback(View view){
        String feed = feedback.getText().toString().trim();

        if(feed == null || feed.equalsIgnoreCase("")){
            new AlertDialog.Builder(this).setMessage("Please feedback product")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).create().show();
        }else{
            JSONObject obj = new JSONObject();

            try{
                obj.put("restaurant_id", prefs.getRestaurant_Id());
                obj.put("restaurant_item_id", prefs.getRestaurant_item_id());
                obj.put("checkin_id", prefs.getCheckin_Id());
                obj.put("feedback_points", prefs.getRating_value());
                obj.put("feedback_description", feed);
            }catch(JSONException e){
                e.printStackTrace();
            }
            postJsonData(Config.innDemand+"restaurant_item_feedback/save/", obj.toString());
        }
    }

    public void getFeedback(){
        JSONObject obj = new JSONObject();

        try{
            obj.put("restaurant_id", "");
            obj.put("restaurant_item_id", "");
        }catch (JSONException e){
            e.printStackTrace();
        }

        Log.d("API DATA Rest Populate", "Check: "+obj.toString());

        postJsonData(Config.innDemand+"restaurant_item_feedback/populate/", obj.toString());
    }

    public void postJsonData(String url, String userData){

        RequestQueue mRequestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();

        final String requestBody = userData;

        System.out.println("inside post json data====="+requestBody);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("yohaha=feedback==success===" + response);

                JSONArray array = null;
                try {
                    array = new JSONArray(response);

                    Log.d("API", "API D" + array);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < array.length(); i++)
                    try {
                        JSONObject object = array.getJSONObject(i);
                        Log.d("API", "API Daa" + array);

                        String names = object.getString("checkin");
                        String rate = object.getString("feedback_points");
                        String descs = object.getString("feedback_description");

                        data = new FeedbackData(names, descs, rate);
                        cardList.add(data);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

    //Custom pop-up for Network Click
    public void networkClick(){
        // custom dialog
        final Dialog dialog = new Dialog(Feedback.this);
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


    //Translation Area Coding .....
    //Google Translate API call to translate Dynamic data

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

        URL obj = new URL(url.replace(" ", "%20"));
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
}
