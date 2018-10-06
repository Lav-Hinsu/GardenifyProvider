package com.example.lav.gardenifyprovider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText namebox,passwordbox;
    Button button;
    DatabaseReference mdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        namebox = findViewById(R.id.name);
        passwordbox = findViewById(R.id.Password);
        button = findViewById(R.id.Login);
        mdatabase= FirebaseDatabase.getInstance().getReference().child("Customer");
        try {

            String name = namebox.getText().toString();
            String password = passwordbox.getText().toString();
            User user = new User();

            user.set(name, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Read from the database
        try {
            mdatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String value = dataSnapshot.getValue(String.class);
                    Log.d("Value", "Value is: " + value);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("Value", "Failed to read value.", error.toException());
                }
            });
        }catch(Exception e)
        {
            Log.d("Value",""+e.getMessage());
        }
    }
}
