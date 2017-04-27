package com.example.tope0_000.nkucompanionapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by espahr on 4/11/17.
 */

public class GpaCalculator extends AppCompatActivity implements OnClickListener, AdapterView.OnItemSelectedListener{

    // define the SharedPreferences object and editor
    private SharedPreferences savedValues;
    private SharedPreferences.Editor editor;

    //define instance variables that should be saved
    private boolean theme = false;

    private EditText prevCredits;
    private EditText prevGpa;
    private EditText className;
    private EditText classCredits;
    private Button calcGpa;
    private Button addCourse;
    private TextView finalGpa;
    private Spinner classGrade;
    private ArrayList<Class> myClasses = new ArrayList<Class>();
    public boolean darkTheme = false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

        //Get theme saved value
        darkTheme = savedValues.getBoolean("theme", false);

        //Set Theme
        if(darkTheme) {
            this.setTheme(R.style.AppTheme_dark);
        }
        setContentView(R.layout.gpacalc_activity);

        prevCredits = (EditText) findViewById(R.id.prevCredits);
        prevGpa = (EditText) findViewById(R.id.prevGpa);
        classCredits = (EditText) findViewById(R.id.classCredits);
        className = (EditText) findViewById(R.id.className);
        addCourse = (Button) findViewById(R.id.addCourse);
        calcGpa = (Button) findViewById(R.id.calcGpa);
        classGrade = (Spinner) findViewById(R.id.classGrade);
        finalGpa = (TextView) findViewById(R.id.finalGpa);

        addCourse.setOnClickListener(this);
        calcGpa.setOnClickListener(this);
        classGrade.setOnItemSelectedListener(this);

        if(darkTheme) {
            calcGpa.setBackgroundResource(R.drawable.button_dark);
            addCourse.setBackgroundResource(R.drawable.button_dark);
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.grades_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classGrade.setAdapter(adapter);

    }

    @Override
    public void onPause() {
        SharedPreferences.Editor editor = savedValues.edit();
        editor.commit();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addCourse:
                addAnotherCourse();
                break;
            case R.id.calcGpa:
                calculateGpa();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void addAnotherCourse(){
        String name = className.getText().toString();
        String grade = classGrade.getSelectedItem().toString();
        int credits = Integer.valueOf(classCredits.getText().toString());

        Class myClass = new Class(name, grade, credits);
        myClasses.add(myClass);

        CustomListAdapter myListAdapter = new CustomListAdapter(this, myClasses);
        ListView classListView = (ListView) findViewById(R.id.classListView);
        classListView.setAdapter(myListAdapter);
    }

    private void calculateGpa(){
        double oldgpa = Double.valueOf(prevGpa.getText().toString()),
                oldcredits = Double.valueOf(prevCredits.getText().toString()),
                credits, classGrade, classGp, currentGp, Gpa, totalCreds;
        totalCreds = oldcredits;
        currentGp = oldgpa * oldcredits;
        for(int i = 0; i < myClasses.size(); i++){
            credits = myClasses.get(i).getCredits();

            switch (myClasses.get(i).getGrade()) {
                case "A":
                    classGrade = 4.00;
                    break;
                case "A-":
                    classGrade = 3.70;
                    break;
                case "B+":
                    classGrade = 3.33;
                    break;
                case "B":
                    classGrade = 3.00;
                    break;
                case "B-":
                    classGrade =2.70;
                    break;
                case "C+":
                    classGrade = 2.30;
                    break;
                case "C":
                    classGrade = 2.00;
                    break;
                case "C-":
                    classGrade = 1.70;
                    break;
                case "D+":
                    classGrade = 1.30;
                    break;
                case "D":
                    classGrade = 1.00;
                    break;
                case "D-":
                    classGrade = 0.70;
                    break;
                case "F":
                    classGrade = 0.0;
                    break;
                default:
                    classGrade = 0.0;
            }
            totalCreds = totalCreds + credits;
            classGp = classGrade * credits;
            currentGp = currentGp + classGp;
        }
        Gpa = currentGp/totalCreds;
        finalGpa.setText(String.format("%.4g%n", Gpa));
    }
}
