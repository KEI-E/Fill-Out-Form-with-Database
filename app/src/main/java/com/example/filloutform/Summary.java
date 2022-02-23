package com.example.filloutform;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Summary extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    float x1, x2;   //Swipe
    Button insert, update, delete, view;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //actionbar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Fill Out Form");

        //EditText
        EditText nameF = findViewById(R.id.name);
        EditText genderF = findViewById(R.id.gender);
        EditText phoneN = findViewById(R.id.phoneNum);
        EditText program = findViewById(R.id.program);
        EditText city = findViewById(R.id.city);
        EditText country = findViewById(R.id.country);
        EditText school = findViewById(R.id.school);

        insert = findViewById(R.id.btnInsert);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        view = findViewById(R.id.btnView);

        db = new DBHelper(this);

        //Button
        Button btnSave = findViewById(R.id.btnSave);
        Button btnBack = findViewById(R.id.btnBack);

        //Register in context menu
        registerForContextMenu(btnSave);

        //Button click listener
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTXT = nameF.getText().toString();
                String genderTXT = genderF.getText().toString();
                String phoneTXT = phoneN.getText().toString();
                String programTXT = program.getText().toString();
                String cityTXT = city.getText().toString();
                String countryTXT = country.getText().toString();
                String schoolTXT = school.getText().toString();

                Boolean checkInsertData = db.insertData(nameTXT, genderTXT, phoneTXT, programTXT, cityTXT, countryTXT, schoolTXT);
                if(checkInsertData == true)
                    Toast.makeText(Summary.this, "Data Successfully Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Summary.this, "Failed to Insert Data", Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTXT = nameF.getText().toString();
                String genderTXT = genderF.getText().toString();
                String phoneTXT = phoneN.getText().toString();
                String programTXT = program.getText().toString();
                String cityTXT = city.getText().toString();
                String countryTXT = country.getText().toString();
                String schoolTXT = school.getText().toString();

                Boolean checkUpdateData = db.updateData(nameTXT, genderTXT, phoneTXT, programTXT, cityTXT, countryTXT, schoolTXT);
                if(checkUpdateData == true)
                    Toast.makeText(Summary.this, "Data Successfully Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Summary.this, "Failed to Update Data", Toast.LENGTH_SHORT).show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTXT = nameF.getText().toString();

                Boolean checkDeleteData = db.deleteData(nameTXT);
                if(checkDeleteData == true)
                    Toast.makeText(Summary.this, "Data Successfully Delete", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Summary.this, "Failed to Delete Data", Toast.LENGTH_SHORT).show();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = db.getData();

                if(res.getCount() == 0)
                {
                    Toast.makeText(Summary.this, "Data Not Found", Toast.LENGTH_SHORT).show();

                    return;
                }

                StringBuffer buffer = new StringBuffer();

                while(res.moveToNext())
                {
                    buffer.append("\nName: " + res.getString(0) + "\n");
                    buffer.append("Gender: " + res.getString(1) + "\n");
                    buffer.append("Phone Number: " + res.getString(2) + "\n");
                    buffer.append("Program: " + res.getString(3) + "\n");
                    buffer.append("City: " + res.getString(4) + "\n");
                    buffer.append("Country: " + res.getString(5) + "\n");
                    buffer.append("School: " + res.getString(6) + "\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(Summary.this);
                builder.setCancelable(true);
                builder.setTitle("Form Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent test = getIntent();
                finish();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.save1:
                //EditText
                EditText nameF = findViewById(R.id.name);
                EditText genderF = findViewById(R.id.gender);
                EditText phoneN = findViewById(R.id.phoneNum);
                EditText program = findViewById(R.id.program);
                EditText city = findViewById(R.id.city);
                EditText country = findViewById(R.id.country);
                EditText school = findViewById(R.id.school);

                //get data from EditText
                String name = nameF.getText().toString();
                String genderP = genderF.getText().toString();
                String phone = phoneN.getText().toString();
                String programP = program.getText().toString();
                String cityP = city.getText().toString();
                String countryP = country.getText().toString();
                String schoolP = school.getText().toString();

                if(name.isEmpty() && genderP.isEmpty() && phone.isEmpty() && programP.isEmpty() && cityP.isEmpty() && countryP.isEmpty() && schoolP.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"No inputs to save", Toast.LENGTH_LONG).show();
                }
                else
                {
                    //activity intent (explicit)
                    Intent i = new Intent(Summary.this, Second_Activity.class);
                    i.putExtra("Name", name);
                    i.putExtra("Gender", genderP);
                    i.putExtra("Phone Number", phone);
                    i.putExtra("Program", programP);
                    i.putExtra("City", cityP);
                    i.putExtra("Country", countryP);
                    i.putExtra("School", schoolP);
                    startActivity(i);
                }
                break;
            case R.id.copy1:
                final EditText nameC = findViewById(R.id.name);
                final EditText genderC = findViewById(R.id.gender);
                final EditText phoneC = findViewById(R.id.phoneNum);
                final EditText programC = findViewById(R.id.program);
                final EditText cityC = findViewById(R.id.city);
                final EditText countryC = findViewById(R.id.country);
                final EditText schoolC = findViewById(R.id.school);

                String nameS = nameC.getText().toString();
                String genderS = genderC.getText().toString();
                String phoneS = phoneC.getText().toString();
                String programS = programC.getText().toString();
                String cityS = cityC.getText().toString();
                String countryS = countryC.getText().toString();
                String schoolS = schoolC.getText().toString();

                if(nameS.isEmpty() && genderS.isEmpty() && phoneS.isEmpty() && programS.isEmpty() && cityS.isEmpty() && countryS.isEmpty() && schoolS.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"No inputs to copy", Toast.LENGTH_LONG).show();
                }
                else
                {
                    ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("DataName", nameS);
                    clipboardManager.setPrimaryClip(clipData);
                    ClipData clipData1 = ClipData.newPlainText("DataGender", genderS);
                    clipboardManager.setPrimaryClip(clipData1);
                    ClipData clipData2 = ClipData.newPlainText("DataPhone", phoneS);
                    clipboardManager.setPrimaryClip(clipData2);
                    ClipData clipData3 = ClipData.newPlainText("DataProgram", programS);
                    clipboardManager.setPrimaryClip(clipData3);
                    ClipData clipData4 = ClipData.newPlainText("DataCity", cityS);
                    clipboardManager.setPrimaryClip(clipData4);
                    ClipData clipData5 = ClipData.newPlainText("DataCountry", countryS);
                    clipboardManager.setPrimaryClip(clipData5);
                    ClipData clipData8 = ClipData.newPlainText("DataSchool", schoolS);
                    clipboardManager.setPrimaryClip(clipData8);

                    Toast.makeText(getApplicationContext(), "Successfully Copied to Clipboard", Toast.LENGTH_LONG).show();;
                }
                break;
            case R.id.delete1:
                final EditText nameD = findViewById(R.id.name);
                final EditText genderD = findViewById(R.id.gender);
                final EditText phoneD = findViewById(R.id.phoneNum);
                final EditText programD = findViewById(R.id.program);
                final EditText cityD = findViewById(R.id.city);
                final EditText countryD = findViewById(R.id.country);
                final EditText schoolD = findViewById(R.id.school);

                String nameT = nameD.getText().toString();
                String genderT = genderD.getText().toString();
                String phoneT = phoneD.getText().toString();
                String programT = programD.getText().toString();
                String cityT = cityD.getText().toString();
                String countryT = countryD.getText().toString();
                String schoolT = schoolD.getText().toString();

                if(nameT.isEmpty() && genderT.isEmpty() && phoneT.isEmpty() && programT.isEmpty() && cityT.isEmpty() && countryT.isEmpty() && schoolT.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"No inputs to delete", Toast.LENGTH_LONG).show();
                }
                else
                {
                    nameD.setText("");
                    genderD.setText("");
                    phoneD.setText("");
                    programD.setText("");
                    cityD.setText("");
                    countryD.setText("");
                    schoolD.setText("");

                    Toast.makeText(getApplicationContext(), "Successfully Deleted Inputs", Toast.LENGTH_LONG).show();;
                }
                break;
        }

        return false;   //return super.onContextItemSelected(item);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
            case MotionEvent.ACTION_UP:
                x2 = event.getX();

                //2nd act to 1st act -- swipe left
                if(x1 < x2)
                {
                    Intent i = new Intent(Summary.this, MainActivity.class);
                    startActivity(i);
                }

                //2nd act to 3rd act -- swipe right
                if(x1 > x2)
                {
                    //EditText
                    EditText nameF = findViewById(R.id.name);
                    EditText genderF = findViewById(R.id.gender);
                    EditText phoneN = findViewById(R.id.phoneNum);
                    EditText program = findViewById(R.id.program);
                    EditText city = findViewById(R.id.city);
                    EditText country = findViewById(R.id.country);
                    EditText school = findViewById(R.id.school);

                    //get data from EditText
                    String name = nameF.getText().toString();
                    String genderP = genderF.getText().toString();
                    String phone = phoneN.getText().toString();
                    String programP = program.getText().toString();
                    String cityP = city.getText().toString();
                    String countryP = country.getText().toString();
                    String schoolP = school.getText().toString();

                    //activity intent (explicit)
                    Intent i = new Intent(Summary.this, Second_Activity.class);
                    i.putExtra("Name", name);
                    i.putExtra("Gender", genderP);
                    i.putExtra("Phone Number", phone);
                    i.putExtra("Program", programP);
                    i.putExtra("City", cityP);
                    i.putExtra("Country", countryP);
                    i.putExtra("School", schoolP);
                    startActivity(i);
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