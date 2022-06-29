package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class YourRecordsActivity extends AppCompatActivity {
/////////////////////////////
    /////////////// PREVIOUS LEADING
    /////////////////////////
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    LeaderNameAndRecord leaderNameAndRecord = new LeaderNameAndRecord();
    int best_record_pushups = 0;
    int best_record_pullups = 0;
    int best_record_parallels = 0;
    int best_record_leg_lifts = 0;
    int best_record_crunches = 0;

    String name_person_best_pushup = "";
    String name_person_best_pullup = "";
    String name_person_best_parallels = "";
    String name_person_best_leg_lifts = "";
    String name_person_best_crunches = "";
    // push ups
    TextView tv_push_up_record;
    ImageButton btn_ok_push_up;
    EditText et_push_up;
    Button btn_push_up_leaderboard;
    static String sportName;

    //pull_up
    TextView tv_pull_up_record;
    ImageButton btn_ok_pull_up;
    EditText et_pull_up;
    Button btn_pull_up_leaderboard;

    //leg_lifts
    TextView tv_leg_lifts_record;
    ImageButton btn_ok_leg_lifts;
    EditText et_leg_lifts;
    Button btn_leg_lifts_leaderboard;

    //parallels
    TextView tv_parallels_record;
    ImageButton btn_ok_parallels;
    EditText et_parallels;
    Button btn_parallels_leaderboard;

    //tv_crunches
    TextView tv_crunches_record;
    ImageButton btn_ok_crunches;
    EditText et_crunches;
    Button btn_crunches_leaderboard;

    ObjectAnimator animator;
    ImageView work_out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_records);
        getSupportActionBar().setTitle("Best Sport record");

        work_out = (ImageView)findViewById(R.id.work_out);
        animator = ObjectAnimator.ofFloat(work_out,"translationX",-2000f);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(5000);

        myRef = database.getReference();

        Query q = myRef.child("leaders names:").orderByValue();
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for (DataSnapshot dst : snapshot.getChildren()) {
                        if (dst.getKey().equals("push up leader")) {
                            best_record_pushups = dst.child("leadingRecord").getValue(Integer.class);
                            name_person_best_pushup = dst.child("leadingPersonName").getValue(String.class);
                        }
                        else if (dst.getKey().equals("pull up leader")) {
                            best_record_pullups = dst.child("leadingRecord").getValue(Integer.class);
                            name_person_best_pullup = dst.child("leadingPersonName").getValue(String.class);
                        }
                        else if (dst.getKey().equals("parallels leader")) {
                            best_record_parallels = dst.child("leadingRecord").getValue(Integer.class);
                            name_person_best_parallels = dst.child("leadingPersonName").getValue(String.class);
                        }
                        else if (dst.getKey().equals("leg lifts leader")) {
                            best_record_leg_lifts = dst.child("leadingRecord").getValue(Integer.class);
                            name_person_best_leg_lifts = dst.child("leadingPersonName").getValue(String.class);
                        }
                        else if (dst.getKey().equals("crunches leader")) {
                            best_record_crunches = dst.child("leadingRecord").getValue(Integer.class);
                            name_person_best_crunches = dst.child("leadingPersonName").getValue(String.class);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        myRef = database.getReference();
        // push ups
        tv_push_up_record = (TextView)findViewById(R.id.tv_push_up_record);
        tv_push_up_record.setText(""+MainActivity.using_this_phone.getPush_ups_record());
        btn_ok_push_up = (ImageButton)findViewById(R.id.btn_ok_push_up);
        et_push_up = (EditText)findViewById(R.id.et_push_up);
        btn_push_up_leaderboard = (Button)findViewById(R.id.btn_push_up_leaderboard);
        btn_ok_push_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(YourRecordsActivity.this, "השיא עודכן", Toast.LENGTH_LONG).show();
                animator.start();
                if(et_push_up.getText().toString().trim().length() !=0) {
                    String newValue = et_push_up.getText().toString();
                    tv_push_up_record.setText(newValue);
                    MainActivity.using_this_phone.setPush_ups_record(Integer.valueOf(newValue));
                    myRef.child("users:").child(MainActivity.using_this_phone.getName()).setValue(MainActivity.using_this_phone);
                    if (best_record_pushups<Integer.valueOf(newValue)){ // checks if the new record is better than the best
                        leaderNameAndRecord.setLeadingRecord(Integer.valueOf(newValue));
                        leaderNameAndRecord.setLeadingPersonName(MainActivity.using_this_phone.getName());
                        leaderNameAndRecord.setPreviousPersonName(name_person_best_pushup);
                        name_person_best_pushup = MainActivity.using_this_phone.getName();
                        myRef.child("leaders names:").child("push up leader").setValue(leaderNameAndRecord);
                    }
                }
            }
        });


        //pull_up
        tv_pull_up_record = (TextView)findViewById(R.id.tv_pull_up_record);
        tv_pull_up_record.setText(""+MainActivity.using_this_phone.getPull_ups_record());
        btn_ok_pull_up = (ImageButton)findViewById(R.id.btn_ok_pull_up);
        et_pull_up = (EditText)findViewById(R.id.et_pull_up);
        btn_pull_up_leaderboard = (Button)findViewById(R.id.btn_pull_up_leaderboard);
        btn_ok_pull_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(YourRecordsActivity.this, "השיא עודכן", Toast.LENGTH_LONG).show();
                animator.start();
                if(et_pull_up.getText().toString().trim().length() !=0) {
                    String newValue = et_pull_up.getText().toString();
                    tv_pull_up_record.setText(newValue);
                    MainActivity.using_this_phone.setPull_ups_record(Integer.valueOf(newValue));
                    myRef.child("users:").child(MainActivity.using_this_phone.getName()).setValue(MainActivity.using_this_phone);
                    if (best_record_pullups<Integer.valueOf(newValue)){ // checks if the new record is better than the best
                        leaderNameAndRecord.setLeadingRecord(Integer.valueOf(newValue));
                        leaderNameAndRecord.setLeadingPersonName(MainActivity.using_this_phone.getName());
                        leaderNameAndRecord.setPreviousPersonName(name_person_best_pullup);
                        myRef.child("leaders names:").child("pull up leader").setValue(leaderNameAndRecord);

                    }
                }
            }
        });

        //leg_lift
        tv_leg_lifts_record = (TextView)findViewById(R.id.tv_leg_lifts_record);
        tv_leg_lifts_record.setText(""+MainActivity.using_this_phone.getLeg_lifts_record());
        btn_ok_leg_lifts = (ImageButton)findViewById(R.id.btn_ok_leg_lifts);
        et_leg_lifts = (EditText)findViewById(R.id.et_leg_lifts);
        btn_leg_lifts_leaderboard = (Button)findViewById(R.id.btn_leg_lifts_leaderboard);
        btn_ok_leg_lifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(YourRecordsActivity.this, "השיא עודכן", Toast.LENGTH_LONG).show();
                animator.start();
                if(et_leg_lifts.getText().toString().trim().length() !=0) {
                    String newValue = et_leg_lifts.getText().toString();
                    tv_leg_lifts_record.setText(newValue);
                    MainActivity.using_this_phone.setLeg_lifts_record(Integer.valueOf(newValue));
                    myRef.child("users:").child(MainActivity.using_this_phone.getName()).setValue(MainActivity.using_this_phone);
                    if (best_record_leg_lifts<Integer.valueOf(newValue)){ // checks if the new record is better than the best
                        leaderNameAndRecord.setLeadingRecord(Integer.valueOf(newValue));
                        leaderNameAndRecord.setLeadingPersonName(MainActivity.using_this_phone.getName());
                        leaderNameAndRecord.setPreviousPersonName(name_person_best_leg_lifts);
                        myRef.child("leaders names:").child("leg lifts leader").setValue(leaderNameAndRecord);


                    }
                }
            }
        });

        //parallels
        tv_parallels_record = (TextView)findViewById(R.id.tv_parallels_record);
        tv_parallels_record.setText(""+MainActivity.using_this_phone.getParallels_record());
        btn_ok_parallels = (ImageButton)findViewById(R.id.btn_ok_parallels);
        et_parallels = (EditText)findViewById(R.id.et_parallels);
        btn_parallels_leaderboard = (Button)findViewById(R.id.btn_parallels_leaderboard);
        btn_ok_parallels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(YourRecordsActivity.this, "השיא עודכן", Toast.LENGTH_LONG).show();
                animator.start();
                if(et_parallels.getText().toString().trim().length() !=0) {
                    String newValue = et_parallels.getText().toString();
                    tv_parallels_record.setText(newValue);
                    MainActivity.using_this_phone.setParallels_record(Integer.valueOf(newValue));
                    myRef.child("users:").child(MainActivity.using_this_phone.getName()).setValue(MainActivity.using_this_phone);
                    if (best_record_parallels<Integer.valueOf(newValue)){ // checks if the new record is better than the best
                        leaderNameAndRecord.setLeadingRecord(Integer.valueOf(newValue));
                        leaderNameAndRecord.setLeadingPersonName(MainActivity.using_this_phone.getName());
                        leaderNameAndRecord.setPreviousPersonName(name_person_best_parallels);
                        myRef.child("leaders names:").child("parallels leader").setValue(leaderNameAndRecord);

                    }
                }
            }
        });

        //tv_crunches
        tv_crunches_record = (TextView)findViewById(R.id.tv_crunches_record);
        tv_crunches_record.setText(""+MainActivity.using_this_phone.getCrunches_record());
        btn_ok_crunches = (ImageButton)findViewById(R.id.btn_ok_crunches);
        et_crunches = (EditText)findViewById(R.id.et_crunches);
        btn_crunches_leaderboard = (Button)findViewById(R.id.btn_crunches_leaderboard);
        btn_ok_crunches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(YourRecordsActivity.this, "השיא עודכן", Toast.LENGTH_LONG).show();
                animator.start();
                if(et_crunches.getText().toString().trim().length() !=0) {
                    String newValue = et_crunches.getText().toString();
                    tv_crunches_record.setText(newValue);
                    MainActivity.using_this_phone.setCrunches_record(Integer.valueOf(newValue));
                    myRef.child("users:").child(MainActivity.using_this_phone.getName()).setValue(MainActivity.using_this_phone);
                    if (best_record_crunches<Integer.valueOf(newValue)){ // checks if the new record is better than the best
                        leaderNameAndRecord.setLeadingRecord(Integer.valueOf(newValue));
                        leaderNameAndRecord.setLeadingPersonName(MainActivity.using_this_phone.getName());
                        leaderNameAndRecord.setPreviousPersonName(name_person_best_crunches);
                        myRef.child("leaders names:").child("crunches leader").setValue(leaderNameAndRecord);

                    }
                }
            }
        });


    }
    public void onClick(View view){
        //push up
        if (view == btn_push_up_leaderboard) sportName = "Push Ups";

        //pull up
        if (view == btn_pull_up_leaderboard) sportName = "Pull Ups";


        //leg_lift
        if (view == btn_leg_lifts_leaderboard) sportName = "Leg Lifts";

        //parallels
        if (view == btn_parallels_leaderboard) sportName = "Parallels";

        //tv_crunches
        if (view == btn_crunches_leaderboard)  sportName = "Crunches";

        Intent i = new Intent(YourRecordsActivity.this, LeaderBoard_pushups_Activity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
        }
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id==R.id.item_push_up) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("הסבר-Push Up");
            LinearLayout linearLayout = new LinearLayout(YourRecordsActivity.this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            LayoutInflater factory = LayoutInflater.from(YourRecordsActivity.this);
            final View view = factory.inflate(R.layout.sample_dialog_pushup_xml, null);
            builder.setView(view);



            builder.setCancelable(true);
            builder.setPositiveButton("הבנתי", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();
        }
        if(id==R.id.item_pull_up){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("הסבר-Pull Up");
            LinearLayout linearLayout = new LinearLayout(YourRecordsActivity.this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            LayoutInflater factory = LayoutInflater.from(YourRecordsActivity.this);
            final View view = factory.inflate(R.layout.sample_dialog_pullup_xml, null);
            builder.setView(view);



            builder.setCancelable(true);
            builder.setPositiveButton("הבנתי", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();
        }
        if(id==R.id.item_leg_lift){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("הסבר-Leg Lift");
            LinearLayout linearLayout = new LinearLayout(YourRecordsActivity.this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            LayoutInflater factory = LayoutInflater.from(YourRecordsActivity.this);
            final View view = factory.inflate(R.layout.sample_dialog_leglift_xml, null);
            builder.setView(view);



            builder.setCancelable(true);
            builder.setPositiveButton("הבנתי", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();
        }
        if(id==R.id.item_parallel){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("הסבר-Leg Lift");
            LinearLayout linearLayout = new LinearLayout(YourRecordsActivity.this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            LayoutInflater factory = LayoutInflater.from(YourRecordsActivity.this);
            final View view = factory.inflate(R.layout.sample_dialog_parallel_xml, null);
            builder.setView(view);



            builder.setCancelable(true);
            builder.setPositiveButton("הבנתי", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();
        }
        if(id==R.id.item_crunche){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("הסבר-Leg Lift");
            LinearLayout linearLayout = new LinearLayout(YourRecordsActivity.this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            LayoutInflater factory = LayoutInflater.from(YourRecordsActivity.this);
            final View view = factory.inflate(R.layout.sample_dialog_cranches_xml, null);
            builder.setView(view);



            builder.setCancelable(true);
            builder.setPositiveButton("הבנתי", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();
        }
        return true;
    }
}