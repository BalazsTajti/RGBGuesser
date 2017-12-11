package com.tajtesz.rgbguesser.sqlite.table;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by PC on 2017.10.13..
 */

public class HighScoresTable {

    public static final String TABLE_HIGH_SCORES = "high_scores";

    private static final String DATABASE_CREATE = "create table " + TABLE_HIGH_SCORES + "("
            + Columns._id.name() + " integer primary key autoincrement, "
            + Columns.username.name() + " text not null, "
            + Columns.score.name() + " integer not null" + ");";

    public static void onCreate(final SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(final SQLiteDatabase database, final int oldVersion, final int newVersion) {
        Log.w(HighScoresTable.class.getName(), "Upgrading from version " + oldVersion + " to " + newVersion);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGH_SCORES);
        onCreate(database);
    }

    public enum Columns {
        _id,
        username,
        score
    }

}
