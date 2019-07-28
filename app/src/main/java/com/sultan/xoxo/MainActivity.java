package com.sultan.xoxo;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button[][] buttons = new Button[3][3];

    private boolean playerOneTurn = true;

    private int roundsCount;

    private int playerOnePoints;
    private int playerTwoPoints;

    private TextView playerOne;
    private TextView playerTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOne = (TextView)findViewById(R.id.play1);
        playerTwo = (TextView)findViewById(R.id.play2);

        for(int row=0; row<3; row++)
        {
            for(int col=0; col<3; col++)
            {
                String butId = "btn"+row+col;
                int rId = getResources().getIdentifier(butId, "id", getPackageName());
                buttons[row][col] = findViewById(rId);
                buttons[row][col].setOnClickListener(this);
            }
        }

        Button resetBtn = (Button)findViewById(R.id.reset);
        resetBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                resetXO();
            }
        });

    }

    @Override
    public void onClick(View v)
    {
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        if(playerOneTurn)
        {
            ((Button) v).setText("X");
        }
        else
        {
            ((Button) v).setText("O");
        }

        roundsCount++;

        if(winner())
        {
            if(playerOneTurn)
                playerOneWinner();
            else
                playerTwoWinner();
        }
        else if(roundsCount == 9)
        {
            draw();
        }
        else
        {
            playerOneTurn = !playerOneTurn;
        }
    }

    private  boolean winner()
    {
        String[][] check = new String[3][3];
        for(int row=0; row<3; row++)
        {
            for(int col=0; col<3; col++)
            {
                check[row][col] = buttons[row][col].getText().toString();
            }
        }
        for(int row=0; row<3; row++)
        {
            for(int col=0; col<3; col++)
            {
                if(check[row][0].equals(check[row][1])
                    && check[row][0].equals(check[row][2])
                    && !check[row][0].equals(""))
                {
                    return true;
                }
            }
        }
        for(int row=0; row<3; row++)
        {
            for(int col=0; col<3; col++)
            {
                if(check[0][row].equals(check[1][row])
                        && check[0][row].equals(check[2][row])
                        && !check[0][row].equals(""))
                {
                    return true;
                }
            }
        }

        if(check[0][0].equals(check[1][1])
                && check[0][0].equals(check[2][2])
                && !check[0][0].equals(""))
        {
            return true;
        }

        if(check[0][2].equals(check[1][1])
                && check[0][2].equals(check[2][0])
                && !check[0][2].equals(""))
        {
            return true;
        }

        return false;
    }

    private void playerOneWinner()
    {
        playerOnePoints++;
        Toast.makeText(this, "Player 1 wins!!", Toast.LENGTH_SHORT).show();
        updatePoints();
        resetRowCol();
    }
    private void playerTwoWinner()
    {
        playerTwoPoints++;
        Toast.makeText(this, "Player 1 wins!!", Toast.LENGTH_SHORT).show();
        updatePoints();
        resetRowCol();
    }
    private void draw()
    {
        Toast.makeText(this, "Draw!!", Toast.LENGTH_SHORT).show();
        resetRowCol();
    }
    private void updatePoints()
    {
        playerOne.setText("Player 1: " + playerOnePoints);
        playerTwo.setText("Player 2: " +  playerTwoPoints);
    }
    private void resetRowCol()
    {
        for(int row=0; row<3; row++)
        {
            for(int col=0; col<3; col++)
            {
                buttons[row][col].setText("");
            }
        }
        roundsCount=0;
        playerOneTurn = true;
    }

    private void resetXO() {
        playerOnePoints = 0;
        playerTwoPoints = 0;
        updatePoints();
        resetRowCol();
    }

}