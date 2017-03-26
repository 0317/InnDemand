package demand.inn.com.inndemand.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.constants.OrderData;
import demand.inn.com.inndemand.utility.AppPreferences;

/**
 * Created by akash
 */
public class OrderAdapter extends  RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private List<OrderData> hotelData;
    private Context mContext;
    AppPreferences prefs;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, desc, price, day;

        public MyViewHolder(View view) {
            super(view);
            prefs = new AppPreferences(mContext);
            title = (TextView) view.findViewById(R.id.hotel_title);
            desc = (TextView) view.findViewById(R.id.hotel_desc);
//            price = (TextView) view.findViewById(R.id.hotel_price);
//            day = (TextView) view.findViewById(R.id.hotel_day);
        }
    }

    public OrderAdapter(Context mContext, List<OrderData> hotelData) {
        this.mContext = mContext;
        this.hotelData = hotelData;
    }

    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orderadapt, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        OrderData data = hotelData.get(position);
        data.setDay("Monday");
        data.setDesc("Hellli serverd hot mild");
        holder.title.setText(data.getTitle());
        holder.desc.setText(data.getDesc());
//        holder.price.setText(data.getPrice());
//        holder.day.setText(data.getDay());

    }

    @Override
    public int getItemCount() {
        return hotelData.size();
    }
}
