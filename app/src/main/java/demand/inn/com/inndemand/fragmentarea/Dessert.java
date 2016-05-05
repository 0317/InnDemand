package demand.inn.com.inndemand.fragmentarea;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import demand.inn.com.inndemand.R;

/**
 * Created by akash on 5/5/16.
 */
public class Dessert extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dessert, container, false);

        return  view;
    }
}
