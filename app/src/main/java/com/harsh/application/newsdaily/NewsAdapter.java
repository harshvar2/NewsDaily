package com.harsh.application.newsdaily;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<NewsData> {


    public NewsAdapter(Context context, List<NewsData> arrayList) {
        super(context, 0, arrayList);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_structure, parent, false);
        }


        //Find the news at a given position in the list of news

        NewsData currentData = getItem(position);

        //Find the textview with id Title
        TextView titleview = listItemView.findViewById(R.id.title);
        assert currentData != null;
        titleview.setText(currentData.getTitle());

        TextView sectionNameView = listItemView.findViewById(R.id.sectionName);
        sectionNameView.setText(currentData.getSectionName());

        TextView dateView = listItemView.findViewById(R.id.date);
        dateView.setText(currentData.getDate());

        TextView timeView = listItemView.findViewById(R.id.time);
        timeView.setText(currentData.getTime());
        

        return listItemView;
    }
}
