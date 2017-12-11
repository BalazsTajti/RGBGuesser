package com.tajtesz.rgbguesser.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tajtesz.rgbguesser.sqlite.table.HighScoresTable;

/**
 * Created by PC on 2017.10.13..
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "highscores.db";

    private static final int DATABASE_VERSION = 1;

    public DBHelper(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        HighScoresTable.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase sqLiteDatabase, final int oldVersion, final int newVersion) {
        HighScoresTable.onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }
}
