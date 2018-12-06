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
import edu.wm.cs.cs301.skylarbarrera.generation.CardinalDirection;
import edu.wm.cs.cs301.skylarbarrera.generation.Cells;
import edu.wm.cs.cs301.skylarbarrera.generation.MazeConfiguration;

public class PlayManuallyActivity extends AppCompatActivity {

    private ToggleButton mapToggle;
    private Switch SolutionToggle;
    private Switch wallsToggle;
    private String driver;
    private String Gen;
    private int mazeDif;
    private FirstPersonDrawer firstPersonView;
    private MapDrawer mapView;
    private MazePanel panel;
    //Controller control;
    private MazeConfiguration mazeConfig;
    private boolean showMaze;
    private boolean showSolution;
    private boolean mapMode;

    int px, py;
    int dx,dy;

    int angle;
    int walkStep;
    Cells seenCells;
    boolean started;




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
                mapMode = true;
                draw();

            }else {
                Toast.makeText(getApplicationContext(), "Map Toggled Off", Toast.LENGTH_SHORT).show();
                Log.v("Manual - MapToggle", "Map Toggled Off");
                mapMode = false;
                draw();

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
                showSolution = true;
                draw();
            }else {
                Toast.makeText(getApplicationContext(), "Solution Toggled Off", Toast.LENGTH_SHORT).show();                Log.v("AMaze - SolutionToggle", "Solution Toggled On");
                Log.v("Manual - SolutionToggle", "Solution Toggled Off");
                showSolution = false;
                draw();



            }

        }
    });

    wallsToggle = (Switch) findViewById(R.id.toggleWallsMan);
       wallsToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                Toast.makeText(getApplicationContext(), "Visible Walls Toggled On", Toast.LENGTH_SHORT).show();
                Log.v("Manual - VisibleToggle", "Visible Walls Toggled On");
                showMaze = true;
                draw();

            }else {
                Toast.makeText(getApplicationContext(), "Visible Walls Toggled Off", Toast.LENGTH_SHORT).show();
                Log.v("Manual - VisibleToggle", "Visible Walls Toggled Off");
                showMaze = false;
                draw();

            }
        }
    });
       panel = (MazePanel) findViewById(R.id.mazePanel4);




        MazeSingleton ms = MazeSingleton.getInstance();
        mazeConfig = ms.getData();
        started = false;

        //panel = this;

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
        //logPosition(); // debugging
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
        walkStep = 0; // reset counter for next time
        //logPosition(); // debugging
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

















    public void start(MazePanel panel) {
        Log.v("ManualActivity", "Start");
        started = true;

    }
    /**
     *Switches State to Winning Screen, passes in relevent info(PathLength & Energy
     *
     */
    public void switchState2Winning(){
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
        adjustMapScale(true);


    }

    /**
     * Scales Map Overlap Down by 1
     * @param view
     */
    public void scaleMapDown(View view){
        Toast.makeText(this, "Map Scaled Down", Toast.LENGTH_SHORT).show();
        Log.v("Manual - Map Scale", "Scaled Down");
        adjustMapScale(false);


    }

    /**
     * Moves Robot Forward, Simulates "button press"
     * @param view
     */
    public void moveUp(View view){
        Toast.makeText(this, "Move Player Up", Toast.LENGTH_SHORT).show();
        walk(1);
        Log.v("Manual - Move", "Move Up");
        draw();
        if (isOutside(px,py)) {
            switchState2Winning();
        }
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
        rotate(1);
        draw();
    }

    /**
     * Rotates Robot Right, Simulates "button press"
     * @param view
     */
    public void moveRight(View view){
        Toast.makeText(this, "Move Player Right", Toast.LENGTH_SHORT).show();
        Log.v("Manual - Move", "Move Right");
        rotate(-1);
        draw();
    }


    /**
     * Adjusts the internal map scale setting for the map view.
     * @param increment if true increase, otherwise decrease scale for map
     */
    private void adjustMapScale(boolean increment) {
        if (increment) {
            mapView.incrementMapScale() ;
        }
        else {
            mapView.decrementMapScale() ;
        }
        draw();
    }







}
