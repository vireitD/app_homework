package com.example.cvuse;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class todolistFragment extends Fragment {

    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    FloatingActionButton fab;
    todoadapter todoadapter;
    List<todomodel> tasklist;
    Button clear;
    CheckBox checkboxtask;
    Dialog dialog;
    Gson gson = new Gson();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todolist, container, false);

        sharedPreferences = getActivity().getSharedPreferences("taskList",Context.MODE_PRIVATE);
        loadTask();


        recyclerView = view.findViewById(R.id.rcv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        todoadapter = new todoadapter((MainPage) getActivity());
        recyclerView.setAdapter(todoadapter);



        todoadapter.setTasks(tasklist);

        fab =view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_add_task);
                EditText editTextTaskName = dialog.findViewById(R.id.edit_text_task_name);
                Button buttonSave = dialog.findViewById(R.id.button_save);
                buttonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String taskName = editTextTaskName.getText().toString();
                        todomodel task = new todomodel();
                        task.setTask(taskName);
                        task.setStatus(0);
                        task.setId(tasklist.size() + 1);
                        tasklist.add(task);
                        saveTask();
                        todoadapter.setTasks(tasklist);
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        checkboxtask = view.findViewById(R.id.todobox);
        clear = view.findViewById(R.id.clearButton);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Iterator<todomodel> iterator = tasklist.iterator();
                while (iterator.hasNext()){
                    todomodel task = iterator.next();
                    int checkClear = task.getStatus();
                    if(checkClear ==1){
                        iterator.remove();
                        saveTask();
                    }
                }
                todoadapter.setTasks(tasklist);
            }
        });


        return view;
    }


    public void loadTask(){

        String json = sharedPreferences.getString("taskList", null);
        Type type = new TypeToken<ArrayList<todomodel>>() {}.getType();
        tasklist = gson.fromJson(json, type);
        if (tasklist == null) {
            tasklist = new ArrayList<>();
        }
    }

    public void saveTask(){
        String json = gson.toJson(tasklist);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("taskList", json);
        editor.apply();
    }
}