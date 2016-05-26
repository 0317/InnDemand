package demand.inn.com.inndemand.fragmentarea;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.adapter.MainCourseAdapter;
import demand.inn.com.inndemand.adapter.RestaurantAdapter;
import demand.inn.com.inndemand.constants.CartData;
import demand.inn.com.inndemand.constants.MaincourseData;

/**
 * Created by akash
 */
public class MainCourse extends Fragment {

    View view;

    //UI Calls
    RelativeLayout maincourse_options;
    Context context;
    TextView cart_item, cart_total;
    LinearLayout menu_options;


    private RecyclerView recyclerView;
    private MainCourseAdapter adapter;
    private List<MaincourseData> cardList;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.maincourse, container, false);

        //UI initialize
        cart_item = (TextView) view.findViewById(R.id.maincourse_items);
        cart_total = (TextView) view.findViewById(R.id.maincourse_total);
        cart_total.setText("Total: Rs 2000");
        cart_item.setText("(10 items)");

        //UI Initialize to select Main Course Veg/Non-Veg options
        maincourse_options = (RelativeLayout) view.findViewById(R.id.maincourse_option_click);
        //UI Linearlayout for Menu Options to select Menu Items
        menu_options = (LinearLayout) view.findViewById(R.id.menu_options);
        menu_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getActivity(), menu_options);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
//
                        switch (item.getItemId()) {
                            case R.id.action_all:

                                return  true;

                            case R.id.action_veg:
                                return true;

                            case R.id.action_nonveg:
                                return true;
                        }
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });

        //ListItems in RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        cardList = new ArrayList<>();
        adapter = new MainCourseAdapter(getActivity(), cardList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        prepareCart();

        return  view;
    }

    /**
     * Adding few stuff for testing
     */
    private void prepareCart() {

        MaincourseData a = new MaincourseData("Indian", "Shahi Paneer", "Rs: 360");
        cardList.add(a);

        a = new MaincourseData("", "Chicken kabab", "Rs: 440");
        cardList.add(a);

        a = new MaincourseData("", "Chicken Korma", "Rs: 520");
        cardList.add(a);

        a = new MaincourseData("Thai", "Chicken Curry", "Rs: 480");
        cardList.add(a);

        a = new MaincourseData("", "Chinese Water Spinach Curry", "Rs: 460");
        cardList.add(a);

        a = new MaincourseData("Italian", "Chicken Scallopini", "Rs: 500");
        cardList.add(a);

        a = new MaincourseData("", "Veneto Chicken", "Rs: 480");
        cardList.add(a);

        a = new MaincourseData("", "Mediterranean Pasta", "Rs: 430");
        cardList.add(a);

        adapter.notifyDataSetChanged();
    }
}
