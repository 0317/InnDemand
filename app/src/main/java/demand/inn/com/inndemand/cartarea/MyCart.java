package demand.inn.com.inndemand.cartarea;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.adapter.CartAdapter;
import demand.inn.com.inndemand.adapter.MainCourseAdapter;
import demand.inn.com.inndemand.adapter.RestaurantAdapter;
import demand.inn.com.inndemand.constants.CartData;
import demand.inn.com.inndemand.constants.MaincourseData;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.welcome.CommentArea;

/**
 * Created by akash
 */
public class MyCart extends AppCompatActivity {

    //Utility Class call
    NetworkUtility nu;
    AppPreferences prefs;

    //UI call area
    LinearLayout write_comment, enterPromo,edit_promocode, items_details;
    RecyclerView list;
    TextView promo_text, cart_item, cart_total;
    EditText input_code;
    RelativeLayout layout;
    Button cancel_promo, apply_promo;

    //Class call
    private List<CartData> cardList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CartAdapter adapter;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycart);

        getSupportActionBar().hide();

        //UI initialize
        cart_item = (TextView) findViewById(R.id.cart_items);
        cart_total = (TextView) findViewById(R.id.cart_total);
        cart_total.setText("Total: Rs 2000");
        cart_item.setText("(10 items)");

        input_code = (EditText) findViewById(R.id.input_code);
//        items_details = (LinearLayout) findViewById(R.id.items_details);
        enterPromo = (LinearLayout) findViewById(R.id.apply_coupon_code);

        layout = (RelativeLayout) findViewById(R.id.layout_button);
        layout.setVisibility(View.INVISIBLE);

        promo_text = (TextView) findViewById(R.id.promo_text);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) enterPromo.getLayoutParams();
        LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) promo_text.getLayoutParams();
        // Changes the height and width to the specified *pixels*
        params.height = 50;
//        enterPromo.setBackgroundResource(R.color.view_gray);
        enterPromo.setLayoutParams(params);
        promo_text.setText("Apply Promo Code");
        param.topMargin = 10;
        promo_text.setLayoutParams(param);
        enterPromo.setGravity(Gravity.CENTER_HORIZONTAL);
//        edit_promocode.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.INVISIBLE);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        try {
            display.getRealSize(size);
        } catch (NoSuchMethodError err) {
            display.getSize(size);
        }
        final int width = size.x;
        int height = size.y;

        enterPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) enterPromo.getLayoutParams();
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int)(width), LinearLayout.LayoutParams.MATCH_PARENT);
                LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) promo_text.getLayoutParams();
// Changes the height and width to the specified *pixels*
                params.height = 200;
                enterPromo.setBackgroundResource(R.color.colorPrimary);
                enterPromo.setLayoutParams(params);
                enterPromo.setLayoutParams(lp);
                promo_text.setText("Enter Promo Code");
                param.topMargin = 10;
                promo_text.setLayoutParams(param);
                enterPromo.setGravity(Gravity.CENTER_HORIZONTAL);
//                edit_promocode.setVisibility(View.VISIBLE);
                layout.setVisibility(View.VISIBLE);
                layout.setGravity(Gravity.RIGHT);
            }
        });

        input_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                items_details.setVisibility(View.INVISIBLE);
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

        //ListItems in RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        cardList = new ArrayList<>();
        adapter = new CartAdapter(this, cardList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        prepareCart();


    }

    public void cancelPromo(View view){
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) enterPromo.getLayoutParams();
        LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) promo_text.getLayoutParams();
// Changes the height and width to the specified *pixels*
        params.height = 50;
//        params.leftMargin = 20;
//        params.rightMargin = 20;
        params.setMargins(20, 0, 20, 0);
        enterPromo.setBackgroundResource(R.color.view_gray);
        promo_text.setText("Apply Promo Code");
        param.topMargin = 10;
        promo_text.setLayoutParams(param);
        enterPromo.setGravity(Gravity.CENTER_HORIZONTAL);
//        edit_promocode.setVisibility(View.INVISIBLE);
        enterPromo.setLayoutParams(params);
        layout.setVisibility(View.INVISIBLE);
    }

    public void applyPromo(View view){
        String promoCode = input_code.getText().toString().trim();

        if(promoCode == null){
            Snackbar.make(view, "Please enter valid Code",  Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else{

        }
    }

    @Override
    public void onBackPressed() {
//        items_details.setVisibility(View.VISIBLE);
        super.onBackPressed();

    }

    /**
     * Adding few stuff for testing
     */
    private void prepareCart() {

        CartData a = new CartData("Veg", "Shahi Paneer", "Rs: 250");
        cardList.add(a);

        a = new CartData("Non-Veg", "Chicken Tikka", "Rs: 200");
        cardList.add(a);

        a = new CartData("Non-Veg", "Chicken Tikka", "Rs: 200");
        cardList.add(a);

        a = new CartData("Non-Veg", "Chicken Tikka", "Rs: 200");
        cardList.add(a);

        a = new CartData("Non-Veg", "Chicken Tikka", "Rs: 200");
        cardList.add(a);

        a = new CartData("Non-Veg", "Chicken Tikka", "Rs: 200");
        cardList.add(a);

        a = new CartData("Non-Veg", "Chicken Tikka", "Rs: 200");
        cardList.add(a);

        a = new CartData("Non-Veg", "Chicken Tikka", "Rs: 200");
        cardList.add(a);

        a = new CartData("Non-Veg", "Chicken Tikka", "Rs: 200");
        cardList.add(a);

        a = new CartData("Non-Veg", "Chicken Tikka", "Rs: 200");
        cardList.add(a);



        adapter.notifyDataSetChanged();
    }
}
