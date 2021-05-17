package com.kotlin.base.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class MySQLiteDBDao {

    private final MySQLiteOpenHelper liteOpenHelper;
    private static MySQLiteDBDao intance;

    public static MySQLiteDBDao getInstance(Context context) {
        if (intance == null) {
            synchronized (MySQLiteDBDao.class) {
                if (intance == null) {
                    intance = new MySQLiteDBDao(context);
                }
            }
        }
        return intance;
    }

    private MySQLiteDBDao(Context context) {
        liteOpenHelper = new MySQLiteOpenHelper(context);
    }

    public void delete(String tableName, String whereClause, String value) {
        if (liteOpenHelper != null) {
            try {
                SQLiteDatabase writableDatabase = liteOpenHelper.getWritableDatabase();
                writableDatabase.delete(tableName, whereClause + "=?", new String[]{value});
                writableDatabase.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void insertHistory(String tableName, String history) {
        if (TextUtils.isEmpty(tableName) || TextUtils.isEmpty(history)) {
            return;
        }
        if (liteOpenHelper != null) {
            try {
                SQLiteDatabase writableDatabase = liteOpenHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MySQLiteOpenHelper.table.HistoryTable.Column._HISTORY, history);
                writableDatabase.insert(tableName, null, contentValues);
                writableDatabase.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteHistory(String tableName, String history) {
        if (liteOpenHelper != null) {
            try {
                SQLiteDatabase writableDatabase = liteOpenHelper.getWritableDatabase();
                writableDatabase.delete(tableName, MySQLiteOpenHelper.table.HistoryTable.Column._HISTORY + "=?", new String[]{history});
                writableDatabase.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public List<String> queryHistoryList(String tableName) {
        if (liteOpenHelper != null) {
            List<String> historyList = new ArrayList<>();
            try {
                SQLiteDatabase readableDatabase = liteOpenHelper.getReadableDatabase();
                Cursor cursor = readableDatabase.rawQuery("select * from " + tableName, null);
                while (cursor.moveToNext()) {
                    String history = cursor.getString(1);
                    historyList.add(history);
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return historyList;
        }
        return null;
    }
}
