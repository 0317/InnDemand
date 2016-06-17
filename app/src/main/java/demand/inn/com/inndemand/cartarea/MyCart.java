package demand.inn.com.inndemand.cartarea;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
import demand.inn.com.inndemand.welcome.CommentArea;
import demand.inn.com.inndemand.welcome.SplashScreen;

/**
 * Created by akash
 */
public class MyCart extends AppCompatActivity {

    //Utility Class call
    NetworkUtility nu;
    AppPreferences prefs;

    //UI call area
    LinearLayout write_comment, enterPromo;
    RecyclerView list;
    TextView cart_item, cart_total;
    EditText input_code;
    CoordinatorLayout coordinatorLayout;
    Toolbar toolbar;

    //Class call
    private List<CartData> cardList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CartAdapter adapter;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycart);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);

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

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        input_code = (EditText) findViewById(R.id.input_code);
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
        Button checkout = (Button) dialog.findViewById(R.id.cart_promoApply);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

}
