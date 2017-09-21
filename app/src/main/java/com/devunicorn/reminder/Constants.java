package com.devunicorn.reminder;



public class Constants {

    public static final int TODO_LAYOUT = R.layout.fragment_todo;
    public static final int DONE_LAYOUT = R.layout.fragment_done;

    public static final int TAB_ONE = 0;
    public static final int TAB_TWO = 1;


    //priority status
    public static final int PRIORITY_LOW = 0;
    public static final int PRIORITY_NORMAL = 1;
    public static final int PRIORITY_HIGH = 2;

    public static final String[] PRIORITY_LEVELS = {"Low priority", "Normal priority", "High priority"};

    public static final int STATUS_OVERDUE = 0;
    public static final int STATUS_CURRENT = 1;
    public static final int STATUS_DONE = 2;


    //database
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "reminder_database";

    public static final String TASKS_TABLE = "tasks_table";

    public static final String TASK_TITLE_COLUMN = "task_title";
    public static final String TASK_DATE_COLUMN = "task_date";
    public static final String TASK_PRIORITY_COLUMN = "task_priority";
    public static final String TASK_STATUS_COLUMN = "task_status";
    public static final String TASK_TIME_STAMP_COLUMN = "task_time_stamp";

}
