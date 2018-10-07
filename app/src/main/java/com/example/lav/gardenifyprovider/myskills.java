package com.example.lav.gardenifyprovider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class myskills extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    DatabaseReference mdatabase;
    final String myprefs="UserPrefs";
    final String key="Name";
    final String key2="City";
    ListView skillsview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myskills);
        sharedPreferences=getSharedPreferences(myprefs, Context.MODE_PRIVATE);
        String name=sharedPreferences.getString(key,"");
        String city=sharedPreferences.getString(key2,"");
        skillsview=findViewById(R.id.myskillsview);
        mdatabase=FirebaseDatabase.getInstance().getReference().child(city.toUpperCase()).child("PROVIDER").child(name);


        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user= new User();
                user=dataSnapshot.getValue(User.class);
                ArrayList<String> skills=new ArrayList<String>();
                skills=user.getSkills();
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,skills);
                skillsview.setAdapter(arrayAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.skillsfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddSkill.class);
                startActivity(intent);
            }
        });











    }
}
