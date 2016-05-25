package demand.inn.com.inndemand.model.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.android.volley.*;
import com.android.volley.toolbox.HttpHeaderParser;
import demand.inn.com.inndemand.model.request.listener.IRequestCompletionListener;
import demand.inn.com.inndemand.model.response.ServiceResponse;
import demand.inn.com.inndemand.model.response.error.JVolleyError;
import demand.inn.com.inndemand.parser.IParser;
//import demand.inn.com.inndemand.welcome.BaseActivity;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created  by Akash
 */
public class VolleyGenericRequest extends com.android.volley.Request<Object> implements IRequest {



    public interface ContentType {

        int PLAIN_TEXT = 0;
        int FORM_ENCODED_DATA = 1;
        int JSON = 2;
    }

    /**
     * Charset for request.
     */
    private static final String PROTOCOL_CHARSET = "utf-8";

    /**
     * Content type for request.
     */
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);

    private Map<String, String> mParams;
    private int mEventType;
    protected Response.Listener mListener;
    protected Response.ErrorListener mErrorListener;
    protected IParser mParser;
    private String mPostData;
    private String mPutData;
    protected IRequestCompletionListener mRequestCompletionListener;
    protected int mContentType;
    protected Context mContext;

    private long mUiRenderingStartTime;
    private long mUiRenderingTotalTime;
    private long mApiRequestStartTime;
    private long mApiRequestResponseTime;
    private long mParsingStartTime;
    private long mParsingTime;

    SharedPreferences settings;

    private Object mRequestData = null;

    /**
     * Creates a new request with the given method.
     *
     * @param url           URL to fetch the string at
     * @param listener      Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public VolleyGenericRequest(int contentType, String url, String postData, Response.Listener<String> listener,
                                Response.ErrorListener errorListener, Context ctx) {
        super(Method.POST, url, errorListener);
        mListener = listener;
        mContentType = contentType;
        mPostData = postData;
        mContext = ctx.getApplicationContext();
        settings =    PreferenceManager.getDefaultSharedPreferences(mContext);
    }


    /**
     * Creates a new request with the given method.
     *
     * @param url           URL to fetch the string at
     * @param listener      Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public VolleyGenericRequest(int contentType, String url, String putData, Response.Listener<String> listener,
                                Response.ErrorListener errorListener, Context ctx, int request) {
        super(Method.PUT, url, errorListener);
        mListener = listener;
        mContentType = contentType;
        mPostData = putData;
        mContext = ctx.getApplicationContext();
        settings =    PreferenceManager.getDefaultSharedPreferences(mContext);
    }


    /**
     * Creates a new request with the given method.
     *
     * @param url           URL to fetch the string at
     * @param listener      Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public VolleyGenericRequest(String url, Response.Listener<String> listener,
                                Response.ErrorListener errorListener, Context ctx, int request) {
        super(Method.DELETE, url, errorListener);
        mListener = listener;
        mContext = ctx.getApplicationContext();
        settings =    PreferenceManager.getDefaultSharedPreferences(mContext);
    }



    public VolleyGenericRequest(int contentType, String url, HashMap<String, String> paramsMap, Response.Listener<String> listener,
                                Response.ErrorListener errorListener, Context ctx) {
        super(Method.POST, url, errorListener);
        mListener = listener;
        mContentType = contentType;
        mParams = paramsMap;
        mContext = ctx.getApplicationContext();
        settings =    PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    /**
     * Creates a new request with the given method.
     *
     * @param url           URL to fetch the string at
     * @param listener      Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public VolleyGenericRequest(String url, Response.Listener<String> listener,
                                Response.ErrorListener errorListener, Context ctx) {
        super(Method.GET, url, errorListener);
        mListener = listener;
        mContext = ctx.getApplicationContext();
        settings =    PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        //headers.put("X-ROCKET-MOBAPI-PLATFORM", "application/android.rocket.SHOP_MOBILEAPI_STAGING-v1.1+json");
        //headers.put("X-ROCKET-MOBAPI-TOKEN", "b5467071c82d1e0d88a46f6e057dbb88");
        /*headers.put("X-ROCKET-MOBAPI-VERSION", "");
        headers.put("isComingFromApp", "YES");
        headers.put("isNewApp", "YES");
        headers.put("Device-OS", "Android");
        String deviceType = "mobile";*/

        headers.put("x-access-token", settings.getString("token",""));
        //   headers.put("Accept-Encoding","gzip");

       // SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
 //       System.out.println(mPrefs.getBoolean(Constants.TAG_LOGIN, false));
   //     System.out.println(mPrefs.getString(Constants.AUTO_LOGIN_TOKEN, ""));
