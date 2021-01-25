package com.ensahgl.counttimer.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context.*;


import com.ensahgl.counttimer.models.EventModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewHolder {
    TextView Product;
    TextView TimeRemaining;
    EventModel mEvent;
    Context myContext;


    public ViewHolder(Context context)
    {
        this.myContext = context;

    }

    public void setData(EventModel item) {
        mEvent = item;
        Product.setText(item.getName());
        long now = new Date().getTime();
        updateTimeRemaining(now);
    }

//    Countedown timer
    public void updateTimeRemaining(long currentTime ) {
            try {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date futureDate = dateFormat.parse(mEvent.getDate());
                assert futureDate != null;
                long diff = futureDate.getTime() - currentTime;
                if (diff > 0) {
                    int days = (int) diff / (24 * 60 * 60 * 1000);
                    diff -= days * (24 * 60 * 60 * 1000);
                    int hours = (int) diff / (60 * 60 * 1000);
                    diff -= hours * (60 * 60 * 1000);
                    int minutes = (int) diff / (60 * 1000);
                    diff -= minutes * (60 * 1000);
                    int seconds = (int) diff / 1000;
                    TimeRemaining.setText(String.format("%02d days %02d hours %02d minutes %02d seconds", days, hours, minutes, seconds));
                } else {
//                    try {
////                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
////                        Ringtone r = RingtoneManager.getRingtone(myContext, notification);
////                        r.play();
////                        Thread.sleep(2000);
////                        r.stop();
////                        events.indexOf(mEvent);
////                        events.remove(mEvent);
////                        mEvent = null;
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    TimeRemaining.setText("The event started");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


}
