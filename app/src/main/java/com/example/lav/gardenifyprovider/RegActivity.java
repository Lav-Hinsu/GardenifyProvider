package com.example.lav.gardenifyprovider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegActivity extends AppCompatActivity {
    EditText regname,regpassword,regconfirmpass;
    Button register;
    DatabaseReference mdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        regname = findViewById(R.id.regname);
        regpassword = findViewById(R.id.regpassword);
        regconfirmpass = findViewById(R.id.regconfirmpass);
        register = findViewById(R.id.regbutton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                String name=regname.getText().toString();
                String pass=regpassword.getText().toString();
                String cpass=regconfirmpass.getText().toString();
                if(pass.equals(cpass))
                {
                    mdatabase= FirebaseDatabase.getInstance().getReference();
                    User user = new User();
                    user.set(name,pass);
                    mdatabase.child("Provider").child(name).setValue(user);
                    Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
                    finish();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Passwords do not match",Toast.LENGTH_SHORT).show();
                }}
                catch(Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
