package demand.inn.com.inndemand.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.constants.HotelData;
import demand.inn.com.inndemand.constants.ListData;
import demand.inn.com.inndemand.utility.AppPreferences;

/**
 * Created by akash
 */

public class ListAdapter extends  RecyclerView.Adapter<ListAdapter.MyViewHolder>  {

    private List<ListData> hotelData;
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

    public ListAdapter(Context mContext, List<ListData> hotelData) {
        this.mContext = mContext;
        this.hotelData = hotelData;
    }

    @Override
    public ListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hoteladapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ListData data = hotelData.get(position);
        holder.title.setText(data.getTitle());
        if(data.getDesc().contains("/")){

            SpannableStringBuilder builder = new SpannableStringBuilder();
            String sp[] = data.getDesc().split("/");
            String part = sp[0];
            String parts = sp[1];

            String col = part+"\n";
            SpannableString graySpannable= new SpannableString(col);
            graySpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, col.length(), 0);
            builder.append(graySpannable);

            String cols = parts;
            SpannableString graysSpannable = new SpannableString(cols);
            graysSpannable.setSpan(new ForegroundColorSpan(Color.GRAY), 0, cols.length(), 0);
            builder.append(graysSpannable);

            holder.desc.setText(builder, TextView.BufferType.SPANNABLE);
        }else{
            holder.desc.setText(data.getDesc());
        }

    }

    @Override
    public int getItemCount() {
        return hotelData.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}

