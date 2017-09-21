package com.devunicorn.reminder.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.devunicorn.reminder.Constants;
import com.devunicorn.reminder.data.RemindData;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TASKS_TABLE_CREATE_SCRIPT = "CREATE TABLE "
            + Constants.TASKS_TABLE + " (" + BaseColumns._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Constants.TASK_TITLE_COLUMN + " TEXT NOT NULL, "
            + Constants.TASK_DATE_COLUMN + " LONG, " + Constants.TASK_PRIORITY_COLUMN + " INTEGER, "
            + Constants.TASK_STATUS_COLUMN + " INTEGER, " + Constants.TASK_TIME_STAMP_COLUMN + " LONG);";

    public DBHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TASKS_TABLE_CREATE_SCRIPT); // создаем таблицу при помощи скрипта
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + Constants.TASKS_TABLE); // удаление и создание таблицы
        onCreate(db);
    }

    public void saveTasks(RemindData task) { // метод для сохранения тасков

        ContentValues newValues = new ContentValues();

        newValues.put(Constants.TASK_TITLE_COLUMN, task.getTitle());
        newValues.put(Constants.TASK_DATE_COLUMN, task.getDate());
        newValues.put(Constants.TASK_PRIORITY_COLUMN, task.getPriority());
        newValues.put(Constants.TASK_STATUS_COLUMN, task.getStatus());
        newValues.put(Constants.TASK_TIME_STAMP_COLUMN, task.getTimeStamp());

        getWritableDatabase().insert(Constants.TASKS_TABLE, null, newValues);

    }
}
