package com.ensahgl.counttimer.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ensahgl.counttimer.R;
import com.ensahgl.counttimer.models.EventModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CountdownAdapter extends ArrayAdapter<EventModel> {

    private LayoutInflater lf;
    private List<ViewHolder> Holders;
    private Handler Handler = new Handler();
    private Context context;


    public CountdownAdapter(Context context, List<EventModel> objects) {
        super(context, 0, objects);
        this.context = context;
        this.lf = LayoutInflater.from(context);
        this.Holders = new ArrayList<>();
        startUpdateTimer();


    }

//    handle each event every second
    private void startUpdateTimer() {
        Timer tmr = new Timer();
        tmr.schedule(new TimerTask() {
            @Override
            public void run() {
                Handler.post(updateRemainingTime);
            }
        }, 1000, 1000);
    }


//    compare each event with current time
    private Runnable updateRemainingTime = new Runnable() {
        @Override
        public void run() {
            synchronized (Holders) {
                long currentTime = new Date().getTime();
                for (ViewHolder holder : Holders) {
                    holder.updateTimeRemaining(currentTime);
                }
            }
        }
    };


// update the view using View holders
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder(context);
            convertView = lf.inflate(R.layout.list_item, parent, false);
            holder.Product = (TextView) convertView.findViewById(R.id.Product);
            holder.TimeRemaining = (TextView) convertView.findViewById(R.id.TimeRemaining);
            convertView.setTag(holder);
            synchronized (Holders) {
                Holders.add(holder);
            }
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


            holder.setData(getItem(position));


        return convertView;
    }
}
