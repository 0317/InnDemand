package demand.inn.com.inndemand.setting;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
    TextView name, desc, price;
    Button submit;
    Button rating_one, rating_two, rating_three, rating_four, rating_five;
    EditText feedback;

    //Class call
    private List<FeedbackData> cardList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FeedbackAdapter adapter;

    FeedbackData data;

    //Other variables
    String itemName, itemDesc, itemPrice;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);

        itemName = getIntent().getStringExtra("itemname");
        itemDesc = getIntent().getStringExtra("itemdesc");
        itemPrice = getIntent().getStringExtra("itemprice");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(itemName);
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
//        price = (TextView) findViewById(R.id.product_price);

        rating_one = (Button) findViewById(R.id.rating_one);
        rating_two = (Button) findViewById(R.id.rating_two);
        rating_three = (Button) findViewById(R.id.rating_three);
        rating_four = (Button) findViewById(R.id.rating_four);
        rating_five = (Button) findViewById(R.id.rating_five);

        feedback = (EditText) findViewById(R.id.product_feedback);

        name.setText(itemName);
        desc.setText(itemDesc);
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
}
