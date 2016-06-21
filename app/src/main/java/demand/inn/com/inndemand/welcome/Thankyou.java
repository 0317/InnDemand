package demand.inn.com.inndemand.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.login.HotelDetails;

/**
 * Created by akash
 */
public class Thankyou extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thankyou);

        call();
    }

    public void call(){
//        if (nu.isConnectingToInternet()) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                    if (nu.isConnectingToInternet()) {
                    Intent in = new Intent(Thankyou.this, HotelDetails.class);
                    startActivity(in);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();

//                    }else{
//                        networkClick();
//                        snackbar = Snackbar.make(coordinatorLayout, "Oops! No Internet Connection", Snackbar.LENGTH_LONG)
//                                .setAction("Action", null);
////                        View snackbarView = snackbar.getView();
////                        snackbarView.setBackgroundColor(Color.YELLOW);
////                        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
////                        textView.setTextColor(getResources().getColor(R.color.confirm_demand_click));
//                        snackbar.show();
//                    }
            }
        }, 3000 /* 3sec delay*/);
    }
}
