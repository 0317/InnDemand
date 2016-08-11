package demand.inn.com.inndemand.hotelserv;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
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
import demand.inn.com.inndemand.adapter.RestaurantAdapter;
import demand.inn.com.inndemand.adapter.ViewPagerAdapter;
import demand.inn.com.inndemand.cartarea.MyCart;
import demand.inn.com.inndemand.constants.CartData;
import demand.inn.com.inndemand.constants.Config;
import demand.inn.com.inndemand.constants.FragmentData;
import demand.inn.com.inndemand.fragmentarea.Appetizer;
import demand.inn.com.inndemand.gcm.GCMNotifications;
import demand.inn.com.inndemand.model.ResturantDataModel;
import demand.inn.com.inndemand.model.SearchDB;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.volleycall.AppController;

/**
 * Created by akash
 */

public class Restaurant extends AppCompatActivity{

    //Utility Class area
    NetworkUtility nu;
    AppPreferences prefs;

    //UI Call area for the screen
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter adapter;
    TextView restaurant_text;
    TextView cart_item, cart_total;
    Toolbar toolbar;

//    Others requirements (if need)
    View view;
    private Menu menu;
    int positions;

//    Alert-dialog
    private ProgressDialog mProgressDialog;

//    TabClass Class call for dynamic approach of tabs (create multiple tabs automatically)
    Appetizer mAppetizer;

//    String to define to value
//    Values fetching from APIs in the form of String
    String ids;
    String itemName;
    String itemDesc;
    String category;
    String food;
    String subCategory;
    String amount;
    String catName;
    String catType;
    String catStatus;
    String type_id;
    String totalCash;
    String totalItems;
    String broaditemName;

//    Getting Restaurant name via Intent in a String
    String restName;

//    Adding values in the cart in the form of String
    String cart_value;
    String cart_val;

//  Fragments data in the screen and List of tabs in tab layout shown
    List<FragmentData> tabList;
    FragmentData data;

    //Adapter Class with String requirements
    RestaurantAdapter adapt;
    String cash, items;
    Bundle getBundle;
    List<ResturantDataModel> resturantDataModelList;
    ResturantDataModel dataModel, model;

//    String which provides final result after Translation
    public String destinationString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant);
//        Utility Class Initialize
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);

//        Constant Class n Database calss initialize
        model = new ResturantDataModel();

        restName = getIntent().getStringExtra("rest_name");

        //Toolbar call for the screen
//        Includes menu on the top right through restaurant_menu.xml file and back press icon
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.inflateMenu(R.menu.restaurant_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                //noinspection SimplifiableIfStatement
                if (id == R.id.action_settings) {
                    Intent in = new Intent(Restaurant.this, SearchDB.class);
                    startActivity(in);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }else if(id == R.id.action_cart){
                    Intent in = new Intent(Restaurant.this, MyCart.class);
                    startActivity(in);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }else if(id == R.id.action_notification){
                    Intent in = new Intent(Restaurant.this, GCMNotifications.class);
                    startActivity(in);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }else if(id == R.id.action_food){
                    final View menuItemView = findViewById(R.id.action_food);
                    final PopupMenu popup = new PopupMenu(Restaurant.this, menuItemView);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
//
                            switch (item.getItemId()) {
                                case R.id.action_all:
                                    toolbar.getMenu().getItem(0).setIcon(R.mipmap.ic_menu_filter);

                                    return  true;

                                case R.id.action_veg:
                                    toolbar.getMenu().getItem(0).setIcon(R.mipmap.ic_menu_filter_green);

                                    Log.d("Food value", "value"+dataModel.getFood());
                                    if(dataModel.getFood().contains("1")){
                                        for(int i = 0; i < 10 ; i++){
                                            dataModel.setName(dataModel.getName());
                                            dataModel.setDescription(dataModel.getDescription());
                                            dataModel.setPrice(dataModel.getPrice());
                                            dataModel.setFood(dataModel.getFood());

                                            resturantDataModelList.add(dataModel);
                                        }
                                    }
                                    return true;

                                case R.id.action_nonveg:
                                    toolbar.getMenu().getItem(0).setIcon(R.mipmap.ic_menu_filter_red);

                                    if(model.getFood() == "2"){
                                        for(int i = 0; i < 10 ; i++){
                                            dataModel.setName(dataModel.getName());
                                            dataModel.setDescription(dataModel.getDescription());
                                            dataModel.setPrice(dataModel.getPrice());
                                        }
                                    }

                                    return true;
                            }
                            return true;
                        }
                    });

                    popup.show();//showing popup menu
                }
                return true;
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


