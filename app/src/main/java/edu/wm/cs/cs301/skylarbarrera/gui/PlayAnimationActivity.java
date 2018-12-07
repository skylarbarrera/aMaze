package edu.wm.cs.cs301.skylarbarrera.gui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import edu.wm.cs.cs301.skylarbarrera.R;
import edu.wm.cs.cs301.skylarbarrera.generation.CardinalDirection;
import edu.wm.cs.cs301.skylarbarrera.generation.Cells;
import edu.wm.cs.cs301.skylarbarrera.generation.MazeConfiguration;

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
    private int mazeDif;
    private FirstPersonDrawer firstPersonView;
    private MapDrawer mapView;
    private MazePanel panel;
    private MazeConfiguration mazeConfig;
    private boolean showMaze;
    private boolean showSolution;
    private boolean mapMode;

    private int px, py;
    private  int dx,dy;

    private int angle;
    private int walkStep;
    private Cells seenCells;
    private boolean started;

    //private int energy = 240;
    //private int pathLength = 169;
    private int Odom;
    private int Battery;
    private BasicRobot robot;
    private ProgressBar pb;

    @Override
    protected void onStart() {
        super.onStart();
        MazeSingleton ms = MazeSingleton.getInstance();
        mazeConfig = ms.getData();
        driver = getIntent().getStringExtra("Driver");
        robot = new BasicRobot(this);
        //private String[] mazeDriver = new String[]{"Manual", "Wizard", "WallFollower", "Explorer", "Pledge"};

        final WallFollower wf = new WallFollower();
        final Wizard wz = new Wizard(mazeConfig);
        final Explorer ex = new Explorer();
        final Pledge pl = new Pledge();
        switch(driver){
            case "WallFollower":

                wf.setRobot(robot);
                break;
            case "Wizard":

                wz.setRobot(robot);
                break;
            case"Explorer":

                ex.setRobot(robot);
                break;
            case"Pledge":

                pl.setRobot(robot);
                break;


        }


        class proveAsync extends AsyncTask<Integer, Void, Void> {

            protected void onPreExecute() {
            }

            @Override
            protected Void doInBackground(Integer... integers) {
                try {
                    switch(driver){
                        case "WallFollower":

                            wf.drive2Exit();
                            break;
                        case "Wizard":

                            wz.drive2Exit();
                            break;
                        case"Explorer":

                            ex.drive2Exit();
                            break;
                        case"Pledge":

                            pl.drive2Exit();
                            break;


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        new proveAsync().execute();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_animation);

        Gen = getIntent().getStringExtra("Gen");
        mazeDif = getIntent().getIntExtra("MazeDifValue",0);


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

       panel = findViewById(R.id.mazePanelAnim);

        pb = (ProgressBar) findViewById(R.id.energyBar);
        //pb.setProgress((int)robot.getBatteryLevel());

       MazeSingleton ms = MazeSingleton.getInstance();
       mazeConfig = ms.getData();
       started = false;

        showMaze = false;
        showSolution = false;
        mapMode = false;

       seenCells = new Cells(mazeConfig.getWidth() +1, mazeConfig.getHeight()+1);
        setPositionDirectionViewingDirection();
        walkStep = 0;




        if (panel != null){
            startDrawer();
        } else{
            //print warning
        }
        panel.update();



    }


    /**
     * Initializes the drawer for the first person view
     * and the map view and then draws the initial screen
     * for this state.
     */
    protected void startDrawer() {
        firstPersonView = new FirstPersonDrawer(1400,
                1400, Constants.MAP_UNIT,
                Constants.STEP_SIZE, seenCells, mazeConfig.getRootnode()) ;
        mapView = new MapDrawer(seenCells, 15, mazeConfig) ;
        // draw the initial screen for this state
        draw();
    }

    /**
     * Draws the current content on panel to show it on screen.
     */
    protected void draw() {
        if (panel == null) {

            return;
        }
        // draw the first person view and the map view if wanted
        firstPersonView.draw(panel, px, py, walkStep, angle) ;
        if (isInMapMode()) {
            mapView.draw(panel, px, py, angle, walkStep,
                    isInShowMazeMode(),isInShowSolutionMode()) ;
        }
        // update the screen with the buffer graphics
        panel.update() ;
    }

    /**
     * Internal method to set the current position, the direction
     * and the viewing direction to values consistent with the
     * given maze.
     */
    private void setPositionDirectionViewingDirection() {
        // obtain starting position
        int[] start = mazeConfig.getStartingPosition() ;
        setCurrentPosition(start[0],start[1]) ;
        // set current view direction and angle
        angle = 0; // angle matches with east direction,
        // hidden consistency constraint!
        setDirectionToMatchCurrentAngle();
        // initial direction is east, check this for sanity:
        assert(dx == 1);
        assert(dy == 0);
    }

    protected void setCurrentPosition(int x, int y) {
        px = x ;
        py = y ;
    }
    private void setCurrentDirection(int x, int y) {
        dx = x ;
        dy = y ;
    }
    /**
     * Sets fields dx and dy to be consistent with
     * current setting of field angle.
     */
    private void setDirectionToMatchCurrentAngle() {
        setCurrentDirection((int) Math.cos(radify(angle)), (int) Math.sin(radify(angle))) ;
    }

    ////////////////////////////// get methods ///////////////////////////////////////////////////////////////
    protected int[] getCurrentPosition() {
        int[] result = new int[2];
        result[0] = px;
        result[1] = py;
        return result;
    }
    protected CardinalDirection getCurrentDirection() {
        return CardinalDirection.getDirection(dx, dy);
    }
    boolean isInMapMode() {
        return mapMode ;
    }
    boolean isInShowMazeMode() {
        return showMaze ;
    }
    boolean isInShowSolutionMode() {
        return showSolution ;
    }
    public MazeConfiguration getMazeConfiguration() {
        return mazeConfig ;
    }
    final double radify(int x) {
        return x*Math.PI/180;
    }

    /**
     * Helper method for walk()
     * @param dir
     * @return true if there is no wall in this direction
     */
    protected boolean checkMove(int dir) {
        CardinalDirection cd = null;
        switch (dir) {
            case 1: // forward
                cd = getCurrentDirection();
                break;
            case -1: // backward
                cd = getCurrentDirection().oppositeDirection();
                break;
            default:
                throw new RuntimeException("Unexpected direction value: " + dir);
        }
        return !mazeConfig.hasWall(px, py, cd);
    }
    /**
     * Draws and waits. Used to obtain a smooth appearance for rotate and move operations
     */
    private void slowedDownRedraw() {
        draw() ;
        try {
            Thread.sleep(25);
        } catch (Exception e) {
            // may happen if thread is interrupted
            // no reason to do anything about it, ignore exception
        }
    }

    /**
     * Performs a rotation with 4 intermediate views,
     * updates the screen and the internal direction
     * @param dir for current direction, values are either 1 or -1
     */
    synchronized private void rotate(int dir) {
        final int originalAngle = angle;
        final int steps = 4;

        for (int i = 0; i != steps; i++) {
            // add 1/4 of 90 degrees per step
            // if dir is -1 then subtract instead of addition
            angle = originalAngle + dir*(90*(i+1))/steps;
            angle = (angle+1800) % 360;
            // draw method is called and uses angle field for direction
            // information.
            slowedDownRedraw();
        }
        // update maze direction only after intermediate steps are done
        // because choice of direction values are more limited.
        setDirectionToMatchCurrentAngle();
        pb.setProgress((int)robot.getBatteryLevel());
        //logPosition(); // debugging
    }

    public void walkWrap(  int dir){

       walk(dir);
        if (robot.hasStopped()){
            switchState2Losing();
        }


//Start


    }

    public void rotateWrap( int dir){
       rotate(dir);
       if (robot.hasStopped()){
           switchState2Losing();
       }



    }

    /**
     * Moves in the given direction with 4 intermediate steps,
     * updates the screen and the internal position
     * @param dir, only possible values are 1 (forward) and -1 (backward)
     */
    synchronized private void walk(int dir) {
        // check if there is a wall in the way
        if (!checkMove(dir))
            return;
        // walkStep is a parameter of FirstPersonDrawer.draw()
        // it is used there for scaling steps
        // so walkStep is implicitly used in slowedDownRedraw
        // which triggers the draw operation in
        // FirstPersonDrawer and MapDrawer
        for (int step = 0; step != 4; step++) {
            walkStep += dir;
            slowedDownRedraw();
        }
        setCurrentPosition(px + dir*dx, py + dir*dy) ;

        if(isOutside(px ,py )){
            switchState2Winning();
        }
        walkStep = 0; // reset counter for next time
        //logPosition(); // debugging
        pb.setProgress((int)robot.getBatteryLevel());
    }

    /**
     * Checks if the given position is outside the maze
     * @param x coordinate of position
     * @param y coordinate of position
     * @return true if position is outside, false otherwise
     */
    private boolean isOutside(int x, int y) {
        return !mazeConfig.isValidPosition(x, y) ;
    }






    /**
     *Switches State to Winning Screen, passes in relevent info(PathLength & Energy
     *
     */
    public void switchState2Winning(){
        Intent intent = new Intent(this,WinningActivity.class );
        Log.v("Anim - switch2Winning", "Switching State to Winning");
        intent.putExtra("Odom", robot.getOdometerReading());
        intent.putExtra("Battery", robot.getBatteryLevel());
        //intent.putExtra("Odom", Odom);
        int[] start = mazeConfig.getStartingPosition();
        intent.putExtra("short", mazeConfig.getDistanceToExit(start[0], start[1]));

        this.startActivity(intent);
    }

    /**
     *Switches state to Losing Screen, passes in relevent info(PathLength & Energy
     * @param view
     */
    public void switchState2Losing(){
        Intent intent = new Intent(this,LosingActivity.class );
        Log.v("Anim - switch2Losing", "Switching State to Losing");
        intent.putExtra("Odom", robot.getOdometerReading());
        intent.putExtra("Battery", robot.getBatteryLevel());
        int[] start = mazeConfig.getStartingPosition();
        intent.putExtra("short", mazeConfig.getDistanceToExit(start[0], start[1]));
        //intent.putExtra("Energy", energy);
        this.startActivity(intent);
    }

    /**
     * Scales Map Overlap Up by 1
     * @param view
     */
    public void scaleMapUp(View view){
        Toast.makeText(this, "Map Scaled Up", Toast.LENGTH_SHORT).show();
        Log.v("Anim - MapScale", "Map Scaled Up");


    }
    /**
     * Scales Map Overlap Down by 1
     * @param view
     */
    public void scaleMapDown(View view){
        Toast.makeText(this, "Map Scaled Down", Toast.LENGTH_SHORT).show();
        Log.v("Anim - MapScale", "Map Scaled Down");

    }

    /**
     * Pause/Plays Driver Animation depending on state of paused
     * @param view
     */
    public void pauseAnim(View view){
        //ill pause
        if (paused) {
            paused = false;
            Toast.makeText(this, "Animation Paused", Toast.LENGTH_SHORT).show();
            Log.v("Anim - PlayPause", "Animation Paused");
        } else {
            // pausing
            //ill pause again
            paused = true;
            Toast.makeText(this, "Animation UnPaused", Toast.LENGTH_SHORT).show();
            Log.v("Anim - PlayPause", "Animation UnPaused");
        }
    }




}
