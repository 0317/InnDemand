package demand.inn.com.inndemand.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.constants.AppetiserData;
import demand.inn.com.inndemand.constants.CartData;
import demand.inn.com.inndemand.constants.Header;
import demand.inn.com.inndemand.constants.MaincourseData;
import demand.inn.com.inndemand.model.ResturantDataModel;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.welcome.DBHelper;

/**
 * Created by akash
 */
public class CartAdapter extends  RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private List<CartData> cartData;
    private List<CartData> itemprice;
    private Context mContext;
    AppPreferences prefs;

    int finalamount;
    String total_price;
    String dataItem;
    String dataCash;

    RecyclerView.Adapter adapter;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    // Start with first item selected
    private int selectedItem = 0;

    Header header;

    //DATABASE
    DBHelper db;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subtitle, rupees, count;
        public ImageView plus, minus;
        public LinearLayout gray_back;

        public MyViewHolder(View view) {
            super(view);
            subtitle = (TextView) view.findViewById(R.id.restaurant_listitems_name);
            rupees = (TextView) view.findViewById(R.id.restaurant_listitems_rupees);
//            count = (TextView) view.findViewById(R.id.restaurant_counts);
//            plus = (ImageView) view.findViewById(R.id.restaurant_plus);
//            minus = (ImageView) view.findViewById(R.id.restaurant_minus);
            gray_back = (LinearLayout) view.findViewById(R.id.gray_back);
        }
    }

    public CartAdapter(Context mContext, List<CartData> cartData) {
        this.mContext = mContext;
        this.cartData = cartData;
    }

    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mycartitems, parent, false);
        prefs = new AppPreferences(mContext);
        db = new DBHelper(mContext);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CartData data = cartData.get(position);
        holder.subtitle.setText(data.getName());
        holder.rupees.setText(data.getDesc());
//        holder.count.setText(data.getRupees());
//        if(data.getName() == null || data.getName().equalsIgnoreCase("")){
//            holder.gray_back.setVisibility(View.GONE);
//        }

       /* holder.plus.setOnClickListener(new PlusButtonListener(position, data, holder.count, holder.title));
        holder.minus.setOnClickListener(new MinusButtonListener(position, data, holder.count, holder.title));

        finalamount =0;
        for (int temp1 = 0; temp1 < itemprice.size(); temp1++)
            data = itemprice.get(temp1);
        {
//            data.setProductsaleprice(data.getRupees() * data.getUserQty());
            finalamount += data.getProductsaleprice();
//            holder.count.setText("Rs."+finalamount);
        }
        Log.d("TAG", "Total Price is:" + finalamount);*/
    }

    class PlusButtonListener implements View.OnClickListener {
        private int position;
        CartData data = cartData.get(position);
        TextView totalTextView, title;

        PlusButtonListener(int position, CartData data, TextView totalTextView, TextView title) {
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
                    in.putExtra("selectedItem", cartData.get(position).getName());
                    in.putExtra("totalItems", quantity);
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(in);

                }
            }
        }
    }

    class MinusButtonListener implements View.OnClickListener {
        private int position;
        CartData data;
        TextView totalTextView, title;

        MinusButtonListener(int position, CartData data, TextView totalTextView, TextView title) {
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
//                    totalTextView.setText(String.valueOf(data.getUserqty()));
                SharedPreferences settings = mContext.getSharedPreferences("LIST_VALUE", Context.MODE_PRIVATE);
                String quantityValue = settings.getString("quantity", "");
                totalTextView.setText(quantityValue);
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
                    //To save
                    SharedPreferences settingss = mContext.getSharedPreferences("LIST_VALUE", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settingss.edit();
                    editor.putString("quantity",quantity);
                    editor.commit();
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
}
