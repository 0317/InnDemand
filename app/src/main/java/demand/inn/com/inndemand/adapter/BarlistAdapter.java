package demand.inn.com.inndemand.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.constants.BarlistData;
import demand.inn.com.inndemand.constants.ListData;
import demand.inn.com.inndemand.utility.AppPreferences;

/**
 * Created by akash
 */
public class BarlistAdapter extends  RecyclerView.Adapter<BarlistAdapter.MyViewHolder>  {

    private List<BarlistData> hotelData;
    private Context mContext;
    AppPreferences prefs;
    private static MyClickListener myClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title, desc;

        public MyViewHolder(View view) {
            super(view);
            prefs = new AppPreferences(mContext);
            title = (TextView) view.findViewById(R.id.hotel_title);
            desc = (TextView) view.findViewById(R.id.hotel_desc);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public BarlistAdapter(Context mContext, List<BarlistData> hotelData) {
        this.mContext = mContext;
        this.hotelData = hotelData;
    }

    @Override
    public BarlistAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hoteladapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BarlistData data = hotelData.get(position);
        holder.title.setText(data.getTitle());

        if(data.getStatus() == "0" || data.getStatus().equalsIgnoreCase("0"))
            holder.title.setVisibility(View.GONE);
        else
            holder.title.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return hotelData.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}