//        Happens when We scroll up the screen and the Image turned into toolbar
//        It is basically that effect toggling Image into toolbar n vice-versa
//        Setting title for Collapsing Toolbar as Restaurant Name also
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(restName);
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle("");
                    isShow = false;
                }
            }
        });

        //UI TextView Initialize
        restaurant_text = (TextView) findViewById(R.id.restaurant_text);

        //tabclass initialization
        mAppetizer = new Appetizer();

        viewPager = (ViewPager) findViewById(R.id.container);


        //Tab call area
//        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                positions = tab.getPosition();
                viewPager.setCurrentItem(tab.getPosition());
                cart_value = cart_total.getText().toString();
                cart_val = cart_item.getText().toString();
                Log.d("Tab Position: ", String.valueOf(positions));
                Intent in = new Intent("position-message");
                in.putExtra("positionsof", type_id);
                LocalBroadcastManager.getInstance(Restaurant.this).sendBroadcast(in);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //UI initialize
        cart_item = (TextView) findViewById(R.id.bottom_items);
        cart_total = (TextView) findViewById(R.id.bottom_total);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("custom-message"));

//        if(mAppetizer.isResumed())
//            Toast.makeText(getApplicationContext(), "Appetiser", Toast.LENGTH_LONG).show();
//        else if(mMaincourse.isVisible())
//            Toast.makeText(getApplicationContext(), "Main Course", Toast.LENGTH_LONG).show();



        if(nu.isConnectingToInternet()) {
            showProgressDialog();
            if(prefs.getFm_restaurant() == true) {
                restaurant_text.setText(R.string.restaurant_servenotavailable);
                hideProgressDialog();
            } else {
                restaurant_text.setText(R.string.restaurant_serveavailable);
                hideProgressDialog();
                getData();
                getCategory();
            }
        }else{
            networkClick();
        }

        tabList = new ArrayList<>();
    }

//    Here receives data from Fragments class Adapter in the form of broadcast
//    getting counter for the items selected n items detail in food list
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            totalCash = intent.getStringExtra("totalCash");
            totalItems = intent.getStringExtra("totalItems");
            broaditemName = intent.getStringExtra("selectedItem");

            int cash = Integer.parseInt(totalCash);
            int item = Integer.parseInt(totalItems);

            double vals = Double.parseDouble(totalCash);
            double vas = Double.parseDouble(totalItems);

            prefs.setTotal_cash(totalCash);
            prefs.setTotal_items(totalItems);


            try {

                int cart_cash = Integer.parseInt(cart_value);
                int cart_itemss = Integer.parseInt(cart_val);

                int totValue = cart_cash+cash;
                int totItem = cart_itemss+item;

                double val = Double.parseDouble(totalCash); //+Double.parseDouble(cart_value);
                double va = Double.parseDouble(totalItems); //+Double.parseDouble(cart_val);

                Log.d("totalValue", "dataValue: "+cart_cash);
                Log.d("totalItem", "dataValue: "+cart_itemss);

                Log.d("SelectedItem", "dataValue: "+broaditemName);


                cart_total.setText(String.valueOf(totValue));
                cart_item.setText(String.valueOf(totItem));

            }catch (NumberFormatException e)
            {
                e.printStackTrace();
            }

            Intent in = new Intent("data-message");
            in.putExtra("cartItem", cart_item.getText().toString());
            in.putExtra("cartTotal", cart_total.getText().toString());
            LocalBroadcastManager.getInstance(Restaurant.this).sendBroadcast(in);
        }
    };

