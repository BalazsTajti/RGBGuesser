package com.tajtesz.rgbguesser;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tajtesz.rgbguesser.sqlite.PersistentDataHelper;

public class ResultActivity extends AppCompatActivity {

    public static final String KEY_SCORE = "KEY_SCORE";
    private SQLiteDatabase scoreDatabase;
    private String name;
    private int score;
    private PersistentDataHelper dataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        dataHelper = new PersistentDataHelper(this);
        dataHelper.open();

        Intent intent = getIntent();
        score = intent.getIntExtra("KEY_SCORE", -1);

        TextView scoreTextView = (TextView) findViewById(R.id.resultScoreTextView);
        scoreTextView.setText(Integer.toString(score));

        if (dataHelper.isDbEmpty()) {
            dataHelper.fillTableWithDummy();
        }

        if (dataHelper.shouldInsert(score)) {
            showDialog();
        }

        else {
            Toast.makeText(ResultActivity.this, "You did not make it to the High Score list!", Toast.LENGTH_SHORT);
        }




        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        Button mainMenuButton = (Button) findViewById(R.id.mainMenuButton);

        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, GameActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void showDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_alert_layout);
        dialog.setTitle("Congratulations!");

        final EditText nameText = (EditText) dialog.findViewById(R.id.nameEditText);
        Button btnSubmit = (Button) dialog.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameText.getText().toString().isEmpty()) {
                    nameText.setError("Enter your name!");
                } else {
                    name = nameText.getText().toString();
                    dialog.dismiss();
                    dataHelper.persistHighScore(new HighScore(name, score));
                }
            }
        });

        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataHelper.open();
    }

    @Override
    protected void onPause() {
        dataHelper.close();
        super.onPause();
    }
}
