package demand.inn.com.inndemand.setting;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;

/**
 * Created by akash
 */
public class Settings extends AppCompatActivity {

    //Utility class area
    NetworkUtility nu;
    AppPreferences prefs;

    View view;

    //UI call Area
    Toolbar toolbar;
    ToggleButton notiHotel, notiInndemand;
    Button deleteAccount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);

//        getSupportActionBar().hide();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Settings");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.mipmap.ic_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //UI Initialize
        notiHotel = (ToggleButton) findViewById(R.id.hotel_noti);
        notiInndemand = (ToggleButton) findViewById(R.id.inndemand_noti);

        deleteAccount = (Button) findViewById(R.id.delete_account);
    }

    public void deleteAccount(View view){
        new AlertDialog.Builder(Settings.this).setMessage("Its seems like you want to delete your account.")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create().show();
    }
}
