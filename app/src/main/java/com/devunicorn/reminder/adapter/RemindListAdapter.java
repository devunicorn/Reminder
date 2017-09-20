package com.devunicorn.reminder.adapter;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devunicorn.reminder.R;
import com.devunicorn.reminder.data.RemindData;
import com.devunicorn.reminder.fragment.Utils;

import java.util.ArrayList;
import java.util.List;

public class RemindListAdapter extends RecyclerView.Adapter<RemindListAdapter.RemindViewHolder> {

    private List<RemindData> data;

    public RemindListAdapter() {
        this.data = new ArrayList<>();
    }

    public RemindListAdapter(List<RemindData> data) {
        this.data = data;
    }


    @Override
    public RemindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.remind_item, parent, false);

        return new RemindViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RemindViewHolder holder, int position) { //просечиваем значение на item; holder - экземпляр remindviewholder
        RemindData item = data.get(position);
        holder.itemView.setEnabled(true); //активация возможности нажатия таска
        holder.title.setText(item.getTitle());
        holder.date.setText(Utils.getFullDate(item.getDate()));
        //holder.title.setText(data.get(position).getTitle());
        //holder.date.setText(String.valueOf(data.get(position).getDate()));
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

    public static class RemindViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView title;
        TextView date;

        public RemindViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            date = (TextView) itemView.findViewById(R.id.date);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }
}
