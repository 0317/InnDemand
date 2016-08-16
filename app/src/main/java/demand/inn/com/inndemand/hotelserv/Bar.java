package demand.inn.com.inndemand.hotelserv;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
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
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.adapter.ViewPagerAdapter;
import demand.inn.com.inndemand.cartarea.MyCart;
import demand.inn.com.inndemand.constants.Config;
import demand.inn.com.inndemand.constants.FragmentData;
import demand.inn.com.inndemand.constants.TabData;
import demand.inn.com.inndemand.database.DBHelper;
import demand.inn.com.inndemand.fragmentarea.AppetiserBar;
import demand.inn.com.inndemand.fragmentarea.Appetizer;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.volleycall.AppController;

/**
 * Created by akash
 */

public class Bar extends AppCompatActivity{

    //Utility Clas Area
    NetworkUtility nu;
    AppPreferences prefs;

    //UI Call area for the screen
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter adapter;
    TextView restaurant_text;
    Toolbar toolbar;

    //Others
    View view;
    private Menu menu;

    //Fragment Class
    AppetiserBar mAppetiser;

    //Fragments data in the screen and List of tabs in tab layout shown
    List<FragmentData> tabList;

    //List to add category(tabs)
    List<TabData> tabData;

    //String to define to value
    //Values fetching from APIs in the form of String
    String id;
    String itemName;
    String itemDesc;
    String category;
    String food;
    String subCategory;
    String amount;

    //Getting Bar name via Intent in a String
    String barName;

    //DATABASE CLASS CALL AREA
    DBHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bar);
        //Utility Class Initialize
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);
        db = new DBHelper(this);
        tabData = new ArrayList<>();

        barName = getIntent().getStringExtra("bar_name");

        //Toolbar call for the screen
//        Includes menu on the top right through restaurant_menu.xml file and back press icon
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.inflateMenu(R.menu.restaurant_menu);
        this.menu = menu;
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                //noinspection SimplifiableIfStatement
                if (id == R.id.action_settings) {
                    return true;
                }else if(id == R.id.action_cart){
//                    Intent in = new Intent(Restaurant.this, MyCart.class);
//                    startActivity(in);
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }else if(id == R.id.action_notification){

                }else if(id == R.id.action_food){
                    final View menuItemView = findViewById(R.id.action_food);
                    final PopupMenu popup = new PopupMenu(Bar.this, menuItemView);
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
                                    return true;

                                case R.id.action_nonveg:
                                    toolbar.getMenu().getItem(0).setIcon(R.mipmap.ic_menu_filter_red);
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
                    collapsingToolbarLayout.setTitle(barName);
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle("");
                    isShow = false;
                }
            }
        });

        mAppetiser = new AppetiserBar();
        tabList = new ArrayList<>();

        viewPager = (ViewPager) findViewById(R.id.container);

        //Tab call area
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        //UI TextView Initialize
        restaurant_text = (TextView) findViewById(R.id.restaurant_text);

        getData();

        if(prefs.getFm_restaurant() == true)
            restaurant_text.setText("NOTE: Bar Services are not available");
        else
            restaurant_text.setText("NOTE: It will take a minimum of 60 mins to get the order");


        adapter = new ViewPagerAdapter(getSupportFragmentManager());
         /*
         * Here trying to get Category for the Fragments (Tabs)
         * Data saving in DB from Server in last work out
         * Fetching category from Database (SQLite)
         */
        List<TabData> tabdata = db.getAllCategory();

        Log.d("TabData", "check: "+tabdata);
        for(TabData tab : tabdata) {
//            Bundle bundle = new Bundle();
//            bundle.putString("category_id", tab.getName());

            TabData data = new TabData(tab.getName(), tab.getType());
            tabData.add(data);

            Fragment appFrf = new AppetiserBar();
//            appFrf.setArguments(bundle);

            adapter.addFragment(appFrf, tab.getName());

            adapter.notifyDataSetChanged();
            viewPager.setAdapter(adapter);

            tabLayout.setupWithViewPager(viewPager);

            adapter.notifyDataSetChanged();
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        public void addTab(String title) {
            mFragmentTitleList.add(title);
            notifyDataSetChanged();
        }
        public void removeTab() {
            if (!mFragmentTitleList.isEmpty()) {
                mFragmentTitleList.remove(mFragmentTitleList.size() - 1);
                notifyDataSetChanged();
            }
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    /*
    * Method is to add items to cart with onClick
    * Shows a pop-up asking view cart or not now
    */
    public void addCart(View view){
//        db.insertData(new CartData(broaditemName, totalCash, totalItems));
        new AlertDialog.Builder(Bar.this).setMessage(R.string.restaurant_foodadded_tocart)
                .setPositiveButton(R.string.viewcart, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent in = new Intent(Bar.this, MyCart.class);
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

//    API call to send bar ID to server to get list of Bar items
    public void getData(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("bar_id", prefs.getBar_Id());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("Check API Data", obj.toString());

        postJsonData(Config.innDemand+"bar_items/details/", obj.toString());

    }

//    Volley Library Main method to get Details of Bar items list as response
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
                System.out.println("yohaha==data==success===" + response);

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
                        Log.d("API", "API Daa" + array);
                        Log.d("API", "API ID" + id);
                        itemName = object.getString("name");
                        Log.d("API", "API na" + itemName);
                        itemDesc = object.getString("description");
                        category = object.getString("category");
                        Log.d("API", "API Ca" + category);
                        food = object.getString("food");

                        subCategory = object.getString("subcategory");
                        amount = object.getString("price");

                        prefs.setItemName(itemName);
                        prefs.setItemDesc(itemDesc);
                        prefs.setPrice(amount);
//                        prefs.setCategory(category);
                        prefs.setRestaurant_food(food);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(Bar.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

}
