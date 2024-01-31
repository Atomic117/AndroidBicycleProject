package com.example.projectcyclingmobileapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EventsJoinedAdapter extends ArrayAdapter<String> {
    private Activity context;
    List<EventsJoined> joinedEvents;
    public EventsJoinedAdapter(Activity context, List events){
        super(context, R.layout.list, events);
        this.context = context;
        this.joinedEvents = events;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list, null, true);

        TextView textViewTitle = (TextView) listViewItem.findViewById(R.id.title);
        TextView textViewDetails = (TextView) listViewItem.findViewById(R.id.subTitle);

        EventsJoined event = joinedEvents.get(position);
        textViewTitle.setText(event.getTitle());
        textViewDetails.setText(event.getEventType());
        return listViewItem;

    }




}
