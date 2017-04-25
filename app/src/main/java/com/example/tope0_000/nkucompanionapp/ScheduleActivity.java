package com.example.tope0_000.nkucompanionapp;

import android.content.SharedPreferences;
import android.gesture.Gesture;
import android.graphics.Matrix;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ScheduleActivity extends AppCompatActivity {

    private ImageView imageView;
    Float scale = 1f;
    private ViewGroup mainLayout;
    private int xDelta;
    private int yDelta;
    private boolean move = true;
    public boolean darkTheme = false;
    GestureDetector gestureDetector;

    // define the SharedPreferences object and editor
    private SharedPreferences savedValues;
    private SharedPreferences.Editor editor;

    //define instance variables that should be saved
    private boolean theme = false;

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if(imageView.getScaleX() == 1.5f) {
                imageView.setScaleX(2.3f);
                imageView.setScaleY(1.9f);
            }
            else {
                imageView.setScaleX(1.5f);
                imageView.setScaleY(1.3f);
            }
            return true;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get SharedPreferences object
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

        //Get theme saved value
        darkTheme = savedValues.getBoolean("theme", false);

        //Set Theme
        if(darkTheme)
            this.setTheme(R.style.AppTheme_dark);
        setContentView(R.layout.activity_schedule);

        gestureDetector = new GestureDetector(this, new GestureListener());

        mainLayout = (RelativeLayout) findViewById(R.id.root);
        imageView = (ImageView) findViewById(R.id.scheduleImageView);
        imageView.setImageResource(R.drawable.my_schedule);
        imageView.setOnTouchListener(onTouchListener());
        imageView.setRotation(90f);
        imageView.setScaleY(1.3f);
        imageView.setScaleX(1.5f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return gestureDetector.onTouchEvent(e);
    }

    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                gestureDetector.onTouchEvent(event);

                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();
                        //layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;

                        if(layoutParams.topMargin < -2000){
                            layoutParams.topMargin = -2000;
                        }
                        if(layoutParams.topMargin > 1900) {
                            layoutParams.topMargin = 1900;
                        }

                        //layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);
                        break;
                }
                mainLayout.invalidate();
                return true;
            }
        };
    }

    @Override
    public void onPause() {
        editor = savedValues.edit();
        editor.putBoolean("theme", darkTheme);
        editor.commit();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        // get the instance variables
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
        darkTheme = savedValues.getBoolean("theme", false);
    }
}
