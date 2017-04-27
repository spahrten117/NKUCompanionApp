package com.example.tope0_000.nkucompanionapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AboutActivity extends AppCompatActivity {

    public boolean darkTheme = false;

    // define the SharedPreferences object and editor
    private SharedPreferences savedValues;
    private SharedPreferences.Editor editor;

    //define instance variables that should be saved
    private boolean theme = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get SharedPreferences object
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

        //Get theme saved value
        darkTheme = savedValues.getBoolean("theme", false);

        //Set Theme
        if(darkTheme) {
            this.setTheme(R.style.AppTheme_dark);
        }
        setContentView(R.layout.activity_about);
    }
}
