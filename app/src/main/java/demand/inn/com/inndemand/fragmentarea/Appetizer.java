package demand.inn.com.inndemand.fragmentarea;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

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

import javax.net.ssl.HttpsURLConnection;

import demand.inn.com.inndemand.Helper.OnItemCLick;
import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.adapter.RestaurantAdapter;
import demand.inn.com.inndemand.database.DBHelper;
import demand.inn.com.inndemand.model.AppetiserData;
import demand.inn.com.inndemand.model.ResturantDataModel;
import demand.inn.com.inndemand.model.SimpleDividerItemDecoration;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.volleycall.AppController;

/**
 * Created by akash
 */

public class Appetizer extends Fragment {

    //Utility Class call area
    NetworkUtility nu;
    AppPreferences prefs;

    //Others
    View view;

//    String to define to value
//    Values fetching from APIs in the form of String
    String id;
    String itemName;
    String itemDesc;
    String category;
    String food;
    String subCategory;
    String amount;

    //Cart entry Click
    String result_price;

    //Recyclerview(List) and Adapter classes(Data) to show list and data in that list
    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private List<AppetiserData> cardList;
    OnItemCLick onCLick;

    //Constant Class (get/set) required to set and get data for the screen
    ResturantDataModel dataModel;

    //Loading call area
    ProgressDialog dialog;

    //Constant Class (get/set) required to set and get data for the screen
    ResturantDataModel a;

    //String for different values for data coming from API
    public String idCat, subCat, names, desc, price, foods;

    //Constant Class (get/set) required to set and get data for the screen
    ResturantDataModel resturantDataModel;

    //DATABASE CLASSES
    DBHelper db;

    //String to get dynamic Translated data
    public String destinationString = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.appetizer, container, false);
        //Utility Class Initialize
        nu = new NetworkUtility(getActivity());
        prefs = new AppPreferences(getActivity());

        //Database Class Initialisation
        db = new DBHelper(getActivity());

        //Custom toolbar class call
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

//        Code to call Broadcast Receiver to get or send data In or to different activity
//        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
//                new IntentFilter("position-message"));

        idCat = getArguments().getString("id_type");

        Log.d("names", "name"+subCat);
        Log.d("names", "cat"+names);

        //ListItems in RecyclerView with adapter class to set all the items in list
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        cardList = new ArrayList<>();
        adapter = new RestaurantAdapter(getActivity(), cardList, cardList);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerView.setAdapter(adapter);

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
                List<AppetiserData> rest_list = db.getAllDatarl();

                Log.d("RestaurantModel: ", "Check: "+rest_list);

                for(AppetiserData data : rest_list){
                    AppetiserData model = new AppetiserData(data.getTitle(),
                            data.getDescription(), data.getCategory());
                    cardList.add(model);
                }
            }
        } else {
                networkClick();
        }

        return view;
    }

    /*
     * Here receives data from Fragments class Adapter in the form of broadcast
     * Here we are hiting the server to get details of Restaurant food stuff by sending
     * category ID and Restaurant ID
     */
   /* public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String dataPositions = intent.getStringExtra("positionsof");
            JSONObject obj = new JSONObject();
            try {
               *//* obj.put("category_id", idCat);
                obj.put("restaurant_id", prefs.getRestaurant_Id());*//*
                obj.put("category_id", "");
                obj.put("restaurant_id", "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("Check CategoryType", obj.toString());

            postJsonData(Config.innDemand+"restaurant_items/details/", obj.toString());
        }
    };*/


    /*
     * Volley Library Main method to get Category and other requirements as response by sending
     * restaurant ID
     */
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
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
//                        dataModel = new ResturantDataModel(i);
                        Log.d("API", "API Daa"+array);
                        Log.d("API", "API ID"+id);
                        itemName = object.getString("name");
                        Log.d("API", "API na"+itemName);
                        itemDesc = object.getString("description");
                        category = object.getString("category");
                        Log.d("API", "API Ca"+category);
                        food = object.getString("food");
                        subCategory = object.getString("subcategory");
                        amount = object.getString("price");
                        JSONObject ob = object.getJSONObject("average");
                        String average = ob.getString("feedback_points__avg");

                      /*  try {
                            String subCate = new getTraslatedString().execute(
                                    prefs.getLocaleset(), subCategory).get();
                            String subNamez = new getTraslatedString().execute(
                                    prefs.getLocaleset(), itemName).get();
                            String subDescs = new getTraslatedString().execute(
                                    prefs.getLocaleset(), itemDesc).get();
                            String subAmount = new getTraslatedString().execute(
                                    prefs.getLocaleset(), amount).get();
                            String subFood = new getTraslatedString().execute(
                                    prefs.getLocaleset(), food).get();
                            String subRating = new getTraslatedString().execute(
                                    prefs.getLocaleset(), average).get();

                            dbs.insertData(new ResturantDataModel(subCate, subNamez, subDescs,
                                    subAmount, subFood, subRating));*/

                          /*  dbs.insertData(new ResturantDataModel(subCategory, itemName, itemDesc,
                                amount, food, average));
*/
//                        storage.insertData(new ResturantDataModel(subCategory, itemName, itemDesc,
//                                amount, food, average));

                           /* } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }*/

                        /*List<ResturantDataModel> datas = dbs.getAllData();
                        for(ResturantDataModel card : datas) {
                            cardList.clear();
                                a = new ResturantDataModel(card.getSubcategory(), card.getName(),
                                        card.getDescription(), card.getPrice(), card.getFood(),
                                        card.getRating());
                                cardList.add(a);


                        }
                            adapter.notifyDataSetChanged();*/

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s " +
                            "using %s", requestBody, "utf-8");
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
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)
                item.getMenuInfo();
        int listPosition = info.position;
        String price= cardList.get(listPosition).getPrice();//list item price

        result_price = price;

        return  true;
    }



    //Translation Area Coding .....
    //Google Translate API call to translate Dynamic data

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

    //Custom pop-up for Internet Check (If connected or not)
    public void networkClick(){
        // custom dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.network);

        // set the custom dialog components - text, image and button
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Button checkout = (Button) dialog.findViewById(R.id.ok_click);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        dialog.show();
    }
}
