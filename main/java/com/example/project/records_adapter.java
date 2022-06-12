package com.example.project;

import android.app.Activity;
import android.app.Person;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class records_adapter extends ArrayAdapter<person> {
    Context context;
    List<person> objects;
    String sportName;

    public records_adapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<person> objects, String sportType) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.objects = objects;
        this.sportName = sportType;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.t_person_line,parent,false);

        TextView tvPersonName = (TextView)view.findViewById(R.id.name);
        TextView tvPersonRecord = (TextView)view.findViewById(R.id.record);
        TextView tvPersonRank=(TextView)view.findViewById(R.id.rank);
        person temp = objects.get(position);
        tvPersonRank.setText("#"+String.valueOf(position+1));
        tvPersonName.setText(String.valueOf(temp.getName()));
        if (sportName.equals("push_ups")) tvPersonRecord.setText(""+temp.getPush_ups_record());
        if (sportName.equals("pull_ups")) tvPersonRecord.setText(""+temp.getPull_ups_record());
        if (sportName.equals("leg_lifts")) tvPersonRecord.setText(""+temp.getLeg_lifts_record());
        if (sportName.equals("crunches")) tvPersonRecord.setText(""+temp.getCrunches_record());
        if (sportName.equals("parallels")) tvPersonRecord.setText(""+temp.getParallels_record());
        return view;
    }
}
