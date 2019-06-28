package java.thread.androidtown.org.samplejavathread;

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

        // 버튼 이벤트 처리
        Button showBtn = (Button) findViewById(R.id.showBtn);
        showBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                textView01.setText("스레드에서 받은 값 : " + value);
            }
        });
    }

    /**
     * 화면 보일 때 스레드 시작
     */
    protected void onResume() {
        super.onResume();

        running = true;
        Thread thread1 = new BackgroundThread();
        thread1.start();
    }

    /**
     * 화면 안보일 때 스레드 중지
     */
    protected void onPause() {
        super.onPause();

        running = false;
        value = 0;
    }

    /**
     * 스레드 정의
     */
    class BackgroundThread extends Thread {
        public void run() {
            while(running) {
                try {
                    // 1초마다 한번씩 값을 증가시킴
                    Thread.sleep(1000);
                    value++;

                } catch(InterruptedException ex) {
                    Log.e("SampleJavaThread", "Exception in thread.", ex);
                }
            }
        }
    }

}

