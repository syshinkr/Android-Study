package com.project.syshinkr.howltalk.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.project.syshinkr.howltalk.Model.UserModel;
import com.project.syshinkr.howltalk.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText email;
    private EditText name;
    private EditText password;
    private Button signUp;
    String splash_background;
    private ImageView profile;
    private Uri imageUri = null;
    private static final int PICK_FROM_ALBUM = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        splash_background = mFirebaseRemoteConfig.getString(getString(R.string.rc_color));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(splash_background));
        }

        profile = findViewById(R.id.signupActivity_imageview_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, PICK_FROM_ALBUM);
            }
        });

        email = findViewById(R.id.signupActivity_edittext_email);
        name = findViewById(R.id.signupActivity_edittext_name);
        password = findViewById(R.id.signupActivity_edittext_password);
        signUp = findViewById(R.id.signupActivity_button_signup);
        signUp.setBackgroundColor(Color.parseColor(splash_background));

        //회원가입 버튼 눌렀을 때 유저가 추가되고서 프로필이미지는 스토리지에 저장. 스토리지에 저장되면 데이터베이스에 저장(uid, profileImageUrl)
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(name.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
                    Toast.makeText(SignUpActivity.this, "이메일, 성명, 패스워드 중 작성하지 않은 항목이 있습니다.", Toast.LENGTH_LONG).show();
                    return;
                }

                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.getException().getMessage() != null) {
                                    Log.i("Signup", task.getException().getMessage());
                                    return;
                                }
                                final String uid = task.getResult().getUser().getUid();
                                //회원가입시 자신의 이름 넣기
                                UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name.getText().toString()).build();
                                task.getResult().getUser().updateProfile(userProfileChangeRequest);

                                if (imageUri == null) {
                                    imageUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.ic_person_24dp);
                                }
//                                if(email.getText().toString()==null || name.getText().toString() == null || password.getText().toString() || imageUri == null){
//                                    return;
//                                }

                                FirebaseStorage.getInstance().getReference().child("userImages").child(uid).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                        String imageUrl = task.getResult().getDownloadUrl().toString();
                                        UserModel userModel = new UserModel();
                                        userModel.userName = name.getText().toString();
                                        userModel.profileImageUrl = imageUrl;
                                        userModel.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                        FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                SignUpActivity.this.finish();
                                            }
                                        });
                                    }
                                });

                            }
                        });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_FROM_ALBUM && resultCode == RESULT_OK) {
            profile.setImageURI(data.getData()); //뷰 이미지 변경
            imageUri = data.getData(); // 이미지 경로 저장
        }
    }
}
