package com.assessment2.tic_tac_toe;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends Activity implements View.OnClickListener{
    //Global Variables
    private TextView playerView;
    private TextView winView;
    private String winner;
    private int turnCounter = 0;
    private final char[] tokens = {'X', 'O'};
    private char humanPlayer, computerPlayer;
    final private TextView[] allSquares = new TextView[9];
    private boolean endGame = false;
    Button exit;
    final private int[][] winningCombinations = {
            {0, 1, 2},  // Top row
            {3, 4, 5},  // Middle row
            {6, 7, 8},  // Bottom row
            {0, 3, 6},  // Left column
            {1, 4, 7},  // Middle column
            {2, 5, 8},  // Right column
            {0, 4, 8},  // Top-left to bottom-right diagonal
            {2, 4, 6}   // Top-right to bottom-left diagonal
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        exit = findViewById(R.id.exit);
        exit.setOnClickListener(this);

        playerView = findViewById(R.id.playerView);

        winView = findViewById(R.id.winnerView);
        winView.setText("");

        //initiate all playable squares
        allSquares[0] = findViewById(R.id.square1);
        allSquares[1] = findViewById(R.id.square2);
        allSquares[2] = findViewById(R.id.square3);
        allSquares[3] = findViewById(R.id.square4);
        allSquares[4] = findViewById(R.id.square5);
        allSquares[5] = findViewById(R.id.square6);
        allSquares[6] = findViewById(R.id.square7);
        allSquares[7] = findViewById(R.id.square8);
        allSquares[8] = findViewById(R.id.square9);

        //Randomly generate the player as X or O and the computer the other
        Random tokenSelect = new Random();
        int randomIndex = tokenSelect.nextInt(2);
        humanPlayer = tokens[randomIndex];
        computerPlayer = tokens[1 - randomIndex];

        playerView.setText("You are " + humanPlayer);

    }

    public void squareSelector(View view) {
        //Gets the id of the square being selected
        TextView selectSquare = findViewById(view.getId());

        /*if statement that calls all methods to play the game, populate square, computer to take
        a turn, increment the turnCounter and check the win conditions*/
        if (selectSquare.getText().toString().isEmpty()) {

            playToken(selectSquare);

            nextMove();

            turnCounter++;

            winView.setText("Number of turns:  " + turnCounter);

            checkWin();

            /*Delays the end of the game to see the results for 2 seconds before taking the player
            to the save page*/
            if (endGame) {

                final Handler handler = new Handler();
                handler.postDelayed(() -> {

                    Intent saveGame = new Intent(GameActivity.this, SaveActivity.class);

                    //store the winner and counter to use in the save page
                    saveGame.putExtra("turnCounter", turnCounter);
                    saveGame.putExtra("winner", winner);
                    startActivity(saveGame);
                    finish();

                }, 2000);
            }
        }
        //If the player selects a square that is already populated, tells the player to try again
        else playerView.setText("Try Again! You are " + humanPlayer);

    }

    /*takes in the TextView being selected, depending on the turnCounter being even or odd,
    sets the TextView to either X or O*/
    public void playToken(TextView square) {
        if (turnCounter % 2 == 0) square.setText(String.valueOf(humanPlayer));
        square.setTextColor(Color.parseColor("#2814FF"));
        checkWin();
    }

    //Method to check the winning conditions
    public void checkWin() {

        for (int[] combination : winningCombinations) {
            int firstIndex = combination[0];
            int secondIndex = combination[1];
            int thirdIndex = combination[2];

            String firstSquare = allSquares[firstIndex].getText().toString();
            String secondSquare = allSquares[secondIndex].getText().toString();
            String thirdSquare = allSquares[thirdIndex].getText().toString();

            if (firstSquare.equals(String.valueOf(humanPlayer)) &&
                    secondSquare.equals(String.valueOf(humanPlayer)) &&
                    thirdSquare.equals(String.valueOf(humanPlayer))) {
                winView.setText("You Win\n" + "In " + turnCounter + " moves");
                playerView.setText("Game Over");
                winner = "You Win";
                endGame = true;
                break; // Exit the loop once a win is detected
            } else if (firstSquare.equals(String.valueOf(computerPlayer)) &&
                    secondSquare.equals(String.valueOf(computerPlayer)) &&
                    thirdSquare.equals(String.valueOf(computerPlayer))) {
                winView.setText("You Lose\n" + "In " + turnCounter + " moves");
                playerView.setText("Game Over");
                winner = "You Lose";
                endGame = true;
                break; // Exit the loop once a win is detected

            }
        }

        //Detects if all 9 possible moves have been used and declares a draw
        if (turnCounter == 9) {
            winView.setText("It's a Draw");
            playerView.setText("Game Over");
            winner = "It's a Draw";
            endGame = true;
        }

    }

    //Method for the computer to either block the player from winning or select a random square
    public void nextMove() {
        Random randomSquare = new Random();
        int randomIndex;
        boolean winDetected = false;

        /*Reuse the winning conditions, however checks if the human player has tokens in them and is
        about to win and blocks them, if not, will select a random square*/
        for (int[] combination : winningCombinations) {
            int firstIndex = combination[0];
            int secondIndex = combination[1];
            int thirdIndex = combination[2];

            String firstSquare = allSquares[firstIndex].getText().toString();
            String secondSquare = allSquares[secondIndex].getText().toString();
            String thirdSquare = allSquares[thirdIndex].getText().toString();

            if (firstSquare.isEmpty() &&
                    secondSquare.equals(String.valueOf(humanPlayer)) &&
                    thirdSquare.equals(String.valueOf(humanPlayer))) {
                allSquares[firstIndex].setText(String.valueOf(computerPlayer));
                allSquares[firstIndex].setTextColor(Color.parseColor("#FC0F3E"));
                turnCounter++;
                winDetected = true;
                break; // Exit the loop once a win is detected
            } else if (firstSquare.equals(String.valueOf(humanPlayer)) &&
                    secondSquare.isEmpty() &&
                    thirdSquare.equals(String.valueOf(humanPlayer))) {
                allSquares[secondIndex].setText(String.valueOf(computerPlayer));
                allSquares[secondIndex].setTextColor(Color.parseColor("#FC0F3E"));
                turnCounter++;
                winDetected = true;
                break; // Exit the loop once a win is detected

            } else if (firstSquare.equals(String.valueOf(humanPlayer)) &&
                    secondSquare.equals(String.valueOf(humanPlayer)) &&
                    thirdSquare.isEmpty()) {
                allSquares[thirdIndex].setText(String.valueOf(computerPlayer));
                allSquares[thirdIndex].setTextColor(Color.parseColor("#FC0F3E"));
                turnCounter++;
                winDetected = true;
                break; // Exit the loop once a win is detected

            }


        }
        //Will create an array of empty squares available to the computer
        if(!winDetected){
            List<Integer> emptySquareIndices = new ArrayList<>();
            for (int i = 0; i < allSquares.length; i++) {
                String squareValue = allSquares[i].getText().toString();
                if (squareValue.equals("")) {
                    emptySquareIndices.add(i);
                }
            }
            //Will randomly select a square from the available empty squares
            if (!emptySquareIndices.isEmpty()) {
                randomIndex = emptySquareIndices.get(randomSquare.nextInt(emptySquareIndices.size()));
                allSquares[randomIndex].setText(String.valueOf(computerPlayer));
                allSquares[randomIndex].setTextColor(Color.parseColor("#FC0F3E"));
                turnCounter++;
            }
        }

    }

    //Exit button
    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.exit){
            finish();
        }
    }
}