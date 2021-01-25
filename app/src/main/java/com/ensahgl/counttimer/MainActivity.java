package com.ensahgl.counttimer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ensahgl.counttimer.helpers.CountdownAdapter;
import com.ensahgl.counttimer.models.EventModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ListView lvItems;
    private List<EventModel>  events;
    Button addEvent , clearAll;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        addEvent  = (Button) findViewById(R.id.add);
        clearAll = (Button) findViewById(R.id.clear);

        events = new ArrayList<>();
        LoadData();


//        configure ListView
        lvItems.setAdapter(new  CountdownAdapter(MainActivity.this,events) );

//      add event method listener
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this, AddEvent.class );
                startActivity(i);
            }
        });
//       clear storage listener
        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData();
            }
        });



    }



// load all events
    public synchronized void LoadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<EventModel>>() {}.getType();
        events = gson.fromJson(json, type);
        if (events == null) {
            events = new ArrayList<>();
        }

    }

// delete all storage
    public synchronized void clearData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        events =  new ArrayList<>();

    }



}