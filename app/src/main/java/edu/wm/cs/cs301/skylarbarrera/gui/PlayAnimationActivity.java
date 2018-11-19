package edu.wm.cs.cs301.skylarbarrera.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


    private ToggleButton mapToggle;
    private ToggleButton SolutionToggle;
    private ToggleButton wallsToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_animation);

        mapToggle = (ToggleButton) findViewById(R.id.toggleMapButton);
        mapToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getApplicationContext(), "Map Toggled On", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Map Toggled Off", Toast.LENGTH_SHORT).show();

                }
            }
        });

       SolutionToggle = (ToggleButton) findViewById(R.id.toggleSolutionButton);
       SolutionToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked) {
                   Toast.makeText(getApplicationContext(), "Solution Toggled On", Toast.LENGTH_SHORT).show();
               }else {
                   Toast.makeText(getApplicationContext(), "Solution Toggled Off", Toast.LENGTH_SHORT).show();

               }

           }
       });

       wallsToggle = (ToggleButton) findViewById(R.id.toggleWalls);
       wallsToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked) {
                   Toast.makeText(getApplicationContext(), "Visible Walls Toggled On", Toast.LENGTH_SHORT).show();
               }else {
                   Toast.makeText(getApplicationContext(), "Visible Walls Toggled Off", Toast.LENGTH_SHORT).show();

               }
           }
       });


    }


    public void switchState2Winning(View view){
        Intent intent = new Intent(this,WinningActivity.class );
        this.startActivity(intent);
    }


    public void scaleMapUp(View view){
        Toast.makeText(this, "Map Scaled Up", Toast.LENGTH_SHORT).show();

    }

    public void scaleMapDown(View view){
        Toast.makeText(this, "Map Scaled Down", Toast.LENGTH_SHORT).show();


    }

    public void toggleMap( boolean isChecked){
        if (isChecked){
            Toast.makeText(this, "Map Toggled On", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Map Toggled Off", Toast.LENGTH_SHORT).show();
        }



    }

    public void toggleSolution(View view){
        Toast.makeText(this, "Solution Toggled", Toast.LENGTH_SHORT).show();

    }

    public void toggleWalls(View view){
        Toast.makeText(this, "Visible Walls Toggled", Toast.LENGTH_SHORT).show();

    }
}
