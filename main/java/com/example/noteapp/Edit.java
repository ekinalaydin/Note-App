package com.example.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Objects;

public class Edit extends AppCompatActivity {
    Toolbar toolbar;
    EditText noteTitle, noteDetails;
    Calendar c;
    String todaysDate;
    String currentTime;
    NoteDatabase db;
    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent i = getIntent();
        long id = i.getLongExtra("ID", 0);
        db = new NoteDatabase(this);
        note = db.getNote(id);



        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(note.getTitle());
        getSupportActionBar().setTitle(note.getTitle());

        noteTitle = findViewById(R.id.noteTitle);
        noteDetails = findViewById(R.id.noteDetails);

        noteTitle.setText(note.getTitle());
        noteDetails.setText(note.getContent());

        noteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    Objects.requireNonNull(getSupportActionBar()).setTitle(charSequence);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        c = Calendar.getInstance();
        todaysDate = c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR);
        currentTime = pad(c.get(Calendar.HOUR)) + ":" + pad(c.get(Calendar.MINUTE));
        Log.d("calendar", " Date and Time:  " + todaysDate + " and " + currentTime);
    }


    private String pad(int i) {
        if (i < 10)
            return "0" + i;
        return String.valueOf(i);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete) {

            Toast.makeText(this, "Note not Saved", Toast.LENGTH_SHORT).show();

        }
        if (item.getItemId() == R.id.save) {
            if (noteTitle.getText().length() != 0) {
                note.setTitle(noteTitle.getText().toString());
                note.setContent(noteDetails.getText().toString());
                int id = db.editNote(note);
                if( id == note.getId()){
                    Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Error updating note", Toast.LENGTH_SHORT).show();
                }
                goToMain();
                Intent i  = new Intent(getApplicationContext(), Details.class);
                i.putExtra("ID", note.getId());
                startActivity(i);
                
            } else {
                noteTitle.setError("Title can not be blank");
            }
        }else if(item.getItemId() == R.id.delete){
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }

    private void goToMain() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }



    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
