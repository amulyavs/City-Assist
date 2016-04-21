package nantha91.com.simpleapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseClassName;

import java.util.ArrayList;

import nantha91.com.simpleapp.R;
import nantha91.com.simpleapp.model.ContentEntry;

/**
 * Created by nantha on 16/4/15.
 */
public class SimpleAdapter extends BaseAdapter {
    public ArrayList<ContentEntry> source;
    private Context context;

    //constructor
    public SimpleAdapter(ArrayList txtNotifications, Context context) {
        this.source = txtNotifications;
        this.context = context;
    }

    @Override
    public int getCount() {
        return source.size();
    }

    @Override
    public ContentEntry getItem(int i) {
        return source.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;
        if (view == null) {
            //inflate by using custom layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.simpleviewmodel, null, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.content);
          //  viewHolder.date = (TextView) view.findViewById(R.id.date);
            //  viewHolder.view = resultView;
            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();
        final ContentEntry textNotification = source.get(position);
        // viewHolder.date.setText(String.valueOf(textNotification.getCreated_at()));
        viewHolder.name.setText(textNotification.getName());

        return view;
    }

    static class ViewHolder {
        TextView name, date;
        View view;

    }
}
