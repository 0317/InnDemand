package demand.inn.com.inndemand.cartarea;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.adapter.CartAdapter;
import demand.inn.com.inndemand.adapter.MainCourseAdapter;
import demand.inn.com.inndemand.adapter.RestaurantAdapter;
import demand.inn.com.inndemand.constants.CartData;
import demand.inn.com.inndemand.constants.Config;
import demand.inn.com.inndemand.constants.MaincourseData;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.volleycall.AppController;
import demand.inn.com.inndemand.welcome.CommentArea;
import demand.inn.com.inndemand.welcome.DBHelper;
import demand.inn.com.inndemand.welcome.SplashScreen;
import demand.inn.com.inndemand.welcome.Thankyou;

/**
 * Created by akash
 */
public class MyCart extends AppCompatActivity {

    //Utility Class call
    NetworkUtility nu;
    AppPreferences prefs;

    static final int TIME_DIALOG_ID = 1111;

    //UI call area
    LinearLayout write_comment, enterPromo, confirm_demand_click_cart;
    RecyclerView list;
    TextView cart_item, cart_total;
    EditText comment_area;
    TextView now, pickTime;
    CoordinatorLayout coordinatorLayout;
    Toolbar toolbar;
    private ProgressDialog mProgressDialog;

    //Class call
    private List<CartData> cardList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CartAdapter adapter;

    //Others
    String commentData;

    //Date & Time
    Calendar c;
    SimpleDateFormat df, date;
    String formattedDate, getDate;
    String finalTime;
    String getTime;
    private int hour;
    private int minute;

    //Database
    DBHelper db;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycart);
        nu = new NetworkUtility(this);

        db = new DBHelper(this);

        getSupportActionBar().hide();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My Cart");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.mipmap.ic_cancel);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //UI initialize
        cart_item = (TextView) findViewById(R.id.cart_items);
        cart_total = (TextView) findViewById(R.id.cart_total);

        //TextView UI Initialize area
        now = (TextView) findViewById(R.id.now_cart);
        pickTime = (TextView) findViewById(R.id.picktime_cart);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        confirm_demand_click_cart = (LinearLayout) findViewById(R.id.confirm_demand_click_cart);

        comment_area = (EditText) findViewById(R.id.comment_area);
        enterPromo = (LinearLayout) findViewById(R.id.apply_coupon_code);

        enterPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkoutClick();
            }
        });

        write_comment = (LinearLayout) findViewById(R.id.write_comments);
        write_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyCart.this, CommentArea.class);
                startActivity(intent);
            }
        });

//       ListItems in RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        cardList = new ArrayList<>();
        adapter = new CartAdapter(this, cardList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyCart.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerView.setAdapter(adapter);
        prepareCart();

        c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c.getTime());
        getDate = date.format(c.getTime());
        // formattedDate have current date/time

        now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                System.out.println("Current time => "+c.getTime());

                df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                formattedDate = df.format(c.getTime());
                // formattedDate have current date/time
                finalTime =formattedDate;
            }
        });

        pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });

        commentData = comment_area.getText().toString();

    }

    public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        public SimpleDividerItemDecoration(Context context) {
            mDivider = ContextCompat.getDrawable(context, R.drawable.line_divider);
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

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:

                // set time picker as current time
                return new TimePickerDialog(this, timePickerListener, hour, minute,
                        true);

        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {


        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            // TODO Auto-generated method stub

            hour   = hourOfDay;
            minute = minutes;

            updateTime(hour,minute);

        }
    };

    private static String utilTime(int value) {

        if (value < 10)
            return "0" + String.valueOf(value);
        else
            return String.valueOf(value);
    }

    // Used to convert 24hr format to 12hr format with AM/PM values
    private void updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        getTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(":").append("00").toString();

        finalTime = getDate+" "+getTime;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * Adding few stuff for testing
     */

    private void prepareCart() {

        CartData a = new CartData("", "Shahi Paneer", "Rs: 250");
        cardList.add(a);

        a = new CartData("","Chicken Tikka", "Rs: 200");
        cardList.add(a);

        a = new CartData("","Chicken Tikka", "Rs: 200");
        cardList.add(a);

        a = new CartData("","Chicken Tikka", "Rs: 200");
        cardList.add(a);

        a = new CartData("","Chicken Tikka", "Rs: 200");
        cardList.add(a);

        a = new CartData("","Chicken Tikka", "Rs: 200");
        cardList.add(a);

        adapter.notifyDataSetChanged();
    }

//  Custom pop-up designed for Apply COUPON Click
    public void checkoutClick(){
        // custom dialog
        final Dialog dialog = new Dialog(MyCart.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.promocode);

        // set the custom dialog components - text, image and button
        final EditText input_code = (EditText) dialog.findViewById(R.id.input_code);
        Button checkout = (Button) dialog.findViewById(R.id.cart_promoApply);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = input_code.getText().toString();

                if(input == null || input.equalsIgnoreCase("")){
                    new AlertDialog.Builder(MyCart.this).setMessage("Please enter Coupon code")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).create().show();
                }else{
                    //Network call to send Coupon code to server

                }
            }
        });

        Button cancel = (Button) dialog.findViewById(R.id.cart_promoCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    public void getData(){
        JSONObject obj = new JSONObject();

        postJsonData(Config.innDemand+"", "");
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
                System.out.println("yohaha=cart==success==="+response);
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

    public void proceed(View view){
        Intent in = new Intent(MyCart.this, Thankyou.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    //Pop-up messages to show data loading.
    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("loading...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    //To hide pop-up
    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    //Custom pop-up for Network Click
    public void networkClick(){
        // custom dialog
        final Dialog dialog = new Dialog(MyCart.this);
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
