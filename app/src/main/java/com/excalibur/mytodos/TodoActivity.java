
package com.excalibur.mytodos;

import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TodoActivity extends ListActivity {

    TextView textViewGreetings;
    Button btnAdd;
    ArrayList<String> todos;
    SharedPreferences sharedPref;
    String packageName = "com.excalibur.mytodos";

    TextView textViewIntro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        textViewIntro = (TextView) findViewById(R.id.textViewGreetings);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        Bundle extras = getIntent().getExtras();
        String message = "  ";
        if(extras != null){
            message = extras.getString("id");
        }
        textViewIntro.setText("Hello " + message);

        sharedPref = this.getSharedPreferences(packageName, Context.MODE_PRIVATE);
        todos = new ArrayList<String>();
        loadArrayList();
        updateList();

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                showAddDialog();
            }
        });
    }

    protected void onListItemClick(ListView l, View v, final int position, long id) {
        super.onListItemClick(l, v, position, id);
        String work = todos.get(position);
        LogLibrary.print("clicked: " + work);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(work);
        builder.setMessage("? ?? ? ????");
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //? ?? ??? ????
                todos.remove(position);
                saveArrayList();
                updateList();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("add");
        builder.setMessage("? ?? ??? ???");

        final EditText todo_input = new EditText(this);
        todo_input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(todo_input); //todo_input ? alertdialog? ????
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
           @Override
           public void onClick(DialogInterface dialog, int which){
               //OK ? ??? ? ???? ??
               String input = todo_input.getText().toString();
               todos.add(input);
               LogLibrary.printEach(todos);
               saveArrayList();
               updateList();
           }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Cancel ??? ?? cancel
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void saveArrayList(){
        String todo_str = "";
        for(String todo : todos){
            todo_str = todo_str + todo + "&&&&&";
            LogLibrary.print(todo_str);
        }
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("todo", todo_str);
        editor.commit();
    }

    public void loadArrayList() {
        String todo = sharedPref.getString("todo", "");
        String[] todo_arr = todo.split("&&&&&");
        LogLibrary.printEach(todo_arr);
        for (String work : todo_arr) {
            todos.add(work);
        }
    }
;
    public void updateList(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,
                android.R.layout.simple_list_item_1, todos);
        setListAdapter(adapter);
    }
}

