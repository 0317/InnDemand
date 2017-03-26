package demand.inn.com.inndemand.adapter;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.List;
import demand.inn.com.inndemand.constants.FragmentData;
import demand.inn.com.inndemand.fragmentarea.DefaultFragment;

/**
 * Created by akash
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    Context mContext;

    private List<FragmentData> cartData;

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

    public void addFragment(Fragment fragment, String title){
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
