package demand.inn.com.inndemand.login;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.adapter.HotelAdapter;
import demand.inn.com.inndemand.constants.Config;
import demand.inn.com.inndemand.constants.HotelData;
import demand.inn.com.inndemand.mapdirection.Mapping;
import demand.inn.com.inndemand.roomservice.Restaurant;
import demand.inn.com.inndemand.roomservice.RoomServices;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.volleycall.AppController;
import demand.inn.com.inndemand.welcome.BaseActivity;
import demand.inn.com.inndemand.welcome.SplashScreen;

/**
 * Created by akash
 */

public class HotelDetails extends AppCompatActivity {

    //Utility Class Area
    NetworkUtility nu;
    AppPreferences prefs;

    //UI Call
    Button checkout;
    TextView hotel_Name, hotel_Address, hotel_desc, call_hotel;
    ImageLoader imageLoader;
    NetworkImageView imageView;
    ImageView main_backdrop;
    Toolbar toolbar;

    //Others
    String callHotel;
    String URL, TAG;
    String about_hotel;

    //List Items Bottom
    private RecyclerView recyclerView;
    private HotelAdapter adapter;
    private List<HotelData> hotelData;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hoteldetails);
        nu = new NetworkUtility(HotelDetails.this);
        prefs = new AppPreferences(HotelDetails.this);
        prefs.setCheckout("2");

        URL = "";

        imageLoader = AppController.getInstance().getImageLoader();
//        imageView.setImageUrl(URL, imageLoader);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                //noinspection SimplifiableIfStatement
                if (id == R.id.action_settings) {
                    return true;
                } else if (id == R.id.action_cart) {
//                    Intent in = new Intent(HotelDetails.this, MyCart.class);
//                    startActivity(in);
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                } else if (id == R.id.action_notification) {

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
                    collapsingToolbarLayout.setTitle("InnDemand");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle("");
                    isShow = false;
                }
            }
        });

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
        hotelData = new ArrayList<>();
        adapter = new HotelAdapter(this, hotelData);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

//        if(nu.isConnectingToInternet()) {
        recyclerView.setAdapter(adapter);

        makeJsonObjectRequest();
        makeJsonRequestBottom();
       /* }else{

        }*/

    }

    public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        public SimpleDividerItemDecoration(Context context) {
            mDivider = ContextCompat.getDrawable(context, R.drawable.list_divider);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    //Button onClicklistener to Checkout from the hotel & redirect to Splash Screen
    public void checkOut(View view) {

        //Json Parsing to send hotel details to checkout.
        JSONObject obj = new JSONObject();
        try {
            obj.put("checkin_id", prefs.getCheckin_Id());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        postJsonData(Config.innDemand + "checkins/checkout/", obj.toString());
        prefs.setCheckout("1");
        Intent in = new Intent(HotelDetails.this, SplashScreen.class);
        startActivity(in);
        finish();
    }

    public void direction(View view) {
        Intent in = new Intent(HotelDetails.this, Mapping.class);
        startActivity(in);
    }

    //OnClick to go to Restaurant Screen
    public void restaurantClick(View view) {
        Intent in = new Intent(HotelDetails.this, Restaurant.class);
        startActivity(in);
    }

    //OnClick to go to Room Services Screen
    public void roomServiceClick(View view) {
        Intent in = new Intent(HotelDetails.this, RoomServices.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
    }

    //OnClick to go to Bar Screen
    public void barClick(View view) {
//        Intent in = new Intent(HotelDetails.this, Beverages.class);
//        startActivity(in);
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
            obj.put("hotel_id", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Api Data", obj.toString());

        postJsonData(Config.innDemand+"hotels/details/", obj.toString());
    }

    private void makeJsonRequestBottom(){
        JSONObject objt = new JSONObject();

        try {
            objt.put("hotel_id", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Api Data", objt.toString());

        postJsonDataBottom(Config.innDemand+"info_centre/details/", objt.toString());
    }

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

                    String name = object.getString("name");
                    String location = object.getString("location");
                    String latitude = object.getString("latitude");
                    String longitude = object.getString("longitude");
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

                    hotel_Name.setText(name);
                    hotel_Address.setText(address);

                    Picasso.with(HotelDetails.this).load(restaurant_image).into(main_backdrop);

                    Log.d("Name_", name);
                    Log.d("Address_", address);
                    Log.d("Image", restaurant_image);
                    Log.d("COntact", number);

                    call_hotel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (ActivityCompat.checkSelfPermission(HotelDetails.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.

                                // Should we show an explanation?
                                if (ActivityCompat.shouldShowRequestPermissionRationale(HotelDetails.this,
                                        Manifest.permission.CALL_PHONE)) {

                                    // Show an expanation to the user *asynchronously* -- don't block
                                    // this thread waiting for the user's response! After the user
                                    // sees the explanation, try again to request the permission.

                                    AlertDialog.Builder builder = new AlertDialog.Builder(HotelDetails.this);
                                    builder.setMessage("Need to Call")
                                            .setPositiveButton("Call", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }
                                            });

                                    AlertDialog dialog = builder.create();
                                    dialog.setTitle("Permissions");
                                    dialog.show();

                                } else {

                                    // No explanation needed, we can request the permission.

//                                    ActivityCompat.requestPermissions(HotelDetails.this,
//                                            new String[]{Manifest.permission.CALL_PHONE},
//                                            MY_PERMISSIONS_REQUEST_CALL);

                                    // MY_PERMISSIONS_REQUEST_CALL is an
                                    // app-defined int constant. The callback method gets the
                                    // result of the request.
                                }



                                return;
                            }

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

                            prefs.setSave_data(info_value);

                            HotelData a = new HotelData(info_title, info_value);
                            hotelData.add(a);

                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_CALL: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    // permission was granted, yay! Do the
//                    // contacts-related task you need to do.
//
//                } else {
//
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                }
//                return;
//            }
//
//            // other 'case' lines to check for other
//            // permissions this app might request
//        }
    }

}
