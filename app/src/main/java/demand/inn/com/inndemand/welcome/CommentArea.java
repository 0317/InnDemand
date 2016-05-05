package demand.inn.com.inndemand.welcome;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import demand.inn.com.inndemand.R;

/**
 * Created by akash
 */
public class CommentArea extends Activity {

    //UI call
    EditText comment;
    LinearLayout back_press;

    //Others call
    String info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commentarea);

        //UI Initialize here
        comment = (EditText) findViewById(R.id.type_comment);
        back_press = (LinearLayout) findViewById(R.id.back_press);
        back_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //comments given by user details get into a String
        info = comment.getText().toString().trim();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
