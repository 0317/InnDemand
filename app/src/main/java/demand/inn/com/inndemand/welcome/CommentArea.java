package demand.inn.com.inndemand.welcome;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import demand.inn.com.inndemand.R;

/**
 * Created by akash
 */
public class CommentArea extends AppCompatActivity {

    //UI call
    EditText comment;
    LinearLayout back_press;
    Toolbar toolbar;

    //Others call
    String info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commentarea);

        getSupportActionBar().hide();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.write_comment);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.mipmap.ic_cancel);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //UI Initialize here
        comment = (EditText) findViewById(R.id.type_comment);

        //comments given by user details get into a String
        info = comment.getText().toString().trim();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
