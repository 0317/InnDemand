package demand.inn.com.inndemand;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
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
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

import demand.inn.com.inndemand.adapter.BarlistAdapter;
import demand.inn.com.inndemand.adapter.CircleTransform;
import demand.inn.com.inndemand.adapter.HotelAdapter;
import demand.inn.com.inndemand.adapter.ListAdapter;
import demand.inn.com.inndemand.adapter.ViewPagerAdapter;
import demand.inn.com.inndemand.cartarea.MyCart;
import demand.inn.com.inndemand.constants.BarlistData;
import demand.inn.com.inndemand.constants.Config;
import demand.inn.com.inndemand.constants.HotelData;
import demand.inn.com.inndemand.constants.ListData;
import demand.inn.com.inndemand.constants.TabData;
import demand.inn.com.inndemand.database.DBHelper;
import demand.inn.com.inndemand.gcm.GCMNotifications;
import demand.inn.com.inndemand.hotelserv.Bar;
import demand.inn.com.inndemand.hotelserv.Restaurant;
import demand.inn.com.inndemand.mapdirection.MapArea;
import demand.inn.com.inndemand.model.AppetiserData;
import demand.inn.com.inndemand.model.ResturantDataModel;
import demand.inn.com.inndemand.roomservice.RoomServices;
import demand.inn.com.inndemand.setting.FeedbackList;
import demand.inn.com.inndemand.setting.OrderHistory;
import demand.inn.com.inndemand.setting.Settings;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.volleycall.AppController;
import demand.inn.com.inndemand.welcome.SplashScreen;


