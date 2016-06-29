package demand.inn.com.inndemand.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.constants.NotificationData;
import demand.inn.com.inndemand.login.HotelDetails;
import demand.inn.com.inndemand.roomservice.Bar;
import demand.inn.com.inndemand.roomservice.Restaurant;
import demand.inn.com.inndemand.utility.AppPreferences;

/**
 * Created by akash
 */
public class GCMAdapter extends  RecyclerView.Adapter<GCMAdapter.MyViewHolder>  {

    private List<NotificationData> cartData;
    private Context mContext;
    int counter = 0;
    int count = 0;

    RecyclerView.Adapter adapter;
    AppPreferences prefs;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, details;
        public Button click;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.noti_title);
            details = (TextView) view.findViewById(R.id.noti_msg);
            click = (Button) view.findViewById(R.id.noti_click);
        }
    }

    public GCMAdapter(Context mContext, List<NotificationData> cartData) {
        this.mContext = mContext;
        this.cartData = cartData;
    }

    @Override
    public GCMAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_items, parent, false);
        prefs = new AppPreferences(mContext);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final NotificationData data = cartData.get(position);
        String buttonValue = holder.click.getText().toString();
        holder.title.setText(data.getTitle());
        holder.details.setText(data.getDetails());
        holder.details.setText(data.getDetails());
        holder.details.setMaxLines(2);

        if(holder.details.getMaxLines() == 2 || holder.details.getMaxLines() >=2){
            holder.click.setText("More");
              holder.click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.details.setMaxLines(100);
                    holder.details.setText(data.getDetails());
                    holder.click.setText("Click");
                }
            });
        }else{
            holder.click.setVisibility(View.GONE);
        }

        if(data.getType() == "1" || data.getType().equalsIgnoreCase("1")) {
            if(data.getService() == "1" || data.getService().equalsIgnoreCase("1")){
                String button = holder.click.getText().toString();
                if(button.equalsIgnoreCase("Click") || button == "Click") {
                    Intent in = new Intent(mContext, Bar.class);
                    mContext.startActivity(in);
                }
            }else if(data.getService() == "2" || data.getService().equalsIgnoreCase("2")){
                String button = holder.click.getText().toString();
                if(button.equalsIgnoreCase("Click") || button == "Click") {
                    Intent in = new Intent(mContext, Restaurant.class);
                    mContext.startActivity(in);
                }
            }else if(data.getService() == "3" || data.getService().equalsIgnoreCase("3")){
                String button = holder.click.getText().toString();
                if(button.equalsIgnoreCase("Click") || button == "Click") {
                    Intent in = new Intent(mContext, Restaurant.class);
                    mContext.startActivity(in);
                }
            }

        } else if(data.getType() == "2" || data.getType().equalsIgnoreCase("2")) {
            String button = holder.click.getText().toString();
            if(button.equalsIgnoreCase("Click") || button == "Click") {
                Intent in = new Intent(mContext, Bar.class);
                mContext.startActivity(in);
            }
        }else if(data.getType() == "3" || data.getType().equalsIgnoreCase("3")){
                holder.click.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return cartData.size();
    }
}
