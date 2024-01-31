package com.example.projectcyclingmobileapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FeedbackAdapter extends ArrayAdapter<String> {
    private Activity context;
    List<Feedback> feedbackList;

    public FeedbackAdapter(Activity context, List feedbackList) {
        super(context, R.layout.list, feedbackList);
        this.context = context;
        this.feedbackList = feedbackList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.title);
        TextView textViewType = (TextView) listViewItem.findViewById(R.id.subTitle);

        Feedback feedback = feedbackList.get(position);
        textViewName.setText(feedback.getUserName());
        textViewType.setText(feedback.getRating());
        return listViewItem;
    }
}
