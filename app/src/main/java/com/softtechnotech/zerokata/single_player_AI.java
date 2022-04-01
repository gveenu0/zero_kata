package com.softtechnotech.zerokata;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.softtechnotech.zerokata.addFunc;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Random;

public class single_player_AI extends AppCompatActivity implements View.OnClickListener {
    private int dimention = 3;
    private Button[][] buttons = new Button[dimention][dimention];
    String check = "";
    private boolean p1Turn = true;
    public int p1TotalWins;
    public int p2TotalWins;
    private int runder;
    private int p1Points;
    private int p2Points;
    private TextView p1Score;
    private TextView p2Score;
    char board[][] = {{ '_', '_', '_' }, { '_', '_', '_' }, { '_', '_', '_' }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_dumb);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        p1Score = findViewById(R.id.text_view_p1);
        p2Score = findViewById(R.id.text_view_p2);
        if (addFunc.btn_music) {
            startService(new Intent(single_player_AI.this, ServGot.class));
        }
        for (int i = 0; i < dimention; i++) {
            for (int j = 0; j < dimention; j++) {

                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reset();
            }
        });
        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(single_player_AI.this, singleplayerlevels.class));
            }
        });
    }
    private void Reset() {
        p1Points = 0;
        p2Points = 0;
        ScoreText();
        CleanBoard();
    }
    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals("")) {
            return;
        }
        if (p1Turn) {
            String[] bt_id_name = ((Button) view).getResources().getResourceName(view.getId()).split("_");
            int i = Integer.parseInt(bt_id_name[1])/10;
            int j = Integer.parseInt(bt_id_name[1])%10;
            board[i][j] = 'x';
            ((Button) view).setText("X");
            ((Button)view).setTextColor(getColor(R.color.p1));
            p1Turn = false;
        }
        if (!p1Turn && runder != 4 ) {

            bestMove.Move bestMove = com.softtechnotech.zerokata.bestMove.findBestMove(board);
            int m = bestMove.row;
            int n = bestMove.col;
            board[m][n]='o';
            buttons[m][n].setText("O");
            buttons[m][n].setTextColor(getColor(R.color.cpu));
        }
        runder++;
        if (checkForWin()) {
            if (check == "P1") {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        p1Win();
                    }
                }, 1000);
            } else {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        p2Win();
                    }
                }, 1000);
            }
        } else if (runder == 5) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    draw();
                }
            }, 1000);
        } else {
            p1Turn = true;
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[dimention][dimention];

        for (int i = 0; i < dimention; i++) {
            for (int j = 0; j < dimention; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i < dimention; i++) {
            if (field[i][0].equals(field[i][1]) &&
                    field[i][0].equals(field[i][2]) &&
                    !field[i][0].equals("")) {
                buttons[i][0].setBackground(this.getResources().getDrawable(R.drawable.xowinbtn));
                buttons[i][1].setBackground(this.getResources().getDrawable(R.drawable.xowinbtn));
                buttons[i][2].setBackground(this.getResources().getDrawable(R.drawable.xowinbtn));
                if (field[i][0].equals("X"))
                    check = "P1";
                else
                    check = "AI";
                return true;
            }
        }

        for (int i = 0; i < dimention; i++) {
            if (field[0][i].equals(field[1][i]) &&
                    field[0][i].equals(field[2][i]) &&
                    !field[0][i].equals("")) {
                buttons[0][i].setBackground(this.getResources().getDrawable(R.drawable.xowinbtn));
                buttons[1][i].setBackground(this.getResources().getDrawable(R.drawable.xowinbtn));
                buttons[2][i].setBackground(this.getResources().getDrawable(R.drawable.xowinbtn));
                if (field[0][i].equals("X"))
                    check = "P1";
                else
                    check = "AI";
                return true;
            }
        }

        if (field[0][0].equals(field[1][1]) &&
                field[0][0].equals(field[2][2]) &&
                !field[0][0].equals("")) {
            buttons[0][0].setBackground(this.getResources().getDrawable(R.drawable.xowinbtn));
            buttons[1][1].setBackground(this.getResources().getDrawable(R.drawable.xowinbtn));
            buttons[2][2].setBackground(this.getResources().getDrawable(R.drawable.xowinbtn));
            if (field[0][0].equals("X"))
                check = "P1";
            else
                check = "AI";
            return true;
        }

        if (field[0][2].equals(field[1][1]) &&
                field[0][2].equals(field[2][0]) &&
                !field[0][2].equals("")) {
            buttons[2][0].setBackground(this.getResources().getDrawable(R.drawable.xowinbtn));
            buttons[1][1].setBackground(this.getResources().getDrawable(R.drawable.xowinbtn));
            buttons[0][2].setBackground(this.getResources().getDrawable(R.drawable.xowinbtn));
            if (field[0][2].equals("X"))
                check = "P1";
            else
                check = "AI";
            return true;
        }

        return false;
    }

    private void p1Win() {

        p1Points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        p1TotalWins++;
        ScoreText();
        CleanBoard();
    }

    private void p2Win() {

        p2Points++;
        Toast.makeText(this, "CPU wins!", Toast.LENGTH_SHORT).show();
        p2TotalWins++;
        ScoreText();
        CleanBoard();
    }

    private void draw() {

        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        CleanBoard();
    }

    private void ScoreText() {
        Aihighscore();
        p1Score.setText("P1: " + p1Points);
        p2Score.setText("CPU: " + p2Points);

    }

    private void CleanBoard() {
        for (int i = 0; i < dimention; i++) {
            for (int j = 0; j < dimention; j++) {
                buttons[i][j].setText("");
                board[i][j]='_';
                buttons[i][j].setBackground(this.getResources().getDrawable(R.drawable.xobuttons));
            }
        }
        runder = 0;
        p1Turn = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("runder", runder);
        outState.putInt("p1points", p1Points);
        outState.putInt("p2points", p2Points);
        outState.putInt("p1TotatlWins", p1TotalWins);
        outState.putInt("p1TotatlWins", p1TotalWins);
        outState.putBoolean("p1Turn", p1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        runder = savedInstanceState.getInt("runder");
        p1Points = savedInstanceState.getInt("p1points");
        p2Points = savedInstanceState.getInt("p2points");
        p1Turn = savedInstanceState.getBoolean("p1Turn");
        p1TotalWins = savedInstanceState.getInt("p1TotalWins");
        p2TotalWins = savedInstanceState.getInt("p2TotalWins");
    }

    public void Aihighscore() {

        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("key", p1Points);
        editor.putInt("Ai", p2Points);
        editor.commit();
    }

    @Override
    public void onPause(){
        super.onPause();
        if (addFunc.btn_music) {
            stopService(new Intent(this, ServGot.class));
        }
    }

}
