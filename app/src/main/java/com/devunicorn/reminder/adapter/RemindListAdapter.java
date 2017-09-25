package com.devunicorn.reminder.adapter;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.devunicorn.reminder.data.RemindData;
import com.devunicorn.reminder.fragment.AbstractTabFragment;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public abstract class RemindListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<RemindData> items;
    AbstractTabFragment abstractTabFragment;

    /*public RemindListAdapter() { //инициализация массива
        this.items = new ArrayList<>();
    }*/

    public RemindListAdapter(AbstractTabFragment abstractTabFragment) { //инициализация массива
        this.abstractTabFragment = abstractTabFragment;
        items = new ArrayList<>();
    }

    public RemindData getItem(int position) {
        return items.get(position);
    }

    public void addItem(RemindData item) {
        items.add(item);
        notifyItemInserted(getItemCount() - 1); //сообщаем о добавлении нового элемента в список (анимация при добавлении)
    }

    public void addItem(int location, RemindData item) { //добаление элемента списка в определенную позицию
        items.add(location, item);
        notifyItemInserted(location);
    }

    public void removeItem(int location) { //удаление элемента списка (не используется)
        if (location >= 0 && location <= getItemCount() - 1) {
            items.remove(location);
            notifyItemRemoved(location);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected static class TaskViewHolder extends RecyclerView.ViewHolder {

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

    public AbstractTabFragment getTaskFragment() {
        return abstractTabFragment;
    }
}

    /*@Override
    public RemindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.remind_item, parent, false);

        return new RemindViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RemindViewHolder holder, int position) { //просечиваем значение на item; holder - экземпляр remindviewholder
        final RemindData item = items.get(position);
        holder.itemView.setEnabled(true); //активация возможности нажатия таска

        final View itemView = holder.itemView;
        //final Resources resources = itemView.getResources();


        holder.priority.setText(item.getPriorityStatus());
        holder.title.setText(item.getTitle());
        if (item.getDate() != 0) {
            holder.date.setText(Utils.getFullDate(item.getDate()));
        } else {
            holder.date.setText(null);
        }

        itemView.setVisibility(View.VISIBLE);

        //itemView.setBackgroundColor(resources.getColor(R.color.gray_50));
        //holder.title.setTextColor(resources.getColor(R.color.colorPrimaryText));
        //holder.date.setTextColor(resources.getColor(R.color.colorTextLight));
        //holder.priority.setTextColor(resources.getColor(R.color.colorTextLight));

        holder.priority.setOnClickListener(new View.OnClickListener() { //на клик по Приоритету, меняется его статус
            @Override
            public void onClick(View view) {
                if (item.getStatus() == Constants.STATUS_CURRENT || item.getStatus() == Constants.STATUS_OVERDUE) {
                    item.setStatus(Constants.STATUS_DONE);
                    getTaskFragment().activity.dbHelper.update().status(item.getTimeStamp(), Constants.STATUS_DONE);
                    itemView.setVisibility(View.GONE);     //
                    getTaskFragment().moveTask(item);      // перенос в раздел Done
                    removeItem(holder.getLayoutPosition());//
                } else {
                    item.setStatus(Constants.STATUS_CURRENT);
                    getTaskFragment().activity.dbHelper.update().status(item.getTimeStamp(), Constants.STATUS_CURRENT);
                    itemView.setVisibility(View.GONE);     //
                    getTaskFragment().moveTask(item);      // перенос в раздел Done
                    removeItem(holder.getLayoutPosition());//
                }



                itemView.setVisibility(View.VISIBLE);

                holder.priority.setText(item.getPriorityStatus());

                //itemView.setBackgroundColor(resources.getColor(R.color.gray_500));
                //holder.title.setTextColor(resources.getColor(R.color.colorTextLight));
                //holder.date.setTextColor(resources.getColor(R.color.colorTextLight));
            }
        });
    }*/
