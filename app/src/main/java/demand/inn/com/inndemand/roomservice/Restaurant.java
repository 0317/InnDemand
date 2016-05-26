package demand.inn.com.inndemand.roomservice;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.adapter.RestaurantAdapter;
import demand.inn.com.inndemand.constants.CartData;
import demand.inn.com.inndemand.fragmentarea.Appetizer;
import demand.inn.com.inndemand.fragmentarea.Dessert;
import demand.inn.com.inndemand.fragmentarea.MainCourse;

/**
 * Created by akash
 */
public class Restaurant extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    Toolbar toolbar;

    //TabClass Call
    Appetizer mAppetizer;
    Dessert mDessert;
    MainCourse mMaincourse;

    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private List<CartData> cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant);

        //Toolbar call
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.inflateMenu(R.menu.main_menu);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //tabclass initialization
        mAppetizer = new Appetizer();
        mDessert = new Dessert();
        mMaincourse = new MainCourse();

        viewPager = (ViewPager) findViewById(R.id.container);

        getSupportActionBar().hide();

        //Tab call area
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        //Title set for Collapsing Toolbar
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("Restaurant");
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle("");
                    isShow = false;
                }
            }
        });

    }

    private void prepareCart() {

        CartData a = new CartData("Pizza", "FarmHouse", "Rs: 250");
        cardList.add(a);

        a = new CartData("Chinese", "Noodles", "Rs: 200");
        cardList.add(a);

        a = new CartData("Chinese", "Noodles", "Rs: 200");
        cardList.add(a);

        a = new CartData("Chinese", "Noodles", "Rs: 200");
        cardList.add(a);

        a = new CartData("Chinese", "Noodles", "Rs: 200");
        cardList.add(a);

        a = new CartData("Chinese", "Noodles", "Rs: 200");
        cardList.add(a);
        a = new CartData("Chinese", "Noodles", "Rs: 200");
        cardList.add(a);
        a = new CartData("Chinese", "Noodles", "Rs: 200");
        cardList.add(a);

        a = new CartData("Chinese", "Noodles", "Rs: 200");
        cardList.add(a);

        a = new CartData("Chinese", "Noodles", "Rs: 200");
        cardList.add(a);



        adapter.notifyDataSetChanged();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(mAppetizer, "Appetizer");
        adapter.addFragment(mMaincourse, "Main Course");
        adapter.addFragment(mDessert, "Dessert");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.maincourse_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.action_cart){

        }else if(id == R.id.action_notification){

        }

        return super.onOptionsItemSelected(item);
    }

}
