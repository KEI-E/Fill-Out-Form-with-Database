package com.example.filloutform;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Second_Activity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{
    EditText et;
    GestureDetector gd;
    private Object MainActivity;
    float x1, x2;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);

        //actionbar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Summary of the Form");

        //intent to get the datas
        Intent i = getIntent();
        String name = i.getStringExtra("Name");
        String gender = i.getStringExtra("Gender");
        String phone = i.getStringExtra("Phone Number");
        String program = i.getStringExtra("Program");
        String city = i.getStringExtra("City");
        String country = i.getStringExtra("Country");
        String school = i.getStringExtra("School");

        //Getting the gesture
        gd = new GestureDetector(this, this);
        gd.setOnDoubleTapListener(this);

        //TextView
        TextView resultTv = findViewById(R.id.resultTv);

        //setText
        resultTv.setText("Name: " +name+ "\nGender: " +gender+ "\nPhone Number: " +phone+ "\nProgram: " +program+ "\nCity: " +city+ "\nCountry: " +country+ "\nSchool: " +school);
    }

    public void backBtn(View v)
    {
        Intent i = getIntent();
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
            case MotionEvent.ACTION_UP:
                x2 = event.getX();

                //3rd act to 2nd act -- swipe left
                if(x1 < x2)
                {
                    Intent i = new Intent(Second_Activity.this, Summary.class);
                    finish();
                }
                break;
        }

        return false;   //return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}