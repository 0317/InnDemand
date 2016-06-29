package demand.inn.com.inndemand.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import demand.inn.com.inndemand.Helper.OnItemCLick;
import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.constants.AppetiserData;
import demand.inn.com.inndemand.constants.Header;
import demand.inn.com.inndemand.model.ResturantDataModel;
import demand.inn.com.inndemand.roomservice.Restaurant;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.welcome.DBHelper;

/**
 * Created by akash
 */

public class RestaurantAdapter extends  RecyclerView.Adapter<RestaurantAdapter.MyViewHolder>  {

    private List<ResturantDataModel> cartData;
    private List<ResturantDataModel> itemprice;
    private Context mContext;
    int counter = 0;
    int count = 0;
    int finalamount;
    String total_price;
    String dataItem;
    String dataCash;

    private OnItemCLick mCallback;
    RecyclerView.Adapter adapter;
    AppPreferences prefs;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    Header header;

    //DATABASE
    DBHelper db;
    private static MyClickListener myClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, subtitle, rupees, count, details;
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

    public RestaurantAdapter(Context mContext, List<ResturantDataModel> cartData, List<ResturantDataModel> itemprice) {
        this.mContext = mContext;
        this.cartData = cartData;
        this.itemprice = itemprice;
    }

    @Override
    public RestaurantAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurantadapt, parent, false);
        prefs = new AppPreferences(mContext);
        db = new DBHelper(mContext);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RestaurantAdapter.MyViewHolder holder, final int position) {
        ResturantDataModel data = cartData.get(position);
        holder.title.setText(data.getSubcategory());
        holder.subtitle.setText(data.getName() + " ");
        holder.rupees.setText(data.getPrice());
        holder.details.setText(data.getDescription());

        LocalBroadcastManager.getInstance(mContext).registerReceiver(mMessageReceiver, new IntentFilter("data-message"));

        if (holder.title.getText().toString().trim() == "" || holder.title.getText().toString().trim() == null) {
            holder.title.setVisibility(View.GONE);
        }

//        if (data.getFood() == "2" || data.getFood().equalsIgnoreCase("2"))
//            holder.subtitle.setTextColor(Color.RED);
        else
            holder.subtitle.setTextColor(Color.parseColor("#006600"));

        db.insertData(data.getName(), data.getDescription(), data.getPrice());

       /* holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int s1=(Integer.parseInt(holder.count.getText().toString()));

                    if(s1 < 0){
//                        counter = data.getCount();// update new value
                        holder.count.setText(String.valueOf(counter++));
                    }
                    if(s1 > 0) {
                        counter = ++count;// update new value
                        holder.count.setText(String.valueOf(counter));
                    }
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int s2=(Integer.parseInt(holder.count.getText().toString()));

                if(s2 < 0) {
                    holder.minus.setEnabled(false);
                } else if(s2 > 0){
                    holder.minus.setEnabled(true);
                    count = counter--;
                    holder.count.setText(String.valueOf(count));
                }
            }
        });*/


        holder.plus.setOnClickListener(new PlusButtonListener(position, data, holder.count, holder.title));
        holder.minus.setOnClickListener(new MinusButtonListener(position, data, holder.count, holder.title));

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
            ResturantDataModel data = cartData.get(position);
            TextView totalTextView, title;

            PlusButtonListener(int position, ResturantDataModel data, TextView totalTextView, TextView title) {
                this.position = position;
                this.totalTextView=totalTextView;
                this.title = title;
                this.data = data;
            }

            @Override
            public void onClick(View v) {
                if (cartData.get(position).getUserqty() < 10)
                {
                    data.setUserqty(data.getUserqty() + 1); // incrementing item quantity by 1

                    Log.d("Quantity", "Check: "+data.getUserqty());

                    Log.d("Rupees", "Check: "+data.getPrice());

                    totalTextView.setText(String.valueOf(data.getUserqty()));
                    for (int temp1 = 0; temp1 < itemprice.size(); temp1++)
                        data = itemprice.get(temp1);
                    {
                        data.setProductsaleprice((int) (Float.parseFloat(data.getPrice()) * data.getUserqty()));
                        finalamount = data.getProductsaleprice();
//                        holder.count.setText("Rs."+finalamount);
                        Log.d("Price", "Check: "+finalamount);
                        String value = String.valueOf(data.getProductsaleprice());
                        String quantity = String.valueOf(data.getUserqty());
                        Log.d("Broadcast", "check"+dataItem);
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
            ResturantDataModel data;
            TextView totalTextView, title;

            MinusButtonListener(int position, ResturantDataModel data, TextView totalTextView, TextView title) {
                this.position = position;
                this.totalTextView = totalTextView;
                this.title = title;
                this.data = data;
            }

            @Override
            public void onClick(View v) {
                if (data.getUserqty() > 0) {
                    data.setUserqty(data.getUserqty() - 1);// decrementing item quantity by 1

                    String titleValue = title.getText().toString();
                    totalTextView.setText(String.valueOf(data.getUserqty()));
                    for (int temp1 = 0; temp1 < itemprice.size(); temp1++)
                        data = itemprice.get(temp1);
                    {
                        data.setProductsaleprice((int) (Float.parseFloat(data.getPrice()) * data.getUserqty()));
                        finalamount = data.getProductsaleprice();
                        Log.d("Price", "Check: "+finalamount);
                        Bundle bundle = new Bundle();
                        bundle.putString("selectitems", String.valueOf(data.getUserqty()));
                        bundle.putString("selectcash", String.valueOf(finalamount));
                        String value = String.valueOf(data.getProductsaleprice());
                        String quantity = String.valueOf(data.getUserqty());
                        Intent in = new Intent("custom-message");
                        in.putExtra("totalCash", value);
                        in.putExtra("totalItems", quantity);
                        in.putExtra("itemName", titleValue);
                        LocalBroadcastManager.getInstance(mContext).sendBroadcast(in);
                    }
                }

            }
        }

    @Override
    public int getItemCount() {
        return cartData.size();
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            dataItem = intent.getStringExtra("cartItem");
            dataCash = intent.getStringExtra("cartTotal");
        }
    };

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
