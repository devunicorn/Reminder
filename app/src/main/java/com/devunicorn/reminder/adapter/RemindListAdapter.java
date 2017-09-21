package com.devunicorn.reminder.adapter;


import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devunicorn.reminder.R;
import com.devunicorn.reminder.data.RemindData;
import com.devunicorn.reminder.fragment.AbstractTabFragment;
import com.devunicorn.reminder.fragment.Utils;

import java.util.ArrayList;
import java.util.List;

public class RemindListAdapter extends RecyclerView.Adapter<RemindListAdapter.RemindViewHolder> {

    private List<RemindData> data;
    private AbstractTabFragment abstractTabFragment;

    public RemindListAdapter() { //инициализация массива
        this.data = new ArrayList<>();
    }

    public RemindListAdapter(AbstractTabFragment abstractTabFragment) { //инициализация массива
        this.abstractTabFragment = abstractTabFragment;
        this.data = new ArrayList<>();
    }

    public RemindListAdapter(List<RemindData> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public RemindData getItem(int position) {
        return data.get(position);
    }

    public void addItem(RemindData item) {
        data.add(item);
        notifyItemInserted(getItemCount() - 1); //сообщаем о добавлении нового элемента в список (анимация при добавлении)
    }

    public void addItem(int location, RemindData item) { //добаление элемента списка в определенную позицию
        data.add(location, item);
        notifyItemInserted(location);
    }

    public void removeItem(int location) { //удаление элемента списка (не используется)
        if (location >= 0 && location <= getItemCount() - 1) {
            data.remove(location);
            notifyItemRemoved(location);
        }
    }


    @Override
    public RemindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.remind_item, parent, false);

        return new RemindViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RemindViewHolder holder, int position) { //просечиваем значение на item; holder - экземпляр remindviewholder
        final RemindData item = data.get(position);
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
                if (item.getStatus() == RemindData.STATUS_CURRENT || item.getStatus() == RemindData.STATUS_OVERDUE) {
                    item.setStatus(RemindData.STATUS_DONE);
                } else item.setStatus(RemindData.STATUS_CURRENT);

                itemView.setVisibility(View.VISIBLE);

                holder.priority.setText(item.getPriorityStatus());

                //itemView.setBackgroundColor(resources.getColor(R.color.gray_500));
                //holder.title.setTextColor(resources.getColor(R.color.colorTextLight));
                //holder.date.setTextColor(resources.getColor(R.color.colorTextLight));
            }
        });
    }

    public static class RemindViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView title;
        TextView date;
        TextView priority;

        public RemindViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            date = (TextView) itemView.findViewById(R.id.date);
            priority = (TextView) itemView.findViewById(R.id.priority);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }
}
