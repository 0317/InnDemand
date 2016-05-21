package demand.inn.com.inndemand.fragmentarea;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import demand.inn.com.inndemand.R;

/**
 * Created by akash
 */
public class Appetizer extends Fragment {

    View view;

    //UI call area
    TextView cart_item, cart_total;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.appetizer, container, false);

        //UI initialize
        cart_item = (TextView) view.findViewById(R.id.appetiser_items);
        cart_total = (TextView) view.findViewById(R.id.appetiser_total);
        cart_total.setText("Total: Rs 2000");
        cart_item.setText("(10 items)");

        return  view;
    }
}
