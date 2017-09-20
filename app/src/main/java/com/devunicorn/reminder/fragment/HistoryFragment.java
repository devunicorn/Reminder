package com.devunicorn.reminder.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devunicorn.reminder.R;
import com.devunicorn.reminder.adapter.RemindListAdapter;
import com.devunicorn.reminder.data.RemindData;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends AbstractTabFragment {

    private static final int LAYOUT = R.layout.fragment_history;

    RemindListAdapter adapter;
    RecyclerView rv;

    public static HistoryFragment getInstance(Context context) {
        Bundle args = new Bundle();
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_history));
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        rv = (RecyclerView) view.findViewById(R.id.recyclerView); //найдем recycler view для заагрузки card view в layout history
        rv.setLayoutManager(new LinearLayoutManager(context)); //контекст, расположенный в AbstractTabFragment
        //rv.setAdapter(new RemindListAdapter(createMockRemindListData()));
        adapter = new RemindListAdapter();
        rv.setAdapter(adapter);

        return view;
    }

    public void addTask(RemindData newTask) {
        int position = -1;

        for (int i = 0; i < adapter.getItemCount(); i++) { // добавление элементов по дате
            RemindData item = (RemindData) adapter.getItem(i);
            if (newTask.getDate() < item.getDate()) { // если значение нового элемента списка(таск) будет меньше, чем дата любого из существующих тасков, то новый таск добавляется позицией ВЫШЕ
                position = i;
                break;
            }
        }
        if (position != -1) { //если ни один элемент из списка не больше нового, добавляем элемент позицией НИЖЕ
            adapter.addItem(position, newTask);
        } else {
            adapter.addItem(newTask);
        }
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
