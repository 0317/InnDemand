package demand.inn.com.inndemand.login;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.crashlytics.android.Crashlytics;
import com.firebase.client.Firebase;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.adapter.BarlistAdapter;
import demand.inn.com.inndemand.adapter.HotelAdapter;
import demand.inn.com.inndemand.adapter.ListAdapter;
import demand.inn.com.inndemand.cartarea.MyCart;
import demand.inn.com.inndemand.constants.BarlistData;
import demand.inn.com.inndemand.constants.Config;
import demand.inn.com.inndemand.constants.HotelData;
import demand.inn.com.inndemand.constants.ListData;
import demand.inn.com.inndemand.constants.MarshMallowPermission;
import demand.inn.com.inndemand.gcm.GCMNotifications;
import demand.inn.com.inndemand.mapdirection.MapArea;
import demand.inn.com.inndemand.mapdirection.Mapping;
import demand.inn.com.inndemand.model.SimpleDividerItemDecor;
import demand.inn.com.inndemand.roomservice.Bar;
import demand.inn.com.inndemand.roomservice.Restaurant;
import demand.inn.com.inndemand.roomservice.RoomServices;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.volleycall.AppController;
import demand.inn.com.inndemand.welcome.BaseActivity;
import demand.inn.com.inndemand.welcome.SplashScreen;
import io.fabric.sdk.android.Fabric;

/**
 * Created by akash
 */

public class HotelDetails extends AppCompatActivity {

    //Utility Class Area
    NetworkUtility nu;
    AppPreferences prefs;

    private static final int CALL_PERMISSION_REQUEST_CODE = 5;

    //UI Call
    Button checkout;
    TextView hotel_Name, hotel_Address, hotel_desc, call_hotel;
    ImageLoader imageLoader;
    NetworkImageView imageView;
    ImageView main_backdrop;
    Toolbar toolbar;

    //Area to Show Details of Hotel(Includes Restaurant/ Services/ Spa/ Bar or not)
    LinearLayout restaurant_area, service_area, spa_area, bar_area;

    //Others
    String callHotel;
    String URL, TAG;
    String about_hotel;
    String hotelName;

    //List Items Bottom
    private RecyclerView recyclerView, restaurantList, barList;
    private HotelAdapter adapter;
    private ListAdapter restaurantAdapter;
    private BarlistAdapter barAdapter;
    private List<HotelData> hotelData;
    private List<ListData> restaurantData;
    private List<BarlistData> barData;

    //Timings Call
    String fm_bar;
    String fm_spa;
    String fm_service;
    String fm_restaurant;
    String to_bar;
    String to_spa;
    String to_service;
    String to_restaurant;
    String restaurantId;
    String barId;
    String location;
    String latitude;
    String longitude;

    //Date & Time
    Calendar c;
    SimpleDateFormat df, timeFormat;
    String formattedDate, getTime;

    boolean doubleBackToExitPressedOnce = false;
    private ProgressDialog mProgressDialog;

    boolean bar, spa, restaurant, service;

    Context context;
    private SharedPreferences appSharedpref;
    private SharedPreferences.Editor prefEditor;
    public static final String SKILL_PREFS = "inn_demand_prefs";

    MarshMallowPermission marshMallowPermission;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.hoteldetails);
        nu = new NetworkUtility(HotelDetails.this);
        prefs = new AppPreferences(HotelDetails.this);
        prefs.setCheckout("2");
        prefs.setIs_task_completed(true);
        prefs.setIs_In_Hotel(true);

        URL = "";

        marshMallowPermission = new MarshMallowPermission(HotelDetails.this);

        imageLoader = AppController.getInstance().getImageLoader();
