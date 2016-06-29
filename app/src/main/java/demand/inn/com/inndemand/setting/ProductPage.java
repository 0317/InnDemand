package demand.inn.com.inndemand.setting;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.constants.Config;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.volleycall.AppController;

/**
 * Created by akash
 */
public class ProductPage extends AppCompatActivity{

    //Utility Class Area
    NetworkUtility nu;
    AppPreferences prefs;

    //Other variables
    String itemName, itemDesc, itemPrice;

    //UI Area
    TextView name, desc, price;
    Button submit;
    EditText feedback;
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.productpage);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("About Product");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.mipmap.ic_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //UI Initialize
        name = (TextView) findViewById(R.id.product_name);
        desc = (TextView) findViewById(R.id.product_desc);
        price = (TextView) findViewById(R.id.product_price);

        submit = (Button) findViewById(R.id.submit_product);

        feedback = (EditText) findViewById(R.id.product_feedback);

        itemName = getIntent().getStringExtra("itemname");
        itemDesc = getIntent().getStringExtra("itemdesc");
        itemPrice = getIntent().getStringExtra("itemprice");

        name.setText(itemName);
        desc.setText(itemDesc);
        price.setText(itemPrice);
    }

    public void submitFeedback(View view){
        String feed = feedback.getText().toString().trim();

        if(feed == null || feed.equals("")){
            new AlertDialog.Builder(this).setMessage("Please feedback us")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).create().show();
        }else{
            JSONObject obj = new JSONObject();

            try{
                obj.put("feedback", feed);
            }catch (JSONException e){
                e.printStackTrace();
            }

            Log.d("API Data", "Data: "+obj.toString());

            postJsonData(Config.innDemand+"", obj.toString());
        }
    }

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
                System.out.println("yohaha=feedback==success==="+response);
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
