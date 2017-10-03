package com.devunicorn.reminder.adapter;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.devunicorn.reminder.Constants;
import com.devunicorn.reminder.R;
import com.devunicorn.reminder.data.ModelTask;
import com.devunicorn.reminder.fragment.DoneTaskFragment;
import com.devunicorn.reminder.fragment.Utils;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoneTasksAdapter extends TaskAdapter {

    public DoneTasksAdapter(DoneTaskFragment taskFragment) {
        super(taskFragment);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_task, viewGroup, false);

        CardView cardView = (CardView) view.findViewById(R.id.cardView);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView date = (TextView) view.findViewById(R.id.date);
        ImageView taskMenu = (ImageView) view.findViewById(R.id.taskMenu);
        CircleImageView priority = (CircleImageView) view.findViewById(R.id.priority);

        return new TaskViewHolder(view, cardView, title, date, taskMenu, priority);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ModelTask task = items.get(position);
        holder.itemView.setEnabled(true); //активация возможности нажатия таска
        final TaskViewHolder taskViewHolder = (TaskViewHolder) holder;

        final View itemView = taskViewHolder.itemView;
        final Resources resources = itemView.getResources();

        taskViewHolder.title.setText(task.getTitle());
        if (task.getDate() != 0) {
            taskViewHolder.date.setText(Utils.getFullDate(task.getDate()));
        } else {
            taskViewHolder.date.setText(null);
        }

        taskViewHolder.taskMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                PopupMenu popupMenu = new PopupMenu(view.getContext(), taskViewHolder.taskMenu);
                popupMenu.inflate(R.menu.task_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.taskDelete:
                                Toast.makeText(view.getContext(), "Deleted", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        itemView.setVisibility(View.VISIBLE);
        taskViewHolder.priority.setEnabled(true);

        itemView.setBackgroundColor(resources.getColor(R.color.gray_200));
        taskViewHolder.cardView.setBackgroundColor(resources.getColor(R.color.gray_200));

        taskViewHolder.title.setTextColor(resources.getColor(R.color.primary_text_disabled_material_light));
        taskViewHolder.date.setTextColor(resources.getColor(R.color.secondary_text_disabled_material_light));
        taskViewHolder.priority.setColorFilter(resources.getColor(task.getPriorityColor()));
        taskViewHolder.priority.setImageResource(R.drawable.ic_checkbox_marked_circle);


        /*itemView.setOnLongClickListener(new View.OnLongClickListener() {
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
        });*/

        taskViewHolder.priority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskViewHolder.priority.setEnabled(false);
                task.setStatus(Constants.STATUS_CURRENT);

                getTaskFragment().activity.dbHelper.update().status(task.getTimeStamp(), Constants.STATUS_CURRENT);

                itemView.setBackgroundColor(resources.getColor(R.color.gray_50));
                taskViewHolder.cardView.setBackgroundColor(resources.getColor(R.color.gray_50));

                taskViewHolder.title.setTextColor(resources.getColor(R.color.primary_text_default_material_light));
                taskViewHolder.date.setTextColor(resources.getColor(R.color.secondary_text_default_material_light));
                taskViewHolder.priority.setColorFilter(resources.getColor(task.getPriorityColor()));

                ObjectAnimator flipIn = ObjectAnimator.ofFloat(taskViewHolder.priority, "rotationY", 180f, 0f);
                taskViewHolder.priority.setImageResource(R.drawable.ic_checkbox_blank_circle_white_48dp);

                flipIn.addListener(new Animator.AnimatorListener() {

                    @Override
                    public void onAnimationStart(Animator animation) {

                    }


                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (task.getStatus() != Constants.STATUS_DONE) {

                            ObjectAnimator translationX = ObjectAnimator.ofFloat(itemView,
                                    "translationX", 0f, -itemView.getWidth());
                            ObjectAnimator translationXBack = ObjectAnimator.ofFloat(itemView,
                                    "translationX", -itemView.getWidth(), 0f);

                            translationX.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    itemView.setVisibility(View.GONE);

                                    getTaskFragment().moveTask(task);
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
