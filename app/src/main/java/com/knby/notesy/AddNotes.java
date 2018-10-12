package com.knby.notesy;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import petrov.kristiyan.colorpicker.ColorPicker;

import static com.knby.notesy.R.id.linearLayout;

public class AddNotes extends AppCompatActivity {


    LinearLayout linearLayout;
    int chosenColor=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add note");
        setContentView(R.layout.activity_add_notes);

        final int colour=-11419154;

        FloatingActionButton fabDone=(FloatingActionButton)findViewById(R.id.fabDone);
        final EditText titleText=(EditText)findViewById(R.id.titleText);
        final EditText descriptionText=(EditText)findViewById(R.id.descriptionText);
        linearLayout=(LinearLayout)findViewById(R.id.linear);
        linearLayout.setBackgroundColor(Color.parseColor(String.format("#%06X", (0xFFFFFF & colour))));
        Log.i("Erroe",String.format("#%06X", (0xFFFFFF & colour)));



        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!titleText.getText().toString().isEmpty()) {
                    Note note = new Note();
                    note.setTitle(titleText.getText().toString());
                    note.setDescription(descriptionText.getText().toString());
                    if(chosenColor!=0)
                    note.setColourCode(String.format("#%06X", (0xFFFFFF & chosenColor)));
                    else
                    note.setColourCode(String.format("#%06X", (0xFFFFFF & colour)));
                    MainActivity.notesList.add(note);
                    MainActivity.noteAdapter.notifyDataSetChanged();
                    if(chosenColor!=0)
                    MainActivity.myDatabase.execSQL("INSERT INTO notes(title,description,colourcode) VALUES('" + titleText.getText().toString() + "','" + descriptionText.getText().toString() + "','"+String.format("#%06X", (0xFFFFFF & chosenColor))+"')");
                    else
                    MainActivity.myDatabase.execSQL("INSERT INTO notes(title,description,colourcode) VALUES('" + titleText.getText().toString() + "','" + descriptionText.getText().toString() + "','"+String.format("#%06X", (0xFFFFFF & colour))+"')");
                    MainActivity.noNotes.setVisibility(View.INVISIBLE);
                    AddNotes.this.finish();
                }
                else
                    Toast.makeText(getApplicationContext(),"Please add a title",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.ColourPalette)
        {

            ColorPicker colorPicker = new ColorPicker(AddNotes.this);
            colorPicker.setRoundColorButton(true).setColorButtonSize(45,45).setTitle("").setColumns(5);
            colorPicker.show();
            colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                @Override
                public void onChooseColor(int position,int color) {
                    // put code
                 //   Toast.makeText(getApplicationContext(),String.valueOf(color), Toast.LENGTH_SHORT).show();
                    System.out.println(color);
                    if(color!=0)
                    chosenColor=color;
                    if(color!=0)linearLayout.setBackgroundColor(color);
                }

                @Override
                public void onCancel(){
                    // put code
                }
            });

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.colour_menu,menu);
        return true;
    }
}
