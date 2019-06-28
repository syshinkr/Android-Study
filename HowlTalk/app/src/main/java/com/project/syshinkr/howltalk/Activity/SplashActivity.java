package com.project.syshinkr.howltalk.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.project.syshinkr.howltalk.BuildConfig;
import com.project.syshinkr.howltalk.R;

public class SplashActivity extends AppCompatActivity {


    private static final String TAG = "SplashActivity";
    private LinearLayout linearLayout;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        linearLayout = findViewById(R.id.splash_activity_linear_layout);

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);

        mFirebaseRemoteConfig.setDefaults(R.xml.default_config);


        // cacheExpirationSeconds is set to cacheExpiration here, indicating the next fetch request
// will use fetch data from the Remote Config service, rather than cached parameter values,
// if cached parameter values are more than cacheExpiration seconds old.
// See Best Practices in the README for more information.
        mFirebaseRemoteConfig.fetch(0) // 매개변수마다 요청, 밀리세크 단위
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.i("splash", "페치 시도");
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Fetch Succeeded",
                                    Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "Fetch Succeeded");

                            // After config data is successfully fetched, it must be activated before newly fetched
                            // values are returned.
                            mFirebaseRemoteConfig.activateFetched();
                        } else {
                            Toast.makeText(getApplicationContext(), "Fetch Failed",
                                    Toast.LENGTH_LONG).show();
                            Log.i(TAG, "Fetch Failed");
                        }
                        displayMessage();
                    }
                });


    }

    void displayMessage() {
        String splash_background = mFirebaseRemoteConfig.getString("splash_background");
        boolean caps  = mFirebaseRemoteConfig.getBoolean("splash_message_caps");
        String splash_message = mFirebaseRemoteConfig.getString("splash_message");

        linearLayout.setBackgroundColor(Color.parseColor(splash_background));

        if(caps) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(splash_message).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            builder.create().show();
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}
