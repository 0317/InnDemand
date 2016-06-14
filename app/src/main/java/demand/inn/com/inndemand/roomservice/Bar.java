package demand.inn.com.inndemand.roomservice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;

/**
 * Created by akash
 */

public class Bar extends AppCompatActivity{

    //Utility
    NetworkUtility nu;
    AppPreferences prefs;

    //UI Call area
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter adapter;

    //UI call
    Toolbar toolbar;

    View view;
    private Menu menu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bar);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);

        //Toolbar call
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.inflateMenu(R.menu.restaurant_menu);
        this.menu = menu;
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                //noinspection SimplifiableIfStatement
                if (id == R.id.action_settings) {
                    return true;
                }else if(id == R.id.action_cart){
//                    Intent in = new Intent(Restaurant.this, MyCart.class);
//                    startActivity(in);
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }else if(id == R.id.action_notification){

                }else if(id == R.id.action_food){
                    final View menuItemView = findViewById(R.id.action_food);
                    final PopupMenu popup = new PopupMenu(Bar.this, menuItemView);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
//
                            switch (item.getItemId()) {
                                case R.id.action_all:
                                    toolbar.getMenu().getItem(0).setIcon(R.mipmap.ic_menu_filter);
                                    return  true;

                                case R.id.action_veg:
                                    toolbar.getMenu().getItem(0).setIcon(R.mipmap.ic_menu_filter_green);
                                    return true;

                                case R.id.action_nonveg:
                                    toolbar.getMenu().getItem(0).setIcon(R.mipmap.ic_menu_filter_red);
                                    return true;
                            }
                            return true;
                        }
                    });

                    popup.show();//showing popup menu
                }
                return true;
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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
                    collapsingToolbarLayout.setTitle("Bar");
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle("");
                    isShow = false;
                }
            }
        });

        viewPager = (ViewPager) findViewById(R.id.container);

        //Tab call area
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
//            adapter.addFragment(mAppetizer, "Appetizer");
//            adapter.addFragment(mMaincourse, "Main Course");
//            adapter.addFragment(mDessert, "Dessert");
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        public void addTab(String title) {
            mFragmentTitleList.add(title);
            notifyDataSetChanged();
        }
        public void removeTab() {
            if (!mFragmentTitleList.isEmpty()) {
                mFragmentTitleList.remove(mFragmentTitleList.size() - 1);
                notifyDataSetChanged();
            }
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
}
