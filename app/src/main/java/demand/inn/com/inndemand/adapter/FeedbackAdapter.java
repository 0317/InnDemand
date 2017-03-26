package demand.inn.com.inndemand.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.constants.FeedbackData;
import demand.inn.com.inndemand.utility.AppPreferences;

/**
 * Created by akash
 */
public class FeedbackAdapter extends  RecyclerView.Adapter<FeedbackAdapter.MyViewHolder>  {

    private List<FeedbackData> feedData;
    private Context mContext;
    AppPreferences prefs;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title, desc;

        public MyViewHolder(View view) {
            super(view);
            prefs = new AppPreferences(mContext);
            title = (TextView) view.findViewById(R.id.hotel_title);
            desc = (TextView) view.findViewById(R.id.hotel_desc);

        }

    }

    public FeedbackAdapter(Context mContext, List<FeedbackData> feedData) {
        this.mContext = mContext;
        this.feedData = feedData;
    }

    @Override
    public FeedbackAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hoteladapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FeedbackData data = feedData.get(position);
        holder.title.setText(data.getTitle());
        holder.desc.setText(data.getDesc());
    }

    @Override
    public int getItemCount() {
        return feedData.size();
    }
}
