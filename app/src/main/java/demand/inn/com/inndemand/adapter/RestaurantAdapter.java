package demand.inn.com.inndemand.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.constants.CartData;

/**
 * Created by akash
 */

public class RestaurantAdapter extends  RecyclerView.Adapter<RestaurantAdapter.MyViewHolder>  {

    private List<CartData> cartData;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, name, rupees;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.restaurant_listitems_head);
            name = (TextView) view.findViewById(R.id.restaurant_listitems_name);
            rupees = (TextView) view.findViewById(R.id.restaurant_listitems_rupees);
        }
    }

    public RestaurantAdapter(List<CartData> cartData) {
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
        CartData movie = cartData.get(position);
        holder.title.setText(movie.getTitle());
        holder.name.setText(movie.getName());
        holder.rupees.setText(movie.getRupees());
    }

    @Override
    public int getItemCount() {
        return cartData.size();
    }
}
