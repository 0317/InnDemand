package demand.inn.com.inndemand.fragmentarea;

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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.adapter.DessertAdapter;
import demand.inn.com.inndemand.adapter.MainCourseAdapter;
import demand.inn.com.inndemand.constants.DessertData;
import demand.inn.com.inndemand.constants.MaincourseData;

/**
 * Created by akash
 */
public class Dessert extends Fragment {

    View view;

    //UI call area
    TextView cart_item, cart_total;
    LinearLayout menu_options;

    private RecyclerView recyclerView;
    private DessertAdapter adapter;
    private List<DessertData> cardList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dessert, container, false);

        //UI initialize
        cart_item = (TextView) view.findViewById(R.id.dessert_items);
        cart_total = (TextView) view.findViewById(R.id.dessert_total);
        cart_total.setText("Total: Rs 2000");
        cart_item.setText("(10 items)");


        //ListItems in RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        cardList = new ArrayList<>();
        adapter = new DessertAdapter(getActivity(), cardList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        prepareCart();

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

        return  view;
    }

    private void prepareCart() {

        DessertData a = new DessertData("Ice Cream", "Sundae", "Rs: 250");
        cardList.add(a);

        a = new DessertData("", "Soft serve", "Rs: 200");
        cardList.add(a);

        a = new DessertData("Beverages", "Cold Coffee", "Rs: 200");
        cardList.add(a);

        a = new DessertData("", "Fruit beer", "Rs: 220");
        cardList.add(a);

        a = new DessertData("", "Rainbow Special", "Rs: 220");
        cardList.add(a);

        a = new DessertData("", "Vanilla Storm", "Rs: 180");
        cardList.add(a);

        a = new DessertData("Cupcakes", "Choco Blast", "Rs: 240");
        cardList.add(a);

        a = new DessertData("", "Mango Rush", "Rs: 220");
        cardList.add(a);

        adapter.notifyDataSetChanged();
    }
}
