package com.devunicorn.reminder.fragment;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.devunicorn.reminder.adapter.RemindListAdapter;
import com.devunicorn.reminder.data.RemindData;

public abstract class AbstractTabFragment extends Fragment{

    private String title;
    protected Context context;
    protected View view;
    protected RemindListAdapter adapter;
    protected RecyclerView rv;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
