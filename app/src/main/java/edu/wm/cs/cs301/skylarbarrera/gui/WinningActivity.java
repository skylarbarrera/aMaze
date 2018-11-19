package edu.wm.cs.cs301.skylarbarrera.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import edu.wm.cs.cs301.skylarbarrera.R;

public class WinningActivity extends AppCompatActivity {

    private TextView energyConsumed;
    private TextView pathLength;
    private TextView shortestLen;
    int energy = 540;
    int pathLen = 240;
    int shortest = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winning);

        energyConsumed = (TextView)findViewById(R.id.energyConsumedText);
        energyConsumed.setText("EnergyConsumed: " + energy);
        pathLength = (TextView)findViewById(R.id.pathLengthText);
        pathLength.setText("PathLength: " + pathLen);
        shortestLen = (TextView)findViewById(R.id.shortestPathText);
        shortestLen.setText("Shortest Path Length: " + shortest);
    }
}
//TODO: ADD Robot vs User check
//TODO: if robot show energy else everything but
