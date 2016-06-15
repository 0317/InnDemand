package demand.inn.com.inndemand.roomservice;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
import java.util.Random;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.adapter.RestaurantAdapter;
import demand.inn.com.inndemand.constants.AppetiserData;
import demand.inn.com.inndemand.constants.Config;
import demand.inn.com.inndemand.fragmentarea.Appetizer;
import demand.inn.com.inndemand.fragmentarea.DefaultFragment;
import demand.inn.com.inndemand.fragmentarea.Dessert;
import demand.inn.com.inndemand.fragmentarea.MainCourse;
import demand.inn.com.inndemand.gcm.GCMNotifications;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.volleycall.AppController;

/**
 * Created by akash
 */

public class Restaurant extends AppCompatActivity {

    //Utility
    NetworkUtility nu;
    AppPreferences prefs;

    //UI Call area
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter adapter;
    TextView restaurant_text;

    View view;
    private Menu menu;
    int position;

    Toolbar toolbar;

    //TabClass Call
    Appetizer mAppetizer;
    Dessert mDessert;
    MainCourse mMaincourse;
    DefaultFragment fragment;

    //Others
    String id;
    String itemName;
    String itemDesc;
    String category;
    String food;
    String restaurant;
    String subCategory;
    String amount;
    String tabName;

    private final Random mRandom = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);

        //Toolbar call
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
                                    mAppetizer.notifyChange();
                                    toolbar.getMenu().getItem(0).setIcon(R.mipmap.ic_menu_filter);
                                    JSONObject obj = new JSONObject();
                                    try {
                                        obj.put("restaurant_id", prefs.getRestaurant_Id());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Log.d("Check API Data", obj.toString());

                                    mAppetizer.postJsonData(Config.innDemand + "restaurant_items/details/", obj.toString());

                                    return  true;

                                case R.id.action_veg:
                                    toolbar.getMenu().getItem(0).setIcon(R.mipmap.ic_menu_filter_green);
                                    JSONObject objs = new JSONObject();
                                    try {
                                        objs.put("restaurant_id", prefs.getRestaurant_Id());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Log.d("Check API Data", prefs.getRestaurant_food());

                                    if(food == "1") {
                                        mAppetizer.postJsonData(Config.innDemand + "restaurant_items/details/", objs.toString());
                                    }

                                    return true;

                                case R.id.action_nonveg:
                                    toolbar.getMenu().getItem(0).setIcon(R.mipmap.ic_menu_filter_red);

                                    JSONObject objt = new JSONObject();
                                    try {
                                        objt.put("restaurant_id", prefs.getRestaurant_Id());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Log.d("Check API Data", objt.toString());

                                    if(food == "2") {
                                        mAppetizer.postJsonData(Config.innDemand + "restaurant_items/details/", objt.toString());
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
                    collapsingToolbarLayout.setTitle("Restaurant");
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
        mDessert = new Dessert();
        mMaincourse = new MainCourse();
        fragment  = new DefaultFragment();

        viewPager = (ViewPager) findViewById(R.id.container);


        //Tab call area
//        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        if(mAppetizer.isVisible())
            Toast.makeText(getApplicationContext(), "Appetiser", Toast.LENGTH_LONG).show();
        else if(mMaincourse.isVisible())
            Toast.makeText(getApplicationContext(), "Main Course", Toast.LENGTH_LONG).show();

        getData();

        if(prefs.getFm_restaurant() == true)
            restaurant_text.setText("NOTE: Restaurant Services are not available");
        else
            restaurant_text.setText("NOTE: It will take a minimum of 60 mins to prepare the food");

    }

    private void setupViewPager(ViewPager viewPager) {

    }

//    public void addTab(View view) {
//        String cheese = Cheeses.sCheeseStrings[mRandom.nextInt(Cheeses.sCheeseStrings.length)];
//        String cheese = "Appetiser";
//        adapter.addTab(cheese);
//    }
//    public void selectFirstTab(View view) {
//        if (tabLayout.getTabCount() > 0) {
//            viewPager.setCurrentItem(0);
//        }
//    }
//    public void removeTab(View view) {
//        adapter.removeTab();
//    }

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
//                        id = object.getString(String.valueOf(id));
                        Log.d("API", "API ID" + id);
                        itemName = object.getString("name");
                        Log.d("API", "API na" + itemName);
                        itemDesc = object.getString("description");
                        category = object.getString("category");
                        Log.d("API", "API Ca" + category);
                        food = object.getString("food");
//                        restaurant = object.getString(String.valueOf(restaurant));
                        subCategory = object.getString("subcategory");
                        amount = object.getString("price");

                        prefs.setItemName(itemName);
                        prefs.setItemDesc(itemDesc);
                        prefs.setPrice(amount);
//                        prefs.setCategory(category);
                        prefs.setRestaurant_food(food);

                        adapter = new ViewPagerAdapter(getSupportFragmentManager());

//                        adapter.addFragment(fragment, category);
//                        adapter.addFragment(fragment, category);
//                        adapter.addFragment(fragment, category);
//                        adapter.addFragment(fragment, category);
//                        adapter.addFragment(fragment, category);
                        adapter.addFragment(mAppetizer, "Appetiser");
                        adapter.addFragment(mMaincourse, "Main Course");
                        adapter.addFragment(mDessert, category);

                        adapter.notifyDataSetChanged();
                        viewPager.setAdapter(adapter);

                        tabLayout.setupWithViewPager(viewPager);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Restaurant.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
