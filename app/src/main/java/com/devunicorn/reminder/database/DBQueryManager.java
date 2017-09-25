package com.devunicorn.reminder.database;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.devunicorn.reminder.Constants;
import com.devunicorn.reminder.data.RemindData;

import java.util.ArrayList;
import java.util.List;

//
// ВЫГРУЗКА ДАННЫХ ИЗ БАЗЫ ДАННЫХ
//

public class DBQueryManager {

    private SQLiteDatabase database;

    public DBQueryManager(SQLiteDatabase database) {
        this.database = database;
    }

    public RemindData getTask(long timeStamp) {
        RemindData modelTask = null;
        Cursor cursor = database.query(DBHelper.TASKS_TABLE, null, DBHelper.SELECTION_TIME_STAMP,
                new String[]{Long.toString(timeStamp)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex(DBHelper.TASK_TITLE_COLUMN));
                long date = cursor.getLong(cursor.getColumnIndex(DBHelper.TASK_DATE_COLUMN));
                int priority = cursor.getInt(cursor.getColumnIndex(DBHelper.TASK_PRIORITY_COLUMN));
                int status = cursor.getInt(cursor.getColumnIndex(DBHelper.TASK_STATUS_COLUMN));

                modelTask = new RemindData(title, date, priority, status, timeStamp);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return modelTask;
    }


    public List<RemindData> getTasks(String selection, String[] selectionArgs, String orderBy) {
        List<RemindData> tasks = new ArrayList<>();
        Cursor c = database.query(DBHelper.TASKS_TABLE, null, selection, selectionArgs, null, null, orderBy);
        if (c.moveToFirst()) {
            do {
                String title = c.getString(c.getColumnIndex(DBHelper.TASK_TITLE_COLUMN));
                long date = c.getLong(c.getColumnIndex(DBHelper.TASK_DATE_COLUMN));
                int priority = c.getInt(c.getColumnIndex(DBHelper.TASK_PRIORITY_COLUMN));
                int status = c.getInt(c.getColumnIndex(DBHelper.TASK_STATUS_COLUMN));
                long timeStamp = c.getLong(c.getColumnIndex(DBHelper.TASK_TIME_STAMP_COLUMN));

                RemindData task = new RemindData(title, date, priority, status, timeStamp);
                tasks.add(task);
            }
            while (c.moveToNext());

        }
        c.close();
        return tasks;
    }
}
