package arabiannight.tistory.com.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import arabiannight.tistory.com.R;
import arabiannight.tistory.com.adapter.CustomAdapter;
import arabiannight.tistory.com.conf.Constants;
import arabiannight.tistory.com.data.InfoClass;
import arabiannight.tistory.com.database.DbOpenHelper;
import arabiannight.tistory.com.util.DLog;

public class TestDataBaseActivity extends Activity {
	
	private static final String TAG = "TestDataBaseActivity";
	private DbOpenHelper mDbOpenHelper;
	private Cursor mCursor;
	private InfoClass mInfoClass;
	private ArrayList<InfoClass> mInfoArray;
	private CustomAdapter mAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setLayout();
        
        // DB Create and Open
        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();
        
        mDbOpenHelper.insertColumn("김태희","01000001111" , "angel@google.com");
        mDbOpenHelper.insertColumn("송혜교","01333331111" , "asdffff@emdo.com");
        mDbOpenHelper.insertColumn("낸시랭","01234001111" , "yaya@hhh.com");
        mDbOpenHelper.insertColumn("제시카","01600001111" , "tree777@atat.com");
        mDbOpenHelper.insertColumn("성유리","01700001111" , "tiger@tttt.com");
        mDbOpenHelper.insertColumn("김태우","01800001111" , "gril@zzz.com");
        
//        startManagingCursor(mCursor);
        
        
        mInfoArray = new ArrayList<InfoClass>();
        
        doWhileCursorToArray();
        
        for(InfoClass i : mInfoArray){
        	DLog.d(TAG, "ID = " + i._id);
        	DLog.d(TAG, "name = " + i.name);
        	DLog.d(TAG, "contact = " + i.contact);
        	DLog.d(TAG, "email = " + i.email);
        }
        
        mAdapter = new CustomAdapter(this, mInfoArray);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemLongClickListener(longClickListener);
        
    }
    
    @Override
    protected void onDestroy() {
    	mDbOpenHelper.close();
    	super.onDestroy();
    }
    
    
    /**
     * ListView의 Item을 롱클릭 할때 호출 ( 선택한 아이템의 DB 컬럼과 Data를 삭제 한다. )
     */
    private OnItemLongClickListener longClickListener = new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int position, long arg3) {
			
			DLog.e(TAG, "position = " + position);
			
			boolean result = mDbOpenHelper.deleteColumn(position + 1);
			DLog.e(TAG, "result = " + result);
			
			if(result){
				mInfoArray.remove(position);
				mAdapter.setArrayList(mInfoArray);
				mAdapter.notifyDataSetChanged();
			}else {
				Toast.makeText(getApplicationContext(), "INDEX를 확인해 주세요.", 
						Toast.LENGTH_LONG).show();
			}
			
			return false;
		}
	};
	
	
	/**
	 * DB에서 받아온 값을 ArrayList에 Add
	 */
	private void doWhileCursorToArray(){
		
		mCursor = null;
		mCursor = mDbOpenHelper.getAllColumns();
		DLog.e(TAG, "COUNT = " + mCursor.getCount());
		
		while (mCursor.moveToNext()) {
        	
			mInfoClass = new InfoClass(
					mCursor.getInt(mCursor.getColumnIndex("_id")),
					mCursor.getString(mCursor.getColumnIndex("name")),
					mCursor.getString(mCursor.getColumnIndex("contact")),
					mCursor.getString(mCursor.getColumnIndex("email"))
					);
			
			mInfoArray.add(mInfoClass);
		}
		
		mCursor.close();
	}
    
    
	/**
	 * OnClick Button
	 * @param v
	 */
    public void onClick(View v){
    	switch (v.getId()) {
		case R.id.btn_add:
			mDbOpenHelper.insertColumn
					(
					mEditTexts[Constants.NAME].getText().toString().trim(),
					mEditTexts[Constants.CONTACT].getText().toString().trim(),
					mEditTexts[Constants.EMAIL].getText().toString().trim()
					);
			
			mInfoArray.clear();
			
			doWhileCursorToArray();
			
			mAdapter.setArrayList(mInfoArray);			
			mAdapter.notifyDataSetChanged();
			
			mCursor.close();
			
			break;

		default:
			break;
		}
    }
    
    /*
     * Layout
     */
    private EditText[] mEditTexts;
    private ListView mListView;
    
    private void setLayout(){
    	mEditTexts = new EditText[]{
    			(EditText)findViewById(R.id.et_name),
    			(EditText)findViewById(R.id.et_contact),
    			(EditText)findViewById(R.id.et_email)
    	};
    	
    	mListView = (ListView) findViewById(R.id.lv_list);
    }
}




