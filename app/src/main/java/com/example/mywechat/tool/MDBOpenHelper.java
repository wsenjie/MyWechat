package com.example.mywechat.tool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MDBOpenHelper extends SQLiteOpenHelper {

    private static final String TABLE = "user";

    private static final String sql = "CREATE TABLE IF NOT EXISTS " + TABLE + "(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "userid VARCHAR(12) UNIQUE NOT NULL," +
            "password VARCHAR(12) NOT NULL)";

    MDBOpenHelper(Context context) {
        super(context, "user.db", null, 1);
    }

    /**
     * 数据库第一次创建时被调用
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
    }

    /**
     * 软件版本号发生改变时调用
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * 变更列名
     * @param db
     * @param oldColumn
     * @param newColumn
     * @param typeColumn
     */
    public void updateColumn(SQLiteDatabase db, String oldColumn, String newColumn, String typeColumn) {
        try {
            db.execSQL("ALTER TABLE " +
                    TABLE + " CHANGE " +
                    oldColumn + " " + newColumn +
                    " " + typeColumn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
