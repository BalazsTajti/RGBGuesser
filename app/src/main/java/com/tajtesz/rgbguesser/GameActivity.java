package com.tajtesz.rgbguesser;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private Button btn11;
    private Button btn12;
    private Button btn21;
    private Button btn22;
    private Button btn31;
    private Button btn32;
    private TextView colorText;
    private TextView timerTextView;
    private TextView scoreTextView;
    private MyTimer timer;
    private ArrayList<Integer> randomColors = new ArrayList<>();
    private int pickedColor;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        btn11 = (Button) findViewById(R.id.butt11);
        btn12 = (Button) findViewById(R.id.butt12);
        btn21 = (Button) findViewById(R.id.butt21);
        btn22 = (Button) findViewById(R.id.butt22);
        btn31 = (Button) findViewById(R.id.butt31);
        btn32 = (Button) findViewById(R.id.butt32);
        colorText = (TextView) findViewById(R.id.colorText);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        scoreTextView.setText(Integer.toString(score));

        btn11.setOnClickListener(new MyListener());
        btn12.setOnClickListener(new MyListener());
        btn21.setOnClickListener(new MyListener());
        btn22.setOnClickListener(new MyListener());
        btn32.setOnClickListener(new MyListener());
        btn31.setOnClickListener(new MyListener());

        reset();
    }

    private void generateColors(int n){
        for (int i = 0; i < n; i++){
            int r = (int) Math.floor(Math.random()*256);
            int g = (int) Math.floor(Math.random()*256);
            int b = (int) Math.floor(Math.random()*256);
            randomColors.add(Color.rgb(r,g,b));
        }
    }

    private int pickColor(){
        return randomColors.get((int)Math.floor(Math.random()*randomColors.size()));
    }

    private void reset(){
        if (timer != null) timer.cancel();
        randomColors.clear();
        generateColors(6);

        timer = new MyTimer(10100, 1000, timerTextView, this);
        timer.start();

        ValueAnimator colorAnimation1 = ValueAnimator.ofObject(new ArgbEvaluator(), getViewColor(btn11), randomColors.get(0));
        colorAnimation1.setDuration(1200); // milliseconds
        colorAnimation1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                btn11.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation1.start();

        ValueAnimator colorAnimation2 = ValueAnimator.ofObject(new ArgbEvaluator(), getViewColor(btn12), randomColors.get(1));
        colorAnimation2.setDuration(1200); // milliseconds
        colorAnimation2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                btn12.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation2.start();

        ValueAnimator colorAnimation3 = ValueAnimator.ofObject(new ArgbEvaluator(), getViewColor(btn21), randomColors.get(2));
        colorAnimation3.setDuration(1200); // milliseconds
        colorAnimation3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                btn21.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation3.start();

        ValueAnimator colorAnimation4 = ValueAnimator.ofObject(new ArgbEvaluator(), getViewColor(btn22), randomColors.get(3));
        colorAnimation4.setDuration(1200); // milliseconds
        colorAnimation4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                btn22.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation4.start();

        ValueAnimator colorAnimation5 = ValueAnimator.ofObject(new ArgbEvaluator(), getViewColor(btn31), randomColors.get(4));
        colorAnimation5.setDuration(1200); // milliseconds
        colorAnimation5.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                btn31.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation5.start();

        ValueAnimator colorAnimation6 = ValueAnimator.ofObject(new ArgbEvaluator(), getViewColor(btn32), randomColors.get(5));
        colorAnimation6.setDuration(1200); // milliseconds
        colorAnimation6.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                btn32.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation6.start();


        pickedColor = pickColor();
        colorText.setText(getString(R.string.rgb_open) + Color.red(pickedColor) + getString(R.string.colon) + Color.green(pickedColor) + getString(R.string.colon) + Color.blue(pickedColor) + getString(R.string.rgb_close));

    }

    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int color = getViewColor(view);

            if (color == pickedColor){
                score += timer.getTimeLeft() / 1000 * 10;
                scoreTextView.setText(Integer.toString(score));
                reset();
            }
            else {
                gameOver();
                timer.cancel();
            }
        }
    }

    private int getViewColor(View v){
        Drawable background = v.getBackground();
        if (background instanceof ColorDrawable)
            return ((ColorDrawable) background).getColor();
        return Color.TRANSPARENT;
    }

    public void gameOver(){
        Intent intent = new Intent(GameActivity.this, ResultActivity.class);
        intent.putExtra(ResultActivity.KEY_SCORE, score);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){

        timer.cancel();
        super.onBackPressed();

    }

}
