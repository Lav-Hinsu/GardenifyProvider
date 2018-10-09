package com.example.lav.gardenifyprovider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DialogTitle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegActivity extends AppCompatActivity {
    EditText regname,regpassword,regconfirmpass,address1,mobileno,regcity;
    Button register;
    DatabaseReference mdatabase;
    SharedPreferences sharedPreferences;
    final String myprefs="UserPrefs";
    final String key="Name";
    final String key2="City";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        setTitle("Register");
        regname = findViewById(R.id.regname);
        regpassword = findViewById(R.id.regpassword);
        regconfirmpass = findViewById(R.id.regconfirmpass);
        address1=findViewById(R.id.address1);
        mobileno=findViewById(R.id.mobileno);
        regcity=findViewById(R.id.regcity);
        register = findViewById(R.id.regbutton);
        sharedPreferences=getSharedPreferences(myprefs, Context.MODE_PRIVATE);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                String name=regname.getText().toString();
                String pass=regpassword.getText().toString();
                String cpass=regconfirmpass.getText().toString();
                String address=address1.getText().toString();
                String city= regcity.getText().toString();
                String mobno=mobileno.getText().toString();
                if(pass.equals(cpass)&&name.length()!=0&&pass.length()!=0&&address.length()!=0&&city.length()!=0&& mobno.length()!=0)
                {
                    SharedPreferences.Editor data=sharedPreferences.edit();
                    data.putString(key,name);
                    data.putString(key2,city);
                    data.commit();
                    mdatabase= FirebaseDatabase.getInstance().getReference();
                    User user = new User();
                    user.set(name,pass,address,city,mobno);
                    mdatabase.child(city.toUpperCase()).child("PROVIDER").child(name).setValue(user);
                    Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),LaunchActivity.class);
                    startActivity(intent);
                    finish();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Incorret Details",Toast.LENGTH_SHORT).show();
                }}
                catch(Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
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
