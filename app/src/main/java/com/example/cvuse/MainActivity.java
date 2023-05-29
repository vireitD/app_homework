package com.example.cvuse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;//get register data
    GoogleSignInOptions gso; //google play service default function (use to request the permission what you need and change signIn step)
    GoogleSignInClient gsc; //google play service default function (use to request what you need)
    ImageView googleBtn;//img of google button
    CheckBox checkBox;// remember me checkbox
    EditText ed1,ed2;//user login
    Button btn1;// login button
    TextView tv1,tv2;//register and forgot password
    String login,password,CheckLogin,CheckPassword,kplogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
        keeplogin();// when user click the remember me, always login

        tv1 = findViewById(R.id.Register);
        tv2 = findViewById(R.id.Forgot);
        ed1 = findViewById(R.id.Et1);
        ed2 = findViewById(R.id.Et2);
        btn1 = findViewById(R.id.Login_btn);
        checkBox = findViewById(R.id.checkBox);



        tv1.setOnClickListener(new View.OnClickListener() {//register on click event
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,rg.class);
                startActivity(intent);//go to the register page
            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {//login button on click event
            @Override
            public void onClick(View v) {
                login = ed1.getText().toString();//get user name input
                password = ed2.getText().toString();//get password input


                sharedPreferences = getSharedPreferences("saveInput",MODE_PRIVATE);//find a sharedPreferences database call saveInput
                CheckLogin = sharedPreferences.getString("rgname","");// find the saveInput database data call rgname(user name)
                CheckPassword = sharedPreferences.getString("rgpassword","");// find the saveInput database data call rgpassword(password)

                if(login.equals(CheckLogin) && password.equals(CheckPassword)){
                    if(checkBox.isChecked()){// check remember me is or not clicked
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("kplogin","true");// add a true/false to keep user login
                        editor.apply();// apply() mean confirm or do the editor. like start()
                    }
                    successful();// go to the main page

                } else {
                    Toast.makeText(getApplicationContext(), "i can't find your username and password!",Toast.LENGTH_LONG).show();// wrong username and password message
                    Log.d("TAG",String.format("Name: %s, password: %s",login,password));
                }
            }
        });



        tv2.setOnClickListener(new View.OnClickListener() {//forgot password on click event
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "The function is not available for the moment!",Toast.LENGTH_LONG).show();//message
            }
        });


        googleBtn = findViewById(R.id.google_btn);//img google button
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();//setting the sign in format
        gsc = GoogleSignIn.getClient(this,gso);// will do the sign in using gso format

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = gsc.getSignInIntent();// set a (intent) for go to the google sign in page, the sign in page no need to design when you choose default
                startActivityForResult(signInIntent,1000);
            }
        });




    }

    private void keeplogin() {// keep user login function
        sharedPreferences = getSharedPreferences("saveInput",MODE_PRIVATE);
        kplogin = sharedPreferences.getString("kplogin","");
        Log.d("TAG",String.format("kplogin: %s",kplogin));
        if(kplogin.equals("true")){
            Intent intent = new Intent(MainActivity.this,MainPage.class);
            Toast.makeText(getApplicationContext(), "you login successfully!",Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {//google login function
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try{
                task.getResult(ApiException.class);// when finish the google login
                sharedPreferences = getSharedPreferences("saveInput", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("kplogin","true");
                editor.apply();
                successful();
            } catch (ApiException e){
                Toast.makeText(getApplicationContext(), "Something went wrong",Toast.LENGTH_SHORT).show();
            }
        }
    }

    void successful(){// login successful function
        finish();
        Intent intent = new Intent(MainActivity.this,MainPage.class);
        Toast.makeText(getApplicationContext(), "you login successfully!",Toast.LENGTH_LONG).show();
        startActivity(intent);
    }



}