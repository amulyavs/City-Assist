package nantha91.com.simpleapp.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import nantha91.com.simpleapp.R;

public class NavigationMenuAdapter extends BaseAdapter {

    private ArrayList<String> profileMenus;
    private Context context;
    public static int odd_color = Color.parseColor("#333333");
    public static int even_color = Color.parseColor("#474747");

    public NavigationMenuAdapter(Context ctxt, ArrayList<String> list) {
        // TODO Auto-generated constructor stub
        this.profileMenus = list;
        this.context = ctxt;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return profileMenus.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return profileMenus.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View view = convertView;
        if (view == null) {
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflator.inflate(R.layout.navigationdrawer_list_item, null);
        }
        TextView name = (TextView) view.findViewById(R.id.category_name);
        name.setText(getItem(position).toString());
        view.setBackgroundColor(position % 2 == 0 ? even_color : odd_color);
        return view;
    }

}