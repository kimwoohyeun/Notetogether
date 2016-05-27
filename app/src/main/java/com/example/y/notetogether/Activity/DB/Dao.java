package com.example.y.notetogether.Activity.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by y on 2016-03-22.
 */
public class Dao {
    private Context context;
    private MemoDBHelper dbHelper;
    private SQLiteDatabase db;
    private ContentValues row;

    public Dao(Context context) {
        this.context = context;
        dbHelper = new MemoDBHelper(context);
    }

    public void insertContents(Contents contents) {
        db = dbHelper.getWritableDatabase();
        row = new ContentValues();

        row.put("title", contents.getTitle());
        row.put("time", contents.getTime());
        row.put("content", contents.getContent());
        row.put("color",contents.getColor());
        db.insert("contents", null, row);
    }
    public void delete(long id)
    {
        db = dbHelper.getReadableDatabase();
        String[] whereArgs = {String.valueOf(id)};
        db.delete("contents", "_id=?", whereArgs);
    }
    public void update(long id,String content){
        db = dbHelper.getReadableDatabase();
        String[] whereArgs = {String.valueOf(id)};
        ContentValues values = new ContentValues();
        values.put("content",content);
        db.update("contents",values,"_id=?",whereArgs);
    }
    public ArrayList<Contents> getContents() {
        db = dbHelper.getReadableDatabase();

        ArrayList<Contents> contents = new ArrayList<>();
        int id;
        String title;
        String time;
        String content;
        int color;

        // String sql = "SELECT * FROM contents;";
        //Cursor cursor = db.rawQuery(sql, null);
        Cursor cursor = db.query(
        /* FROM */ "contents",
        /* SELECT */ new String[]{ "*" },
        /* WHERE */ null,
        /* WHERE args */ null,
        /* GROUP BY */ /*"color"*/null,
        /* HAVING */ null,
        /* ORDER BY */ "color,time ASC"
        );
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
            title = cursor.getString(1);
            time = cursor.getString(2);
            content = cursor.getString(3);
            color = cursor.getInt(4);
            contents.add(new Contents(id, title, time, content,color));
        }
        cursor.close();
        return contents;
    }
}