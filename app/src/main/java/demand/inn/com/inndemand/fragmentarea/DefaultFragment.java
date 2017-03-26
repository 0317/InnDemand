package demand.inn.com.inndemand.fragmentarea;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.constants.FragmentData;

/**
 * Created by akash
 */
public class DefaultFragment extends Fragment {

    public DefaultFragment(){
        // required empty constructor
    }
    public static DefaultFragment newInstance(FragmentData category, int position){
        DefaultFragment fragment=new DefaultFragment();

        Bundle args=new Bundle();
        args.putParcelable("category", (Parcelable) category);
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }
    public Object getCategory(){
        return getArguments().getParcelable("category");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.restaurantadapt   , container, false);

        //Fetch data for categoryID
        return view;
    }
}
