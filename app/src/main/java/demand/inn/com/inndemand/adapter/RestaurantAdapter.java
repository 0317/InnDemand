package demand.inn.com.inndemand.adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import demand.inn.com.inndemand.constants.ListData;
import demand.inn.com.inndemand.roomservice.Restaurant;
import demand.inn.com.inndemand.utility.AppPreferences;

/**
 * Created by akash
 */

public class RestaurantAdapter extends  RecyclerView.Adapter<RestaurantAdapter.MyViewHolder>  {

    private List<AppetiserData> cartData;
    private Context mContext;
    int counter = 0;
    int count = 0;
    String total_price;

    RecyclerView.Adapter adapter;
    AppPreferences prefs;

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
        prefs = new AppPreferences(mContext);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RestaurantAdapter.MyViewHolder holder, final int position) {
        final AppetiserData data = cartData.get(position);
        holder.title.setText(data.getTitle());
        holder.subtitle.setText(data.getName()+" ");
        holder.rupees.setText(data.getRupees());
        holder.details.setText(data.getDetails());

        if(holder.title.getText().toString().trim() == "" || holder.title.getText().toString().trim() == null){
            holder.title.setVisibility(View.GONE);
        }

        if(data.getFood() == "2" || data.getFood().equalsIgnoreCase("2"))
            holder.subtitle.setTextColor(Color.RED);
        else
            holder.subtitle.setTextColor(Color.GREEN);

        holder.plus.setTag(position);
        holder.minus.setTag(position);
        holder.count.setTag(position);

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int s1=(Integer.parseInt(holder.count.getText().toString()));

                    if(s1 == 0){
//                        counter = data.getCount();// update new value
                        holder.count.setText(String.valueOf(counter++));
                    }
                    if(s1 > 0) {
                        counter = ++count;// update new value
                        holder.count.setText(String.valueOf(counter));
                    }
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int s2=(Integer.parseInt(holder.count.getText().toString()));

                if(s2 < 0) {
                    holder.minus.setEnabled(false);
                } else if(s2 > 0){
                    holder.minus.setEnabled(true);
                    count = counter--;
                    holder.count.setText(String.valueOf(count));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartData.size();
    }
}
