package com.example.cvuse;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class rg extends AppCompatActivity {

    EditText name,email,password1,password2;
    TextView rgtv5;
    Button submit;

    String getname,getemail,getpassword1,getpassword2;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rg);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.Name);
        email = findViewById(R.id.EmailAddress);
        password1 = findViewById(R.id.Password);
        password2 = findViewById(R.id.Password2);
        rgtv5 = findViewById(R.id.rgtv5);
        submit = findViewById(R.id.submit_btn);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getname = name.getText().toString();
                getemail = email.getText().toString();
                getpassword1 = password1.getText().toString();
                getpassword2 = password2.getText().toString();

                boolean check = ValidateInfo(getname,getemail,getpassword1,getpassword2);

                if(check ==true){
                    sharedPreferences = getSharedPreferences("saveInput",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("rgname",getname);
                    editor.putString("rgemail",getemail);
                    editor.putString("rgpassword",getpassword1);
                    editor.apply();
                    Intent intent = new Intent(rg.this,MainActivity.class);
                    Toast.makeText(getApplicationContext(), "Register successful!",Toast.LENGTH_LONG).show();
                    startActivity(intent);

                }
            }
        });



    }

    private Boolean ValidateInfo(String getname,String getemail,String getpassword1,String getpassword2){
        if(getname.length()==0){
            name.requestFocus();
            name.setError("Field cannot be empty");
            return false;
        }
        else if(!getname.matches("[a-zA-Z0-9]+")){
            name.requestFocus();
            name.setError("username only accept English and number");
            return false;
        }
        else if(getemail.length()==0){
            email.requestFocus();
            email.setError("Field cannot be empty");
            return false;
        }
        else if(!getemail.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            email.requestFocus();
            email.setError("Enter Valid Email");
            return false;
        }
        else if(getpassword1.length()<=6){
            password1.requestFocus();
            password1.setError("Minimum 7 Character required");
            return false;
        }
        else if(!getpassword2.equals(getpassword1)){
            password2.requestFocus();
            password2.setError("confirm password should same as password");
            return false;
        }
        else {
            return true;
        }

    }
}