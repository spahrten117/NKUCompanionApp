package com.example.tope0_000.nkucompanionapp;

/**
 * Created by espahr on 4/26/17.
 */

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter<Class> {

    private final Context context;
    private final ArrayList<Class> classArrayList;

    public CustomListAdapter(Context context, ArrayList<Class> classArrayList) {

        super(context, R.layout.class_row, classArrayList);

        this.context = context;
        this.classArrayList = classArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.class_row, parent, false);

        // 3. Get the two text view from the rowView
        TextView nameView = (TextView) rowView.findViewById(R.id.name);
        TextView gradeView = (TextView) rowView.findViewById(R.id.grade);
        TextView creditsView = (TextView) rowView.findViewById(R.id.credits);

        // 4. Set the text for textView
        nameView.setText(classArrayList.get(position).getName());
        gradeView.setText(classArrayList.get(position).getGrade());
        creditsView.setText(String.valueOf(classArrayList.get(position).getCredits()));

        // 5. retrn rowView
        return rowView;
    }
}
