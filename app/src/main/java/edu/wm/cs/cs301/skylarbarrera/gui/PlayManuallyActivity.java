package edu.wm.cs.cs301.skylarbarrera.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import edu.wm.cs.cs301.skylarbarrera.R;
import edu.wm.cs.cs301.skylarbarrera.generation.MazeConfiguration;

public class PlayManuallyActivity extends AppCompatActivity {

    private ToggleButton mapToggle;
    private Switch SolutionToggle;
    private Switch wallsToggle;
    private String driver;
    private String Gen;
    private int mazeDif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_manually);

        driver = getIntent().getStringExtra("Driver");
        Gen = getIntent().getStringExtra("Gen");
        mazeDif = getIntent().getIntExtra("MazeDifValue", 0);

    //TODO: Add map scale, new UI & & Toasts

    mapToggle =  findViewById(R.id.toggleMapButtonMan);
        mapToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                Toast.makeText(getApplicationContext(), "Map Toggled On", Toast.LENGTH_SHORT).show();
                Log.v("Manual - MapToggle", "Map Toggled On");

            }else {
                Toast.makeText(getApplicationContext(), "Map Toggled Off", Toast.LENGTH_SHORT).show();
                Log.v("Manual - MapToggle", "Map Toggled Off");

            }
        }
    });

    SolutionToggle = (Switch) findViewById(R.id.toggleSolutionButtonMan);
       SolutionToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                Toast.makeText(getApplicationContext(), "Solution Toggled On", Toast.LENGTH_SHORT).show();
                Log.v("Manual - SolutionToggle", "Solution Toggled On");
            }else {
                Toast.makeText(getApplicationContext(), "Solution Toggled Off", Toast.LENGTH_SHORT).show();                Log.v("AMaze - SolutionToggle", "Solution Toggled On");
                Log.v("Manual - SolutionToggle", "Solution Toggled Off");



            }

        }
    });

    wallsToggle = (Switch) findViewById(R.id.toggleWallsMan);
       wallsToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                Toast.makeText(getApplicationContext(), "Visible Walls Toggled On", Toast.LENGTH_SHORT).show();
                Log.v("Manual - VisibleToggle", "Solution Toggled On");

            }else {
                Toast.makeText(getApplicationContext(), "Visible Walls Toggled Off", Toast.LENGTH_SHORT).show();
                Log.v("Manual - VisibleToggle", "Solution Toggled Off");

            }
        }
    });

        MazeSingleton ms = MazeSingleton.getInstance();
        MazeConfiguration mc = ms.getData();
       int[] starter =  mc.getStartingPosition();
       Log.v("starter check","yeet - " +starter[0] + " " + starter[1] );
}

    /**
     *Switches State to Winning Screen, passes in relevent info(PathLength & Energy
     * @param view
     */
    public void switchState2Winning(View view){
        Intent intent = new Intent(this,WinningActivity.class );
        Toast.makeText(getApplicationContext(), "Switching State to Winning", Toast.LENGTH_SHORT).show();
        Log.v("Manual - switchState", "Switching State to Winning");
        intent.putExtra("MazeDifValue",mazeDif);
        intent.putExtra("Gen", Gen);
        intent.putExtra("Driver", driver);

        this.startActivity(intent);
    }

    /**
     * Scales Map Overlap Up by 1
     * @param view
     */
    public void scaleMapUp(View view){
        Toast.makeText(this, "Map Scaled Up", Toast.LENGTH_SHORT).show();
        Log.v("Manual - Map Scale", "Scaled Up");


    }

    /**
     * Scales Map Overlap Down by 1
     * @param view
     */
    public void scaleMapDown(View view){
        Toast.makeText(this, "Map Scaled Down", Toast.LENGTH_SHORT).show();
        Log.v("Manual - Map Scale", "Scaled Down");


    }

    /**
     * Moves Robot Forward, Simulates "button press"
     * @param view
     */
    public void moveUp(View view){
        Toast.makeText(this, "Move Player Up", Toast.LENGTH_SHORT).show();
        Log.v("Manual - Move", "Move Up");
    }
    /**
     * Moves Robot Backward, Simulates "button press"
     * @param view
     */
    public void moveDown(View view){
        Toast.makeText(this, "Move Player Down", Toast.LENGTH_SHORT).show();
        Log.v("Manual - Move", "Move Down");
    }
    /**
     * Rotates Robot Left, Simulates "button press"
     * @param view
     */
    public void moveLeft(View view){
        Toast.makeText(this, "Move Player Left", Toast.LENGTH_SHORT).show();
        Log.v("Manual - Move", "Move Left");
    }

    /**
     * Rotates Robot Right, Simulates "button press"
     * @param view
     */
    public void moveRight(View view){
        Toast.makeText(this, "Move Player Right", Toast.LENGTH_SHORT).show();
        Log.v("Manual - Move", "Move Right");
    }

}
