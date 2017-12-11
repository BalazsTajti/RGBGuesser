package com.tajtesz.rgbguesser;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by PC on 2017.09.28..
 */

public class MyTimer extends CountDownTimer {

    private TextView timerTextView;
    private long timeLeft;
    private GameActivity gameActivity;

    public MyTimer(long millisInFuture, long countDownInterval, TextView tv, GameActivity g) {
        super(millisInFuture, countDownInterval);
        timerTextView = tv;
        gameActivity = g;
    }

    @Override
    public void onTick(long l) {
        timerTextView.setText(String.valueOf(l / 1000) + "s");
        timeLeft = l;
    }

    @Override
    public void onFinish() {
        timerTextView.setText("0s");
        gameActivity.gameOver();
    }

    public long getTimeLeft(){
        return timeLeft;
    }
}
