package com.example.iip_projektas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.EditText;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;

public class Options extends Activity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_activity);
        loadSettings();
    }

    public void loadSettings()
    {
        SharedPreferences sharedPref = getSharedPreferences("Settings",Context.MODE_PRIVATE);
        String name = sharedPref.getString("name","");
        int age = sharedPref.getInt("age",0);
        int value = sharedPref.getInt("difficulity",0);
        String user = sharedPref.getString("username","");


        Spinner spinner = findViewById(R.id.spinner);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.diff_array, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        EditText nameField = findViewById(R.id.NameField);
        nameField.setText(name);

        EditText userField = findViewById(R.id.UsernameField);
        userField.setText(user);

        EditText ageField = findViewById(R.id.AgeField);
        ageField.setText(age+"");

        spinner.setSelection(value);


    }

    public void onSaveClick(View view)
    {
        EditText nameField = findViewById(R.id.NameField);
        EditText ageField = findViewById(R.id.AgeField);
        EditText userField = findViewById(R.id.UsernameField);
        String user = userField.getText().toString();
        String name = nameField.getText().toString();
        String ageString = ageField.getText().toString();
        int age = Integer.parseInt(ageString);
        Spinner spinner = findViewById(R.id.spinner);
        int difficulity = spinner.getSelectedItemPosition();
        SharedPreferences.Editor sharedPrefEditor = getSharedPreferences("Settings",Context.MODE_PRIVATE).edit();
        sharedPrefEditor.putInt("difficulity",difficulity);
        sharedPrefEditor.putString("name",name);
        sharedPrefEditor.putString("username", user);
        sharedPrefEditor.putInt("age",age);
        sharedPrefEditor.commit();

        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String difficulity = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
