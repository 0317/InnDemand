package demand.inn.com.inndemand.fragmentarea;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import demand.inn.com.inndemand.Helper.OnItemCLick;
import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.adapter.BarlistAdapter;
import demand.inn.com.inndemand.adapter.ListAdapter;
import demand.inn.com.inndemand.adapter.RestaurantAdapter;
import demand.inn.com.inndemand.constants.AppetiserData;
import demand.inn.com.inndemand.constants.Config;
import demand.inn.com.inndemand.constants.ListData;
import demand.inn.com.inndemand.model.ResturantDataModel;
import demand.inn.com.inndemand.model.SimpleDividerItemDecoration;
import demand.inn.com.inndemand.roomservice.Bar;
import demand.inn.com.inndemand.roomservice.Restaurant;
import demand.inn.com.inndemand.setting.ProductPage;
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

    //Cart Click
    String result_price;

    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private List<ResturantDataModel> cardList;
    OnItemCLick onCLick;

    //Loading call area
    ProgressDialog dialog;

    ResturantDataModel a;
    public String subCat, names, desc, price, foods;

    ResturantDataModel resturantDataModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.appetizer, container, false);
        nu = new NetworkUtility(getActivity());
        prefs = new AppPreferences(getActivity());


        subCat = getArguments().getString("subCategory");
        names  = getArguments().getString("name");
        desc = getArguments().getString("desc");
        price = getArguments().getString("price");
        foods = getArguments().getString("food");

        Log.d("names", "name"+subCat);
        Log.d("names", "cat"+names);

        //ListItems in RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        cardList = new ArrayList<>();
        adapter = new RestaurantAdapter(getActivity(), cardList, cardList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ResturantDataModel data = cardList.get(position);
//                Toast.makeText(HotelDetails.this, data.getTitle(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        a = new ResturantDataModel(subCat, names, desc, price, foods);
        cardList.add(a);

        adapter.notifyDataSetChanged();

        if (nu.isConnectingToInternet()) {
            if (prefs.getFm_restaurant() == true) {
                new AlertDialog.Builder(getActivity()).setMessage("Restaurant is closed")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).create().show();
            } else {
                //mehtod to send Restaurant ID to server & to get response.
//                callMethod();
            }
        } else {

        }

        return view;
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String data = intent.getStringExtra("bundle");

        }
    };


    public void callMethod(){
        dialog = new ProgressDialog(getActivity());
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
        final Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap

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

//                                a = new AppetiserData(subCategory, itemName, itemDesc, "Rs: " + amount, food);
//                                cardList.add(a);

//                            a = new ResturantDataModel(subCategory, itemName, itemDesc, "Rs: " + amount, food);
//                            cardList.add(a);

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

    public void notifyChange(){
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int listPosition = info.position;
        String price= cardList.get(listPosition).getPrice();//list item price

        result_price = price;

        return  true;
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private Appetizer.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final Appetizer.ClickListener clickListener) {
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
    public void onResume() {
        super.onResume();
        ((RestaurantAdapter) adapter).setOnItemClickListener(new RestaurantAdapter.MyClickListener() {

            @Override
            public void onItemClick(int position, View v) {
                Log.i("TAG", " Clicked on Item " + position);
                Intent in = new Intent(getActivity(), ProductPage.class);
                in.putExtra("itemname", cardList.get(position).getName());
                in.putExtra("itemdesc", cardList.get(position).getDescription());
                in.putExtra("itemprice", cardList.get(position).getPrice());
                startActivity(in);

            }
        });
    }
}
