package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LeaderBoard_pushups_Activity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    ListView lv_leader_bord ;
    ArrayList<person> personArrayList;
    records_adapter recordAdapter;
    TextView tv_tableTitle;
    String sport = YourRecordsActivity.sportName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board_pushups_); lv_leader_bord = (ListView)findViewById(R.id.lv_leader_bord);
        getSupportActionBar().setTitle("Best Sport record");
        lv_leader_bord = (ListView)findViewById(R.id.lv_leader_bord);
        tv_tableTitle = (TextView)findViewById(R.id.tv_tableTitle);
        tv_tableTitle.setText(YourRecordsActivity.sportName+ " records table:");

        myRef = database.getReference();
        personArrayList = new ArrayList<>();

        Query q = myRef.child("users:").orderByValue();
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dst : snapshot.getChildren()){
                    person user = dst.getValue(person.class);
                    personArrayList.add(user);
                }
                Collections.sort(personArrayList, new Comparator<person>() {
                    @Override
                    public int compare(person lhs, person rhs) {
                        int answer = 0;
                        if (sport.equals("Push Ups"))
                            answer =  (-1) * Integer.valueOf(lhs.getPush_ups_record()).compareTo(rhs.getPush_ups_record());

                        else if (sport.equals("Pull Ups"))
                            answer = (-1) * Integer.valueOf(lhs.getPull_ups_record()).compareTo(rhs.getPull_ups_record());

                        else if (sport.equals("Leg Lifts"))
                            answer = (-1) * Integer.valueOf(lhs.getLeg_lifts_record()).compareTo(rhs.getLeg_lifts_record());

                        else if (sport.equals("Parallels"))
                            answer = (-1) * Integer.valueOf(lhs.getParallels_record()).compareTo(rhs.getParallels_record());

                        else if (sport.equals("Crunches"))
                            answer = (-1) * Integer.valueOf(lhs.getCrunches_record()).compareTo(rhs.getCrunches_record());

                        return answer;
                    }
                });
                refreshMyListView();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void refreshMyListView(){
        if (sport.equals("Push Ups")) recordAdapter = new records_adapter(LeaderBoard_pushups_Activity.this,0,0,personArrayList,"push_ups");
        else if (sport.equals("Pull Ups")) recordAdapter = new records_adapter(LeaderBoard_pushups_Activity.this,0,0,personArrayList,"pull_ups");
        else if (sport.equals("Leg Lifts")) recordAdapter = new records_adapter(LeaderBoard_pushups_Activity.this,0,0,personArrayList,"leg_lifts");
        else if (sport.equals("Crunches")) recordAdapter = new records_adapter(LeaderBoard_pushups_Activity.this,0,0,personArrayList,"crunches");
        else if (sport.equals("Parallels")) recordAdapter = new records_adapter(LeaderBoard_pushups_Activity.this,0,0,personArrayList,"parallels");
        lv_leader_bord.setAdapter(recordAdapter);
    }

}