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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_losing);
        driver = getIntent().getStringExtra("Driver");
        Gen = getIntent().getStringExtra("Gen");
        mazeDif = getIntent().getStringExtra("MazeDifValue");

        deathText = (TextView)findViewById(R.id.deathText);

        deathText.setText("Robot Ran into wall and is damaged");
        deathText.setText("You Ran Out of Energy");
    }



}
