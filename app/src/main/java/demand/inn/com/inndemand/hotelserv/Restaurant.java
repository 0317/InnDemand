package demand.inn.com.inndemand.hotelserv;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.adapter.RestaurantAdapter;
import demand.inn.com.inndemand.adapter.ViewPagerAdapter;
import demand.inn.com.inndemand.cartarea.MyCart;
import demand.inn.com.inndemand.constants.FragmentData;
import demand.inn.com.inndemand.constants.TabData;
import demand.inn.com.inndemand.database.DBHelper;
import demand.inn.com.inndemand.fragmentarea.AppetiserBar;
import demand.inn.com.inndemand.fragmentarea.Appetizer;
import demand.inn.com.inndemand.gcm.GCMNotifications;
import demand.inn.com.inndemand.model.ResturantDataModel;
import demand.inn.com.inndemand.model.SearchDB;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;

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

    //Others requirements (if need)
    View view;
    private Menu menu;
    int positions;

    //Alert-dialog
    private ProgressDialog mProgressDialog;

    //TabClass Class call for dynamic approach of tabs (create multiple tabs automatically)
    Appetizer mAppetizer;

    //String to define to value
    //Values fetching from APIs in the form of String
    String type_id;
    String totalCash;
    String totalItems;
    String broaditemName;

    //Getting Restaurant name via Intent in a String
    String restName;

    //Adding values in the cart in the form of String
    String cart_value;
    String cart_val;

    //Fragments data in the screen and List of tabs in tab layout shown
    List<FragmentData> tabList;

    //List to add category(tabs)
    List<TabData> tabData;

    //Adapter Class with String requirements
    List<ResturantDataModel> resturantDataModelList;
    ResturantDataModel dataModel, model;

    //String which provides final result after Translation
    public String destinationString = "";

    //DATABASE CLASS CALL AREA
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant);
        //Utility Class Initialize
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);
        db = new DBHelper(this);
        resturantDataModelList = new ArrayList<>();
        dataModel = new ResturantDataModel();
        tabData = new ArrayList<>();

        //Constant Class n Database calss initialize
        model = new ResturantDataModel();

        restName = getIntent().getStringExtra("rest_name");

        /*
         * Toolbar call for the screen
         * Includes menu on the top right through restaurant_menu.xml file and back press icon
         */
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


        /*
         * Happens when We scroll up the screen and the Image turned into toolbar
         * It is basically that effect toggling Image into toolbar n vice-versa
         * Setting title for Collapsing Toolbar as Restaurant Name also
         */
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
        //setupViewPager(viewPager);
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

        if(nu.isConnectingToInternet()) {
//            showProgressDialog();
            if(prefs.getFm_restaurant() == true) {
                restaurant_text.setText(R.string.restaurant_servenotavailable);
                hideProgressDialog();
            } else {
                restaurant_text.setText(R.string.restaurant_serveavailable);
                hideProgressDialog();
//                getData();
            }
        }else{
            networkClick();
        }

        tabList = new ArrayList<>();

         /*
         * Here trying to get Category for the Fragments (Tabs)
         * Data saving in DB from Server in last work out
         * Fetching category from Database (SQLite)
         */
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        List<TabData> tabdata = db.getAllCategory();

        Log.d("TabData", "check: "+tabdata);
        for(TabData tab : tabdata) {
//            Bundle bundle = new Bundle();
//            bundle.putString("category_id", tab.getName());

            TabData data = new TabData(tab.getName(), tab.getType());
            tabData.add(data);

            Fragment appFrf = new Appetizer();
            Bundle bundle = new Bundle();
            bundle.putString("title",tab.getName());
            appFrf.setArguments(bundle);


            adapter.addFragment(appFrf, tab.getName());

            adapter.notifyDataSetChanged();
            viewPager.setAdapter(adapter);

            tabLayout.setupWithViewPager(viewPager);

            adapter.notifyDataSetChanged();
        }

//        Cursor cursor = db.getData(data.getCategory());
//        cursor.moveToFirst();
//        String getValue = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_RRCATEGORY));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("PageSelected", "Check: "+position);
                Intent in = new Intent("position-message");
                in.putExtra("positionsof", type_id);
                LocalBroadcastManager.getInstance(Restaurant.this).sendBroadcast(in);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /*
     * Here receives data from Fragments class Adapter in the form of broadcast
     * getting counter for the items selected n items detail in Restaurant food list
     */
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

    /*
     * Method is to add items to cart with onClick
     * Shows a pop-up asking view cart or not now
     */
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


    //Alert-dialog to show when data is loading
    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("loading...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    //hides the dialog pop-up after data gets loaded
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


    /*
     * API call to translate data
     * Translation coding to Translate all the data coming from server in target language
     */
    public class getTraslatedString extends AsyncTask<String, String, String>{

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
