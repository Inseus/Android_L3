package com.example.iip_projektas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class WinActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        Bundle bundle = getIntent().getExtras();
        int c_turn = bundle.getInt("ejimas");
        int turns = bundle.getInt("viso");
        int score = bundle.getInt("score");
        int highscore=0;
        SharedPreferences sharedPref = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        highscore=sharedPref.getInt("highscore",0);
        int tempscore = highscore;
        if (score>highscore)
        {
            highscore=score;
            SharedPreferences.Editor sharedPrefEditor = getSharedPreferences("Settings",Context.MODE_PRIVATE).edit();
            sharedPrefEditor.putInt("highscore",highscore);
            sharedPrefEditor.commit();
        }
        SharedPreferences.Editor sharedPrefEditor = getSharedPreferences("Settings",Context.MODE_PRIVATE).edit();
        sharedPrefEditor.putInt("score",score);
        sharedPrefEditor.commit();

        TextView resField = findViewById(R.id.winText);

        resField.setText("Pergalė! Iš "+turns+ " ėjimų, jūs pasiekėte tik per "+ c_turn);

        TextView scoreField = findViewById(R.id.winScore);
        scoreField.setText("Surinkti taškai: "+score);
        TextView hiField = findViewById(R.id.winHi);
        if(score >= highscore){
            hiField.setText("Sveikinimai! Jūs pagerinote rekordą: "+highscore);
        }
        else
            hiField.setText("Visų geriausias: "+tempscore);
    }

    public void onWinClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
