/*
package demand.inn.com.inndemand.welcome;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import demand.inn.com.inndemand.constants.ApiType;
//import demand.inn.com.inndemand.controller.webservice.VolleyHelper;
import demand.inn.com.inndemand.model.request.VolleyGenericRequest;
import demand.inn.com.inndemand.model.response.ServiceResponse;
import demand.inn.com.inndemand.model.response.error.JVolleyError;
import demand.inn.com.inndemand.parser.BaseParser;
import demand.inn.com.inndemand.parser.IParser;
import demand.inn.com.inndemand.utils.CommonEventHandler;
import demand.inn.com.inndemand.volleycall.LruBitmapCache;
import demand.inn.com.inndemand.utils.StringUtils;
import demand.inn.com.inndemand.utils.Utils;
import demand.inn.com.inndemand.constants.Config;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

*/
/**
 * Created by Akash
 *//*


public class BaseActivity extends AppCompatActivity implements
        Response.Listener, Response.ErrorListener, CommonEventHandler {
    public LruBitmapCache mImageCache;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        mImageCache = LruBitmapCache.getImageCache();

    }




    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (savedInstanceState != null) {
            //we are restoring from previous state
            //instance created because of device configuration changes(rotation)

            //updating listener for pending requests
//            VolleyHelper.getInstance(this).updateListeners(this);
        } else {
            //we don't have any previous state to restore
            // perhaps this must be default creation
            //requestData(-1, null);
        }


    }




    */
/**
     * Helper method for making a http POST request
     *
     * @param url         request url
     * @param eventType   request event type
     * @param map         post body params as map
     * @param postData    string/json post body
     * @param contentType content type for distinguishing json/plain text request
    private void postData(String url, int eventType, HashMap<String, String> map, String postData, int contentType, IParser parser, boolean put) {
     * @param parser      parser object tobe used for response parsing
     *//*



    url = addSessionId(url, eventType);
    try {
        VolleyGenericRequest req = null;
        if (map != null) {
            req = new VolleyGenericRequest(VolleyGenericRequest.ContentType.FORM_ENCODED_DATA, url, map, this, this, this);
        } else {
            if(put)
                req = new VolleyGenericRequest(contentType, url, postData, this, this, this, 1);
            else
                req = new VolleyGenericRequest(contentType, url, postData, this, this, this);
        }
        req.setEventType(eventType);

        req.setParser(parser == null ? new BaseParser() : parser);

        VolleyHelper.getInstance(this).addRequestInQueue(req);
        //   Log.d("URL:  ", url);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    */
/**
     * Helper method for making a http GET request
     *
     * @param url         request url
     * @param eventType   request event type
     * @param postData    string/json post body
     * @param contentType content type for distinguishing json/plain text request
     * @param parser      parser object tobe used for response parsing
     *//*

    public void postData(String url, int eventType, String postData, int contentType, IParser parser) {
        postData(url, eventType, null, postData, contentType, parser, false);

    }


    */
/**
     * Helper method for making a http GET request
     *
     * @param url         request url
     * @param eventType   request event type
     * @param postData    string/json post body
     * @param contentType content type for distinguishing json/plain text request
     * @param parser      parser object tobe used for response parsing
     *//*

    public void putData(String url, int eventType, String postData, int contentType, IParser parser) {
        postData(url, eventType, null, postData, contentType, parser, true);

    }







    */
/**
     * Helper method for making a http post request
     *
     * @param url       request url
     * @param eventType request event type
     * @param map       post body params as map
     * @param parser    parser object tobe used for response parsing
     *//*

    public void postData(String url, int eventType, HashMap<String, String> map, IParser parser) {
        postData(url, eventType, map, null, VolleyGenericRequest.ContentType.FORM_ENCODED_DATA, parser, false);

    }

    private String addSessionId(String url, int eventType) {

        return url;
    }

    */
/**
     * Helper method to make Http get data from server.
     *
     * @param url       request url
     * @param eventType request event type
     * @param parser    parser object to be used for response parsing
     * @param requestObject Object used to uniquely identify the response
     *//*

    public boolean fetchData(String url, final int eventType, IParser parser, Object requestObject, boolean delete) {
        boolean returnVal = false;
        Log.d("request", url + "");
        if (Utils.isInternetAvailable(this)) {


            final IParser parser1 = parser == null ? new BaseParser() : parser;

            String cachedResponse = getJSONForRequest(eventType);

            //TODO apiToken in refine catalog api
            if (StringUtils.isNullOrEmpty(cachedResponse)) {
                url = addSessionId(url, eventType);


                VolleyGenericRequest req = delete ? new VolleyGenericRequest(url, this, this, this, 1) : new VolleyGenericRequest(url, this, this, this);
                req.setRequestData(requestObject);

                req.setEventType(eventType);
                req.setParser(parser1);
                //TODO  req.setRequestTimeOut(Constants.API_TIMEOUT);
                VolleyHelper.getInstance(this).addRequestInQueue(req);
            } else {

                final String tempResponse = cachedResponse;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //       Log.d("BaseActivity fetchData", "found response in cache for eventType " + eventType);
                        onResponse(parser1.parseData(eventType, tempResponse));
                        //TODO execute it on Non UI Thread
                    }
                });
            }
        } else {

            showCommonError("network error");
            returnVal = true;
        }
        return returnVal;
    }

    */