//        imageView.setImageUrl(URL, imageLoader);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.action_settings) {
                    return true;
                } else if (id == R.id.action_cart) {
                    Intent in = new Intent(HotelDetails.this, MyCart.class);
                    startActivity(in);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                } else if (id == R.id.action_notification) {
                    Intent in = new Intent(HotelDetails.this, GCMNotifications.class);
                    startActivity(in);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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

        c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timeFormat = new SimpleDateFormat("HH:mm:ss");
        formattedDate = df.format(c.getTime());
        getTime = timeFormat.format(c.getTime());
        // formattedDate have current date/time

        if (nu.isConnectingToInternet()) {
            //Getting All Restaurant List of hotel
            showProgressDialog();
            restaurantList();

            //Getting all Bar list of hotel
            barList();

            //Method to get Inclusion data for Hotel (Boolean values)
            inclusion();

            //Mehod to get Timings for Hotel Services
            timings();

            //Loading Data
            makeJsonObjectRequest();
            makeJsonRequestBottom();

        } else {
            networkClick();
        }

        //Title set for Collapsing Toolbar
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
                    collapsingToolbarLayout.setTitle(hotelName);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle("");
                    isShow = false;
                }
            }
        });

        //UI Initialize
        restaurant_area = (LinearLayout) findViewById(R.id.hotel_restaurant);
        service_area = (LinearLayout) findViewById(R.id.hotel_roomservice);
        spa_area = (LinearLayout) findViewById(R.id.hotel_spa);
        bar_area = (LinearLayout) findViewById(R.id.hotel_bar);

        restaurant_area.setVisibility(View.GONE);
        service_area.setVisibility(View.GONE);
        bar_area.setVisibility(View.GONE);
        spa_area.setVisibility(View.GONE);


        main_backdrop = (ImageView) findViewById(R.id.main_backdrop);
        // If you are using normal ImageView
        imageLoader.get(URL, new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Image Load Error: " + error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    // load image into imageview
                    main_backdrop.setImageBitmap(response.getBitmap());
                }
            }
        });

        //UI initialize
        hotel_Name = (TextView) findViewById(R.id.hotel_Name);
        hotel_Address = (TextView) findViewById(R.id.hotel_Address);
        call_hotel = (TextView) findViewById(R.id.call_hotel);

        //variable to get Hotel contact number
        callHotel = "";

        //ListItems in RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.hotel_recycler_view);
        restaurantList = (RecyclerView) findViewById(R.id.hotel_recycler_list);
        barList = (RecyclerView) findViewById(R.id.hotel_barlist);
        restaurantList.setVisibility(View.GONE);
        barList.setVisibility(View.GONE);
        hotelData = new ArrayList<>();
        restaurantData = new ArrayList<>();
        barData = new ArrayList<>();
        adapter = new HotelAdapter(this, hotelData);
        restaurantAdapter = new ListAdapter(this, restaurantData);
        barAdapter = new BarlistAdapter(this, barData);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SimpleDividerItemDecor(this));

        RecyclerView.LayoutManager mLayoutManagers = new LinearLayoutManager(this);
        restaurantList.setLayoutManager(mLayoutManagers);
        restaurantList.setItemAnimator(new DefaultItemAnimator());
        restaurantList.addItemDecoration(new SimpleDividerItemDecor(this));

        RecyclerView.LayoutManager mLayoutManagerss = new LinearLayoutManager(this);
        barList.setLayoutManager(mLayoutManagerss);
        barList.setItemAnimator(new DefaultItemAnimator());
        barList.addItemDecoration(new SimpleDividerItemDecor(this));

        recyclerView.setAdapter(adapter);
        restaurantList.setAdapter(restaurantAdapter);
        barList.setAdapter(barAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ListData data = restaurantData.get(position);
//                Toast.makeText(HotelDetails.this, data.getTitle(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    public void restaurantList() {
        JSONObject obj = new JSONObject();
//
        try {
            obj.put("hotel_id", prefs.getHotel_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Api Hotel Data", obj.toString());

        postJsonRestaurant(Config.innDemand + "restaurant/details/", obj.toString());
    }

    public void getToken(){

    }

    public void barList() {
        JSONObject obj = new JSONObject();
//
        try {
            obj.put("hotel_id", prefs.getHotel_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Api Bar Data", obj.toString());

        postJsonDataBar(Config.innDemand + "bars/details/", obj.toString());
    }

    public void inclusion() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("hotel_id", prefs.getHotel_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Api Hotel Data", obj.toString());

        postJsonDataInclusion(Config.innDemand + "inclusion/details/", obj.toString());
    }

    public void timings() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("hotel_id", prefs.getHotel_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Api Hotel Data", obj.toString());

        postJsonDataTimings(Config.innDemand + "timing/details/", obj.toString());
    }

    //Button onClicklistener to Checkout from the hotel & redirect to Splash Screen
    public void checkOut(View view) {

        checkoutClick();

//        AlertDialog.Builder builder = new AlertDialog.Builder(HotelDetails.this);
//        builder.setMessage("It seems like you want to checkout?")
//                .setPositiveButton("Checkout", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //Json Parsing to send hotel details to checkout.
//                        JSONObject obj = new JSONObject();
//                        try {
//                            obj.put("checkin_id", prefs.getCheckin_Id());
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        postJsonData(Config.innDemand + "checkins/checkout/", obj.toString());
//                        prefs.setCheckout("1");
//                        prefs.setIs_In_Hotel(false);
//                        Toast.makeText(HotelDetails.this, "Successfully Checked Out", Toast.LENGTH_LONG).show();
//                        Intent in = new Intent(HotelDetails.this, SplashScreen.class);
//                        startActivity(in);
//                        finish();
//                    }
//                })
//                .setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//
//        AlertDialog dialog = builder.create();
//        dialog.setTitle("Are you Sure?");
//        dialog.show();
    }

    public void direction(View view) {
        Intent in = new Intent(HotelDetails.this, MapArea.class);
        in.putExtra("latitude", prefs.getHotel_latitude());
        in.putExtra("longitude", prefs.getHotel_longitude());
        startActivity(in);
    }

    //OnClick to show Restaurant List of the Hotel
    public void restaurantClick(View view) {
        if (prefs.getCheck_list() == false) {
            restaurantList.setVisibility(View.VISIBLE);
            prefs.setCheck_list(true);
        } else if (prefs.getCheck_list() == true) {
            restaurantList.setVisibility(View.GONE);
            prefs.setCheck_list(false);
        }
    }

    //OnClick to go to Room Services Screen
    public void roomServiceClick(View view) {
        Intent in = new Intent(HotelDetails.this, RoomServices.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
    }

    //OnClick to go to Bar Screen
    public void barClick(View view) {
        if (prefs.getBarList() == false) {
            barList.setVisibility(View.VISIBLE);
            prefs.setBarList(true);
        } else if (prefs.getBarList() == true) {
            barList.setVisibility(View.GONE);
            prefs.setBarList(false);
        }
    }

    //OnClick to go to Spa Screen
    public void spaClick(View view) {
//        Intent in = new Intent(HotelDetails.this, Restaurant.class);
//        startActivity(in);
    }

    /**
     * Method to make json object get call
     * */

    private void makeJsonObjectRequest() {

        JSONObject obj = new JSONObject();

        try {
            obj.put("hotel_id", prefs.getHotel_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Api Data", obj.toString());

        postJsonData(Config.innDemand + "hotels/details/", obj.toString());
    }

    private void makeJsonRequestBottom() {
        JSONObject objt = new JSONObject();

        try {
            objt.put("hotel_id", prefs.getHotel_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Api Data", objt.toString());

        postJsonDataBottom(Config.innDemand + "info_centre/details/", objt.toString());
    }

    //    Network Response getting all details of Hotel
//    Details: Picture, name, address, location, contact number etc
//    Values set to show on the Screen
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
                System.out.println("yohaha=success===" + response);
                try {
                    JSONObject object = new JSONObject(response);

                    hotelName = object.getString("name");
                    location = object.getString("location");
                    latitude = object.getString("latitude");
                    longitude = object.getString("longitude");
                    String address = object.getString("address");
                    final String number = object.getString("contact_number");
                    Boolean service = object.getBoolean("has_restaurant_service");
                    String restaurant_image = object.getString("restaurant_image");
                    Boolean room_service = object.getBoolean("has_room_service");
                    String room_img = object.getString("room_service_image");
                    Boolean bar_service = object.getBoolean("has_bar_service");
                    String bar_img = object.getString("bar_image");
                    Boolean spa_service = object.getBoolean("has_spa_service");
                    String spa_img = object.getString("spa_image");
                    about_hotel = object.getString("about_hotel");

                    hotel_Name.setText(hotelName);
                    hotel_Address.setText(address);

                    Picasso.with(HotelDetails.this).load(restaurant_image).into(main_backdrop);

                    prefs.setHotel_latitude(latitude);
                    prefs.setHotel_longitude(longitude);


                    Log.d("lati", latitude);
                    Log.d("longi", longitude);
                    Log.d("Name_", hotelName);
                    Log.d("Address_", address);
                    Log.d("Image", restaurant_image);
                    Log.d("COntact", number);

                    call_hotel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getFromCall();

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HotelDetails.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

    //CALL permissions Area
    public void getFromCall() {
        if (!marshMallowPermission.checkPermissionForCall()) {
            marshMallowPermission.requestPermissionForCall();
        } else {
//
            try {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("+919729305557"));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

//    Method to get response for Miscellaneous values in the hotel
//    Saving name of title & details to show in the list set at the bottom of page
    public void postJsonDataBottom(String url, String userData){
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
                System.out.println("yohaha=bottom=success===" + response);


                JSONArray array = null;
                try {
                    array = new JSONArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < array.length(); i++) {
                        try {
                            JSONObject object = array.getJSONObject(i);

                            String info_title = object.getString("info_title");
                            String info_value = object.getString("info_value");
                            String info_id = object.getString("id");
                            String info_hotel = object.getString("hotel");
                            String info_screen_key = object.getString("screen_key");


                            if(info_screen_key.contains("main") && !info_title.contains("Note")) {
                                hotelData.clear();
                                HotelData a = new HotelData(info_title, info_value);
                                hotelData.add(a);
                            }else if(info_screen_key == "laundry" || info_screen_key.equalsIgnoreCase("laundry")){
                                prefs.setLaundrynote(info_value);
                            }else if(info_screen_key == "cab" || info_screen_key.equalsIgnoreCase("cab")){
                                prefs.setCabnote(info_value);
                            }

                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
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

//    Method to get response of list of Restaurant in the hotel
//    Saving name of restaurant & show in the list
//    Saving ID of restaurant for further usage
//    Setting status (getting in response) to check which restaurant to Hide/Show through Adapter Class
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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

                        restaurantId = object.getString("id");
                        String rest_name = object.getString("name");
                        String hotel = object.getString("hotel");
                        String status = object.getString("status");

                        ListData data = new ListData(restaurantId, rest_name, status);
                        restaurantData.add(data);

                    } catch (JSONException e) {
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


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CALL_PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("+919729305557"));
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(callIntent);

                } else {
                    Toast.makeText(HotelDetails.this, "Permission Denied", Toast.LENGTH_LONG).show();

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    //ListClick
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private HotelDetails.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final HotelDetails.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

//    OnResume method to set Restaurant/Bars list
//    Activating OnCLick function, saving value & firing Intent
    @Override
    protected void onResume() {
        super.onResume();
        ((ListAdapter) restaurantAdapter).setOnItemClickListener(new ListAdapter.MyClickListener() {

            @Override
            public void onItemClick(int position, View v) {
                Log.i("TAG", " Clicked on Item " + position);
                prefs.setRestaurant_Id(restaurantData.get(position).getId());
                Log.d("Restaurant_id: ", prefs.getRestaurant_Id());
            Intent in = new Intent(HotelDetails.this, Restaurant.class);
            startActivity(in);
                prefs.setCheck_list(false);
                restaurantList.setVisibility(View.GONE);
            }
        });

        ((BarlistAdapter) barAdapter).setOnItemClickListener(new BarlistAdapter.MyClickListener() {

            @Override
            public void onItemClick(int position, View v) {
                Log.i("TAG", " Clicked on Item " + position);
                prefs.setBar_Id(barData.get(position).getId());
                Log.d("Restaurant_id: ", prefs.getBar_Id());
                Intent in = new Intent(HotelDetails.this, Bar.class);
                startActivity(in);
                prefs.setBarList(false);
                barList.setVisibility(View.GONE);
            }
        });
    }

//    Network Call to set Inclusion whether the hotel is having the facilities (Restaurant, Bar, Services, Spa)
//    Bollean value to set/get whether to show/not
//    Boolean value to show which room service is available in the hotel
//    Room services value are also mentioned(Beverages/Bath Essentials/Bed tea/coffee)

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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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
                    Boolean bathroom_essentials_towels = object.getBoolean("bathroom_essentials_towels");
                    Boolean bathroom_essentials_toiletries = object.getBoolean("bathroom_essentials_toiletries");
                    Boolean bathroom_essentials_maintenance = object.getBoolean("bathroom_essentials_maintenance");
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

                    //Condition area for Hotel Details
                    if(prefs.getRestaurant() == false)
                        restaurant_area.setVisibility(View.GONE);
                    else
                        restaurant_area.setVisibility(View.VISIBLE);

                    if(prefs.getRoom_service() == false)
                        service_area.setVisibility(View.GONE);
                    else
                        service_area.setVisibility(View.VISIBLE);

                    if(prefs.getBar()== false)
                        bar_area.setVisibility(View.GONE);
                    else
                        bar_area.setVisibility(View.VISIBLE);

                    if(prefs.getSpa()== false)
                        spa_area.setVisibility(View.GONE);
                    else
                        spa_area.setVisibility(View.VISIBLE);

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

//    Method to get timings of all hotel services(Restaurant timings/Spa timings(if there) & others like same)
//    Compare timings to Open/Close the Restaurant/Room services/Bar/Spa
    public void postJsonDataTimings(String url, String userData){
        RequestQueue mRequestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();

        final String requestBody = userData;

        System.out.println("inside post timings json data=====" + requestBody);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideProgressDialog();
                System.out.println("yohaha=timings=success===" + response);


                JSONObject object = null;
                try {
                    object = new JSONObject(response);

                    fm_bar = object.getString("from_bar");
                    fm_spa = object.getString("from_spa");
                    fm_service = object.getString("from_room_services");
                    fm_restaurant = object.getString("from_restaurant");
                    to_bar = object.getString("to_bar");
                    to_spa = object.getString("to_spa");
                    to_service = object.getString("to_room_services");
                    to_restaurant = object.getString("to_restaurant");

                    try {
                        //Calling 3 methods to test (Will set only one method finally)
                        isTimeBetweenTwoTime(fm_service, to_service, getTime);
                        isTimeBetweenRestaurant(fm_restaurant, to_restaurant, getTime);
                        isTimeBetweenBar(fm_bar, to_bar, getTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

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

    public boolean isTimeBetweenTwoTime(String argStartTime,
                                        String argEndTime, String argCurrentTime) throws ParseException {
        String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
//        AppPreferences pref = new AppPreferences(HotelDetails.this);
        //
        if (argStartTime.matches(reg) && argEndTime.matches(reg) && argCurrentTime.matches(reg)) {
            boolean valid = false;
            // Start Time
            java.util.Date startTime = new SimpleDateFormat("HH:mm:ss").parse(argStartTime);
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startTime);

            // Current Time
            java.util.Date currentTime = new SimpleDateFormat("HH:mm:ss").parse(argCurrentTime);
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(currentTime);

            // End Time
            java.util.Date endTime = new SimpleDateFormat("HH:mm:ss").parse(argEndTime);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endTime);

            //
            if (currentTime.compareTo(endTime) < 0) {

                currentCalendar.add(Calendar.DATE, 1);
                currentTime = currentCalendar.getTime();

            }

            if (startTime.compareTo(endTime) < 0) {

                startCalendar.add(Calendar.DATE, 1);
                startTime = startCalendar.getTime();

            }
            //
            if (currentTime.before(startTime)) {

                System.out.println(" Time is Lesser ");
                prefs.setFm_service(true);
                valid = false;
                System.out.println("Values , Start Time /n " + prefs.getFm_service());
            } else {

                if (currentTime.after(endTime)) {
                    endCalendar.add(Calendar.DATE, 1);
                    endTime = endCalendar.getTime();

                }

                System.out.println("Comparing , Start Time /n " + startTime);
                System.out.println("Comparing , End Time /n " + endTime);
                System.out
                        .println("Comparing , Current Time /n " + currentTime);

                if (currentTime.before(endTime)) {
                    System.out.println("RESULT, Time lies b/w");
                    prefs.setFm_service(false);
                    valid = true;
                } else {
                    valid = false;
                    System.out.println("RESULT, Time does not lies b/w");
                    prefs.setFm_service(true);
                }

            }
            return valid;

        } else {
            throw new IllegalArgumentException(
                    "Not a valid time, expecting HH:MM:SS format");
        }
    }

    public boolean isTimeBetweenRestaurant(String argStartTime,
                                        String argEndTime, String argCurrentTime) throws ParseException {
        String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
//        AppPreferences pref = new AppPreferences(HotelDetails.this);
        //
        if (argStartTime.matches(reg) && argEndTime.matches(reg) && argCurrentTime.matches(reg)) {
            boolean valid = false;
            // Start Time
            java.util.Date startTime = new SimpleDateFormat("HH:mm:ss").parse(argStartTime);
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startTime);

            // Current Time
            java.util.Date currentTime = new SimpleDateFormat("HH:mm:ss").parse(argCurrentTime);
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(currentTime);

            // End Time
            java.util.Date endTime = new SimpleDateFormat("HH:mm:ss").parse(argEndTime);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endTime);

            //
            if (currentTime.compareTo(endTime) < 0) {

                currentCalendar.add(Calendar.DATE, 1);
                currentTime = currentCalendar.getTime();

            }

            if (startTime.compareTo(endTime) < 0) {

                startCalendar.add(Calendar.DATE, 1);
                startTime = startCalendar.getTime();

            }
            //
            if (currentTime.before(startTime)) {

                System.out.println(" Time is Lesser ");
                prefs.setFm_restaurant(true);
                valid = false;
                System.out.println("Values , Start Time /n " + prefs.getFm_service());
            } else {

                if (currentTime.after(endTime)) {
                    endCalendar.add(Calendar.DATE, 1);
                    endTime = endCalendar.getTime();

                }

                System.out.println("Comparing , Start Time /n " + startTime);
                System.out.println("Comparing , End Time /n " + endTime);
                System.out
                        .println("Comparing , Current Time /n " + currentTime);

                if (currentTime.before(endTime)) {
                    System.out.println("RESULT, Time lies b/w");
                    prefs.setFm_restaurant(false);
                    valid = true;
                } else {
                    valid = false;
                    System.out.println("RESULT, Time does not lies b/w");
                    prefs.setFm_restaurant(true);
                }

            }
            return valid;

        } else {
            throw new IllegalArgumentException(
                    "Not a valid time, expecting HH:MM:SS format");
        }
    }

    public boolean isTimeBetweenBar(String argStartTime,
                                           String argEndTime, String argCurrentTime) throws ParseException {
        String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";

        if (argStartTime.matches(reg) && argEndTime.matches(reg) && argCurrentTime.matches(reg)) {
            boolean valid = false;
            // Start Time
            java.util.Date startTime = new SimpleDateFormat("HH:mm:ss").parse(argStartTime);
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startTime);

            // Current Time
            java.util.Date currentTime = new SimpleDateFormat("HH:mm:ss").parse(argCurrentTime);
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(currentTime);

            // End Time
            java.util.Date endTime = new SimpleDateFormat("HH:mm:ss").parse(argEndTime);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endTime);

            //
            if (currentTime.compareTo(endTime) < 0) {

                currentCalendar.add(Calendar.DATE, 1);
                currentTime = currentCalendar.getTime();

            }

            if (startTime.compareTo(endTime) < 0) {

                startCalendar.add(Calendar.DATE, 1);
                startTime = startCalendar.getTime();

            }
            //
            if (currentTime.before(startTime)) {

                System.out.println(" Time is Lesser ");
                prefs.setFm_bar(true);
                valid = false;
                System.out.println("Values , Start Time /n " + prefs.getFm_service());
            } else {

                if (currentTime.after(endTime)) {
                    endCalendar.add(Calendar.DATE, 1);
                    endTime = endCalendar.getTime();

                }

                System.out.println("Comparing , Start Time /n " + startTime);
                System.out.println("Comparing , End Time /n " + endTime);
                System.out
                        .println("Comparing , Current Time /n " + currentTime);

                if (currentTime.before(endTime)) {
                    System.out.println("RESULT, Time lies b/w");
                    prefs.setFm_bar(false);
                    valid = true;
                } else {
                    valid = false;
                    System.out.println("RESULT, Time does not lies b/w");
                    prefs.setFm_bar(true);
                }

            }
            return valid;

        } else {
            throw new IllegalArgumentException(
                    "Not a valid time, expecting HH:MM:SS format");
        }
    }

//    Method to get response of list of Bars in the hotel
//    Saving name of bar & show in the list
//    Saving ID of bar for further usage
//    Setting status (getting in response) to check which Bar to Hide/Show through Adapter Class
    public void postJsonDataBar(String url, String userData){
        RequestQueue mRequestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();

        final String requestBody = userData;

        System.out.println("inside post barList json data=====" + requestBody);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("yohaha=barList=success===" + response);


                JSONArray array = null;
                try {
                    array = new JSONArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject object = array.getJSONObject(i);

                        barId = object.getString("id");
                        String bar_name = object.getString("name");
                        String hotel = object.getString("hotel");
                        String status = object.getString("status");

                        BarlistData data = new BarlistData(restaurantId, bar_name, status);
                        barData.add(data);

                    } catch (JSONException e) {
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

//    Custom pop-up designed for Checkout Click
//    Checkout click on hotel details page to checkout from hotel
    public void checkoutClick(){
        // custom dialog
        final Dialog dialog = new Dialog(HotelDetails.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.checkout);

        // set the custom dialog components - text, image and button
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Button checkout = (Button) dialog.findViewById(R.id.checkout_click);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Json Parsing to send hotel details to checkout.
                        JSONObject obj = new JSONObject();
                        try {
                            obj.put("checkin_id", prefs.getCheckin_Id());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        postJsonData(Config.innDemand + "checkins/checkout/", obj.toString());
                        prefs.setCheckout("1");
                        prefs.setIs_In_Hotel(false);
                        Toast.makeText(HotelDetails.this, "Successfully Checked Out", Toast.LENGTH_LONG).show();
                        Intent in = new Intent(HotelDetails.this, SplashScreen.class);
                        startActivity(in);
                        finish();
            }
        });

        Button not = (Button) dialog.findViewById(R.id.notnow_click);
        not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    //Custom pop-up for Network Click
    public void networkClick(){
        // custom dialog
        final Dialog dialog = new Dialog(HotelDetails.this);
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

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("loading details....");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    //Registration ID Call
    public void dataRegistration(){
        JSONObject obj = new JSONObject();

        try {
            obj.put("hotel_id", prefs.getHotel_id());
            obj.put("registration_key", prefs.getRefreshToken());
            obj.put("checkin_id", prefs.getCheckin_Id());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Api RefreshToken Data", obj.toString());

        postJsonDataToken(Config.innDemand +"not_registration_key/details/", obj.toString());
    }

    public void postJsonDataToken(String url, String userData){

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
                System.out.println("Notification===success===" + response);
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

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String reg_token = intent.getStringExtra("reg_id");
            prefs.setRefreshToken(reg_token);
        }
    };
}
