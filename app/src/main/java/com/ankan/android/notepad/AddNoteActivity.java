package com.ankan.android.notepad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    EditText inputTextView;
    MainActivity mainActivity;
    SharedPreferences sharedPreferences;
    String inputText = "Add a new note...";
    Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mainActivity = new MainActivity();
        sharedPreferences = this.getSharedPreferences("com.ankan.android.notepad", Context.MODE_PRIVATE);
        inputTextView = findViewById(R.id.inputTextView);
        mainActivity.notesArray.add(inputText);
        id = mainActivity.notesArray.size() - 1;
        if(id == -1) {
            id = 0;
        }
        SaveNote(id, inputText);

        inputTextView.setText(inputText);

        inputTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputText = inputTextView.getText().toString();
                SaveNote(id, inputText);
            }
        });
    }

    public void SaveNote(Integer id, String string) {
        mainActivity.notesArray.set(id, string);
        mainActivity.arrayAdapter.notifyDataSetChanged();
        try {
            sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(mainActivity.notesArray)).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
