package demand.inn.com.inndemand.gcm;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.constants.AppetiserData;
import demand.inn.com.inndemand.constants.NotificationData;
import demand.inn.com.inndemand.roomservice.Restaurant;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;


public class GCMNotifications extends AppCompatActivity {

	//Utility Class area
	NetworkUtility nu;
	AppPreferences prefs;

	//UI class area
	Toolbar toolbar;

	String result = "";
	List<String> notify;

	//Notification List
	private RecyclerView recyclerView;
	private GCMAdapter adapter;
	private List<NotificationData> cardList;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.notifications);
		nu = new NetworkUtility(this);
		prefs = new AppPreferences(this);

		getSupportActionBar().hide();

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("Notification");
		toolbar.setTitleTextColor(Color.WHITE);
		toolbar.setNavigationIcon(R.mipmap.ic_cancel);

		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		//ListItems in RecyclerView
		recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		cardList = new ArrayList<>();
		adapter = new GCMAdapter(GCMNotifications.this, cardList);

		RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(mLayoutManager);
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
		recyclerView.setAdapter(adapter);

		NotificationData a = new NotificationData("InnDemand", "You have won one coupon", "Click");
		cardList.add(a);

		a = new NotificationData("InnDemand", "Restaurant timings are changed. Here are the updated details" +
				"Restaurant timings are changed. Here are the updated details. Restaurant timings are changed. Here are the updated details. Restaurant timings are changed. Here are the updated details. Restaurant timings are changed. Here are the updated details", "More");
		cardList.add(a);

	}

	public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
		private Drawable mDivider;

		public SimpleDividerItemDecoration(Context context) {
			mDivider = ContextCompat.getDrawable(context,R.drawable.list_divider);
		}

		@Override
		public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
			int left = parent.getPaddingLeft();
			int right = parent.getWidth() - parent.getPaddingRight();

			int childCount = parent.getChildCount();
			for (int i = 0; i < childCount; i++) {
				View child = parent.getChildAt(i);

				RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

				int top = child.getBottom() + params.bottomMargin;
				int bottom = top + mDivider.getIntrinsicHeight();

				mDivider.setBounds(left, top, right, bottom);
				mDivider.draw(c);
			}
		}
	}


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
			holder.click.setText(data.getButtonText());

			if(buttonValue == null || buttonValue.equalsIgnoreCase("")) {
				holder.click.setVisibility(View.GONE);
			} else if(holder.click.getText() == "More" || buttonValue.equalsIgnoreCase("More")) {
				holder.click.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						holder.details.setMaxLines(100);
						holder.details.setText(data.getDetails());
						holder.click.setText("");
					}
				});
			}else if(holder.click.getText() == "Less" || buttonValue.equalsIgnoreCase("Less")){
				holder.click.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						holder.details.setMaxLines(2);
						holder.details.setText(data.getDetails());
						holder.click.setText("");
					}
				});
			} else if(holder.click.getText() == "Click" || buttonValue.equalsIgnoreCase("Click")){
				holder.click.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent in = new Intent(GCMNotifications.this, Restaurant.class);
						startActivity(in);
					}
				});
			}

		}

		@Override
		public int getItemCount() {
			return cartData.size();
		}
	}

}
