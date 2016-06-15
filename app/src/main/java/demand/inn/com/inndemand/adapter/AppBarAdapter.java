package demand.inn.com.inndemand.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.constants.AppetiserData;
import demand.inn.com.inndemand.utility.AppPreferences;

/**
 * Created by akash
 */
public class AppBarAdapter extends  RecyclerView.Adapter<AppBarAdapter.MyViewHolder>  {

    private List<AppetiserData> cartData;
    private Context mContext;
    int counter = 0;
    int count = 0;

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

    public AppBarAdapter(Context mContext, List<AppetiserData> cartData) {
        this.mContext = mContext;
        this.cartData = cartData;
    }

    @Override
    public AppBarAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurantadapt, parent, false);
        prefs = new AppPreferences(mContext);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AppBarAdapter.MyViewHolder holder, final int position) {
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
                counter = data.getCount();// update new value
                holder.count.setText(String.valueOf(counter));
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getData = holder.count.getText().toString();
                if(holder.count.getText() == "0") {
                    holder.minus.setEnabled(false);
                } else {
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

