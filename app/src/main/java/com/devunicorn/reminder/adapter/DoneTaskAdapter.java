package com.devunicorn.reminder.adapter;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devunicorn.reminder.Constants;
import com.devunicorn.reminder.R;
import com.devunicorn.reminder.data.RemindData;
import com.devunicorn.reminder.fragment.DoneFragment;
import com.devunicorn.reminder.fragment.Utils;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoneTaskAdapter extends RemindListAdapter {

    public DoneTaskAdapter(DoneFragment doneFragment) {
        super(doneFragment);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.remind_item, viewGroup, false);

        CardView cardView = (CardView) view.findViewById(R.id.cardView);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView date = (TextView) view.findViewById(R.id.date);
        CircleImageView priority = (CircleImageView) view.findViewById(R.id.priority);

        return new TaskViewHolder(view, cardView, title, date, priority);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RemindData item = items.get(position);
        holder.itemView.setEnabled(true); //активация возможности нажатия таска
        final TaskViewHolder taskViewHolder = (TaskViewHolder) holder;

        final View itemView = taskViewHolder.itemView;
        final Resources resources = itemView.getResources();


        taskViewHolder.title.setText(item.getTitle());
        if (item.getDate() != 0) {
            taskViewHolder.date.setText(Utils.getFullDate(item.getDate()));
        } else {
            taskViewHolder.date.setText(null);
        }

        itemView.setVisibility(View.VISIBLE);
        taskViewHolder.cardView.setBackgroundColor(resources.getColor(R.color.gray_200));
        taskViewHolder.priority.setEnabled(true);

        taskViewHolder.title.setTextColor(resources.getColor(R.color.primary_text_disabled_material_light));
        taskViewHolder.date.setTextColor(resources.getColor(R.color.secondary_text_disabled_material_light));
        taskViewHolder.priority.setColorFilter(resources.getColor(item.getPriorityColor()));
        taskViewHolder.priority.setImageResource(R.drawable.ic_checkbox_blank_circle_white_48dp);


        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getTaskFragment().removeTaskDialog(taskViewHolder.getLayoutPosition());
                    }
                }, 1000);

                return true;
            }
        });

        taskViewHolder.priority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                taskViewHolder.priority.setEnabled(false);
                item.setStatus(Constants.STATUS_CURRENT);

                getTaskFragment().mainActivity.dbHelper.update().status(item.getTimeStamp(), Constants.STATUS_CURRENT);

                taskViewHolder.title.setBackgroundColor(resources.getColor(R.color.gray_50));

                taskViewHolder.title.setTextColor(resources.getColor(R.color.primary_text_default_material_light));
                taskViewHolder.date.setTextColor(resources.getColor(R.color.secondary_text_default_material_light));
                taskViewHolder.priority.setColorFilter(resources.getColor(item.getPriorityColor()));

                ObjectAnimator flipIn = ObjectAnimator.ofFloat(taskViewHolder.priority, "rotationY", 180f, 0f);
                taskViewHolder.priority.setImageResource(R.drawable.ic_checkbox_blank_circle_white_48dp);

                flipIn.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        if (item.getStatus() != Constants.STATUS_DONE) {

                            ObjectAnimator translationX = ObjectAnimator.ofFloat(itemView, "translationX", 0f, -itemView.getWidth());
                            ObjectAnimator translationXBack = ObjectAnimator.ofFloat(itemView, "translationX", -itemView.getWidth(), 0f);

                            translationX.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    itemView.setVisibility(View.GONE);

                                    getTaskFragment().moveTask(item);
                                    removeItem(taskViewHolder.getLayoutPosition());
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            });

                            AnimatorSet translationSet = new AnimatorSet();
                            translationSet.play(translationX).before(translationXBack);
                            translationSet.start();
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                flipIn.start();
            }
        });
    }
}
