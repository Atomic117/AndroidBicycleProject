package com.example.projectcyclingmobileapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends ArrayAdapter<String> {
    private Activity context;
    List<User> users;

    public UserAdapter(Activity context, List users) {
        super(context, R.layout.list, users);
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.title);
        TextView textViewType = (TextView) listViewItem.findViewById(R.id.subTitle);

        User user = users.get(position);
        textViewName.setText(user.getUsername());
        textViewType.setText(String.valueOf(user.getType()));
        return listViewItem;
    }
}