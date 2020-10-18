package com.example.connect3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import java.util.Arrays;

public class Intro extends AppCompatActivity {
    Integer coin = 1;//0: empty, 1: red, 2: yellow; coin tells us the color of the coin currently in play

    int[] status = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    boolean gameActive = true;
    String winner = "";


    /**
     * The positionChecker method checks a particular position on the grid to see if it has already been filled or not when any
     * player taps on it. Just in case the position comes up to be empty (i.e., status[positionNumber] == 0), it checks for the
     * color of coin currently in play and places a coin on the position accordingly.
     */

    private void positionChecker(int[] status, ImageView position, int positionNumber) {
        if (status[positionNumber] == 0 && gameActive) {

            if (coin == 1) {
                position.setImageResource(R.drawable.red);
                status[positionNumber] = 1;
                coin = 2;
            } else if (coin == 2) {
                position.setImageResource(R.drawable.yellow);
                status[positionNumber] = 2;
                coin = 1;
            }
        } else if (!gameActive) {
            Toast.makeText(this, "Please start a new match", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please choose a different position", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean boardFull() {
        return status[0] != 0 && status[1] != 0 && status[2] != 0 && status[3] != 0 && status[4] != 0 && status[5] != 0 && status[6] != 0 && status[7] != 0 && status[8] != 0;
    }


    /**
     * The winnerChecker method checks for a winner after every move is played. This method checks for the same coin color
     * across the pre-defined sequences which dictates when a player wins. When a player wins the match, There is a toast
     * telling which color has won.
     */

    private void winnerChecker(int[] status, int[][] winningPositions) {
        for (int[] winningPosition : winningPositions) {

            if (status[winningPosition[0]] == status[winningPosition[1]] &&
                    status[winningPosition[1]] == status[winningPosition[2]] && status[winningPosition[0]] != 0) {
                if (coin == 2) {
                    winner = "Red";
                } else if (coin == 1) {
                    winner = "Yellow";
                }
                Toast.makeText(this, winner + " has won the match!", Toast.LENGTH_SHORT).show();
                gameActive = false;
                Button reset = findViewById(R.id.reset);
                TextView winnerText = findViewById(R.id.winnertextView);
                winnerText.setText(winner + " has won the match!");
                winnerText.setVisibility(View.VISIBLE);
                reset.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * The chance method is an onClick method which is invoked when a person taps on its respective image/button. This method
     * basically does everything a user sees on the app. When the method is invoked, it checks if the position is empty or not
     * and then puts a coin of the player whose turn it is on that position if found empty. After that, this method also checks
     * for a winner every time a move is made.
     */


    public void chance(View view) {
        ImageView position = (ImageView) view; //This ImageView variable holds the position of grid on which the user has clicked
        int positionNumber = Integer.parseInt(position.getTag().toString());
        Log.i("color", Integer.toString(coin));
        positionChecker(status, position, positionNumber);
        winnerChecker(status, winningPositions);
        if (!gameActive) {
            Button reset = findViewById(R.id.reset);
            TextView winnerText = findViewById(R.id.winnertextView);
            Button playAgain = findViewById(R.id.playAgain);
            winnerText.setText(winner + " has won the match!");
            winnerText.setVisibility(View.VISIBLE);
            reset.setVisibility(View.VISIBLE);
            playAgain.setVisibility(View.VISIBLE);
        }
        if (boardFull()) {
            GridLayout grid = findViewById(R.id.grid);
            resetGame(grid);
            Toast.makeText(this, "Standstill! Try Again!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        coin = getIntent().getIntExtra("coin", 1);
    }

    public void newBoard() {
        GridLayout grid = findViewById(R.id.grid);
        for (int j = 0; j < grid.getChildCount(); j++) {
            ImageView child = (ImageView) grid.getChildAt(j);
            child.setImageDrawable(null);
        }

    }

    public void resetVariables() {
        Arrays.fill(status, 0);
        gameActive = true;
        coin = getIntent().getIntExtra("coin", 1);
        winner = "";
    }

    public void resetGame(View view) {
        TextView winnerText = findViewById(R.id.winnertextView);
        Button reset = findViewById(R.id.reset);
        Button playAgain = findViewById(R.id.playAgain);
        winnerText.setVisibility(View.INVISIBLE);
        reset.setVisibility(View.INVISIBLE);
        playAgain.setVisibility(View.INVISIBLE);
        newBoard();
        resetVariables();
    }

    public void playAgain(View view) {
        Intent intent = new Intent(Intro.this, MainActivity.class);
        startActivity(intent);
    }
}