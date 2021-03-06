package com.example.lav.gardenifyprovider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.List;

public class PendingOrders extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    DatabaseReference mdatabase;
    final String myprefs="UserPrefs";
    final String key="Name";
    final String key2="City";
    ListView pendingorders;
    DatabaseReference mdatabase2;
    String city,name;
    ArrayList<String> pendingorders2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_orders);
        setTitle("Pending Orders");

        sharedPreferences=getSharedPreferences(myprefs, Context.MODE_PRIVATE);

        pendingorders=findViewById(R.id.pendingorders);
        try {

            final ProgressDialog progDailog = ProgressDialog.show(this,
                    "Please Wait",
                    "Loading Data.....", true);
            new Thread() {
                public void run() {
                    try {
                        // sleep the thread, whatever time you want.
                        sleep(3000);
                    } catch (Exception e) {
                    }
                    progDailog.dismiss();
                }
            }.start();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show();
        }

        city=sharedPreferences.getString(key2,"");
        name=sharedPreferences.getString(key,"");
        mdatabase2= FirebaseDatabase.getInstance().getReference().child(city.toUpperCase()).child("PROVIDER").child(name);

        mdatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user= new User();
                user=dataSnapshot.getValue(User.class);
                pendingorders2=new ArrayList<String>();
                pendingorders2=user.getPendingorder();
                ArrayList<String > temp = new ArrayList<>();
                for(int i=0;i<pendingorders2.size();i++)
                {
                    String temp2[]=pendingorders2.get(i).split("@",-1);
                    if(temp2.length==4){
                        String name2=temp2[0];
                        String cust2=temp2[1];
                        String time=temp2[2];
                        String price=temp2[3];
                        String disp="Customer Name:"+cust2+" Price:"+price;
                        temp.add(disp);

                    }
                    else if(temp2.length==5){
                        String name2=temp2[0];
                        String cust2=temp2[1];
                        String time=temp2[2];
                        String date2=temp2[3];
                        String price=temp2[4];
                        String disp="Customer Name:"+cust2+" Date"+date2+" Price:"+price;
                        temp.add(disp);


                    }
                }
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,temp);
                pendingorders.setAdapter(arrayAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Check your internet connection",Toast.LENGTH_SHORT).show();

            }
        });
        pendingorders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(),OrderConfirm.class);
                intent.putExtra("order",""+pendingorders2.get(position));
                startActivity(intent);

                Toast.makeText(getApplicationContext(),""+pendingorders2.get(position),Toast.LENGTH_SHORT).show();


            }
        });

    }
}
