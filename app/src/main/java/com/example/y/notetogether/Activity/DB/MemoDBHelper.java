package com.example.y.notetogether.Activity.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kimwoohyeun on 2016-03-23.
 */
public class MemoDBHelper extends SQLiteOpenHelper {
    String TAG = "CREATE TABLE";
    public MemoDBHelper(Context context){
        super(context, "MemoDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql =
                "CREATE TABLE contents(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "title TEXT, time TEXT, content TEXT,color INTEGER);";
        try{
            db.execSQL(sql);
        }catch (Exception ex){
            Log.e(TAG,"Exception in CREATE_SQL",ex);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXIST board");
        //onCreate(db);
    }
}
