package com.kotlin.base.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    public interface table {

        interface HistoryTable {
            String TABLE_NAME = "history";

            interface Column {
                String _ID = "id";
                String _HISTORY = "history";
            }

            String CREATE_TABLE_SQL =
                    "CREATE TABLE " + TABLE_NAME + "("
                            + Column._ID + " INTEGER PRIMARY KEY,"
                            + Column._HISTORY + " VARCHAR(30)"
                            + ")";
        }
    }

    public MySQLiteOpenHelper(@Nullable Context context) {
        super(context, "qmore.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(table.HistoryTable.CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
