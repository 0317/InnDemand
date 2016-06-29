package demand.inn.com.inndemand.setting;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.adapter.CartAdapter;
import demand.inn.com.inndemand.adapter.FeedbackAdapter;
import demand.inn.com.inndemand.constants.FeedbackData;
import demand.inn.com.inndemand.model.SimpleDividerItemDecoration;

/**
 * Created by akash
 */

public class Feedback extends AppCompatActivity {

    //UI
    Toolbar toolbar;

    //Class call
    private List<FeedbackData> cardList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FeedbackAdapter adapter;

    FeedbackData data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Feedback");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.mipmap.ic_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        ListItems in RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        cardList = new ArrayList<>();
        adapter = new FeedbackAdapter(this, cardList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Feedback.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerView.setAdapter(adapter);

        data = new FeedbackData("Inndemand", "Food is very good");
        cardList.add(data);

    }
}
