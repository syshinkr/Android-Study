package com.project.syshinkr.howltalk.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.project.syshinkr.howltalk.R;

public class LoginActivity extends AppCompatActivity {
    private String TAG = "LoginActivity";
    private EditText id, password;
    private Button login;
    private Button signUp;
    private FirebaseRemoteConfig firebaseRemoteConfig;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener; //로그인 성공 여부 체크, 정말로 체크만 한다, 따로 붙여줘야함

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

        String splash_background = firebaseRemoteConfig.getString(getString(R.string.rc_color));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(splash_background));
        }
        id = findViewById(R.id.loginActivity_edittext_id);
        password = findViewById(R.id.loginActivity_edittext_password);

        login = findViewById(R.id.loginActivity_button_login);
        signUp = findViewById(R.id.loginActivity_button_signuo);
        login.setBackgroundColor(Color.parseColor(splash_background));
        signUp.setBackgroundColor(Color.parseColor(splash_background));

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginEvent();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser(); //현재 로그인한 사용자를 가져올 수도 있다. 사용자가 로그인 상태가 아니라면 currentUser 값이 null
                if(user != null) {
                    //로그인
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    //로그아웃
                }
            }
        };
    }

    //로그인 성공 판단
    void loginEvent() {
        Log.i(TAG, "로그인 시도");
        firebaseAuth.signInWithEmailAndPassword(id.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()) {
                    //로그인 실패
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "로그인 실패 : " + task.getException().getMessage());
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}
