package com.assessment2.tic_tac_toe;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SaveActivity extends Activity implements View.OnClickListener {
    //Global variables
    private TextView saveDisplay;
    private EditText nameInput;
    private Button save, playAgain, exit;
    private String winner;
    private int turnCounter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        nameInput = findViewById(R.id.nameInput);

        //stores the winner and counter from the played game
        turnCounter = getIntent().getIntExtra("turnCounter", 0);
        winner = getIntent().getStringExtra("winner");

        save = findViewById(R.id.save);
        playAgain = findViewById(R.id.playAgain);
        exit = findViewById(R.id.exit);

        save.setOnClickListener(this);
        playAgain.setOnClickListener(this);
        exit.setOnClickListener(this);

        saveDisplay = findViewById(R.id.saveDisplay);

        saveDisplay.setText(winner + " in " + turnCounter + " moves");



    }

    //Setting the onClickListener for the buttons
    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.save) {
            try {
                //creates the database object to write to
                DatabaseManager databaseManager = new DatabaseManager(this);
                SQLiteDatabase db = databaseManager.getWritableDatabase();

                String name = nameInput.getText().toString();

                //assigns the values for the database columns
                ContentValues values = new ContentValues();
                values.put("player", name);
                values.put("outcome", winner);
                values.put("counter", turnCounter);
                values.put("date", currentTime());

                //inserts the values into the database
                long newRowId = db.insert("saves", null, values);
                if (newRowId != -1) {
                    //lets the player know data is saved or not
                    Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Data not saved", Toast.LENGTH_SHORT).show();
                }

                //closes the database
                db.close();
                databaseManager.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (id == R.id.playAgain) {

            Intent scoresPage = new Intent(SaveActivity.this, GameActivity.class);
            startActivity(scoresPage);
            finish();
        }

        if (id == R.id.exit) {
            finish();
        }
    }

    //set the datetime and format to be saved to the database
    private String currentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy h:mm a", Locale.getDefault());
        return sdf.format(new Date());
    }
}