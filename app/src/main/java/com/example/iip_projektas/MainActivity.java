package com.example.iip_projektas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_linear);
    }

    public void onApieClick(View view)
    {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }
    public void onSettingsClick(View view)
    {
        Intent intent = new Intent(this, Options.class);
        startActivity(intent);
    }
    public void onLeaderboardClick(View view)
    {
        Intent intent = new Intent(this, Leaderboard.class);
        startActivity(intent);
    }
    public void onPlayClick(View view)
    {
        Intent intent = new Intent(this,Play.class);
        startActivity(intent);
    }
}
