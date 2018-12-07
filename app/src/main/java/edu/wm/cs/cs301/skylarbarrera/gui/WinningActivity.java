package edu.wm.cs.cs301.skylarbarrera.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import edu.wm.cs.cs301.skylarbarrera.R;

public class WinningActivity extends AppCompatActivity {

    private TextView energyConsumed;
    private TextView pathLengthV;
    private TextView shortestLen;
    int energy ;
    int  pathLength;
    String driver;
    int shortest ;


    /**
     * Takes in all passed instnace info and sets variables, displays winning prompt
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winning);
        energy = getIntent().getIntExtra("Battery", energy);
        pathLength = getIntent().getIntExtra("Odom", pathLength);
        shortest = getIntent().getIntExtra("short",shortest);
        energyConsumed = (TextView)findViewById(R.id.energyConsumedText);
        energyConsumed.setText(" ");


        energyConsumed.setText("EnergyConsumed: " + energy);





        pathLengthV = (TextView)findViewById(R.id.pathLengthText);
        pathLengthV.setText("PathLength: " + pathLength);
        shortestLen = (TextView)findViewById(R.id.shortestPathText);
        shortestLen.setText("Shortest Path Length: " + shortest);
    }
}
//TODO: ADD Robot vs User check
//TODO: if robot show energy else everything but
