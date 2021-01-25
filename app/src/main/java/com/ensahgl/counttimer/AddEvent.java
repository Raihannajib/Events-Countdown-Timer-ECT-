package com.ensahgl.counttimer;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ensahgl.counttimer.models.EventModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddEvent extends Activity {
    Button save;
    EditText edittext;
    EditText edittextdate;
    TextView error_n;
    TextView error_d;
    List<EventModel> events;
    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

//Selectors
        save = findViewById(R.id.save);
        edittext = findViewById(R.id.eventName);
        edittextdate = (EditText) findViewById(R.id.dateEvent);
        error_n = (TextView) findViewById(R.id.error_name);
        error_d = (TextView) findViewById(R.id.error_event);

        LoadData();

// save event plus Validation

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edittext.getText().toString().equalsIgnoreCase("")){
                    error_n.setHint("event name is required");
                }else if(edittextdate.getText().toString().equalsIgnoreCase("")){
                    error_d.setHint("event date is required");
                }else {
                    try {
                        EventModel event = new EventModel(edittext.getText().toString(), edittextdate.getText().toString());
                        Toast.makeText(AddEvent.this, event.getDate(), Toast.LENGTH_LONG).show();
                        events.add(event);
                        saveData();
                        finish();

                    } catch (Exception e) {
                        Toast.makeText(AddEvent.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }
        });



//Show Date/Time picker
        edittextdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDateTimePicker();
            }
        });


    }


// Save all events to shared prefrences
    public synchronized void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(events);
        editor.putString("task list", json);
        editor.apply();
    }
// Get all events to shared prefrences
    public synchronized void LoadData() {

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<EventModel>>() {
        }.getType();
        events = gson.fromJson(json, type);
        if (events == null) {
            events = new ArrayList<>();
        }

    }
// Date/Time picker
    public void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(AddEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        myCalendar.set(Calendar.MINUTE, minute);
                        String myFormat = "yyyy-MM-dd HH:mm:ss"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        edittextdate.setText(sdf.format(myCalendar.getTime()));
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true
                ).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

}