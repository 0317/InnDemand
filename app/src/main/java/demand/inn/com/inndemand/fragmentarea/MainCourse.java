package demand.inn.com.inndemand.fragmentarea;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

import demand.inn.com.inndemand.R;

/**
 * Created by akash on 5/5/16.
 */
public class MainCourse extends Fragment {

    View view;

    //UI Calls
    RelativeLayout maincourse_options;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.maincourse, container, false);

        //UI Initialize to select Main Course Veg/Non-Veg options
        maincourse_options = (RelativeLayout) view.findViewById(R.id.maincourse_option_click);
        maincourse_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, maincourse_options);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.maincourse_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
//
                        switch (item.getItemId()) {
                            case R.id.all:

                        return true;

                            case R.id.veg:

                            return true;

                            case R.id.nonveg:

                                return true;
                        }
                        return false;
                    }
                });

                popup.show();//showing popup menu
            }
        });

        return  view;
    }
}
