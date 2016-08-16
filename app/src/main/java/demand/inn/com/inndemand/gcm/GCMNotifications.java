package demand.inn.com.inndemand.gcm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
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

import javax.net.ssl.HttpsURLConnection;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.adapter.GCMAdapter;
import demand.inn.com.inndemand.constants.Config;
import demand.inn.com.inndemand.constants.NotificationData;
import demand.inn.com.inndemand.model.ResturantDataModel;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.volleycall.AppController;


public class GCMNotifications extends AppCompatActivity {

	//Utility Class area
	NetworkUtility nu;
	AppPreferences prefs;

	//UI class area
	Toolbar toolbar;

	String result = "";
	List<String> notify;

	//Notification List
	private RecyclerView recyclerView;
	private GCMAdapter adapter;
	private List<NotificationData> cardList;

	//Call Variables
	String id;
	String type;
	String title;
	String message;
	String service_type;
	String place_id;

	//Class Area
	NotificationData a;

	//Translated String varibale
	public String destinationString = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.notifications);
		nu = new NetworkUtility(this);
		prefs = new AppPreferences(this);

		getSupportActionBar().hide();

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle(R.string.notification);
		toolbar.setTitleTextColor(Color.WHITE);
		toolbar.setNavigationIcon(R.mipmap.ic_back);

		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		//ListItems in RecyclerView
		recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		cardList = new ArrayList<>();
		adapter = new GCMAdapter(GCMNotifications.this, cardList);

		RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(mLayoutManager);
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
		recyclerView.setAdapter(adapter);
		postCall();

//		NotificationData a = new NotificationData("InnDemand", "You have won one coupon", "Click");
//		cardList.add(a);

//		a = new NotificationData("InnDemand", "Restaurant timings are changed. Here are the updated details" +
//				"Restaurant timings are changed. Here are the updated details. Restaurant timings are changed. Here are the updated details. Restaurant timings are changed. Here are the updated details. Restaurant timings are changed. Here are the updated details", "More");
//		cardList.add(a);
//
//		a = new NotificationData("InnDemand", "Restaurant services are resumed", "");
//		cardList.add(a);

	}

	public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
		private Drawable mDivider;

		public SimpleDividerItemDecoration(Context context) {
			mDivider = ContextCompat.getDrawable(context,R.drawable.list_divider);
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

	public void postCall(){
		JSONObject obj = new JSONObject();

		try {
			obj.put("hotel_id", prefs.getHotel_id());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		Log.d("Api Hotel Data", obj.toString());

		postJsonData(Config.innDemand+"notification/populate/", obj.toString());
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
				System.out.println("Notification===success===" + response);
//				id', 'type', 'title', 'message', 'service_type', 'place_id'

				JSONArray array = null;
				try {
					array = new JSONArray(response);

					Log.d("API", "API D" + array);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				for (int i = 0; i < array.length(); i++)
					try {
						JSONObject object = array.getJSONObject(i);
						Log.d("API", "API Daa" + array);
						Log.d("API", "API ID" + id);
						id = object.getString("id");
						type = object.getString("type");
						title = object.getString("title");
						message = object.getString("message");
						service_type = object.getString("service_type");
						place_id = object.getString("place_id");

						if(prefs.getLocaleset() == "en" || prefs.getLocaleset().equals("en")){
							a = new NotificationData(title, message, type, service_type, place_id);
							cardList.add(a);
						}else {
							String noti_title = new getTraslatedString().execute(prefs.getLocaleset(),
									title).get();
							String noti_msg = new getTraslatedString().execute(prefs.getLocaleset(),
									title).get();
							String noti_type = new getTraslatedString().execute(prefs.getLocaleset(),
									title).get();
							a = new NotificationData(noti_title, noti_msg, noti_type, service_type,
									place_id);
							cardList.add(a);
						}

					}catch (JSONException e){
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}

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


	/*
    * API call to translate data
    * Translation coding to Translate all the data coming from server in target language
    */
	public class getTraslatedString extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String[] target) {

			String trasRequest = "https://www.googleapis.com/language/translate/v2?" +
					"key=AIzaSyAK9Vu9g2vv4jsT0aljz5DFHiTqS9IKsBk&source="+target[0] +
					"&target="+target[1]+"&q="+target[2];

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

		java.net.URL obj = new URL(url.replace(" ", "%20"));
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

	/*
     *Custom pop-up for Internet Check (If connected or not)
     * When No internet connectivity a pop-up will arise by below method
     */
	public void networkClick(){
		// custom dialog
		final Dialog dialog = new Dialog(GCMNotifications.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.network);

		// set the custom dialog components - text, image and button
		ImageView image = (ImageView) dialog.findViewById(R.id.image);
		Button checkout = (Button) dialog.findViewById(R.id.ok_click);
		checkout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		dialog.show();
	}

}
