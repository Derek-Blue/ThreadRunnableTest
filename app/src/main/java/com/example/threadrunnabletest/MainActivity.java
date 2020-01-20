package com.example.threadrunnabletest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    TextView textView, currentTime;
    Button start, stop, refresh;

    boolean isActive;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentTime = findViewById(R.id.currentTime);
        textView = findViewById(R.id.textview);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        refresh = findViewById(R.id.refresh);

        Thread thread = new Thread(timeRunnable);
        thread.start();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isActive = true;
                content();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = count-1;
                isActive = false;
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isActive = false;
                count = -1;
                textView.setText("計時: 0 秒");
            }
        });
    }

    public void content(){
        count++;
        textView.setText("計時: "+count+" 秒");

        if (isActive){
            refresh(1000);
        }
    }

    private void refresh(int milliSeconds) {

        Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                content();
            }
        };

        handler.postDelayed(runnable, milliSeconds);
    }

    private Runnable timeRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                while (true){
                    Thread.sleep(1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            long time = System.currentTimeMillis();
                            Date date = new Date(time);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分ss秒");
                            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
                            currentTime.setText(simpleDateFormat.format(date));
                        }
                    });
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

}
