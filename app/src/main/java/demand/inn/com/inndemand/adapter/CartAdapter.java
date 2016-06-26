package demand.inn.com.inndemand.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.constants.AppetiserData;
import demand.inn.com.inndemand.constants.CartData;
import demand.inn.com.inndemand.constants.MaincourseData;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.welcome.DBHelper;

/**
 * Created by akash
 */
public class CartAdapter extends  RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private List<CartData> cartData;
    private Context mContext;
    DBHelper db;
    AppPreferences prefs;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subtitle, rupees, count;
        public ImageView plus, minus;
        public LinearLayout gray_back;

        public MyViewHolder(View view) {
            super(view);
            subtitle = (TextView) view.findViewById(R.id.restaurant_listitems_name);
            rupees = (TextView) view.findViewById(R.id.restaurant_listitems_rupees);
            gray_back = (LinearLayout) view.findViewById(R.id.gray_back);
        }
    }

    public CartAdapter(Context mContext, List<CartData> cartData) {
        this.mContext = mContext;
        this.cartData = cartData;
    }

    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurantadapt, parent, false);
        prefs = new AppPreferences(mContext);
        db = new DBHelper(mContext);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CartData data = cartData.get(position);
        holder.subtitle.setText(data.getName());
        holder.rupees.setText(data.getRupees());

//        if(data.getName() == null || data.getName().equalsIgnoreCase("")){
//            holder.gray_back.setVisibility(View.GONE);
//        }
    }

    @Override
    public int getItemCount() {
        return cartData.size();
    }
}
