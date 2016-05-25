package demand.inn.com.inndemand.fragmentarea;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.adapter.RestaurantAdapter;
import demand.inn.com.inndemand.constants.CartData;

/**
 * Created by akash
 */
public class Appetizer extends Fragment {

    View view;
    Context mContext;

    //UI call area
    TextView cart_item, cart_total;


    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private List<CartData> cardList;

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
        recyclerView.setAdapter(adapter);
        prepareCart();

        return  view;
    }

    public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        public SimpleDividerItemDecoration(Context context) {
            mDivider = context.getResources().getDrawable(R.drawable.line_divider);
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
     * Adding few albums for testing
     */
    private void prepareCart() {

        CartData a = new CartData("Pizza", "FarmHouse", "Rs: 250");
        cardList.add(a);

        a = new CartData("Chinese", "Noodles", "Rs: 200");
        cardList.add(a);

        adapter.notifyDataSetChanged();
    }
}
