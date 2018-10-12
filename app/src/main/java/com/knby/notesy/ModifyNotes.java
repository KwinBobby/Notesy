package com.knby.notesy;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import petrov.kristiyan.colorpicker.ColorPicker;

import static com.knby.notesy.R.id.linearLayout;

public class ModifyNotes extends AppCompatActivity {

    Note note;
    LinearLayout linearLayout;
    int chosenColor=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_notes);

        Intent i=getIntent();
        final int position=i.getIntExtra("Position",-1);

        FloatingActionButton fabDone=(FloatingActionButton)findViewById(R.id.fabDone);
        final EditText titleText=(EditText)findViewById(R.id.titleText);
        final EditText descriptionText=(EditText)findViewById(R.id.descriptionText);
        linearLayout=(LinearLayout)findViewById(R.id.modify_linear);

        note =new Note();
        note=MainActivity.notesList.get(position);
        titleText.setText(note.getTitle());
        descriptionText.setText(note.getDescription());
        linearLayout.setBackgroundColor(Color.parseColor(note.getColourCode()));

        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity.myDatabase.execSQL("UPDATE notes SET title='"+titleText.getText().toString()+"' WHERE rowid="+String.valueOf(position+1));
                MainActivity.myDatabase.execSQL("UPDATE notes SET description='"+descriptionText.getText().toString()+"' WHERE rowid="+String.valueOf(position+1));
               if(chosenColor!=0)
                MainActivity.myDatabase.execSQL("UPDATE notes SET colourcode='"+String.format("#%06X", (0xFFFFFF & chosenColor))+"' WHERE rowid="+String.valueOf(position+1));
                else
                MainActivity.myDatabase.execSQL("UPDATE notes SET colourcode='"+note.getColourCode()+"' WHERE rowid="+String.valueOf(position+1));
                note.setTitle(titleText.getText().toString());
                note.setDescription(descriptionText.getText().toString());
                if(chosenColor!=0)
                    note.setColourCode(String.format("#%06X", (0xFFFFFF & chosenColor)));
                else
                    note.setColourCode(note.getColourCode());

                MainActivity.notesList.set(position,note);
                MainActivity.noteAdapter.notifyDataSetChanged();

                ModifyNotes.this.finish();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.ColourPalette)
        {

            ColorPicker colorPicker = new ColorPicker(ModifyNotes.this);
            colorPicker.setRoundColorButton(true).setColorButtonSize(45,45).setTitle("").setColumns(5);
            colorPicker.show();
            colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                @Override
                public void onChooseColor(int position,int color) {
                    // put code
                   // Toast.makeText(getApplicationContext(),String.valueOf(color), Toast.LENGTH_SHORT).show();
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
