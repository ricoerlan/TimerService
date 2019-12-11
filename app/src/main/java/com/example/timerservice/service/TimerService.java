package com.example.timerservice.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

public class TimerService extends Service {

    public static final String BROADCASH_ACTION = "com.example.timerservice";
    long timeInMilliseconds = 0L;
    private Intent intent;
    private Handler handler = new Handler();
    private long initial_time;

    private Runnable sendUpdatesToUi = new Runnable() {
        @Override
        public void run() {
            DisplayloggingInfo();
            handler.postDelayed(this, 1000); //1 seconds
        }

        private void DisplayloggingInfo() {
            timeInMilliseconds = SystemClock.uptimeMillis() - initial_time;

            int timer = (int) timeInMilliseconds / 1000;
            int mins = timer /60;
            int secs = timer %60;
             intent.putExtra("mins",mins);
             intent.putExtra("secs", secs);

            Log.i("D3MI03","" + mins + ":" + String.format("%02d", secs));
            sendBroadcast(intent);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        initial_time = SystemClock.uptimeMillis();
        intent = new Intent(BROADCASH_ACTION);
        handler.removeCallbacks(sendUpdatesToUi);
        handler.postDelayed(sendUpdatesToUi,1000); // 1 second
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(sendUpdatesToUi);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startService(intent);
        return START_NOT_STICKY;
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
