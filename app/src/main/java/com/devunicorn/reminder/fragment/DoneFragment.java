package com.devunicorn.reminder.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devunicorn.reminder.R;


public class DoneFragment extends AbstractTabFragment {

    private static final int LAYOUT = R.layout.fragment_example;

    public static DoneFragment getInstance(Context context) {
        Bundle args = new Bundle();
        DoneFragment fragment = new DoneFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_done));
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        return view;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
