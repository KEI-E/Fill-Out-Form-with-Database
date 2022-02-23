package com.example.filloutform;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    TextView tV;
    final static float move = 200;
    float ratio = 1.0f;
    int baseDist;
    float baseRatio;
    Layout lo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //actionbar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Fill Out Form");

        tV = findViewById(R.id.tv);
        tV.setTextSize(ratio+25);

        tV.setOnTouchListener(new View.OnTouchListener()
        {
            GestureDetector gd = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener()
            {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    tV.getTextSize();
                    tV.setTextSize(20);

                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gd.onTouchEvent(event);
                return false;
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getPointerCount() == 2)
        {
            int action = event.getAction();
            int mainAction = action&MotionEvent.ACTION_MASK;

            if(mainAction == MotionEvent.ACTION_POINTER_DOWN)
            {
                baseDist = getDistance(event);
                baseRatio = ratio;
            }
            else
            {
                float scale = (getDistance(event) - baseDist)/move;
                float factor = (float)Math.pow(2,scale);
                ratio = Math.min(1024.0f, Math.max(0.1f, baseRatio*factor));
                tV.setTextSize(ratio+25);
                tV.setMovementMethod(new ScrollingMovementMethod());
            }
        }

        return true;   //return super.onTouchEvent(event);
    }

    private int getDistance(MotionEvent event) {
        int dx = (int) (event.getX(0)-event.getX(1));
        int dy = (int) (event.getY(0)-event.getY(1));

        return (int)(Math.sqrt(dx*dx + dy*dy));
    }

    public void fillOutForm(View v) {
        Intent intent = new Intent(this, Summary.class);
        startActivity(intent);
    }

    public void exit(View v)
    {
        System.exit(0);
    }
}