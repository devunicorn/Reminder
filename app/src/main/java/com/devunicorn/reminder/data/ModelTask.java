package com.devunicorn.reminder.data;


import com.devunicorn.reminder.Constants;
import com.devunicorn.reminder.R;

import java.util.Date;

public class ModelTask {

    private String title;
    private long date;
    private int priority;
    private int status;
    private long timeStamp;

    public ModelTask() {
        this.status = -1;
        this.timeStamp = new Date().getTime();
    }

    public ModelTask(String title, long date, int priority, int status, long timeStamp) {
        this.title = title;
        this.date = date;
        this.priority = priority;
        this.status = status;
        this.timeStamp = timeStamp;
    }

    public int getPriorityColor() {
        switch (getPriority()) {
            case Constants.PRIORITY_HIGH:
                if (getStatus() == Constants.STATUS_CURRENT || getStatus() == Constants.STATUS_OVERDUE) {
                    return R.color.priority_high;
                } else {
                    return R.color.priority_high_selected;
                }
            case Constants.PRIORITY_NORMAL:
                if (getStatus() == Constants.STATUS_CURRENT || getStatus() == Constants.STATUS_OVERDUE) {
                    return R.color.priority_normal;
                } else {
                    return R.color.priority_normal_selected;
                }
            case Constants.PRIORITY_LOW:
                if (getStatus() == Constants.STATUS_CURRENT || getStatus() == Constants.STATUS_OVERDUE) {
                    return R.color.priority_low;
                } else {
                    return R.color.priority_low_selected;
                }
            default:
                return Constants.PRIORITY_LOW;
        }
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

}
