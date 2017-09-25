package com.devunicorn.reminder.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.devunicorn.reminder.MainActivity;
import com.devunicorn.reminder.R;
import com.devunicorn.reminder.adapter.RemindListAdapter;
import com.devunicorn.reminder.data.RemindData;

public abstract class AbstractTabFragment extends Fragment {

    private String title;
    protected Context context;
    //protected View view;
    protected RemindListAdapter adapter;
    protected RecyclerView rv;

    public MainActivity mainActivity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            mainActivity = (MainActivity) getActivity();
        }
            addTaskFromDB();
    }

    public void addTask(RemindData newTask, boolean saveToDB) {
        int position = -1;
        for (int i = 0; i < adapter.getItemCount(); i++) { // добавление элементов по дате
            RemindData task = adapter.getItem(i);
            if (newTask.getDate() < task.getDate()) { // если значение нового элемента списка(таск) будет меньше, чем дата любого из существующих тасков, то новый таск добавляется позицией ВЫШЕ
                position = i;
                break;
            }
        }
        if (position != -1) { //если ни один элемент из списка не больше нового, добавляем элемент позицией НИЖЕ
            adapter.addItem(position, newTask);
        } else {
            adapter.addItem(newTask);
        }

        if (saveToDB) {
            mainActivity.dbHelper.saveTask(newTask);
        }
    }

    public void removeTaskDialog(final int location) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setMessage(R.string.dialog_removing_message);

        RemindData removingTask = adapter.getItem(location);

            //RemindData removingTask = (RemindData) item;
            final long timeStamp = removingTask.getTimeStamp();

            final boolean[] isRemoved = {false};
            dialogBuilder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    adapter.removeItem(location);
                    isRemoved[0] = true;

                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.coordinator),
                            R.string.dialog_removed, Snackbar.LENGTH_LONG);

                    snackbar.setAction(R.string.dialog_cancel, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addTask(mainActivity.dbHelper.query().getTask(timeStamp), false);
                            isRemoved[0] = false;
                        }
                    });
                    snackbar.getView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                        @Override
                        public void onViewAttachedToWindow(View v) {

                        }

                        @Override
                        public void onViewDetachedFromWindow(View v) {

                            if (isRemoved[0]) {
                                mainActivity.dbHelper.removeTask(timeStamp);
                            }

                        }
                    });

                    snackbar.show();
                    dialog.dismiss();
                }
            });


            dialogBuilder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    dialog.cancel();
                }
            });
        dialogBuilder.show();
    }

    public abstract void addTaskFromDB();


    public abstract void moveTask(RemindData task);

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
