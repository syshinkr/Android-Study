package org.androidtown.samplereceiver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

import static android.R.attr.format;

public class SmsActivity extends AppCompatActivity {
    EditText editText;
    EditText editText2;
    EditText editText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent passedIntent = getIntent();
        processIntent(passedIntent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processIntent(intent);

        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent) {
        if(intent != null) {
            String sender = intent.getStringExtra("sender");
            String contents = intent.getStringExtra("contents");
            String receivedDate = intent.getStringExtra("receivedDate");

            editText.setText(sender);
            editText2.setText(contents);
            editText3.setText(receivedDate);
        }
    }

    private void sentToActivity(Context context, String sender, String contents, Date receivedDate) {
        Intent myintent = new Intent(context, SmsActivity.class);

        myintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        myintent.putExtra("sender", sender);
        myintent.putExtra("contents", contents);
        myintent.putExtra("receivedDate", format.format(receivedDate));

        context.startActivity(myintent);
    }
}
