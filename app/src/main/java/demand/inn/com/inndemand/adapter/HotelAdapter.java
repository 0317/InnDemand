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
import demand.inn.com.inndemand.utility.AppPreferences;

/**
 * Created by akash
 */

public class HotelAdapter extends  RecyclerView.Adapter<HotelAdapter.MyViewHolder>  {

    private List<HotelData> hotelData;
    private Context mContext;
    AppPreferences prefs;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, desc;

        public MyViewHolder(View view) {
            super(view);
            prefs = new AppPreferences(mContext);
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
//        if(data.getScreen_key() == "main" || data.getScreen_key().equalsIgnoreCase("main")) {
            holder.title.setText(data.getTitle());
            if (data.getDesc().contains("-")) {

                SpannableStringBuilder builder = new SpannableStringBuilder();
                String sp[] = data.getDesc().split("-");
                String part = sp[0];
                String parts = sp[1];

                String col = part + "\n";
                SpannableString graySpannable = new SpannableString(col);
                graySpannable.setSpan(new ForegroundColorSpan(Color.GRAY), 0, col.length(), 0);
                builder.append(graySpannable);

                String cols = parts;
                SpannableString graysSpannable = new SpannableString(cols);
                graysSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, cols.length(), 0);
                builder.append(graysSpannable);

                holder.desc.setText(builder, TextView.BufferType.SPANNABLE);
            } else {
                holder.desc.setText(data.getDesc());
            }
       /* }else if(data.getScreen_key() == "laundry" || data.getScreen_key().equalsIgnoreCase("laundry")){
                prefs.setLaundrynote(data.getTitle()+": "+data.getDesc());
        }else if(data.getScreen_key() == "cab" || data.getScreen_key().equalsIgnoreCase("cab")){
                prefs.setCabnote(data.getTitle()+": "+data.getDesc());
        }*/

    }

    @Override
    public int getItemCount() {
        return hotelData.size();
    }
}

