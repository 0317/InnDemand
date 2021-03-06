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
import demand.inn.com.inndemand.constants.DessertData;

/**
 * Created by akash
 */
public class DesBarAdapter extends RecyclerView.Adapter<DesBarAdapter.MyViewHolder>  {

    private List<DessertData> cartData;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subtitle, rupees, details;
        public ImageView plus, minus;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.restaurant_listitems_head);
            subtitle = (TextView) view.findViewById(R.id.restaurant_listitems_name);
            rupees = (TextView) view.findViewById(R.id.restaurant_listitems_rupees);
            details = (TextView) view.findViewById(R.id.restaurant_listitems_details);

        }
    }

    public DesBarAdapter(Context mContext, List<DessertData> cartData) {
        this.mContext = mContext;
        this.cartData = cartData;
    }

    @Override
    public DesBarAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurantadapt, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DessertData data = cartData.get(position);
        holder.title.setText(data.getTitle());
        holder.subtitle.setText(data.getName());
        holder.details.setText(data.getDetails() +"...");
        holder.rupees.setText(data.getRupees());

        if(holder.title.getText().toString().trim() == "" || holder.title.getText().toString().trim() == null){
            holder.title.setVisibility(View.GONE);
        }

        if(data.getFood() == "2" || data.getFood().equalsIgnoreCase("2"))
            holder.subtitle.setTextColor(Color.RED);
        else
            holder.subtitle.setTextColor(Color.GREEN);
    }

    @Override
    public int getItemCount() {
        return cartData.size();
    }
}

