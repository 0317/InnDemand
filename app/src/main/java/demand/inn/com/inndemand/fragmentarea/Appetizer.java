package demand.inn.com.inndemand.fragmentarea;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import demand.inn.com.inndemand.Helper.OnItemCLick;
import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.adapter.RestaurantAdapter;
import demand.inn.com.inndemand.constants.Utils;
import demand.inn.com.inndemand.database.DBHelper;
import demand.inn.com.inndemand.model.AppetiserData;
import demand.inn.com.inndemand.model.ResturantDataModel;
import demand.inn.com.inndemand.model.SimpleDividerItemDecoration;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;

/**
 * Created by akash
 */

public class Appetizer extends Fragment {

    //Utility Class call area
    NetworkUtility nu;
    AppPreferences prefs;

    //Others
    View view;

    //Cart entry Click
    String result_price;

    //Recyclerview(List) and Adapter classes(Data) to show list and data in that list
    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private List<AppetiserData> cardList;
    OnItemCLick onCLick;

    //String for different values for data coming from API
    public String idCat, subCat, names, desc, price, foods;

    //DATABASE CLASSES CALL AREA
    DBHelper db;

    //String to get dynamic Translated data
    public String destinationString = "";

    // create boolean for fetching data
    private boolean isViewShown = false;

    String title = "";
    int is_set = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments().getString("title") != null)
            title = getArguments().getString("title");

        Log.d("CategoryName","Checking:"+title);
    }

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
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter("position-message"));

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
                //method to send Restaurant ID to server & to get response.
            }
        } else {
                networkClick();
        }

            List<AppetiserData> rest_list = db.getAllDatarl();

           /* AppetiserData rest_lists = db.getAllDatarls(title);

            Log.d("RestaurantModel: ", "Check: "+rest_list);

        AppetiserData modelss = new AppetiserData(rest_lists.getName(), rest_lists.getDescription(),
                rest_lists.getCategory(), rest_lists.getPrice(), "");
        cardList.add(modelss);
*/

           /* for(AppetiserData data : rest_list) {
                is_set = Integer.parseInt(data.getId());
                Cursor cursor;
                cursor = db.getData(is_set);
                if (cursor.getCount() != 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            String name = cursor.getString
                                    (cursor.getColumnIndex(DBHelper.COLUMN_RRNAME));
                            String desc = cursor.getString
                                    (cursor.getColumnIndex(DBHelper.COLUMN_RRDESC));
                            String price = cursor.getString
                                    (cursor.getColumnIndex(DBHelper.COLUMN_RRAMOUNT));
                            String sub = cursor.getString
                                    (cursor.getColumnIndex(DBHelper.COLUMN_RRTABS));

                            AppetiserData modelss = new AppetiserData(name, desc, "", price, sub);
                            cardList.add(modelss);

                        }*/


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Cursor cursor = db.getDetail(title);
        if (cursor != null) {
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndexOrThrow
                        (Utils.FeedEntry.COLUMN_RRNAME));
                String subtitle = cursor.getString(cursor.getColumnIndexOrThrow
                        (Utils.FeedEntry.COLUMN_RRDESC));

                new AlertDialog.Builder(getActivity()).setMessage(title)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).create().show();
//                AppetiserData modelss = new AppetiserData(title, subtitle, "", "", "");
//                cardList.add(modelss);
            }
        }
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

   /* @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null) {
            isViewShown = true;
            // fetchdata() contains logic to show data when page is selected mostly asynctask to fill the data
            List<AppetiserData> rest_list = db.getAllDatarl();

            Log.d("RestaurantModel: ", "Check: "+rest_list);

            for(AppetiserData data : rest_list){
                AppetiserData models = new AppetiserData(data.getName(),
                        data.getDescription(), data.getCategory(), data.getPrice());
                cardList.add(models);
            }
        } else {
            isViewShown = false;
        }
    }*/

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
