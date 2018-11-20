package edu.wm.cs.cs301.skylarbarrera.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import edu.wm.cs.cs301.skylarbarrera.R;

public class WinningActivity extends AppCompatActivity {

    private TextView energyConsumed;
    private TextView pathLengthV;
    private TextView shortestLen;
    String energy ;
    String pathLength;
    String driver;
    int shortest = 25;


    /**
     * Takes in all passed instnace info and sets variables, displays winning prompt
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winning);
        energy = getIntent().getStringExtra("Energy");
        pathLength = getIntent().getStringExtra("Path");
        driver = getIntent().getStringExtra("Driver");
        energyConsumed = (TextView)findViewById(R.id.energyConsumedText);
        energyConsumed.setText(" ");

        if (!driver.equals("Manual")){
            energyConsumed.setText("EnergyConsumed: " + energy);
        }




        pathLengthV = (TextView)findViewById(R.id.pathLengthText);
        pathLengthV.setText("PathLength: " + 678);
        shortestLen = (TextView)findViewById(R.id.shortestPathText);
        shortestLen.setText("Shortest Path Length: " + shortest);
    }
}
//TODO: ADD Robot vs User check
//TODO: if robot show energy else everything but
