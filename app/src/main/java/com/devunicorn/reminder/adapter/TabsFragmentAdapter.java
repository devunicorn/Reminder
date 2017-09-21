package com.devunicorn.reminder.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.devunicorn.reminder.fragment.AbstractTabFragment;
import com.devunicorn.reminder.fragment.TodoFragment;
import com.devunicorn.reminder.fragment.DoneFragment;

import java.util.HashMap;
import java.util.Map;


public class TabsFragmentAdapter extends FragmentPagerAdapter {

    private Map<Integer, AbstractTabFragment> tabs;
    private Context context;

    public TabsFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);

        this.context = context;
        initTabsMap(context);
    }



    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int position) {

        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    private void initTabsMap(Context context) {
        tabs = new HashMap<>();
        tabs.put(0, TodoFragment.getInstance(context));
        //tabs.put(1, IdeasFragment.getInstance(context));
        tabs.put(1, DoneFragment.getInstance(context));
        //tabs.put(3, BirthdaysFragment.getInstance(context));
    }
}
