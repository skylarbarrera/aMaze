package edu.wm.cs.cs301.skylarbarrera.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import edu.wm.cs.cs301.skylarbarrera.R;

public class GeneratingActivity extends AppCompatActivity {

    private ProgressBar genBar;
    private String driver;
    private String Gen;
    private String mazeDif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generating);

        //TODO: store map config params, based on selection - different intents
        driver = getIntent().getStringExtra("Driver");
        Gen = getIntent().getStringExtra("Gen");
        mazeDif = getIntent().getStringExtra("MazeDifValue");


        genBar = (ProgressBar)findViewById(R.id.progGenBar);
        new fakeGen().execute();

    }

    class fakeGen extends AsyncTask<Void, Integer, Integer>{

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


            for( int i = 0; i < 100; i++){
                publishProgress(i);
                try{
                    Thread.sleep(50);

                } catch (InterruptedException ie){
                    ie.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            switchStateGentoPlay();


        }
    }

    public void switchStateGentoPlay() {
        boolean bool = driver.equals("Manual");
        Intent intentAnim = new Intent(this, PlayAnimationActivity.class);
        Intent intentManual = new Intent(this, PlayManuallyActivity.class);


            Toast.makeText(getApplicationContext(), "Maze Generated", Toast.LENGTH_SHORT).show();

            if (bool) {

                Log.v("Generation - Switch2Play", "Maze Generated -> Playing Manual");
                intentManual.putExtra("MazeDifValue",mazeDif);
                intentManual.putExtra("Gen", Gen);
                intentManual.putExtra("Driver", driver);
                this.startActivity(intentManual);
            } else {
                Log.v("Generation - Switch2Play", "Maze Generated -> Playing Animated");
                intentAnim.putExtra("MazeDifValue",mazeDif);
                intentAnim.putExtra("Gen", Gen);
                intentAnim.putExtra("Driver", driver);
                this.startActivity(intentAnim);

            }


    }


}

