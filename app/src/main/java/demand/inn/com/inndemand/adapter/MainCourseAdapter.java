package demand.inn.com.inndemand.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.constants.CartData;
import demand.inn.com.inndemand.constants.MaincourseData;

/**
 * Created by akash
 */
public class MainCourseAdapter extends  RecyclerView.Adapter<MainCourseAdapter.MyViewHolder>  {

    private List<MaincourseData> cartData;
    private Context mContext;
    ArrayList<Integer> counters = new ArrayList<Integer>();
    int counter = 0;
    int count;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subtitle, rupees, details, count;
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

    public MainCourseAdapter(Context mContext, List<MaincourseData> cartData) {
        this.mContext = mContext;
        this.cartData = cartData;
    }

    @Override
    public MainCourseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurantadapt, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MaincourseData data = cartData.get(position);
        holder.title.setText(data.getTitle());
        holder.subtitle.setText(data.getName());
        holder.details.setText(data.getDetails());
        holder.rupees.setText(data.getRupees());

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
                    counter = data.getCount();
                    holder.count.setText(String.valueOf(counter));
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = counter--;
                holder.count.setText(String.valueOf(count));
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartData.size();
    }
}

