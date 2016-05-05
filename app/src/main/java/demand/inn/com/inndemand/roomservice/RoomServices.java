package demand.inn.com.inndemand.roomservice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import demand.inn.com.inndemand.R;

/**
 * Created by akash on 4/5/16.
 */
public class RoomServices extends Activity {


    //UI Initialize
    LinearLayout beverages_click, laundry_click, cab_click, bed_tea_click, bath_essentials_click, bell_boy_click,
                 room_clean_click, wake_up_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roomservices);

        //UI elements call

        //Linearlayout click call for different services
        beverages_click = (LinearLayout) findViewById(R.id.beverages_click);
        laundry_click = (LinearLayout) findViewById(R.id.laundry_click);
        cab_click = (LinearLayout) findViewById(R.id.cab_click);
        bed_tea_click = (LinearLayout) findViewById(R.id.bed_tea_click);
        bath_essentials_click = (LinearLayout) findViewById(R.id.bath_essentials_click);
        bell_boy_click = (LinearLayout) findViewById(R.id.bell_boy_click);
        room_clean_click = (LinearLayout) findViewById(R.id.roomclean_click);
        wake_up_click = (LinearLayout) findViewById(R.id.wake_up_click);

        //onClick Listener Call for different services (Linearlayout)
        beverages_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        laundry_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cab_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bed_tea_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bath_essentials_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bell_boy_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        room_clean_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        wake_up_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
