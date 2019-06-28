package personal.project.simplerecyclerview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;

import personal.project.simplerecyclerview.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private String[] names = {"Charlie", "Andrew", "Han", "Liz", "Thomas", "Sky", "Andy", "Lee", "Park"};
    private static final int LAYOUT = R.layout.activity_main;
    private ActivityMainBinding mainBinding;
    private RecyclerView.Adapter adapter;

    private ArrayList<RecyclerItem> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, LAYOUT);
        setRecyclerView();

    }

    private void setRecyclerView() {
// 각 Item 들이 RecyclerView 의 전체 크기를 변경하지 않는 다면
// setHasFixedSize() 함수를 사용해서 성능을 개선할 수 있습니다.
// 변경될 가능성이 있다면 false 로 , 없다면 true를 설정해주세요.
        mainBinding.recyclerView.setHasFixedSize(true);

// RecyclerView에 Adapter를 설정해줍니다.
        adapter = new RecyclerAdapter(mItems);
        mainBinding.recyclerView.setAdapter(adapter);

// 다양한 LayoutManager 가 있습니다. 원하시는 방법을 선택해주세요.
// 지그재그형의 그리드 형식
//mainBinding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
// 그리드 형식
//mainBinding.recyclerView.setLayoutManager(new GridLayoutManager(this,4));
// 가로 또는 세로 스크롤 목록 형식
        mainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setData();
        addition();
    }

    private void setData() {
        mItems.clear();
// RecyclerView 에 들어갈 데이터를 추가합니다.
        for (String name : names) {
            mItems.add(new RecyclerItem(name));
            mItems.add(new RecyclerItem(name));
        }
// 데이터 추가가 완료되었으면 notifyDataSetChanged() 메서드를 호출해 데이터 변경 체크를 실행합니다.
        adapter.notifyDataSetChanged();
    }

    private void addition() {
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getApplicationContext(), new LinearLayoutManager(this).getOrientation());
        mainBinding.recyclerView.addItemDecoration(dividerItemDecoration);
        mainBinding.recyclerView.setItemAnimator(new MyItemAnimator());
    }

    class MyItemAnimator extends DefaultItemAnimator {

        @Override
        public boolean animateAdd(RecyclerView.ViewHolder holder) {
            return super.animateAdd(holder);
        }

        @Override
        public boolean animateRemove(RecyclerView.ViewHolder holder) {

            View view = holder.itemView;

            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 1.0f, 0.0f).setDuration(100);
            alphaAnimator.setInterpolator(new DecelerateInterpolator());
            alphaAnimator.start();

            return true;
        }

        @Override
        public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {

            return super.animateMove(holder, fromX, fromY, toX, toY);
        }
    }
}
