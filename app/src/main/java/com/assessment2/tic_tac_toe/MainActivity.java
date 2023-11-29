package com.assessment2.tic_tac_toe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{
    //Global variables
    private Button play, scores, exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sets the buttons
        play = findViewById(R.id.playButton);
        scores = findViewById(R.id.scores);
        exit = findViewById(R.id.exit);

        play.setOnClickListener(this);
        scores.setOnClickListener(this);
        exit.setOnClickListener(this);

    }

    //Setting the onClickListener for the buttons
    @Override
    public void onClick(View view) {
        int id = view.getId();

         if(id == R.id.playButton) {
             //start game page
             Intent gamePage = new Intent(MainActivity.this, GameActivity.class);
             startActivity(gamePage);
         }

         if (id == R.id.scores) {
             //go to saved games page
             Intent scoresPage = new Intent(MainActivity.this, ScoresActivity.class);
             startActivity(scoresPage);
         }

         if (id == R.id.exit) {
             //close the application
             finishAffinity();
         }

    }

}


