package com.knby.notesy;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static SQLiteDatabase myDatabase;
    static List<Note> notesList;
    RecyclerView recyclerview;
    RecyclerView.LayoutManager layoutmanager;
    static NoteAdapter noteAdapter;
    static TextView noNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Your notes");
        setContentView(R.layout.activity_main);


        FloatingActionButton add=(FloatingActionButton)findViewById(R.id.fabAdd);
        noNotes=(TextView)findViewById(R.id.noNotes);
        recyclerview=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        layoutmanager=new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutmanager);
        notesList=new ArrayList<>();

        try{

            myDatabase=this.openOrCreateDatabase("NoteDatabase",MODE_PRIVATE,null);

        }catch (Exception e){
            Log.i("Error","not working");
        };
        MainActivity.myDatabase.execSQL("CREATE TABLE IF NOT EXISTS notes(title VARCHAR,description VARCHAR,colourcode VARCHAR)");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),AddNotes.class);
                startActivity(intent);
            }
        });

        Cursor allNotes=myDatabase.rawQuery("SELECT * FROM notes",null);
        if(allNotes.getCount()==0)
            noNotes.setVisibility(View.VISIBLE);
        if(allNotes.getCount()!=0){
            allNotes.moveToFirst();
            noNotes.setVisibility(View.GONE);

            while(allNotes!=null)
            {
                Note note=new Note();
                Log.i("Errore",allNotes.getString(0)+"   "+allNotes.getString(1)+"   ");
                note.setTitle(allNotes.getString(0));
                note.setDescription(allNotes.getString(1));
                note.setColourCode(allNotes.getString(2));
                notesList.add(note);

                if(allNotes.moveToNext()==false)break;
            }}

        noteAdapter=new NoteAdapter(notesList,getApplicationContext());
        recyclerview.setAdapter(noteAdapter);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.Help)
        {
            new AlertDialog.Builder(this).setTitle("Instructions").setMessage("1.Press '+' to add new note\n\n2.Hold on a note to delete it\n\n3.Tap on a note to modify it")
                    .setPositiveButton("OK",null)
                    .show();
        }
        else if(id==R.id.Info)
        {
            Intent intent =new Intent(getApplicationContext(),AboutActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
}
