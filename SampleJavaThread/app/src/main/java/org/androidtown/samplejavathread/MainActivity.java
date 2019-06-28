package org.androidtown.samplejavathread;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int value = 0;
    private boolean running = false;

    TextView textView01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView01 = (TextView) findViewById(R.id.textView01);

        Button showBtn = (Button) findViewById(R.id.showBtn);
        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView01.setText("스레드에서 받은 값 : " + value);
            }
        });
    }

    protected void onResume() {
        super.onResume();

        running = true;
        Thread thread1 = new BackgroundThread();
        thread1.start();
    }

    protected void onPause() {
        super.onPause();

        running = false;
        value = 0;
    }

    class BackgroundThread extends Thread {
        public void run() {
            while(running) {
                try {
                    Thread.sleep(1000);
                    value++;
                } catch (InterruptedException e) {
                    Log.e("SamplejavaThread", "Exception in thread", e);
                }
            }
        }
    }
}
