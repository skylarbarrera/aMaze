package edu.wm.cs.cs301.skylarbarrera.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import edu.wm.cs.cs301.skylarbarrera.R;

public class AMazeActivity extends AppCompatActivity {

    //TODO: Generating low difficult mazes and storing?
    private int mazeSize;

    private boolean manual;
    private int MazeDriver;

    private Spinner mazeGenSpinner;
    private String[] mazeGen = new String[]{"DFS Alg", "Prim's Alg", "Eller's Alg"};
    private String chosenGen;
   // private int[] MazeGenValue = new String[]{0,1,2};

    private Spinner mazeDriverSpinner;
    private String[] mazeDriver = new String[]{"Manual", "Wizard", "WallFollower", "Explorer", "Pledge's"};
    private String chosenDriver;
    private SeekBar MazeDif;
    int MazeDifValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("AMaze", "ScreenSet" );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.amaze_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mazeGenSpinner = (Spinner)findViewById(R.id.mazeGenSpinner);
        ArrayAdapter<String> mazeGenAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, mazeGen);
        mazeGenSpinner.setAdapter(mazeGenAdapter);
        mazeGenSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenGen = (String)parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Gen Chosen: " + chosenGen, Toast.LENGTH_SHORT).show();
                Log.v("AMaze - MazeGenSpinner", "Generation Method Chosen: " + chosenGen);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mazeDriverSpinner = (Spinner)findViewById(R.id.mazeDriverSpinner);
        ArrayAdapter<String> mazeDriverAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, mazeDriver);
        mazeDriverSpinner.setAdapter(mazeDriverAdapter);
        mazeDriverSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenDriver =(String)parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Driver Chosen: " + chosenDriver, Toast.LENGTH_SHORT).show();
                Log.v("AMaze - MazeDriverSpin", "Driver Chosen: " + chosenDriver);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        MazeDif = (SeekBar)findViewById(R.id.mazeDifSeekBar);
        MazeDif.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                MazeDifValue = progress;
                TextView mazeDifText = (TextView)findViewById(R.id.mazeDifText);
                mazeDifText.setText("Maze Difficulty: " + MazeDifValue);
                Toast.makeText(getApplicationContext(), "Maze Difficulty Set to " + MazeDifValue, Toast.LENGTH_SHORT).show();
                Log.v("AMaze - MazeDifSeekBar", "Maze Difficulty Set to " + MazeDifValue);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }

    /**
     * Switches state to Generating, Passes in all neccessary information
     * @param view
     */
    public void  exploreMazeButton(View view){

        //TODO: Take in config values and pass to GeneratingActivity

        Intent intent = new Intent(this,GeneratingActivity.class );
        intent.putExtra("MazeDifValue",MazeDifValue);
        intent.putExtra("Gen", chosenGen);
        intent.putExtra("Driver", chosenDriver);

        Toast.makeText(getApplicationContext(), "Explore Button Pressed", Toast.LENGTH_SHORT).show();
        Log.v("AMaze - Explore", "Explore Config: Diff - " + MazeDifValue + " Gen - " +chosenGen + "Driver - " + chosenDriver);


        this.startActivity(intent);
    }

    /**
     * Revisit Button
     * Switches state to Generating, passes in relevent info to load maze
     * @param view
     */
    public void  revisitMazeButton(View view){

        //TODO: Take in config values and pass to GeneratingActivity


        Intent intent = new Intent(this,GeneratingActivity.class );
        Toast.makeText(getApplicationContext(), "Revisit Button Pressed", Toast.LENGTH_SHORT).show();
        Log.v("AMaze - Revisit", "Revisit Config: Diff - " + MazeDifValue + " Gen - " +chosenGen + "Driver - " + chosenDriver);


        this.startActivity(intent);
    }








}