//    method is to add items to cart with onClick
//    Shows a pop-up asking view cart or not now
    public void addCart(View view){
//        db.insertData(new CartData(broaditemName, totalCash, totalItems));
        new AlertDialog.Builder(Restaurant.this).setMessage(R.string.restaurant_foodadded_tocart)
                .setPositiveButton(R.string.viewcart, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent in = new Intent(Restaurant.this, MyCart.class);
                        startActivity(in);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create().show();
    }

// API call to get category (Tab data/different tabs to show) for the screen
    public void getCategory(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("hotel_id", prefs.getHotel_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("Check API Data", obj.toString());

        postJsonDataCategory(Config.innDemand+"category/details/", obj.toString());
    }

//    API call to get Food list items for the Recyclerview
    public void getData(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("restaurant_id", prefs.getRestaurant_Id());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("Check API Data", obj.toString());

        postJsonData(Config.innDemand+"restaurant_items/details/", obj.toString());

    }

//    Volley Library Main method to get Category and other requirements as response by sending
//    restaurant ID
    public void postJsonData(String url, String userData) {

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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideProgressDialog();
                System.out.println("yohaha==restaurantss==success==" + response);
                resturantDataModelList = new ArrayList<>();
                JSONArray array = null;
                try {
                    array = new JSONArray(response);

                    Log.d("API", "API D"+array);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < array.length(); i++)
                    try {
                        JSONObject object = array.getJSONObject(i);
                        dataModel = new ResturantDataModel(i);
                        Log.d("API", "API Daa" + array);
                        Log.d("API", "API ID" + ids);
                        ids = object.getString("id");
                        itemName = object.getString("name");

                        Log.d("API", "API na" + itemName);
                        itemDesc = object.getString("description");
                        category = object.getString("category");
                        Log.d("API", "API Cat" + category);
                        food = object.getString("food");

                        subCategory = object.getString("subcategory");
                        amount = object.getString("price");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(Restaurant.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void postJsonDataCategory(String url, String userData) {

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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideProgressDialog();
                resturantDataModelList = new ArrayList<>();
                System.out.println("yohaha==category==success===" + response);

                JSONArray array = null;
                try {
                    array = new JSONArray(response);

                    Log.d("API", "API D"+array);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < array.length(); i++)
                    try {
                        JSONObject object = array.getJSONObject(i);
                        dataModel = new ResturantDataModel(i);
                        //catName = Response (category names to show in tablayout)
                        type_id = object.getString("id");
                        catName = object.getString("name");

                        //catType = Response 1/2/3 (1 = Bar, 2 = Restaurant, 3 = Spa)
                        catType = object.getString("category_type");

                        //catStatus = Response 0/1 (check if 0, remove the tab attribute)
//                        catStatus = object.getString("status");
                        Log.d("API", "API Category: " + catName);

                        String valueCat = null;
                        try {
                           /* if(prefs.getLocaleset() == "en" || prefs.getLocaleset().equals("en")) {

                                dataModel.setId(type_id);
                                dataModel.setCategory(catName);
//                        dataModel.setId(catType);
                                resturantDataModelList.add(dataModel);
                            }else {*/
                                valueCat = new getTraslatedString().execute(prefs.getLocaleset(),
                                        catName).get();

                                dataModel.setId(type_id);
                                dataModel.setCategory(valueCat);
//                        dataModel.setId(catType);
                            resturantDataModelList.add(dataModel);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        Log.d("LogValueCat: ", valueCat);
                        /*dataModel.setId(type_id);
                        dataModel.setCategory(valueCat);
//                        dataModel.setId(catType);
                        resturantDataModelList.add(dataModel);*/
//                        data = new FragmentData(catName);
//                        tabList.add(data);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                adapter = new ViewPagerAdapter(getSupportFragmentManager());

                for (ResturantDataModel dataModel:resturantDataModelList){

                    Bundle bundle = new Bundle();
                    bundle.putString("category_id", dataModel.getCategory());
                    bundle.putString("id_type",dataModel.getId());

                    Fragment appFrf = new Appetizer();
                    appFrf.setArguments(bundle);

                    adapter.addFragment(appFrf, dataModel.getCategory());

                }

                 adapter.notifyDataSetChanged();
                viewPager.setAdapter(adapter);

                tabLayout.setupWithViewPager(viewPager);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(Restaurant.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

//    Alert-dialog to show when data is loading
    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("loading...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

//    hides the dialog pop-up after data gets loaded
    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    //Custom pop-up for Internet Connection(opens if not connected)
    public void networkClick(){
        // custom dialog
        final Dialog dialog = new Dialog(Restaurant.this);
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


//      API call to translate data
    //    Translation coding to Translate all the data coming from server in target language
    public class getTraslatedString extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String[] target) {

            String trasRequest = "https://www.googleapis.com/language/translate/v2?key=AIzaSyAK9Vu9g2vv4jsT0aljz5DFHiTqS9IKsBk&source=en&target="+target[0]+"&q="+target[1];

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
