package com.example.lav.gardenifyprovider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConfirmedOrders extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    ListView confirmedorders;
    final String myprefs="UserPrefs";
    final String key="Name";
    final String key2="City";
    String city,name;
    DatabaseReference mdatabase2;
    ArrayList<String> confirmorders2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmed_orders);

        confirmedorders=findViewById(R.id.confirmedorders);
        sharedPreferences=getSharedPreferences(myprefs, Context.MODE_PRIVATE);



        city=sharedPreferences.getString(key2,"");
        name=sharedPreferences.getString(key,"");
        mdatabase2= FirebaseDatabase.getInstance().getReference().child(city.toUpperCase()).child("PROVIDER").child(name);

        mdatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user= new User();
                user=dataSnapshot.getValue(User.class);
                confirmorders2=new ArrayList<String>();
                confirmorders2=user.getPendingorder();
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,confirmorders2);
                confirmedorders.setAdapter(arrayAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Check your internet connection",Toast.LENGTH_SHORT).show();

            }
        });
       /* confirmedorders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(),OrderConfirm.class);
                intent.putExtra("order",""+pendingorders2.get(position));
                startActivity(intent);

                Toast.makeText(getApplicationContext(),""+pendingorders2.get(position),Toast.LENGTH_SHORT).show();


            }
        });*/





    }
}