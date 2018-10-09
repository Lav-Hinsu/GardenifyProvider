package com.example.lav.gardenifyprovider;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddSkill extends AppCompatActivity {
    TextView textView;
    EditText skillentry,pricebox;
    RadioGroup duration;
    RadioButton selected;
    Button addbutton;


    SharedPreferences sharedPreferences;
    final String myprefs="UserPrefs";
    final String key="Name";
    final String key2="City";


    DatabaseReference mdatabase;
    DatabaseReference mdatabase2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_skill);
        setTitle("Add Services");
        duration= findViewById(R.id.radioduration);
        addbutton= findViewById(R.id.addskillbutton);
        skillentry= findViewById(R.id.addskill);
        sharedPreferences=getSharedPreferences(myprefs, Context.MODE_PRIVATE);

        pricebox=findViewById(R.id.priceentry);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectid = duration.getCheckedRadioButtonId();
                selected=findViewById(selectid);
                final String duration=selected.getText().toString();
                final String skill=skillentry.getText().toString();



                sharedPreferences=getSharedPreferences(myprefs, Context.MODE_PRIVATE);
                final String name1=sharedPreferences.getString(key,"");
                final String city=sharedPreferences.getString(key2,"");

                mdatabase= FirebaseDatabase.getInstance().getReference().child(city.toUpperCase()).child("PROVIDER").child(name1);
                mdatabase2=FirebaseDatabase.getInstance().getReference().child(city.toUpperCase()).child("SERVICES").child(duration.toUpperCase()).child(skill.toUpperCase());
                mdatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user= new User();
                        user = dataSnapshot.getValue(User.class);
                        user.addskills(skill+"@"+duration);
                        update(mdatabase,user);
                        mdatabase2.push().setValue(name1+"@"+pricebox.getText().toString());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(),"Check Your Internet Connection",Toast.LENGTH_SHORT).show();
                    }
                });




            }
        });

    }

    void update(DatabaseReference mdatabase,User user){
        mdatabase.setValue(user);
        this.finish();
    }

}
