package com.devunicorn.reminder.fragment;

import android.app.Activity;
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
import com.devunicorn.reminder.adapter.TodoTaskAdapter;
import com.devunicorn.reminder.data.RemindData;
import com.devunicorn.reminder.database.DBHelper;

import java.util.ArrayList;
import java.util.List;


public class TodoFragment extends AbstractTabFragment {


    public TodoFragment() {
    }

    public OnTaskDoneListener onTaskDoneListener;

    public interface OnTaskDoneListener {
        void onTaskDone(RemindData task);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onTaskDoneListener = (OnTaskDoneListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTaskDoneListener");
        }
    }

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(Constants.TODO_LAYOUT, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.rvTodoFragment); //найдем recycler view для заагрузки card view в layout
        rv.setLayoutManager(new LinearLayoutManager(getActivity())); //контекст, расположенный в AbstractTabFragment
        adapter = new TodoTaskAdapter(this);
        rv.setAdapter(adapter);
        return rootView;
    }


    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void addTaskFromDB() {
        List<RemindData> tasks = new ArrayList<>();
        tasks.addAll(mainActivity.dbHelper.query().getTasks(DBHelper.SELECTION_STATUS + " OR "
                + DBHelper.SELECTION_STATUS, new String[]{Integer.toString(Constants.STATUS_CURRENT),
                Integer.toString(Constants.STATUS_OVERDUE)}, DBHelper.TASK_DATE_COLUMN));
        for (int i = 0; i < tasks.size(); i++) {
            addTask(tasks.get(i), false);
        }
    }

    @Override
    public void moveTask(RemindData task) {
        onTaskDoneListener.onTaskDone(task);
    }
}
