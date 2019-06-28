package personal.project.sampledb;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DBOpenHelper mDBOpenHelper;
    private Cursor mCursor;
    private Info mInfo;
    private ArrayList<Info> mInfoArr;
    private CustomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInflation();

        mDBOpenHelper = new DBOpenHelper(this);
        mDBOpenHelper.open();

        mDBOpenHelper.insertColumn("김태희", "01012345678", "angel@google.com");
        mDBOpenHelper.insertColumn("송혜교","01333331111" , "asdffff@emdo.com");
        mDBOpenHelper.insertColumn("낸시랭","01234001111" , "yaya@hhh.com");
        mDBOpenHelper.insertColumn("제시카","01600001111" , "tree777@atat.com");
        mDBOpenHelper.insertColumn("성유리","01700001111" , "tiger@tttt.com");
        mDBOpenHelper.insertColumn("김태우","01800001111" , "gril@zzz.com");

        mInfoArr = new ArrayList<>();

        doWhileCursorToArr();

        for(Info i : mInfoArr){
            DLog.d(TAG, "ID = " + i._id);
            DLog.d(TAG, "name = " + i.name);
            DLog.d(TAG, "contact = " + i.contact);
            DLog.d(TAG, "email = " + i.email);
        }


        mAdapter = new CustomAdapter(this, mInfoArr);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemLongClickListener(longClickListener);
    }

    @Override
    protected void onDestroy() {
        mDBOpenHelper.close();
        super.onDestroy();
    }

    private OnItemLongClickListener longClickListener = new OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
            DLog.e(TAG, "Position = " + position);
            boolean result = mDBOpenHelper.deleteColumn(position + 1);
            DLog.e(TAG, "result = " + result) ;
            if(result) {
                mInfoArr.remove(position);
                mAdapter.setInfoArr(mInfoArr);
                mAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(), "Index를 확인해주세요", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    };

    private void doWhileCursorToArr() {
        mCursor = null;
        mCursor = mDBOpenHelper.getAllColumns();
        DLog.e(TAG, "COUNT = " + mCursor.getCount());

        while(mCursor.moveToNext()) {
            mInfo = new Info(
                    mCursor.getInt(mCursor.getColumnIndex("_id")),
                    mCursor.getString(mCursor.getColumnIndex("name")),
                    mCursor.getString(mCursor.getColumnIndex("contact")),
                    mCursor.getString(mCursor.getColumnIndex("email"))
                    );
            mInfoArr.add(mInfo);
        }
        mCursor.close();
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_add :
                mDBOpenHelper.insertColumn(
                        mEditTexts[Constants.NAME].getText().toString().trim(),
                        mEditTexts[Constants.CONTACT].getText().toString().trim(),
                        mEditTexts[Constants.EMAIL].getText().toString().trim()
                );

                mInfoArr.clear();

                doWhileCursorToArr();

                mAdapter.setInfoArr(mInfoArr);
                mAdapter.notifyDataSetChanged();

                mCursor.close();

                break;
            default :
                break;
        }
    }

    private EditText[] mEditTexts;
    private ListView mListView;

    private void setInflation() {
        mEditTexts = new EditText[] {
                (EditText) findViewById(R.id.et_name),
                (EditText) findViewById(R.id.et_contact),
                (EditText) findViewById(R.id.et_email)
        };

        mListView = (ListView) findViewById(R.id.lv_list);
    }
}
