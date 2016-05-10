package demand.inn.com.inndemand.mapcall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import demand.inn.com.inndemand.R;

/**
 * Created by akash on 10/5/16.
 */
public class GoogleMap extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.googlemap);
    }
}
