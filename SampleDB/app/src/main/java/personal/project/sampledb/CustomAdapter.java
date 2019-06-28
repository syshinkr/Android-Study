package personal.project.sampledb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by admin on 2017-12-18.
 */

public class CustomAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private ArrayList<Info> infoArr;
    private ViewHolder viewHolder;

    class ViewHolder {
        TextView name;
        TextView contact;
        TextView email;
    }

    public CustomAdapter(Context c, ArrayList<Info> arr) {
        inflater = LayoutInflater.from(c);
        infoArr = arr;
    }

    @Override
    public int getCount() {
        return infoArr.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null) {
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.list_row, null);
            viewHolder.name = (TextView) v.findViewById(R.id.tv_name);
            viewHolder.contact = (TextView) v.findViewById(R.id.tv_contact);
            viewHolder.email = (TextView) v.findViewById(R.id.tv_email);
            v.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        viewHolder.name.setText(infoArr.get(position).name);
        viewHolder.contact.setText(infoArr.get(position).contact);
        viewHolder.email.setText(infoArr.get(position).email);

        return v;
    }

    public void setInfoArr(ArrayList<Info> arr) {
        this.infoArr = arr;
    }
    public ArrayList<Info> getInfoArr(){
        return infoArr;
    }
}
