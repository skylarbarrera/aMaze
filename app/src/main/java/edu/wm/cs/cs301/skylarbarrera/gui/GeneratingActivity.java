package edu.wm.cs.cs301.skylarbarrera.gui;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import edu.wm.cs.cs301.skylarbarrera.R;

public class GeneratingActivity extends AppCompatActivity {

    ProgressBar genBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generating);

        //TODO: store map config params, based on selection - different intents

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
                    Thread.sleep(100);

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

    public void switchStateGentoPlay(){
        Intent intent = new Intent(this,PlayAnimationActivity.class );
        this.startActivity(intent);
    }
}

