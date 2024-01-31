package com.example.projectcyclingmobileapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EventTypeAdapter extends ArrayAdapter<String> {
    private Activity context;
    List<EventType> types;

    public EventTypeAdapter(Activity context, List types) {
        super(context, R.layout.list, types);
        this.context = context;
        this.types = types;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.title);
        TextView textViewType = (TextView) listViewItem.findViewById(R.id.subTitle);

        EventType eventType = types.get(position);
        textViewName.setText(eventType.getEventType());
        textViewType.setText(eventType.getAttributeName1() + ", " + eventType.getAttributeName2());
        return listViewItem;
    }
}
