package com.example.cvuse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainPage extends AppCompatActivity {

    BottomNavigationView bnv;
    Menu menu;
    MenuItem menuItem;
    profileFragment profileFragment = new profileFragment();
    todolistFragment todolistFragment = new todolistFragment();
    homeFragment homeFragment = new homeFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);


        bnv = findViewById(R.id.bottomNavView);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();//set default page(home)

        menu = bnv.getMenu();
        menuItem = menu.findItem(R.id.home);
        menuItem.setChecked(true);//fix bug of the default page and the menuItem should be the middle one


        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                        return true;
                    case R.id.todolist:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,todolistFragment).commit();
                        return true;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,profileFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}