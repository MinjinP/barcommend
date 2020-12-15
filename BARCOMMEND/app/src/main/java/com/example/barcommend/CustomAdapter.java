package com.example.barcommend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private ArrayList<dataSet> listViewItemList = new ArrayList<dataSet>();

    public CustomAdapter() {}

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.content_scrolling, parent, false);
        }
        // int[] ints = new int[] {R.id.rv_id, R.id.rv_text, R.id.ratingbarSmall, R.id.rv_date};
        TextView rv_id = (TextView) convertView.findViewById(R.id.rv_id);
        TextView rv_text = (TextView) convertView.findViewById(R.id.rv_text);
        RatingBar rv_rating = (RatingBar) convertView.findViewById(R.id.ratingbarSmall);
        TextView rv_date = (TextView) convertView.findViewById(R.id.rv_date);

        dataSet data = listViewItemList.get(position);

        rv_id.setText(data.getUserName());
        rv_text.setText(data.getReviewText());
        rv_rating.setRating(data.getStarPoint());
        rv_date.setText(data.getReviewDate());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    public void addItem(String id, String text, String rate, String Date) {
        dataSet data = new dataSet(id, text, rate, Date);
        listViewItemList.add(data);
    }
}
