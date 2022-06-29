package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText et_name;
    EditText et_password;
    Button btn_LogIn;
    Button btn_Signup;
    static person using_this_phone;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    WifiReceiver wifiReceiver = new WifiReceiver();
    Intent my_notification_intent;

    @Override   
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Best Sport record");

        my_notification_intent = new Intent(MainActivity.this,TryService.class);
        startService(my_notification_intent);

        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(wifiReceiver,intentFilter);

        et_name = (EditText)findViewById(R.id.et_name);
        et_password = (EditText)findViewById(R.id.et_password);
        btn_LogIn = (Button)findViewById(R.id.btn_LogIn);
        btn_Signup = (Button)findViewById(R.id.btn_SignUp);
        myRef = database.getReference();
        Query q = myRef.child("leaders names:").orderByValue();
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){ // checks if the player exists already
                    LeaderNameAndRecord leaderNameAndRecord = new LeaderNameAndRecord();
                    myRef.child("leaders names:").child("push up leader").setValue(leaderNameAndRecord);
                    myRef.child("leaders names:").child("pull up leader").setValue(leaderNameAndRecord);
                    myRef.child("leaders names:").child("parallels leader").setValue(leaderNameAndRecord);
                    myRef.child("leaders names:").child("leg lifts leader").setValue(leaderNameAndRecord);
                    myRef.child("leaders names:").child("crunches leader").setValue(leaderNameAndRecord);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void OnClick(View view)
    {
        if((et_name.getText().toString().trim().length())==0 || (et_password.getText().toString().trim().length())==0 ) {
            Toast.makeText(MainActivity.this, "לא הוכנס שם משתמש או סיסמה", Toast.LENGTH_LONG).show();
        }


        else{
            if(view==btn_LogIn){
                Query q = myRef.child("users:").orderByValue();
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean userExsist = false;
                        for (DataSnapshot dst : snapshot.getChildren()){
                            person user = dst.getValue(person.class);
                            if (user.getName().equals(et_name.getText().toString())) {
                                if (user.getPassword().equals(et_password.getText().toString())) {
                                    Toast.makeText(MainActivity.this, "התחברת בהצלחה", Toast.LENGTH_LONG).show();
                                    userExsist = true;
                                    using_this_phone = user;
                                    Intent i = new Intent(MainActivity.this, YourRecordsActivity.class);
                                    startActivity(i);
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "הסיסמה שגויה", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                        if (!userExsist){
                            Toast.makeText(MainActivity.this, "שם משמש אינו קיים", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            if(view==btn_Signup){
                Query q = myRef.child("users:").orderByValue();
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean userExsist = false;
                        for (DataSnapshot dst : snapshot.getChildren()){
                            person user = dst.getValue(person.class);
                            if (user.getName().equals(et_name.getText().toString())){
                                userExsist = true;
                            }
                        }
                        if (userExsist==false){
                            person newuser = new person(et_name.getText().toString());
                            newuser.setPassword(et_password.getText().toString());
                            myRef.child("users:").child(newuser.getName()).setValue(newuser);
                            using_this_phone = newuser;
                            Intent i = new Intent(MainActivity.this, YourRecordsActivity.class);
                            startActivity(i);
                        }
                        if (userExsist==true){
                            Toast.makeText(MainActivity.this, "שם המשתמש תפוס", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(wifiReceiver);

    }


}