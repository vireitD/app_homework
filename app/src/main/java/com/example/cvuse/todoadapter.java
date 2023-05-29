package com.example.cvuse;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class todoadapter extends RecyclerView.Adapter<todoadapter.ViewHolder> {

     List<todomodel> todolist;
     MainPage mainPage;

    public  todoadapter(MainPage mainPage){
        this.mainPage = mainPage;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.taskbox1, parent, false);


        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder,int position){
        todomodel item = todolist.get(position);
        Log.d("Todoadapter", "setText called on task " + item.getTask());
        if (holder.task != null) {
            holder.task.setText(item.getTask());
            holder.task.setChecked(toBoolean(item.getStatus()));
            holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                       item.setStatus(1);
                    }else {
                        item.setStatus(0);
                    }
                }
            });
        }
    }

    public int getItemCount(){
        return todolist.size();
    }

    private boolean toBoolean(int n){
        return n!=0;
    }

    public void setTasks(List<todomodel> todolist){
        this.todolist = todolist;
        notifyDataSetChanged();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;

        ViewHolder(View view){
            super(view);
            task = view.findViewById(R.id.todobox);

        }
    }

}
