package com.example.lav.gardenifyprovider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorTreeAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderConfirm extends AppCompatActivity {

    DatabaseReference mdatabase3,mdatabase4;
    TextView orderinfo;
    Button confirm,decline;

    SharedPreferences sharedPreferences;
    final String myprefs="UserPrefs";
    final String key="Name";
    final String key2="City";
    String city,name,order,customer;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);

        orderinfo=findViewById(R.id.orderinfo);
        confirm=findViewById(R.id.confirm);
        decline=findViewById(R.id.deny);

        Bundle extras=getIntent().getExtras();
        order=extras.get("order").toString();

        sharedPreferences=getSharedPreferences(myprefs, Context.MODE_PRIVATE);
        city=sharedPreferences.getString(key2,"");
        name=sharedPreferences.getString(key,"");

        final String temp[]=order.split("@",-1);
        customer=temp[1]; mdatabase3 = FirebaseDatabase.getInstance().getReference().child(city.toUpperCase()).child("CUSTOMER").child(customer);

        mdatabase3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Customer customer1 = dataSnapshot.getValue(Customer.class);
                mdatabase3.setValue(customer1);
                if (temp.length == 4) {
                    orderinfo.setText("Name:" + customer1.getName() + "\nAddress:" + customer1.getAddress() + "\nPhone Number:" + customer1.getPhoneno() + "\nTime:"+temp[2]+"\nPrice"+temp[3]);


                }else
                {
                    orderinfo.setText("Name:" + customer1.getName() + "\nAddress:" + customer1.getAddress() + "\nPhone Number:" + customer1.getPhoneno() + "\nTime:"+temp[2]+"\nDate:"+temp[3]+"\nPrice"+temp[4]);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"FAILED",Toast.LENGTH_SHORT).show();

            }
        });
        mdatabase4=FirebaseDatabase.getInstance().getReference().child(city.toUpperCase()).child("PROVIDER").child(name);
        mdatabase4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdatabase3 = FirebaseDatabase.getInstance().getReference().child(city.toUpperCase()).child("CUSTOMER").child(customer);

                mdatabase3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Customer customer1=dataSnapshot.getValue(Customer.class);
                        customer1.addconfirmorder(order);
                        customer1.removependingorder(order);
                        mdatabase3.setValue(customer1);
                        user.removependingorder(order);
                        user.addconfirmorder(order);
                        mdatabase4.setValue(user);
                        Toast.makeText(getApplicationContext(), "Order Confirmed", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mdatabase3 = FirebaseDatabase.getInstance().getReference().child(city.toUpperCase()).child("CUSTOMER").child(customer);

                mdatabase3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Customer customer1=dataSnapshot.getValue(Customer.class);
                        customer1.addtodeclineorders(order);
                        customer1.removependingorder(order);
                        mdatabase3.setValue(customer1);
                        user.removependingorder(order);
                        user.adddeclineorder(order);
                        mdatabase4.setValue(user);

                        Toast.makeText(getApplicationContext(), "Order Declined", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(),"Check Your Internet Connection",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });



    }
}
