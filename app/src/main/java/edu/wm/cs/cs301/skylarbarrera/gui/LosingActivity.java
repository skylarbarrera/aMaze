package edu.wm.cs.cs301.skylarbarrera.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import edu.wm.cs.cs301.skylarbarrera.R;

public class LosingActivity extends AppCompatActivity {

    private TextView deathText;
    private String driver;
    private String Gen;
    private String mazeDif;
    private int Odom;
    private int energy;
    private int shorte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_losing);
        //driver = getIntent().getStringExtra("Driver");
        //Gen = getIntent().getStringExtra("Gen");
        //mazeDif = getIntent().getStringExtra("MazeDifValue");
        Odom = getIntent().getIntExtra("Odom", Odom);
        energy = getIntent().getIntExtra("Battery",energy);
        shorte = getIntent().getIntExtra("short", shorte);

        deathText = (TextView)findViewById(R.id.deathText);

        if (energy < 5 ) {
            deathText.setText("You Ran Out of Energy. Odometer reading - " + Odom + " Short Path was " + shorte);
        } else {


            deathText.setText("Robot Ran into wall and is damaged");
        }
    }



}
