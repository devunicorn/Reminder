package com.devunicorn.reminder.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devunicorn.reminder.Constants;
import com.devunicorn.reminder.R;
import com.devunicorn.reminder.adapter.RemindListAdapter;


public class TodoFragment extends AbstractTabFragment {


    public static TodoFragment getInstance(Context context) {
        Bundle args = new Bundle();
        TodoFragment fragment = new TodoFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_todo));
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(Constants.HISTORY_LAYOUT, container, false);

        rv = (RecyclerView) view.findViewById(R.id.recyclerView); //найдем recycler view для заагрузки card view в layout history
        rv.setLayoutManager(new LinearLayoutManager(context)); //контекст, расположенный в AbstractTabFragment
        //rv.setAdapter(new RemindListAdapter(createMockRemindListData()));
        adapter = new RemindListAdapter(this);
        rv.setAdapter(adapter);

        return view;
    }


    /*private List<RemindData> createMockRemindListData() { //заглушка, тестовые данные для вывода (база данных, сервер)

        List<RemindData> data = new ArrayList<>();
        data.add(new RemindData("Item 1", 120120120));
        return data;
    }*/

    public void setContext(Context context) {
        this.context = context;
    }

}
