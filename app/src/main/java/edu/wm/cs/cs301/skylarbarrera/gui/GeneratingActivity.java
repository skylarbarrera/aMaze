package edu.wm.cs.cs301.skylarbarrera.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;

import edu.wm.cs.cs301.skylarbarrera.R;
import edu.wm.cs.cs301.skylarbarrera.generation.MazeConfiguration;
import edu.wm.cs.cs301.skylarbarrera.generation.MazeFactory;
import edu.wm.cs.cs301.skylarbarrera.generation.Order;
import edu.wm.cs.cs301.skylarbarrera.generation.StubOrder;

public class GeneratingActivity extends AppCompatActivity {

    private ProgressBar genBar;
    private String driver;
    private String Gen;
    private int mazeDif;
    private StubOrder.Builder builder;
    public fakeGen fakeG;
    private GeneratingActivity myself = this;
    public static MazeConfiguration mazeBuild;
    private boolean revisit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generating);

        //TODO: store map config params, based on selection - different intents
        driver = getIntent().getStringExtra("Driver");
        Gen = getIntent().getStringExtra("Gen");
        mazeDif = getIntent().getIntExtra("MazeDifValue", 0);
        revisit = getIntent().getBooleanExtra("Revisit", false);
        Log.v("Revisit", "Last mAZE - " + revisit);
        switch(Gen){

            case "Eller":
                builder = StubOrder.Builder.Eller;
                break;
            case "DFS":
                builder = StubOrder.Builder.DFS;
                break;
            case "Kruskal":
                builder = StubOrder.Builder.Kruskal;
                break;
            case "Prim":
                builder = StubOrder.Builder.Prim;
                break;
        }



        genBar = (ProgressBar)findViewById(R.id.progGenBar);
        mazeFactory();

       fakeG = new fakeGen();
       fakeG.execute();

    }

    public void updatedProg(int progress){
        fakeG.updateprod(progress);
    }

    class fakeGen extends AsyncTask<Void, Integer, Integer>{

        public void updateprod(int progress){
            publishProgress(progress);
            onProgressUpdate(progress);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            genBar.setMax(100);


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            genBar.setProgress(values[0]);
        }

        @Override
        protected Integer doInBackground(Void... voids) {

            Order order = new StubOrder(myself);
            ((StubOrder) order).setContext(getFilesDir());
            ((StubOrder) order).setBuilder(builder);
            ((StubOrder) order).setSkillLevel(mazeDif);
            ((StubOrder) order).setPerfect(true);


            String filename = "lastMaze.xml";
            MazeSingleton ms = MazeSingleton.getInstance();

            if (revisit == true){
                filename = getFilesDir().getPath() + "/lastMaze.xml";
                MazeFileReader mazeReader = new MazeFileReader(filename);
                ms.setData(mazeReader.getMazeConfiguration());
                Log.v("Last Maze", "aaaaaa ") ;

            } else {
                if (mazeDif == 0) {
                    filename = "Maze0.xml";
                    filename = getFilesDir().getPath() + "/" + filename;
                    MazeFileReader mazeReader = new MazeFileReader(filename);
                    ms.setData(mazeReader.getMazeConfiguration());
                    Log.v("maze0.xml set", "checking - " + ms.getData().getStartingPosition());

                } else if (mazeDif == 1) {
                    filename = "Maze1.xml";
                    filename = getFilesDir().getPath() + "/" + filename;
                    MazeFileReader mazeReader = new MazeFileReader(filename);
                    ms.setData(mazeReader.getMazeConfiguration());
                    Log.v("maze1.xml set", "checking - " + ms.getData().getStartingPosition());


                } else if (mazeDif == 2) {
                    filename = "Maze2.xml";
                    filename = getFilesDir().getPath() + "/" + filename;
                    MazeFileReader mazeReader = new MazeFileReader(filename);
                    ms.setData(mazeReader.getMazeConfiguration());
                    Log.v("maze2.xml set", "checking - " + ms.getData().getStartingPosition());


                } else if (mazeDif == 3) {
                    filename = "Maze3.xml";
                    filename = getFilesDir().getPath() + "/" + filename;
                    MazeFileReader mazeReader = new MazeFileReader(filename);
                    ms.setData(mazeReader.getMazeConfiguration());
                    Log.v("maze3.xml set", "checking - " + ms.getData().getStartingPosition());


                } else {

                    MazeFactory mazeFact = new MazeFactory();
                    mazeFact.order(order);

                    mazeFact.waitTillDelivered();


                    ms.setData(((StubOrder) order).getMazeConfiguration());

                }
            }
            //mazeBuild = ((StubOrder) order).getMazeConfiguration();


            //MazeFileReader mazeReader = new MazeFileReader(filename);
           // MazeConfiguration mc = mazeReader.getMazeConfiguration();
            //Log.v("aaaaaaa","" + "dist to exit " + mc.getDistanceToExit(2,2));
            //ms.setData(mazeBuild);
            //MazeConfiguration mc = ((StubOrder) order).getMazeConfiguration();
            //MazeFileWriter mazeWriter = new MazeFileWriter();
           // mazeWriter.store(filename, mc.getWidth(),mc.getHeight(),mc.)


            //MazeFileReader mazeReader = new MazeFileReader();


            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            switchStateGentoPlay();


        }
    }


    public void mazeFactory(){






    }

    /**
     * Switches state to Playing depending on Driver Var, passes on relevent info
     * @param view
     */

    public void switchStateGentoPlay() {
        boolean bool = driver.equals("Manual");
        Intent intentAnim = new Intent(this, PlayAnimationActivity.class);
        Intent intentManual = new Intent(this, PlayManuallyActivity.class);


            Toast.makeText(getApplicationContext(), "Maze Generated", Toast.LENGTH_SHORT).show();

            if (bool) {

                Log.v("Generation - Switch2Pla", "Maze Generated -> Playing Manual");
                intentManual.putExtra("MazeDifValue",mazeDif);
                intentManual.putExtra("Gen", Gen);
                intentManual.putExtra("Driver", driver);
                this.startActivity(intentManual);
            } else {
                Log.v("Generation - Switch2Pla", "Maze Generated -> Playing Animated");
                intentAnim.putExtra("MazeDifValue",mazeDif);
                intentAnim.putExtra("Gen", Gen);
                intentAnim.putExtra("Driver", driver);
                this.startActivity(intentAnim);

            }


    }


}

