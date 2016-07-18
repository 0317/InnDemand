package demand.inn.com.inndemand.model;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
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

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.adapter.ViewPagerAdapter;
import demand.inn.com.inndemand.constants.Config;
import demand.inn.com.inndemand.fragmentarea.Appetizer;
import demand.inn.com.inndemand.roomservice.Beverages;
import demand.inn.com.inndemand.roomservice.Restaurant;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.volleycall.AppController;
import demand.inn.com.inndemand.welcome.DBAdapter;

/**
 * Created by akash
 */

public class SearchDB extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    //Utility Class
    NetworkUtility nu;
    AppPreferences prefs;

    private ListView mListView;
    private SearchView searchView;
    private DBAdapter mDbHelper;

    private TextView customerText;
    private TextView nameText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchlist);
        nu = new NetworkUtility(this);
        prefs  =new AppPreferences(this);

        searchView = (SearchView) findViewById(R.id.search);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);

        mListView = (ListView) findViewById(R.id.list);

        getCategory();

        mDbHelper = new DBAdapter(this);
        mDbHelper.open();

        //Clean all Demand
        mDbHelper.deleteAllDemand();
        //Add some data as a sample
        mDbHelper.createDemand("Akash", "Taj", "Noida, Delhi", "Kingfisher", "Kinley", "White towel", "park Avenue");
        mDbHelper.createDemand("JB", "Oberoi", "Mumbai", "Club10", "Bisleri", "towel", "pears");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDbHelper  != null) {
            mDbHelper.close();
        }
    }

    @Override
    public boolean onClose() {
        showResults("");
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        showResults(query + "*");
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        showResults(newText + "*");
        return false;
    }

    private void showResults(String query) {

        Cursor cursor = mDbHelper.searchDemand((query != null ? query.toString() : "@@@@"));

        if (cursor == null) {
            //
        } else {
            // Specify the columns we want to display in the result
            String[] from = new String[]{
                DBAdapter.KEY_CUSTOMER,
                    DBAdapter.KEY_NAME,
                    DBAdapter.KEY_ADDRESS,
                    DBAdapter.KEY_SODA,
                    DBAdapter.KEY_WATER,
                    DBAdapter.KEY_TOWEL,
                    DBAdapter.KEY_SOAP
            };

            // Specify the Corresponding layout elements where we want the columns to go
            int[] to = new int[] {
                    R.id.scustomer,
                    R.id.sname,
                    R.id.saddress,
                    R.id.scity,
                    R.id.sstate,
                    R.id.szipCode};

            // Create a simple cursor adapter for the definitions and apply them to the ListView
            SimpleCursorAdapter customers = new SimpleCursorAdapter(this,R.layout.searchresult, cursor, from, to);
            mListView.setAdapter(customers);

            // Define the on-click listener for the list items
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Get the cursor, positioned to the corresponding row in the result set
                    Cursor cursor = (Cursor) mListView.getItemAtPosition(position);

                    if(cursor.equals("Starter")){
                        Intent in = new Intent(SearchDB.this, Beverages.class);
                        startActivity(in);
                    }else{
                        Intent in = new Intent(SearchDB.this, Restaurant.class);
                        startActivity(in);
                    }

                    searchView.setQuery("",true);
                }
            });
        }
    }

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
                        //catName = Response (category names to show in tablayout)
                        String type_id = object.getString("id");
                        String catName = object.getString("name");

                        //catType = Response 1/2/3 (1 = Bar, 2 = Restaurant, 3 = Spa)
                        String catType = object.getString("category_type");

                        Log.d("API", "API Category: " + catName);

                        mDbHelper.createDemand(catName, "", "", "", "", "", "");


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
}
