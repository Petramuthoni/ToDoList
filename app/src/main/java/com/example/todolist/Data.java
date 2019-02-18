package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Data extends SQLiteOpenHelper {
    public static final String DB_NAME="todo.db";
    public static final String TABLE_NAME="tasks";
    public static final int DB_VERSION=1;
    public static final String COLUMN_NAME="TaskName";
    public Data( Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query=String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT ,%s TEXT NOT NULL);",TABLE_NAME , COLUMN_NAME);
        sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query=String.format("DELETE TABLE IF EXISTS %s",TABLE_NAME);
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);

    }
    public void insertTask(String task){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_NAME,task);
        sqLiteDatabase.insertWithOnConflict(TABLE_NAME,null,contentValues,sqLiteDatabase.CONFLICT_REPLACE);
        sqLiteDatabase.close();
    }
    public void deleteTask(String task){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME,COLUMN_NAME + " =?",new String[]{task});
        sqLiteDatabase.close();

    }
    public ArrayList<String> getTaskList(){
        ArrayList<String> taskList=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.query(TABLE_NAME,new String[]{COLUMN_NAME},null,null,null,null,null);
        while (cursor.moveToNext()){
            int index=cursor.getColumnIndex(COLUMN_NAME);
            taskList.add(cursor.getString(index));

        }
        cursor.close();
        sqLiteDatabase.close();
        return taskList;

    }
}
