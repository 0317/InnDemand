package demand.inn.com.inndemand.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import demand.inn.com.inndemand.R;

/**
 * Created by akash
 */
public class QrEntry extends AppCompatActivity{

    Button click_OK;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrentry);

        click_OK = (Button) findViewById(R.id.click_ok);

    }

    public void click_OK(View view){
        Intent in = new Intent(QrEntry.this, QRscanning.class);
        startActivity(in);
    }
}
