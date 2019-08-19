package com.example.seid.gc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by seid on 8/17/2019.
 */

public class Users  extends SQLiteOpenHelper {

    public Users(Context context) {
        super(context, "gc.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user(userId text, name text, email text, phone text, year text, department text, password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists user");
        onCreate(db);
    }
}
