package com.tajtesz.rgbguesser.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.tajtesz.rgbguesser.HighScore;
import com.tajtesz.rgbguesser.sqlite.table.HighScoresTable;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by PC on 2017.10.13..
 */

public class PersistentDataHelper {

    private SQLiteDatabase database;

    private DBHelper dbHelper;

    private String[] highScoreColumns = {
            HighScoresTable.Columns._id.name(),
            HighScoresTable.Columns.username.name(),
            HighScoresTable.Columns.score.name()
    };

    public PersistentDataHelper(final Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLiteException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void persistHighScore(HighScore hs) {
        final ContentValues values = new ContentValues();
        values.put(HighScoresTable.Columns.username.name(), hs.getName());
        values.put(HighScoresTable.Columns.score.name(), hs.getScore());
        database.insert(HighScoresTable.TABLE_HIGH_SCORES, null, values);
    }

    public void clearHighScores() {
        database.delete(HighScoresTable.TABLE_HIGH_SCORES, null, null);
    }

    public boolean isDbEmpty(){
        boolean empty = true;
        Cursor cur = database.rawQuery("SELECT COUNT(*) FROM " + HighScoresTable.TABLE_HIGH_SCORES, null);
        if (cur != null && cur.moveToFirst()) {
            empty = (cur.getInt (0) == 0);
        }
        cur.close();

        return empty;
    }

    public boolean shouldInsert(int score) {
        Cursor c = database.rawQuery("SELECT * FROM (SELECT * FROM " + HighScoresTable.TABLE_HIGH_SCORES + " ORDER BY score DESC) LIMIT 10", null);
        int usernameIndex = c.getColumnIndex("username");
        int scoreIndex = c.getColumnIndex("score");
        c.moveToLast();

        if (score > c.getInt(HighScoresTable.Columns.score.ordinal())) {
            return true;
        }
        return false;
    }

    public void fillTableWithDummy() {
        for (int i = 0; i < 10; i++) database.execSQL("INSERT INTO " + HighScoresTable.TABLE_HIGH_SCORES + " (username, score) VALUES ('AAAAA', 0)");
    }

    public ArrayList<HighScore> getTopTen() {
        ArrayList<HighScore> results = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT * FROM (SELECT * FROM " + HighScoresTable.TABLE_HIGH_SCORES + " ORDER BY score DESC) LIMIT 10", null);
        int usernameIndex = c.getColumnIndex(HighScoresTable.Columns.username.name());
        int scoreIndex = c.getColumnIndex(HighScoresTable.Columns.score.name());
        String name;
        int score;
        int index = 1;

        if (c.moveToFirst()) {
            do {
                name = c.getString(usernameIndex);
                score = c.getInt(scoreIndex);

                results.add(new HighScore(name, score));
                index++;
            } while (c.moveToNext());
        }
        return results;
    }

}
