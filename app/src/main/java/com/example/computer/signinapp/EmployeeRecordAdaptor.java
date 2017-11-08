package com.example.computer.signinapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Computer on 9/20/2017.
 */

public class EmployeeRecordAdaptor extends ArrayAdapter<User_account> {

    ArrayList<User_account> Users_list=null;
    Context context;
    public EmployeeRecordAdaptor(Activity context, ArrayList<User_account> usersdetails) {

        super(context, R.layout.list_item, usersdetails);
        this.context=context;
        this.Users_list=usersdetails;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= ((Activity)context).getLayoutInflater();
        convertView= inflater.inflate(R.layout.list_item, parent, false);
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        // Get the {@link AndroidFlavor} object located at this position in the list
        //User_account currentUser = getItem(position);
    // Find the TextView in the list_item.xml layout with the ID version_name
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.EmpName);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        nameTextView.setText(Users_list.get(position).getEname());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView EmailTextView = (TextView) listItemView.findViewById(R.id.EmpEmail);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        EmailTextView.setText(Users_list.get(position).getEmail());
   // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
