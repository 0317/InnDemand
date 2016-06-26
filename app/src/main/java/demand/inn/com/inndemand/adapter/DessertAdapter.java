package demand.inn.com.inndemand.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.constants.AppetiserData;
import demand.inn.com.inndemand.constants.DessertData;
import demand.inn.com.inndemand.constants.MaincourseData;
import demand.inn.com.inndemand.fragmentarea.Dessert;
import demand.inn.com.inndemand.utility.AppPreferences;

/**
 * Created by akash
 */
public class DessertAdapter extends  RecyclerView.Adapter<DessertAdapter.MyViewHolder>  {

    private List<DessertData> cartData;
    private List<DessertData> itemprice;
    private Context mContext;
    int finalamount;
    AppPreferences prefs;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subtitle, count, rupees, details;
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

    public DessertAdapter(Context mContext, List<DessertData> cartData, List<DessertData> itemprice) {
        this.mContext = mContext;
        this.cartData = cartData;
        this.itemprice = itemprice;
    }

    @Override
    public DessertAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurantadapt, parent, false);
        prefs = new AppPreferences(mContext);

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

//        if(data.getFood() == "2" || data.getFood().equalsIgnoreCase("2"))
//            holder.subtitle.setTextColor(Color.RED);
//        else
//            holder.subtitle.setTextColor(Color.GREEN);

        holder.plus.setOnClickListener(new PlusButtonListener(position, data, holder.count));
        holder.minus.setOnClickListener(new MinusButtonListener(position, data, holder.count));

        finalamount =0;
        for (int temp1 = 0; temp1 < itemprice.size(); temp1++)
            data = itemprice.get(temp1);
        {
//            data.setProductsaleprice(data.getRupees() * data.getUserQty());
            finalamount += data.getProductsaleprice();
//            holder.count.setText("Rs."+finalamount);
        }
        Log.d("TAG", "Total Price is:" + finalamount);
//        RestaurantAdapter.this.notifyDataSetChanged();
    }

    class PlusButtonListener implements View.OnClickListener {
        private int position;
        DessertData data = cartData.get(position);
        TextView totalTextView;

        PlusButtonListener(int position, DessertData data, TextView totalTextView) {
            this.position = position;
            this.totalTextView=totalTextView;
            this.data = data;
        }

        @Override
        public void onClick(View v) {
            if (cartData.get(position).getUserqty() < 10)
            {
                data.setUserqty(data.getUserqty() + 1); // incrementing item quantity by 1

                Log.d("Quantity", "Check: "+data.getUserqty());

                Log.d("Rupees", "Check: "+data.getRupees());

                totalTextView.setText(String.valueOf(data.getUserqty()));
                for (int temp1 = 0; temp1 < itemprice.size(); temp1++)
                    data = itemprice.get(temp1);
                {
                    data.setProductsaleprice(Integer.parseInt(data.getRupees()) * data.getUserqty());
                    finalamount = data.getProductsaleprice();
//                        holder.count.setText("Rs."+finalamount);
                    Log.d("Price", "Check: "+finalamount);
                    String value = String.valueOf(data.getProductsaleprice());
                    String quantity = String.valueOf(data.getUserqty());
                    Intent in = new Intent("custom-message");
                    in.putExtra("totalCash", value);
                    in.putExtra("totalItems", quantity);
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(in);

                }
            }
        }
    }

    class MinusButtonListener implements View.OnClickListener {
        private int position;
        DessertData data;
        TextView totalTextView;

        MinusButtonListener(int position, DessertData data, TextView totalTextView) {
            this.position = position;
            this.totalTextView = totalTextView;
            this.data = data;
        }

        @Override
        public void onClick(View v) {
            if (data.getUserqty() > 0) {
                data.setUserqty(data.getUserqty() - 1);// decrementing item quantity by 1

                totalTextView.setText(String.valueOf(data.getUserqty()));
                for (int temp1 = 0; temp1 < itemprice.size(); temp1++)
                    data = itemprice.get(temp1);
                {
                    data.setProductsaleprice(Integer.parseInt(data.getRupees()) * data.getUserqty());
                    finalamount = data.getProductsaleprice();
//                        holder.count.setText("Rs." + finalamount);
                    Log.d("Price", "Check: "+finalamount);
                    Bundle bundle = new Bundle();
                    bundle.putString("selectitems", String.valueOf(data.getUserqty()));
                    bundle.putString("selectcash", String.valueOf(finalamount));
                    String value = String.valueOf(data.getProductsaleprice());
                    String quantity = String.valueOf(data.getUserqty());
                    Intent in = new Intent("custom-message");
                    in.putExtra("totalCash", value);
                    in.putExtra("totalItems", quantity);
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(in);
                }
            }

        }
    }

    @Override
    public int getItemCount() {
        return cartData.size();
    }
}
