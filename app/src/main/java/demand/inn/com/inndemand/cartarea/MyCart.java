package demand.inn.com.inndemand.cartarea;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.welcome.CommentArea;

/**
 * Created by akash on 4/5/16.
 */
public class MyCart extends Activity {

    LinearLayout write_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycart);

        write_comment = (LinearLayout) findViewById(R.id.write_comments);
        write_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyCart.this, CommentArea.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