public class DashBoard extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    //Utility Class Area (Internet Connection and Preference Class)
    NetworkUtility nu;
    AppPreferences prefs;

    //UI Call area for the screen
    Button checkout;
    TextView hotel_Name, hotel_Address, hotel_desc, call_hotel;
    ImageLoader imageLoader;
    NetworkImageView imageView;
    ImageView main_backdrop;
    Toolbar toolbar;


    public static Activity dash;

    //Navigation Details (shows Name, Email and DP on Navigation Drawer)
    ImageView dp;
    TextView dp_name, dp_email;
    Bundle getBundle = null;

    //Area to Show Details of Hotel(Includes Restaurant/ Services/ Spa/ Bar or not)
    LinearLayout restaurant_area, service_area, spa_area, bar_area;

    //Others
    //String to call hotel, Hotel name, Hotel Pic
    String callHotel;
    String URL, TAG;
    String about_hotel;
    String hotelName;

    //List Items with Recyclerviews (showing list) and Adapter classes (showing data in the list)
    private RecyclerView recyclerView, restaurantList, barList;
    private HotelAdapter adapter;
    private ListAdapter restaurantAdapter;
    private BarlistAdapter barAdapter;
    private List<HotelData> hotelData;
    HotelData datas;
    private List<ListData> restaurantData;
    private List<BarlistData> barData;

    //String for Timings Call API (to check and match the timings)
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

    //Current Date & Time
    Calendar c;
    SimpleDateFormat df, timeFormat;
    String formattedDate, getTime;

    //Call to press back button twice to exit from app
    boolean doubleBackToExitPressedOnce = false;

    //Alert-Dialog
    private ProgressDialog mProgressDialog;

    //Preferences Call
    SharedPreferences settings;

    //Google Client to know the status (If need)
    GoogleApiClient mGoogleApiClient;

    //Translate API String to get/fetch the final result
    public String destinationString = "";

    //Restaurant Tabs category List
    List<ResturantDataModel> resturantDataModelList;

    //DATABASE CLASS CALL AREA
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_dash_board);
        //Utility Class and Preferences ClassInitialisation
        nu = new NetworkUtility(DashBoard.this);
        prefs = new AppPreferences(DashBoard.this);
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        dash = this;
        db = new DBHelper(this);
        datas = new HotelData();

        //Blank String URl call and Volley Imageloader library call to load images from server
        URL = "";
        imageLoader = AppController.getInstance().getImageLoader();

        //To hide default toolbar within the screen
        getSupportActionBar().hide();


        /*
         *Calling toolbar from layout
         *Sets menu in the toolbar calling main_menu.xml
         *Assigning different functionality to different icons in the menu by getting there IDs
         */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.action_settings) {
                    return true;
                } else if (id == R.id.action_cart) {
                    Intent in = new Intent(DashBoard.this, MyCart.class);
                    startActivity(in);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                } else if (id == R.id.action_notification) {
                    Intent in = new Intent(DashBoard.this, GCMNotifications.class);
                    startActivity(in);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                return true;
            }
        });

        /*
         *Preferences to check different requirements
         *To check if user Checked into hotel
         *To check if the task to Checked into Hotel is completed
         *To check If the User is in the Hotel
         */
        prefs.setHotel_check(true);
        prefs.setCheckout("2");
        prefs.setIs_task_completed(true);
        prefs.setIs_In_Hotel(true);

        //Google Class call code to check/get status of the User (If Sign-In then User can Sign-Out)
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        //Navigation Drawer layout Code to toggle either slides to open or to close
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

         /*
          *Navigation Drawer area to set profile details
          *Details like DP, Name, Email on Navigation area
          */
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.removeHeaderView(navigationView.getHeaderView(0));

        View profile = navigationView.inflateHeaderView(R.layout.nav_header_dash_board);
        profile.findViewById(R.id.head_view);
        dp = (ImageView) profile.findViewById(R.id.imageView);
        dp_name = (TextView) profile.findViewById(R.id.drawer_name);
        dp_email = (TextView) profile.findViewById(R.id.drawer_email);


        //Setting values for DP, Name, Email in Navigation Drawer view
        if(prefs.getUser_fbpic() == null || prefs.getUser_fbpic().equalsIgnoreCase("")) {
            Picasso.with(DashBoard.this).load(prefs.getUser_gpicture())
                    .transform(new CircleTransform()).into(dp);
        }else {
            Picasso.with(DashBoard.this).load(prefs.getUser_fbpic())
                    .transform(new CircleTransform()).into(dp);
        }

        if(prefs.getUser_fbname() == null || prefs.getUser_fbname().equalsIgnoreCase("")) {
            dp_name.setText(prefs.getUser_gname());
        } else {
            dp_name.setText(prefs.getUser_fbname());
        }

        if(prefs.getUser_fbemail() == null || prefs.getUser_fbemail().equalsIgnoreCase("")) {
            dp_email.setText(prefs.getUser_gemail());
        } else {
            dp_email.setText(prefs.getUser_fbemail());
        }

        /**
         * Happens when We scroll up the screen and the Image turned into toolbar
         * It is basically that effect toggling Image into toolbar n vice-versa
         * Setting title for Collapsing Toolbar as Hotel Name also
         */
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.collapsing);
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
                    if(prefs.getLocaleset() == "en" || prefs.getLocaleset().equals("en")){
                        collapsingToolbarLayout.setTitle(prefs.getHotel_name());
                    }else{
                        try {
                            String htName = new getTraslatedString().execute(prefs.getLocaleset(),
                                    prefs.getHotel_name()).get();

                            collapsingToolbarLayout.setTitle(htName);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle("");
                    isShow = false;
                }
            }
        });

        //Current date and time coding with UTC timezone
        c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        timeFormat = new SimpleDateFormat("HH:mm:ss");
        formattedDate = df.format(c.getTime());
        getTime = timeFormat.format(c.getTime());
        // formattedDate have current date/time

        /*
         * Here we are calling all the APIs to show data in the screen
         * Data like: Hotel Details, Restaurant, Bar list etc
         */
        if(nu.isConnectingToInternet()) {
            //Mehod to get Timings for Hotel Services(eg: Restaurant/Bar/Room Services)
            timings();
            if(prefs.getCategory_check() == false) {
                getCategory();
            }
            if(prefs.getData_check() == false){
                getData();
            }
        }else{
            networkClick();
        }

        //UI Class call Initialize
        restaurant_area = (LinearLayout) findViewById(R.id.hotel_restaurant);
        service_area = (LinearLayout) findViewById(R.id.hotel_roomservice);
        spa_area = (LinearLayout) findViewById(R.id.hotel_spa);
        bar_area = (LinearLayout) findViewById(R.id.hotel_bar);

       /**
        * Here we are initially hiding the Services by hotel either it is restaurant/bar/spa
        * We will show these details if get a response to show these in the API else Hide.
        * Its like If the hotel is having only Restaurant and Bar but not spa
        * then we will show only restaurant and Bar and Spa will be hide (Response based on APIs)
        */
        restaurant_area.setVisibility(View.GONE);
        service_area.setVisibility(View.GONE);
        bar_area.setVisibility(View.GONE);
        spa_area.setVisibility(View.GONE);


        /*
         * This Imageview shows the Hotel Image at the Top inside Collapsing toolbar
         * Volley method for the Image (can cacha if needed)
         */
        main_backdrop = (ImageView) findViewById(R.id.main_backdrop);
        //If you are using normal ImageView
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

        //UI Class initialize for Hotel Name, Address, and call
        hotel_Name = (TextView) findViewById(R.id.hotel_Name);
        hotel_Address = (TextView) findViewById(R.id.hotel_Address);
        call_hotel = (TextView) findViewById(R.id.call_hotel);

            try {
                if(prefs.getLocaleset() == "en" || prefs.getLocaleset().equals("en")){
                    hotel_Name.setText(prefs.getHotel_name());
                    hotel_Address.setText(prefs.getHotel_address());
                }else {
                    String htName = new getTraslatedString().execute(prefs.getLocaleset(),
                            prefs.getHotel_name()).get();
                    String htAddress = new getTraslatedString().execute(prefs.getLocaleset(),
                            prefs.getHotel_address()).get();

                    hotel_Name.setText(htName);
                    hotel_Address.setText(htAddress);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        Picasso.with(DashBoard.this).load(prefs.getHotel_dp()).into(main_backdrop);

        //variable to get Hotel contact number
        callHotel = "";

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

//        Initialisation of ListItems in RecyclerView with all adapter classes required
//        The whole setup manages the Recyclerview (List) and Data inside the List.
        recyclerView = (RecyclerView) findViewById(R.id.hotel_recycler_view);
        restaurantList = (RecyclerView) findViewById(R.id.hotel_recycler_list);
        barList = (RecyclerView) findViewById(R.id.hotel_barlist);
        restaurantList.setVisibility(View.GONE);
        barList.setVisibility(View.GONE);
        hotelData = new ArrayList<>();
        restaurantData  = new ArrayList<>();
        barData = new ArrayList<>();
        adapter = new HotelAdapter(this, hotelData);
        restaurantAdapter = new ListAdapter(this, restaurantData);
        barAdapter = new BarlistAdapter(this, barData);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        RecyclerView.LayoutManager mLayoutManagers = new LinearLayoutManager(this);
        restaurantList.setLayoutManager(mLayoutManagers);
        restaurantList.setItemAnimator(new DefaultItemAnimator());
        restaurantList.addItemDecoration(new SimpleDividerItemDecoration(this));

        RecyclerView.LayoutManager mLayoutManagerss = new LinearLayoutManager(this);
        barList.setLayoutManager(mLayoutManagerss);
        barList.setItemAnimator(new DefaultItemAnimator());
        barList.addItemDecoration(new SimpleDividerItemDecoration(this));

        recyclerView.setAdapter(adapter);
        restaurantList.setAdapter(restaurantAdapter);
        barList.setAdapter(barAdapter);

        adapter.notifyDataSetChanged();
        restaurantAdapter.notifyDataSetChanged();
        barAdapter.notifyDataSetChanged();


        /*
         * Adding Misc info Data fetching from server in last work out(done b4 this class)
         * Here in below code getting all the data from Database
         * Also satisfying conditions for Translation(If any time happens)
         */
        List<HotelData> htdata = db.getAllData();

        for(HotelData ddt : htdata){
            if(prefs.getLocaleset() == "en" || prefs.getLocaleset().equals("en")){
                HotelData dt = new HotelData(ddt.getTitle(), ddt.getDesc(), ddt.getScreen_key());
                hotelData.add(dt);
            }else {
                try {
                    String infoName = new getTraslatedString().execute(prefs.getLocaleset(),
                            ddt.getTitle()).get();
                    String infoDesc = new getTraslatedString().execute(prefs.getLocaleset(),
                            ddt.getDesc()).get();

                    HotelData dt = new HotelData(infoName, infoDesc, ddt.getScreen_key());
                    hotelData.add(dt);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * Adding Restaurants List Data fetching from server in last work out(done b4 this class)
         * Here in below code getting all the data from Database
         * Also satisfying conditions for Translation(If any time happens)
         */
        List<ListData> ltdata = db.getAllDatas();

        for(ListData lsdata : ltdata){
            if(prefs.getLocaleset() == "en" || prefs.getLocaleset().equals("en")) {
                ListData ldata = new ListData(lsdata.getTitle(), lsdata.getStatus(), lsdata.getId());
                restaurantData.add(ldata);
            }else{
                try {
                    String infoName = new getTraslatedString().execute(prefs.getLocaleset(),
                            lsdata.getTitle()).get();
                    String infoDesc = new getTraslatedString().execute(prefs.getLocaleset(),
                            lsdata.getStatus()).get();

                    ListData ldata = new ListData(infoName, infoDesc, lsdata.getId());
                    restaurantData.add(ldata);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        /*
         * Adding Bars List Data fetching from server in last work out(done b4 this class)
         * Here in below code getting all the data from Database
         * Also satisfying conditions for Translation(If any time happens)
         */
        List<BarlistData> lttdata = db.getAllDatab();

        for(BarlistData lsdata : lttdata){
            if(prefs.getLocaleset() == "en" || prefs.getLocaleset().equals("en")) {
                BarlistData ldata = new BarlistData(lsdata.getTitle(), lsdata.getStatus(),
                        lsdata.getId());
                barData.add(ldata);
            }else {
                try {
                    String infoName = new getTraslatedString().execute(prefs.getLocaleset(),
                            lsdata.getTitle()).get();
                    String infoDesc = new getTraslatedString().execute(prefs.getLocaleset(),
                            lsdata.getStatus()).get();

                    BarlistData ldata = new BarlistData(infoName, infoDesc, lsdata.getId());
                    barData.add(ldata);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }


        /*
         * This area is for Recyclerview Click for Restaurants in the list
         */
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                recyclerView, new ClickListener() {
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

//    BackPress method to double press the back button to exit from app
    @Override
    public void onBackPressed() {
      /*  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/

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

//    Default Menu Method to call menu on the Top right of the screen
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

//    Default Menu call area by getting items ID (not in use)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    Method to call different menu in Navigation list
//    Actions performed based on the IDs of the menu items in the list
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            // Handle the camera action
        }/* else if (id == R.id.nav_gallery) {
            Intent in = new Intent(DashBoard.this, RoomServices.class);
            startActivity(in);
            finish();

        }*/ else if (id == R.id.nav_history) {
            Intent in = new Intent(DashBoard.this, OrderHistory.class);
            startActivity(in);
        } else if (id == R.id.nav_share) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                    "Inndemand App - https://play.google.com/store?hl=en");
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        } else if (id == R.id.nav_settings) {
            Intent in = new Intent(DashBoard.this, Settings.class);
            startActivity(in);
        }else if (id == R.id.nav_feedback) {
            Intent in = new Intent(DashBoard.this, FeedbackList.class);
            startActivity(in);
        }else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_signout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DashBoard.this);
            builder.setMessage(R.string.sign_out_hotel)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(prefs.getFacebook_logged_In() == true){
                                if(LoginManager.getInstance()!=null)
                                    prefs.clearPref();
                                LoginManager.getInstance().logOut();
                                Toast.makeText(DashBoard.this, R.string.successful_logout ,
                                        Toast.LENGTH_LONG).show();
                                prefs.setFacebook_logged_In(false);
                                Intent in = new Intent(DashBoard.this, SplashScreen.class);
                                startActivity(in);
                                finish();

                            }else if(prefs.getGoogle_logged_In() == true){
                                prefs.setGoogle_logged_In(false);
//                if(mGoogleApiClient.isConnected())
                                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                        new ResultCallback<Status>() {
                                            @Override
                                            public void onResult(Status status) {
                                                prefs.clearPref();
                                                Toast.makeText(DashBoard.this,
                                                        R.string.successful_logout ,
                                                        Toast.LENGTH_LONG).show();
                                                Intent in = new Intent(DashBoard.this,
                                                        SplashScreen.class);
                                                startActivity(in);
                                                finish();
                                            }
                                        });
//                mGoogleApiClient.disconnect();
                            }
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.setTitle("Are you sure?");
            dialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


//    RecyclerView Decoration area
//    Defining gap between every item in the list by calling xml file list_divider.xml
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

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                        child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    //API call to get category (Tab data/different tabs to show) for the screen
    public void getCategory(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("hotel_id", prefs.getHotel_id());
            obj.put("category_type", "2");
            obj.put("place_id", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("Check API Category Data", obj.toString());

        postJsonDataCategory(Config.innDemand+"category/details/", obj.toString());
    }

    //API call to get Food list items for the Recyclerview
    public void getData(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("restaurant_id", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("Check API Data", obj.toString());

        postJsonData(Config.innDemand+"restaurant_items/details/", obj.toString());

    }

    //API call to set timings for opening the Restaurant/Bar/Spa or Room Services.
    public void timings(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("hotel_id", prefs.getHotel_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Api Hotel Data", obj.toString());

        postJsonDataTimings(Config.innDemand+"timing/details/", obj.toString());
    }

    /*
     * Button onClicklistener to Checkout from the hotel & redirect to Splash Screen
     * Pop-up opens to show either to check-out from hotel or not now options.
     */
    public void checkOut(View view) {

        checkoutClick();
    }

    /*
     * Getting details for Hotel Location through latitude n longitude
     * Intent fire to get/set details on Map.
     */
    public void direction(View view) {
        Intent in = new Intent(DashBoard.this, MapArea.class);
        in.putExtra("latitude", "28.4089");
        in.putExtra("longitude", "77.3178");
        startActivity(in);
    }

    /*
     * OnClick the restaurant options to load Restaurants List of the Hotel
     * Click once will show the list and Click again hides according to preference check
     */
    public void restaurantClick(View view) {
        if(prefs.getCheck_list() == false) {
            restaurantList.setVisibility(View.VISIBLE);
            prefs.setCheck_list(true);
        }else if(prefs.getCheck_list() == true){
            restaurantList.setVisibility(View.GONE);
            prefs.setCheck_list(false);
        }
    }

    //OnClick the Room Services option to go to Room Services Screen
    public void roomServiceClick(View view) {
        Intent in = new Intent(DashBoard.this, RoomServices.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
    }

    /*
     * OnClick the Bar options to load Bars List of the Hotel
     * Click once will show the list and Click again hides according to preference check
     */
    public void barClick(View view) {
        if(prefs.getBarList() == false) {
            barList.setVisibility(View.VISIBLE);
            prefs.setBarList(true);
        }else if(prefs.getBarList() == true){
            barList.setVisibility(View.GONE);
            prefs.setBarList(false);
        }
    }

    /*
     * OnClick the Spa options to load Spas List of the Hotel
     * Click once will show the list and Click again hides according to preference check
     */
    public void spaClick(View view) {
    // working is still left
    }


    /*
     * Not in Use
     * Marshmallow permissions override method
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
    }

    //OnClick Listener for Recyclerview List OnItemClick
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    //Custom method to get OnItemClick method of Recyclerview working
    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private DashBoard.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView,
                                     final DashBoard.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context,
                    new GestureDetector.SimpleOnGestureListener() {
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
//    Set values and also Includes OnItemClick of the list fires Intent to another Screen
    @Override
    protected void onResume() {
        super.onResume();
        ((ListAdapter) restaurantAdapter).setOnItemClickListener(new ListAdapter.MyClickListener() {

            @Override
            public void onItemClick(int position, View v) {
                Log.i("TAG", " Clicked on Item " + position);
                prefs.setRestaurant_Id(restaurantData.get(position).getId());
                Log.d("Restaurant_id: ", prefs.getRestaurant_Id());
                Intent in = new Intent(DashBoard.this, Restaurant.class);
                in.putExtra("rest_name", restaurantData.get(position).getTitle());
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
                Intent in = new Intent(DashBoard.this, Bar.class);
                in.putExtra("bar_name", barData.get(position).getTitle());
                startActivity(in);
                prefs.setBarList(false);
                barList.setVisibility(View.GONE);
            }
        });
    }

    /*
       * Here in this method we are trying to fetch Restaurant Items List
       * Items List includes: Category (Tab data)
       */
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

                        String type_id = object.getString("id");

                        //catName = Response (category names to show in tablayout)
                        String catName = object.getString("name");

                        //catType = Response 1/2/3 (1 = Bar, 2 = Restaurant, 3 = Spa)
                        String catType = object.getString("category_type");

                        //catStatus = Response 0/1 (check if 0, remove the tab attribute)
                        Log.d("API", "API Category: " + catName);

                        db.insertCategory(new TabData(catName, catType));
                        prefs.setCategory_check(true);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(Restaurant.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        //mRequestQueue.add(stringRequest);
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    /*
    * Volley Library Main method to get Category and other requirements as response by sending
    * restaurant ID
    */
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
                        ResturantDataModel dataModel = new ResturantDataModel(i);
                        Log.d("API", "API Daa" + array);
                        String iids = object.getString("id");
                        String itemName = object.getString("name");

                        String iitemDescription = object.getString("description");
                        String category = object.getString("category");
                        Log.d("API", "API Cat" + category);
                        String food = object.getString("food");

                        String subCategory = object.getString("subcategory");
                        String amount = object.getString("price");

                        db.insertRestaurant(new ResturantDataModel(itemName, iitemDescription,
                                category, amount));
                        prefs.setData_check(true);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(Restaurant.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
     * Here in this method we check timings for Restaurant/Bar/Spa
     * Timings like: If timings match then only loads data of restaurant/bar/Spa
     * For eg: If timings for restaurant are 11:00am-11:00pm then Only in between this timings
     * API will load data else no data will get loaded
     * For Room Services If it is out of time then set disable on Roomservices buttons.
     */
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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

//    Coding to generate current timings and match this timings with the API timings
//    Timings code to load Restaurant/Bar/Spa/Room Services
    public boolean isTimeBetweenTwoTime(String argStartTime, String argEndTime,
                                        String argCurrentTime) throws ParseException {
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

    public boolean isTimeBetweenRestaurant(String argStartTime, String argEndTime,
                                           String argCurrentTime) throws ParseException {
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

    public boolean isTimeBetweenBar(String argStartTime, String argEndTime,
                                    String argCurrentTime) throws ParseException {
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

    //    Custom pop-up designed for Checkout Click
//    Checkout click on hotel details page to checkout from hotel
    public void checkoutClick(){
        // custom dialog
        final Dialog dialog = new Dialog(DashBoard.this);
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

//                postJsonData(Config.innDemand + "checkins/checkout/", obj.toString());
                prefs.setCheckout("1");
                prefs.setIs_In_Hotel(false);
                Toast.makeText(DashBoard.this, "Successfully Checked Out",
                        Toast.LENGTH_LONG).show();
                Intent in = new Intent(DashBoard.this, SplashScreen.class);
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

    //Custom pop-up for Internet Check (If connected or not)
    public void networkClick(){
        // custom dialog
        final Dialog dialog = new Dialog(DashBoard.this);
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

//    API call to translate data
//    Translation coding to Translate all the data coming from server in target language
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

//Added sample comment
}