//        if (mPrefs.getBoolean(Constants.TAG_LOGIN, false) && !mPrefs.getString(Constants.AUTO_LOGIN_TOKEN, "").equals("")){
  //          headers.put("autoLoginToken", mPrefs.getString(Constants.AUTO_LOGIN_TOKEN, ""));
    //    }
        headers.put("Accept-encoding","gzip");
        return headers;
    }

    /**
     * @deprecated Use {@link #getBodyContentType()}.
     */
    @Override
    public String getPostBodyContentType() {
        return getBodyContentType();
    }

    /**
     * @deprecated Use {@link #getBody()}.
     */
    @Override
    public byte[] getPostBody() throws AuthFailureError {
        return getBody();
    }

    @Override
    public String getBodyContentType() {
        switch (mContentType) {
            case ContentType.JSON:
                return PROTOCOL_CONTENT_TYPE;
            default:
                return super.getBodyContentType();
        }

    }

    @Override
    public byte[] getBody() throws AuthFailureError {


        try {
            switch (mContentType) {
                case ContentType.JSON: {
                    return null == mPostData ? null : mPostData.getBytes(PROTOCOL_CHARSET);
                }
                case ContentType.PLAIN_TEXT:
                    return null == mPostData ? null : mPostData.getBytes();
                case ContentType.FORM_ENCODED_DATA:
                    return super.getBody();
            }
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    mPostData, PROTOCOL_CHARSET);

        }
        return null;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    protected Response<Object> parseNetworkResponse(NetworkResponse response) {
        try {
            markResponseReceived();
            markParsingStarted();
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.d("VolleyGenericRequest", jsonString);
            ServiceResponse serviceResponse = getParser().parseData(mEventType, jsonString);
            serviceResponse.setRequestData(getRequestData());
            serviceResponse.setEventType(mEventType);
            Response<Object> resp = Response.success((Object) (serviceResponse), HttpHeaderParser.parseCacheHeaders(response));
            markParsingCompleted();
            return resp;
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }


    @Override
    protected void deliverResponse(Object response) {
        if (mRequestCompletionListener != null)
            mRequestCompletionListener.onRequestProcessed(true, this);
        mUiRenderingStartTime=System.currentTimeMillis();
        mListener.onResponse(response);
        mUiRenderingTotalTime=System.currentTimeMillis()-mUiRenderingStartTime;
        sendGATimingsData(mEventType);
    }

    @Override
    public void deliverError(VolleyError error) {
        if (mRequestCompletionListener != null)
            mRequestCompletionListener.onRequestProcessed(false, this);

        JVolleyError jVolleyError = new JVolleyError(error);
        jVolleyError.setEventType(mEventType);
        super.deliverError(jVolleyError);

    }

    @Override
    public void cancel() {
        if (mRequestCompletionListener != null)
            mRequestCompletionListener.onRequestProcessed(false, this);
        super.cancel();
    }

    @Override
    public IParser getParser() {
        return mParser;
    }

    @Override
    public void setParser(IParser parser) {
        mParser = parser;
    }

    public boolean updateListeners(Object newListener) {
        boolean isUpdated = false;
        if (mErrorListener != null && mErrorListener.getClass().getName().equals(newListener.getClass().getName())) {
            mErrorListener = (Response.ErrorListener) newListener;
            isUpdated = true;

        }

        if (mListener != null && mListener.getClass().getName().equals(newListener.getClass().getName())) {
            mListener = (Response.Listener) newListener;
            isUpdated = true;


        }
        return isUpdated;
    }

    public void setOnCompletedListener(IRequestCompletionListener listener) {
        mRequestCompletionListener = listener;
    }


    public int getEventType() {
        return mEventType;
    }

    public void setEventType(int eventType) {
        this.mEventType = eventType;
    }

    public Object getRequestData() {
        return mRequestData;
    }

    public void setRequestData(Object requestData) {
        this.mRequestData = requestData;
    }
    public void markRequestAddedInQueue()
    {
        mApiRequestStartTime =System.currentTimeMillis();
    }
    public void markResponseReceived()
    {
        mApiRequestResponseTime =System.currentTimeMillis()- mApiRequestStartTime;
    }
    public void markParsingStarted()
    {
        mParsingStartTime =System.currentTimeMillis();
    }
    public void markParsingCompleted()
    {
        mParsingTime =System.currentTimeMillis()- mParsingStartTime;
    }

    private void sendGATimingsData(int eventType)
    {


    }

}
