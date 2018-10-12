package com.knby.notesy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Kevin on 10/12/2016.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {

    List<Note> notesList;
    private Context context;
    public NoteAdapter(List<Note> notesList,Context context)
    {
        super();
        this.notesList=notesList;
        this.context=context;

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cardTitle;
        TextView cardDescription;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            cardTitle = (TextView) itemView.findViewById(R.id.noteTitle);
            cardDescription = (TextView) itemView.findViewById(R.id.noteDescription);
            cardView=(CardView)itemView.findViewById(R.id.card);
        }
    }
    @Override
    public NoteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.added_notes,parent,false);
        MyViewHolder holder=new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final NoteAdapter.MyViewHolder holder, final int position) {
        Note currentNote=notesList.get(position);
        holder.cardTitle.setText(currentNote.getTitle());
        holder.cardDescription.setText(currentNote.getDescription());
        holder.cardView.setCardBackgroundColor(Color.parseColor(currentNote.getColourCode()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Errore","Tapped button is "+String.valueOf(position));
                Intent intent=new Intent(context,ModifyNotes.class);
                intent.putExtra("Position",position);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Log.i("Errore","pressed "+position);
               new AlertDialog.Builder(holder.itemView.getContext())
                        .setTitle("Delete note")
                        .setMessage("Are you sure you want to delete it?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                MainActivity.myDatabase.execSQL("DELETE FROM notes WHERE rowid="+String.valueOf(position+1));
                                MainActivity.myDatabase.execSQL("VACUUM notes");
                                Log.i("Errore","DELETE FROM notes WHERE rowid="+String.valueOf(position+1));
                                check();
                                MainActivity.notesList.remove(position);
                                MainActivity.noteAdapter.notifyDataSetChanged();
                                if(MainActivity.notesList.isEmpty())MainActivity.noNotes.setVisibility(View.VISIBLE);

                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                return true;
            }
        });

    }

    void check()
    {
        Cursor allNotes=MainActivity.myDatabase.rawQuery("SELECT rowid,* FROM notes",null);
        if(allNotes.getCount()!=0){
            allNotes.moveToFirst();
            while(allNotes!=null)
            {
                Log.i("Errore",allNotes.getString(0)+"   "+allNotes.getString(1)+"   "+allNotes.getString(2));
                if(allNotes.moveToNext()==false)break;
            }}
    }
    @Override
    public int getItemCount() {
        return notesList.size();
    }
}
