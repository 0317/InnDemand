package demand.inn.com.inndemand.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.constants.HotelData;

/**
 * Created by akash
 */

public class HotelAdapter extends  RecyclerView.Adapter<HotelAdapter.MyViewHolder>  {

    private List<HotelData> hotelData;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, desc;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.hotel_title);
            desc = (TextView) view.findViewById(R.id.hotel_desc);

        }
    }

    public HotelAdapter(Context mContext, List<HotelData> hotelData) {
        this.mContext = mContext;
        this.hotelData = hotelData;
    }

    @Override
    public HotelAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hoteladapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        HotelData data = hotelData.get(position);
        holder.title.setText(data.getTitle());
        holder.desc.setText(data.getDesc());

    }

    @Override
    public int getItemCount() {
        return hotelData.size();
    }
}

