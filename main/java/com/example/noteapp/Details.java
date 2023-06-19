package com.example.noteapp;

import android.content.Intent;
import android.os.Bundle;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class Details extends AppCompatActivity {

    TextView mDetails;
    NoteDatabase db;
    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDetails = findViewById(R.id.detailsOfNote);

        Intent i = getIntent();
        long id = i.getLongExtra("ID", 0);

        db = new NoteDatabase(this);
        note = db.getNote(id);
        if (note == null) {
            Log.d("Details", "Note is null");
        } else {
            Log.d("Details", "Note ID: " + note.getId());
            Log.d("Details", "Note Title: " + note.getTitle());
            Log.d("Details", "Note Content: " + note.getContent());

            Objects.requireNonNull(getSupportActionBar()).setTitle(note.getTitle());
            mDetails.setText(note.getContent());
            mDetails.setMovementMethod(new ScrollingMovementMethod());

            Toast.makeText(this, "Title -> " + note.getTitle(), Toast.LENGTH_SHORT).show();
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            db.deleteNote(note.getId());
            Toast.makeText(getApplicationContext(), "Note is deleted", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));

        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete) {

            Toast.makeText(this, "Note not Saved", Toast.LENGTH_SHORT).show();

        }
        if (item.getItemId() == R.id.editNote) {
            //send user to edit activity
            Toast.makeText(this, "Edit note", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, Edit.class);
            i.putExtra("ID", note.getId());
            startActivity(i);

        }
        return super.onOptionsItemSelected(item);

    }
}
