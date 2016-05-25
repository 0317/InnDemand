package demand.inn.com.inndemand.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.constants.CartData;

/**
 * Created by akash
 */

public class RestaurantAdapter extends  RecyclerView.Adapter<RestaurantAdapter.MyViewHolder>  {

    private List<CartData> cartData;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subtitle, rupees, count;
        public ImageView plus, minus;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.restaurant_listitems_head);
            subtitle = (TextView) view.findViewById(R.id.restaurant_listitems_name);
            rupees = (TextView) view.findViewById(R.id.restaurant_listitems_rupees);
//            count = (TextView) view.findViewById(R.id.restaurant_counting);

        }
    }

    public RestaurantAdapter(Context mContext, List<CartData> cartData) {
        this.mContext = mContext;
        this.cartData = cartData;
    }

    @Override
    public RestaurantAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurantadapt, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RestaurantAdapter.MyViewHolder holder, int position) {
        CartData data = cartData.get(position);
        holder.title.setText(data.getTitle());
        holder.subtitle.setText(data.getName());
        holder.rupees.setText(data.getRupees());
    }

    @Override
    public int getItemCount() {
        return cartData.size();
    }
}
