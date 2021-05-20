package com.example.mywechat.tool;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SqlHelper {
    private MDBOpenHelper dbHelper;
    private SQLiteDatabase db;
    private static final String TAG = "SqlHelper";

    public SqlHelper(Context context){
        /* 初始化并创建数据库 */
        dbHelper = new MDBOpenHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    /**
     *  自定义执行sql语句
     * @param sql
     */
    public void exec(String sql){
        this.db.execSQL(sql);
    }

    /**
     * 自定查询
     * @param sql
     * @param args
     * @return
     */
    public Cursor rawSql(String sql, String[] args){
        return this.db.rawQuery(sql,args);
    }

    public Cursor querysql(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        return this.db.query(table, columns,selection,selectionArgs,groupBy, having,orderBy);
    }

    public void closeDb(){
        if(db.isOpen()){
            Log.d(TAG,"数据库已关闭");
            this.db.close();
        }
    }
}
