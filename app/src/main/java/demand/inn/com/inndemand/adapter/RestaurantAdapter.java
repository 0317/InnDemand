package demand.inn.com.inndemand.adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.logging.Handler;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.constants.AppetiserData;
import demand.inn.com.inndemand.constants.CartData;

/**
 * Created by akash
 */

public class RestaurantAdapter extends  RecyclerView.Adapter<RestaurantAdapter.MyViewHolder>  {

    private List<AppetiserData> cartData;
    private Context mContext;
    int counter;

    RecyclerView.Adapter adapter;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subtitle, rupees, count, details;
        public ImageView plus, minus;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.restaurant_listitems_head);
            subtitle = (TextView) view.findViewById(R.id.restaurant_listitems_name);
            rupees = (TextView) view.findViewById(R.id.restaurant_listitems_rupees);
            details = (TextView) view.findViewById(R.id.restaurant_listitems_details);
            count = (TextView) view.findViewById(R.id.restaurant_counts);
            plus = (ImageView) view.findViewById(R.id.restaurant_plus);
            minus = (ImageView) view.findViewById(R.id.restaurant_minus);
        }
    }

    public RestaurantAdapter(Context mContext, List<AppetiserData> cartData) {
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
    public void onBindViewHolder(final RestaurantAdapter.MyViewHolder holder, final int position) {
        final AppetiserData data = cartData.get(position);
        holder.title.setText(data.getTitle());
        holder.subtitle.setText(data.getName());
        holder.rupees.setText(data.getRupees());
        holder.details.setText(data.getDetails());

        if(holder.title.getText().toString().trim() == "" || holder.title.getText().toString().trim() == null){
            holder.title.setVisibility(View.GONE);
        }

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                data.setCount(counter);
//                counter ++;// update new value
//                holder.count.setText(data.getCount());
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter--;
                data.setCount(counter);
//                holder.count.setText(data.getCount());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartData.size();
    }

}
