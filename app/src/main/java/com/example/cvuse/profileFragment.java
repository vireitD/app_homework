package com.example.cvuse;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class profileFragment extends Fragment {

    SharedPreferences sharedPreferences;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    GoogleSignInAccount acct;
    TextView name,email,changepassword;
    Button logout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(getActivity(),gso);

        acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if(acct != null){
            String name1 = acct.getDisplayName();
            String email1 = acct.getEmail();
            name.setText("user:" + name1);
            email.setText("Email:" + email1);
        } else{
            sharedPreferences = getActivity().getSharedPreferences("saveInput", Context.MODE_PRIVATE);
            String name1 = sharedPreferences.getString("rgname","");
            String email1 = sharedPreferences.getString("rgemail","");
            name.setText("user:" + name1);
            email.setText("Email:" + email1);
        }


        changepassword =view.findViewById(R.id.changepassword);
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "The function is not available for the moment!",Toast.LENGTH_LONG).show();
            }
        });

        logout = view.findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogoutMassage();
            }
        });



        return view;
    }

    void LogoutMassage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Log Out");
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Log Out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sharedPreferences = getActivity().getSharedPreferences("saveInput", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("kplogin","false");
                editor.apply();
                successful();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void successful(){
        acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if(acct != null) {
            gsc.revokeAccess().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    getActivity().finish();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    Toast.makeText(getActivity(), "you log out successfully!", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            });
        } else {
            getActivity().finish();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            Toast.makeText(getActivity(), "you log out successfully!", Toast.LENGTH_LONG).show();
            startActivity(intent);
        }
    }

}