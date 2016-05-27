package demand.inn.com.inndemand.fragmentarea;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import demand.inn.com.inndemand.adapter.RestaurantAdapter;
import demand.inn.com.inndemand.constants.AppetiserData;

/**
 * Created by akash
 */

public class Appetizer extends Fragment {

    View view;
    Context mContext;

    //UI call area
    TextView cart_item, cart_total;
    LinearLayout menu_options;


    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private List<AppetiserData> cardList;

    Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.appetizer, container, false);

        //UI initialize
        cart_item = (TextView) view.findViewById(R.id.appetiser_items);
        cart_total = (TextView) view.findViewById(R.id.appetiser_total);
        cart_total.setText("Total: Rs 2000");
        cart_item.setText("(10 items)");


        //ListItems in RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        cardList = new ArrayList<>();
        adapter = new RestaurantAdapter(getActivity(), cardList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
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



    public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        public SimpleDividerItemDecoration(Context context) {
            mDivider = ContextCompat.getDrawable(context,R.drawable.line_divider);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    /**
     * Adding few stuff for testing
     */
    private void prepareCart() {

        AppetiserData a = new AppetiserData("Pizza", "FarmHouse: ", "Garlic, Capcicum, Tomatoes", "Rs: 250");
        cardList.add(a);

        a = new AppetiserData("", "Chicken Tikka: ", "Chicken, Red Capcicum, Pepper", "Rs: 460");
        cardList.add(a);

        a = new AppetiserData("Chinese", "Corn Soup: ","vegetables, soya sausage", "Rs: 180");
        cardList.add(a);

        a = new AppetiserData("", "Manchurian (dry)","", "Rs: 220");
        cardList.add(a);

        a = new AppetiserData("", "Hakka Noodles","", "Rs: 280");
        cardList.add(a);

        a = new AppetiserData("Continental", "Devilled Eggs: ","Roasted grilled eggs", "Rs: 300");
        cardList.add(a);

        a = new AppetiserData("", "Asparagas Soup","", "Rs: 250");
        cardList.add(a);

        adapter.notifyDataSetChanged();
    }
}
