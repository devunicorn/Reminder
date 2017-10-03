package com.devunicorn.reminder.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.devunicorn.reminder.MainActivity;
import com.devunicorn.reminder.R;
import com.devunicorn.reminder.adapter.TaskAdapter;
import com.devunicorn.reminder.data.ModelTask;

public abstract class TaskFragment extends Fragment {

    public MainActivity activity;
    protected TaskAdapter adapter;
    protected RecyclerView.LayoutManager layoutManager;
    protected RecyclerView recyclerView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            activity = (MainActivity) getActivity();
        }
        addTaskFromDB();
    }

    public void addTask(ModelTask newTask, boolean saveToDB) {
        int position = -1;
        for (int i = 0; i < adapter.getItemCount(); i++) { // добавление элементов по дате
            ModelTask task = adapter.getItem(i);
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
            activity.dbHelper.saveTask(newTask);
        }
    }

    public void removeTaskDialog(final int location) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setMessage(R.string.dialog_removing_message);

        ModelTask removingTask = adapter.getItem(location);

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
                            addTask(activity.dbHelper.query().getTask(timeStamp), false);
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
                                activity.dbHelper.removeTask(timeStamp);
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

    public abstract void moveTask(ModelTask task);

}
