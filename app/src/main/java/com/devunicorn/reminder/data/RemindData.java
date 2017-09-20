package com.devunicorn.reminder.data;


public class RemindData {

    private String title;
    private long date;
    private long time;

   public RemindData() {

    }

    public RemindData(String title, long date) {
        this.title = title;
        this.date = date;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
