package com.example.todolist;

import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Data data;
    ArrayAdapter<String> mAdapter;
    ListView lstTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data=new Data(this);
        lstTask=(ListView)findViewById(R.id.lstTask);
        loadTaskList();
    }

    private void loadTaskList() {
        ArrayList<String> taskList=data.getTaskList();
        if (mAdapter==null){
            mAdapter=new ArrayAdapter<String>(this,R.layout.row,R.id.task_title,taskList);
            lstTask.setAdapter(mAdapter);

        }
        else{
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }

    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        //change the color of the menu
        Drawable icon=menu.getItem(0).getIcon();
        icon.mutate();
        //icon.setColorFilter(getResources().getColor(android.R.color.white, PorterDuff.Mode.SRC_IN));
        return super.onCreateOptionsMenu(menu);

    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_add_task:
                final EditText taskEditText=new EditText(this);
                AlertDialog dialog=new AlertDialog.Builder(this).setTitle("ADD A NEW TASK").setMessage("What Do You Want To Do Next?").setView(taskEditText).setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String task=String.valueOf(taskEditText.getText());
                        data.insertTask(task);
                        loadTaskList();


                    }
                }).setNegativeButton("Cancel",null).create();
                dialog.show();
                return true;

        }
        return super.onOptionsItemSelected(item);

    }
    public void deleteTask(View view){
        View parent=(View)view.getParent();
        TextView taskTextView=(TextView)findViewById(R.id.task_title);
        String task=String.valueOf(taskTextView.getText());
        data.deleteTask(task);
        loadTaskList();


    }

}