/**
     * Helper method to make Http get data from server.
     *
     * @param url       request url
     * @param eventType request event type
     * @param parser    parser object to be used for response parsing
     *//*


    public boolean fetchData(String url, final int eventType, IParser parser) {
        return  fetchData(url, eventType,parser, null, false);
    }


    */
/**
     * Helper method to make Http get data from server.
     *
     * @param url       request url
     * @param eventType request event type
     * @param parser    parser object to be used for response parsing
     *//*


    public boolean deleteData(String url, final int eventType, IParser parser) {
        return  fetchData(url, eventType,parser, null, true);
    }


    public String getJSONForRequest(int eventType) {
        String request = null;
        switch (eventType) {

        }
        return "";
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
                System.out.println("yohaha=success==="+response);
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
        mRequestQueue.add(stringRequest);

    }

    public void showProgressDialog(String body){
        showProgressDialog(body);
    }

    public void removeProgressDialog(){
        removeProgressDialog();
    }


    public void generateAccessToken(String data){

        System.out.println("generate access token=========="+data);

        postData(Config.baseUrl+"authenticate", ApiType.API_TOKEN, data, 2,  null);

    }



    */
/**
     * Volley response callback method
     *
     * @param objResponse response object
     *//*


    @Override
    public void onResponse(Object objResponse) {
        ServiceResponse resp = (ServiceResponse) objResponse;


        Log.d("response::",""+resp.getEventType()+" resp: "+((ServiceResponse) objResponse).getJsonResponse());

        if (resp.getJabongBaseModel() == null) {

            showCommonError(("error"));

        } else {


            */
/*checkIfInitApiDataHasExpired(resp.getJabongBaseModel().getSession().getCacheControlApp());
            if (!StringUtils.isNullOrEmpty(resp.getJabongBaseModel().getSession().getApiToken())) {
                StaticDataDao.getInstance(this).updateApiToken(resp.getJabongBaseModel().getSession().getApiToken());
            }
            if (!StringUtils.isNullOrEmpty(resp.getJabongBaseModel().getSession().getId()) && !(resp.getEventType() == ApiType.API_INIT || resp.getEventType() == ApiType.BANNER_API_REQUEST)) {
                updateSessionId(resp.getJabongBaseModel().getSession().getId());

                if (Utils.isLoggedIn(this) && !resp.getJabongBaseModel().getSession().isLoggedIn()) {
                    logout();
                    updateDrawerMenu();

                    if (resp.getErrorCode() == ServiceResponse.MESSAGE_ERROR) {
                        String message = getErrorMessage(resp);
                        if (!StringUtils.isNullOrEmpty(message)) {
                            Toast.makeText(this, message, 3000).show();
                        }

                    }
                }
            }*//*

            updateUi(resp);
        }

    }


    public void updateUi(ServiceResponse response) {

        switch (response.getEventType()) {

//
            default:
                break;
        }

    }

    private String getErrorMessage(ServiceResponse response) {
        String errorMsg = "";
        if (response.getErrorMessages() != null && response.getErrorMessages().size() > 0) {
            for (int i = 0; i < response.getErrorMessages().size(); i++) {
                if (errorMsg == null) {
                    errorMsg = response.getErrorMessages().get(i);
                } else {
                    errorMsg = errorMsg + response.getErrorMessages().get(i);
                }
            }
        }
        return errorMsg;
    }

    */
/**
     * Volley error response callback method
     *
     * @param error error object
     *//*

    @Override
    public void onErrorResponse(VolleyError error) {

        JVolleyError jError = (JVolleyError) error;
        ServiceResponse response = new ServiceResponse();
        response.setEventType(jError.getEventType());
        response.setErrorCode(ServiceResponse.EXCEPTION);
        response.setErrorText("Volley Error");


        updateUi(response);

    }


    */
/**
     * Utility function for showing common error dialog.
     *
     * @param message
     *//*


    public void showCommonError(String message) {
        if (TextUtils.isEmpty(message)) {
            message = "error";
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    */
/**
     * Utility function for showing common error dialog.
     *
     * @param message message to be shown
     *//*


    public void showWebErrorDialog(String message) {
        showCommonError(message);

    }

    @Override
    public void onEvent(int eventType, Object eventData) {

    }
}*/
