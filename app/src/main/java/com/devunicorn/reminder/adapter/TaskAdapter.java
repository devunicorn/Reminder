package com.devunicorn.reminder.adapter;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.devunicorn.reminder.data.ModelTask;
import com.devunicorn.reminder.fragment.TaskFragment;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public abstract class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ModelTask> items;

    TaskFragment taskFragment;


    public TaskAdapter(TaskFragment taskFragment) { //инициализация массива
        this.taskFragment = taskFragment;
        items = new ArrayList<>();
    }

    public ModelTask getItem(int position) {
        return items.get(position);
    }

    public void addItem(ModelTask item) {
        items.add(item);
        notifyItemInserted(getItemCount() - 1); //сообщаем о добавлении нового элемента в список (анимация при добавлении)
    }

    public void addItem(int location, ModelTask item) { //добаление элемента списка в определенную позицию
        items.add(location, item);
        notifyItemInserted(location);
    }

    public void removeItem(int location) { //удаление элемента списка
        if (location >= 0 && location <= getItemCount() - 1) {
            items.remove(location);
            notifyItemRemoved(location);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class TaskViewHolder extends RecyclerView.ViewHolder {

        protected CardView cardView;
        protected TextView title;
        protected TextView date;
        protected CircleImageView priority;

        public TaskViewHolder(View itemView, CardView cardview, TextView title, TextView date, CircleImageView priority) {
            super(itemView);

            this.title = title;
            this.date = date;
            this.priority = priority;
            this.cardView = cardview;
        }
    }

    public TaskFragment getTaskFragment() {
        return taskFragment;
    }
}