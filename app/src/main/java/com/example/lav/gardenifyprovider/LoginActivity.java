package com.example.lav.gardenifyprovider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText namebox,passwordbox,citybox;
    Button button;
    DatabaseReference mdatabase;
    SharedPreferences sharedPreferences;
    final String myprefs="UserPrefs";
    final String key="Name";
    final String key2="City";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        namebox = findViewById(R.id.name);
        passwordbox = findViewById(R.id.Password);
        citybox=findViewById(R.id.logincity);
        button = findViewById(R.id.loginbutton);
        //mdatabase= FirebaseDatabase.getInstance().getReference().child("Customer").child("Lak");
        sharedPreferences=getSharedPreferences(myprefs, Context.MODE_PRIVATE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    final String name = namebox.getText().toString();
                    final String password = passwordbox.getText().toString();
                    final String city= citybox.getText().toString();
                    mdatabase= FirebaseDatabase.getInstance().getReference().child("ANAND").child("PROVIDER").child(name);
                    // Read from the database
                    mdatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            //String value = dataSnapshot.getValue(String.class);
                            User user=dataSnapshot.getValue(User.class);
                            Log.d("Value", "Name is: " + user.getName()+"Pass is:"+user.getPassword());
                            if(!password.equals(""+user.getPassword())){
                                Toast.makeText(getApplicationContext(),"Invalid Pass",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                SharedPreferences.Editor data=sharedPreferences.edit();
                                data.putString(key,name);
                                data.putString(key2,city);
                                data.commit();
                                Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LaunchActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w("Value", "Failed to read value.", error.toException());
                            Toast.makeText(getApplicationContext(),"Check Your Internet Connection",Toast.LENGTH_SHORT).show();
                        }
                    });
                }catch(Exception e)
                {
                    Log.d("Value",""+e.getMessage());
                    Toast.makeText(getApplicationContext(),"Something went Wrong!",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),LaunchActivity.class);
        startActivity(intent);
        finish();

    }
}
