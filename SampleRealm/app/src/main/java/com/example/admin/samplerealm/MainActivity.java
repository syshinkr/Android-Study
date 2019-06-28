package com.example.admin.samplerealm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.admin.samplerealm.POJO.AniList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private LinearLayout rootLayout;
    private Realm realm;
    private RealmResults<AniList> aniLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootLayout = findViewById(R.id.container);
        rootLayout.removeAllViews();

        Realm.deleteRealm(Realm.getDefaultConfiguration());

        realm = Realm.getDefaultInstance(); //realm 인스턴스 생성
        aniLists = realm.where(AniList.class).findAllAsync();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        realm.close();
    }

    //리스트 정보 리턴
    private RealmResults<AniList> getList() {
        return realm.where(AniList.class).findAll();
    }

    //만약을 위한 삽임 메소드
    private void insertAniList() {
        realm.beginTransaction();
        realm.executeTransaction(r -> {
            AniList aniList = r.createObject(AniList.class, );
        });
        AniList aniList = realm.createObject(AniList.class);
//        aniList.set_id();
//        aniList.setbBc();
//        aniList.setEd();
//        aniList.setGenre();
//        aniList.setHomeLink();
//        aniList.setSd();
//        aniList.setTime();
//        aniList.setTitle();
        realm.commitTransaction();
    }
}
