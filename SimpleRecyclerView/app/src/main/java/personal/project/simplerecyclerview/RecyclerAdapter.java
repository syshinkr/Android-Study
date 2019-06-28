package personal.project.simplerecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by admin on 2017-11-10.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
    ArrayList<RecyclerItem> mItems;

    public RecyclerAdapter(ArrayList<RecyclerItem> items){
        mItems = items;
    }


    // 새로운 뷰 홀더 생성
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view,parent,false);

        return new ItemViewHolder(view);
    }


    // View 의 내용을 해당 인덱스의 데이터로 바꿈
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int index) {
        holder.mNameTv.setText(mItems.get(index).getName());
    }

    // 데이터 셋의 크기를 리턴
    @Override
    public int getItemCount() {
        return mItems.size();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mNameTv;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mNameTv = itemView.findViewById(R.id.itemNameTv);
        }
    }


}
