package com.ankan.android.notepad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditNoteActivity extends AppCompatActivity {

    EditText inputTextView;
    MainActivity mainActivity;
    Integer id;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mainActivity = new MainActivity();
        inputTextView = findViewById(R.id.inputTextView);
        sharedPreferences = this.getSharedPreferences("com.ankan.android.notepad", Context.MODE_PRIVATE);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        inputTextView.setText(mainActivity.notesArray.get(id));

        inputTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = inputTextView.getText().toString();
                SaveNote(id, text);
            }
        });
    }

    public void SaveNote(Integer id, String string) {
        mainActivity.notesArray.set(id, string);
        try {
            sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(mainActivity.notesArray)).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mainActivity.arrayAdapter.notifyDataSetChanged();

    }
}
