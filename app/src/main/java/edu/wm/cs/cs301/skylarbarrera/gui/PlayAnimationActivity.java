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

public class PlayAnimationActivity extends AppCompatActivity {

    //TODO: Play/Pause Button
    //TODO: Robot Stopped Method
    //TODO: Add Go2Losing

    private boolean paused;
    private ToggleButton mapToggle;
    private Switch SolutionToggle;
    private Switch wallsToggle;
    private String driver;
    private String Gen;
    private String mazeDif;
    private int energy = 240;
    private int pathLength = 169;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_animation);
        driver = getIntent().getStringExtra("Driver");
        Gen = getIntent().getStringExtra("Gen");
        mazeDif = getIntent().getStringExtra("MazeDifValue");


        mapToggle = (ToggleButton) findViewById(R.id.toggleMapButton);
        mapToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getApplicationContext(), "Map Toggled On", Toast.LENGTH_SHORT).show();
                    Log.v("Anim - MapToggle", "Map Toggled On");

                }else {
                    Toast.makeText(getApplicationContext(), "Map Toggled Off", Toast.LENGTH_SHORT).show();
                    Log.v("Anim - MapToggle", "Map Toggled Off");


                }
            }
        });

       SolutionToggle = (Switch) findViewById(R.id.toggleSolutionButton);
       SolutionToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked) {
                   Toast.makeText(getApplicationContext(), "Solution Toggled On", Toast.LENGTH_SHORT).show();
                   Log.v("Anim - SolutionToggle", "Solution Toggled On");

               }else {
                   Toast.makeText(getApplicationContext(), "Solution Toggled Off", Toast.LENGTH_SHORT).show();
                   Log.v("Anim - SolutionToggle", "Solution Toggled Off");


               }

           }
       });

       wallsToggle = (Switch) findViewById(R.id.toggleWalls);
       wallsToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked) {
                   Toast.makeText(getApplicationContext(), "Visible Walls Toggled On", Toast.LENGTH_SHORT).show();
                   Log.v("Anim - VisibleToggle", "Visible Walls Toggled On");

               }else {
                   Toast.makeText(getApplicationContext(), "Visible Walls Toggled Off", Toast.LENGTH_SHORT).show();
                   Log.v("Anim - VisibleToggle", "Visible Walls Toggled On");


               }
           }
       });


    }

    /**
     *
     * @param view
     */
    public void switchState2Winning(View view){
        Intent intent = new Intent(this,WinningActivity.class );
        Log.v("Anim - switch2Winning", "Switching State to Winning");
        intent.putExtra("Path", pathLength);
        intent.putExtra("Energy", energy);
        this.startActivity(intent);
    }

    /**
     *
     * @param view
     */
    public void switchState2Losing(View view){
        Intent intent = new Intent(this,LosingActivity.class );
        Log.v("Anim - switch2Losing", "Switching State to Losing");

        intent.putExtra("Path", pathLength);
        intent.putExtra("Energy", energy);
        this.startActivity(intent);
    }

    /**
     *
     * @param view
     */
    public void scaleMapUp(View view){
        Toast.makeText(this, "Map Scaled Up", Toast.LENGTH_SHORT).show();
        Log.v("Anim - MapScale", "Map Scaled Up");


    }

    public void scaleMapDown(View view){
        Toast.makeText(this, "Map Scaled Down", Toast.LENGTH_SHORT).show();
        Log.v("Anim - MapScale", "Map Scaled Down");

    }

    public void pauseAnim(View view){
        if (paused) {
            paused = false;
            Toast.makeText(this, "Animation Paused", Toast.LENGTH_SHORT).show();
            Log.v("Anim - PlayPause", "Animation Paused");
        } else {
            paused = true;
            Toast.makeText(this, "Animation UnPaused", Toast.LENGTH_SHORT).show();
            Log.v("Anim - PlayPause", "Animation UnPaused");
        }
    }




}
