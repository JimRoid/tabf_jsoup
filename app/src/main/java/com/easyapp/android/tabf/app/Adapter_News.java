package com.easyapp.android.tabf.app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by easyapp_jim on 15/5/25.
 */
public class Adapter_News extends BaseAdapter {
    private ArrayList<News> data = new ArrayList<>();
    private Context context;

    public Adapter_News(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<News> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.listview_news_item, null);
            holder = new Holder();
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tv_message = (TextView) convertView.findViewById(R.id.tv_message);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.tv_message.setText(data.get(position).message);
        holder.tv_date.setText(data.get(position).date);

        return convertView;
    }

    private static class Holder {
        public TextView tv_message;
        public TextView tv_date;

    }
}
