package demand.inn.com.inndemand.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.database.DBHelper;
import demand.inn.com.inndemand.model.AppetiserData;
import demand.inn.com.inndemand.utility.AppPreferences;

/**
 * Created by akash
 */

public class RestaurantAdapter extends  RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {

    private SparseBooleanArray selectedItems;

    private List<AppetiserData> cartData;
    private List<AppetiserData> itemprice;
    private Context mContext;
    int finalamount;
    String dataItem;
    String dataCash;

    RecyclerView.Adapter adapter;
    AppPreferences prefs;

    // Start with first item selected
    private int selectedItem = 0;

    //DATABASE
    DBHelper db;
    SharedPreferences preferences;

    private static MyClickListener myClickListener;

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        // Handle key up and key down and attempt to move selection
        recyclerView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();

                // Return false if scrolled to the bounds and allow focus to move off the list
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                        return tryMoveSelection(lm, 1);
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                        return tryMoveSelection(lm, -1);
                    }
                }

                return false;
            }
        });
    }

    private boolean tryMoveSelection(RecyclerView.LayoutManager lm, int direction) {
        int nextSelectItem = selectedItem + direction;

        // If still within valid bounds, move the selection, notify to redraw, and scroll

        return false;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subtitle, rupees, count, details;
        public ImageView plus, minus;
        private LinearLayout item_click;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.restaurant_listitems_head);
            subtitle = (TextView) view.findViewById(R.id.restaurant_listitems_name);
            rupees = (TextView) view.findViewById(R.id.restaurant_listitems_rupees);
            details = (TextView) view.findViewById(R.id.restaurant_listitems_details);
            count = (TextView) view.findViewById(R.id.restaurant_counts);
            plus = (ImageView) view.findViewById(R.id.restaurant_plus);
            minus = (ImageView) view.findViewById(R.id.restaurant_minus);
            item_click = (LinearLayout) view.findViewById(R.id.item_click);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Redraw the old selection and the new
                    notifyItemChanged(selectedItem);
                    selectedItem = getLayoutPosition();
                    notifyItemChanged(selectedItem);
                }
            });
        }
    }

    public RestaurantAdapter(Context mContext, List<AppetiserData> cartData, List<AppetiserData> itemprice) {
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
        preferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RestaurantAdapter.MyViewHolder holder, final int position) {
        AppetiserData data = cartData.get(position);
        Log.d("Category Details", "Check: "+data.getCategory());
            holder.title.setText(data.getSubcategory());
            holder.subtitle.setText(data.getName() + " ");
            holder.rupees.setText(data.getPrice());
            holder.details.setText(data.getDescription());
        holder.item_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   /* Intent in = new Intent(mContext, Feedback.class);
                    in.putExtra("itemname", finalData.getName());
                    in.putExtra("itemdesc", finalData.getDescription());
                    in.putExtra("itemprice", finalData.getPrice());
                    in.putExtra("itemrating", finalData.getRating());
                    mContext.startActivity(in);*/
                }
            });

        LocalBroadcastManager.getInstance(mContext).registerReceiver(mMessageReceiver, new IntentFilter("data-message"));

//        if (holder.title.getText().toString().trim() == "" || holder.title.getText().toString().trim() == null) {
//            holder.title.setVisibility(View.GONE);
//        }

//        if (data.getFood() == "2" || data.getFood().equalsIgnoreCase("2"))
//            holder.subtitle.setTextColor(Color.RED);
//        else
//            holder.subtitle.setTextColor(Color.parseColor("#006600"));

      /* holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               *//* int s1=(Integer.parseInt(holder.count.getText().toString()));

                    if(s1 < 0){
//                        counter = data.getCount();// update new value
                        holder.count.setText(String.valueOf(counter++));
                    }
                    if(s1 > 0) {
                        counter = ++count;// update new value
                        holder.count.setText(String.valueOf(counter));
                    }*//*
                Toast.makeText(mContext, "Hello java", Toast.LENGTH_LONG).show();
            }
        });
*/
       /*  holder.minus.setOnClickListener(new View.OnClickListener() {
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


        holder.plus.setOnClickListener(new PlusButtonListener(position, data,
                holder.count, holder.title));
        holder.minus.setOnClickListener(new MinusButtonListener(position, data,
                holder.count, holder.title));

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
            AppetiserData data = cartData.get(position);
            TextView totalTextView, title;

            PlusButtonListener(int position, AppetiserData data, TextView totalTextView, TextView title) {
                this.position = position;
                this.totalTextView=totalTextView;
                this.title = title;
                this.data = data;
            }

            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Hello Android", Toast.LENGTH_LONG).show();
                if (cartData.get(position).getUserqty() < 10)
                {
                    data.setUserqty(data.getUserqty() + 1); // incrementing item quantity by 1

                    Log.d("Quantity", "Check: "+data.getUserqty());
                    Log.d("Rupees", "Check: "+data.getPrice());
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString("totaltextview", String.valueOf(data.getUserqty()));
                    edit.commit();
                    totalTextView.setText(preferences.getString("totaltextview", ""));
//                    totalTextView.setText(String.valueOf(data.getUserqty()));
                    Log.d("TextCheck", "Check: "+totalTextView.getText().toString());
                    String finalval = preferences.getString("totaltextview", "");

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
                        in.putExtra("selectedItem", cartData.get(position).getName());
                        in.putExtra("totalItems", quantity);
                        LocalBroadcastManager.getInstance(mContext).sendBroadcast(in);

                    }
                }
            }
        }

        class MinusButtonListener implements View.OnClickListener {
            private int position;
            AppetiserData data;
            TextView totalTextView, title;

            MinusButtonListener(int position, AppetiserData data, TextView totalTextView, TextView title) {
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
                        in.putExtra("selectedItem", cartData.get(position).getName());
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
