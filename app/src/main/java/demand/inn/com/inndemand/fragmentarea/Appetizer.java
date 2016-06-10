package demand.inn.com.inndemand.fragmentarea;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import demand.inn.com.inndemand.adapter.RestaurantAdapter;
import demand.inn.com.inndemand.constants.AppetiserData;
import demand.inn.com.inndemand.constants.Config;
import demand.inn.com.inndemand.roomservice.Restaurant;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.volleycall.AppController;

/**
 * Created by akash
 */

public class Appetizer extends Fragment {

    //Utility
    NetworkUtility nu;
    AppPreferences prefs;

    //Others
    View view;
    Context mContext;
    String id;
    String itemName;
    String itemDesc;
    String category;
    String food;
    String restaurant;
    String subCategory;
    String amount;

    //UI call area
    TextView cart_item, cart_total;
    LinearLayout menu_options;


    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private List<AppetiserData> cardList;

    String filterName = "", filterDesc = "", filterPrice = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.appetizer, container, false);
        nu = new NetworkUtility(getActivity());
        prefs = new AppPreferences(getActivity());

        //UI initialize
        cart_item = (TextView) view.findViewById(R.id.appetiser_items);
        cart_total = (TextView) view.findViewById(R.id.appetiser_total);
        cart_total.setText("Total: Rs 2000");
        cart_item.setText("(10 items)");


        //ListItems in RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        cardList = new ArrayList<>();
        adapter = new RestaurantAdapter(getActivity(), cardList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerView.setAdapter(adapter);

        callMethod();

        //mehtod to send Restaurant ID to server & to get response.

        return  view;
    }

    public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        public SimpleDividerItemDecoration(Context context) {
            mDivider = ContextCompat.getDrawable(context,R.drawable.line_divider);
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


    public void callMethod(){
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
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap

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
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject object = array.getJSONObject(i);
                        Log.d("API", "API Daa"+array);
//                        id = object.getString(String.valueOf(id));
                        Log.d("API", "API ID"+id);
                        itemName = object.getString("name");
                        Log.d("API", "API na"+itemName);
                        itemDesc = object.getString("description");
                        category = object.getString("category");
                        Log.d("API", "API Ca"+category);
                        food = object.getString("food");
//                        restaurant = object.getString(String.valueOf(restaurant));
                        subCategory = object.getString("subcategory");
                        amount = object.getString("price");

                        if(category.contains("starter") || category.equalsIgnoreCase("Starter")) {
                            AppetiserData a = new AppetiserData(category, itemName, itemDesc, "Rs:"+amount);
                            cardList.add(a);

                            adapter.notifyDataSetChanged();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
