package com.tajtesz.rgbguesser;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.tajtesz.rgbguesser.sqlite.DBHelper;
import com.tajtesz.rgbguesser.sqlite.PersistentDataHelper;

import java.util.ArrayList;
import java.util.List;

public class HighScoreActivity extends AppCompatActivity {

    private SQLiteDatabase scoreDatabase;
    private ArrayList<HighScore> results = new ArrayList<>();
    private PersistentDataHelper dataHelper;

    private RecyclerView recyclerView;
    private HighScoreAdapter adapter;

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.HighScoreRecyclerView);
        adapter = new HighScoreAdapter();
        loadItemsInBackground();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadItemsInBackground() {
        new AsyncTask<Void, Void, List<HighScore>>() {

            @Override
            protected List<HighScore> doInBackground(Void... voids) {
                dataHelper = new PersistentDataHelper(getApplicationContext());
                dataHelper.open();
                if (dataHelper.isDbEmpty()) {
                    dataHelper.fillTableWithDummy();
                }
                results = dataHelper.getTopTen();
                dataHelper.close();
                return results;
            }

            @Override
            protected void onPostExecute(List<HighScore> scoreItems) {
                super.onPostExecute(scoreItems);
                adapter.update(scoreItems);
            }
        }.execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        initRecyclerView();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
