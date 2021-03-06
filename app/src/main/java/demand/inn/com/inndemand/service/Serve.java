package demand.inn.com.inndemand.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import demand.inn.com.inndemand.constants.Config;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.volleycall.AppController;

/**
 * Created by akash
 */

public class Serve extends Service {

    AppPreferences prefs;
    Context mContext;
    SharedPreferences settings;

    String name;

    private boolean mRunning;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mRunning = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        prefs = new AppPreferences(mContext);
        settings = getSharedPreferences("InDemand", MODE_PRIVATE);
        String restoredText = settings.getString("checkin", null);
        if (restoredText != null) {
            name = settings.getString("checkin", "");
        }
        Log.d("Hotel ID"," Check: "+prefs.getHotel_id());
        Log.d("Checkin ID"," Check: "+prefs.getCheckin_Id());
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        settings = PreferenceManager.getDefaultSharedPreferences(mContext);

        if (!mRunning) {
            mRunning = true;
            // do something
            notiHit();
            Toast.makeText(getApplicationContext(), "Notification Service Started", Toast.LENGTH_LONG).show();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void notiHit() {

        JSONObject obj = new JSONObject();
        try {
            obj.put("hotel_id", settings.getString("hotelID", ""));
            obj.put("checkin_id", settings.getString("checkinID", ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Api Notification Data", obj.toString());

        postJsonData(Config.innDemand + "checkins/notification_for_new_checkin/", obj.toString());
    }

    //API call method to POST data to the server
    public void postJsonData(String url, String userData){

        RequestQueue mRequestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();

        final String requestBody = userData;

        System.out.println("inside post json data====="+requestBody);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("pending==Notification==="+response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
