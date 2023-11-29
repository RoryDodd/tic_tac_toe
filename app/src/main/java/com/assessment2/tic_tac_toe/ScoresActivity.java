package com.assessment2.tic_tac_toe;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class ScoresActivity extends Activity implements View.OnClickListener {

    Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        TextView scores = findViewById(R.id.scoreDisplay);

        exit = findViewById(R.id.exit);
        exit.setOnClickListener(this);

        //open the database
        SQLiteDatabase db = openOrCreateDatabase("game_saves", MODE_PRIVATE, null);

        //select the information in the saved database
        Cursor cr = db.rawQuery("SELECT player,outcome,counter,date FROM saves ORDER BY counter ASC", null);

        //initiate the string builder to display the text
        StringBuilder displayText = new StringBuilder();
        try {
            if (cr.moveToFirst()) {
                do {
                    //go through the populated columns and collect the data
                    String player = cr.getString(cr.getColumnIndex("player"));
                    String outcome = cr.getString(cr.getColumnIndex("outcome"));
                    int counter = cr.getInt(cr.getColumnIndex("counter"));
                    String date = cr.getString(cr.getColumnIndex("date"));

                    // Append the data to the displayText string builder for display
                    displayText.append("Player: ").append(player).append("\n");
                    displayText.append("Outcome: ").append(outcome).append("\n");
                    displayText.append("Counter: ").append(counter).append("\n");
                    displayText.append("Date: ").append(date).append("\n\n");
                } while (cr.moveToNext());

            // if there is no saved data, informs the player
            }else {displayText.append("No Saved Games");}

            //close database
            cr.close();
            db.close();

            scores.setText(displayText.toString());
        }
        //exception catch for no database
        catch(Exception e){
            Toast.makeText(null,"No Saved Games",Toast.LENGTH_SHORT).show();
        }
    }

    //exit back to main page
    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.exit){

            finish();
        }
    }
}
