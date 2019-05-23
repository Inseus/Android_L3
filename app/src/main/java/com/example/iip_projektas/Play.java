package com.example.iip_projektas;

import android.app.Activity;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class Play extends Activity{

    LeaderboardDatabaseHandler dbhandler;
    private int limit_from = 1;
    private int limit_to = 10;
    private int entered_number=0;
    private int difficulity=0;
    private String username="";
    private int score = 0;
    private int multiplier=0;
    private int random_number=0;
    private int current_turn = 1;
    private int turns = 5;
    private int all_turns=turns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = getSharedPreferences("Settings",Context.MODE_PRIVATE);
        difficulity=sharedPref.getInt("difficulity",0);
        username=sharedPref.getString("username","");
        dbhandler = new LeaderboardDatabaseHandler(this);

        if(difficulity==0)
        {
            limit_from=1;
            multiplier=10;
            limit_to=10;
            turns=5;
            all_turns=turns;
        }
        if(difficulity==1 )
            {
                limit_from=1;
                multiplier=100;
                limit_to=100;
                turns=10;
                all_turns=turns;
        }
        if(difficulity==2 )
        {
            limit_from=1;
            multiplier=1000;
            limit_to=1000;
            turns=15;
            all_turns=turns;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Random ran = new Random();
        random_number = ran.nextInt(limit_to)+limit_from;
        String rem_turn_string = "Liko spėjimų: "+Integer.toString(turns);
        TextView r_turn_field=findViewById(R.id.liko_ejimu);
        r_turn_field.setText(rem_turn_string);

        String range_string = "Rinkitės skaičių nuo  "+Integer.toString(limit_from)+" iki "+ Integer.toString(limit_to);
        TextView range_field=findViewById(R.id.range);
        range_field.setText(range_string);

        String turn_string="Ėjimas: "+ Integer.toString(current_turn);
        TextView turn_field=findViewById(R.id.ejimas);
        turn_field.setText(turn_string);

    }
    private void updateFields()
    {
        String turn_string="Ėjimas: "+ Integer.toString(current_turn);
        TextView turn_field=findViewById(R.id.ejimas);
        turn_field.setText(turn_string);

        String rem_turn_string = "Liko spėjimų: "+Integer.toString(turns);
        TextView r_turn_field=findViewById(R.id.liko_ejimu);
        r_turn_field.setText(rem_turn_string);

    }

    public void checkGuess(int entered_number){
        if(entered_number == random_number)
        {
            Intent intent = new Intent(this,WinActivity.class);
            score = turns*multiplier;
            Bundle bundle = new Bundle();
            bundle.putInt("ejimas",current_turn);
            bundle.putInt("viso",all_turns);
            bundle.putInt("score",score);
            dbhandler.addEntry(username,Integer.toString(score));
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
        if(entered_number > random_number &&(entered_number<=limit_to )&& (entered_number>=limit_from))
        {
            TextView resultField=findViewById(R.id.resultField);
            resultField.setText("Jūs spėjote: "+entered_number+", bet skaičius turi būti mažesnis");
        }
        else if(entered_number < random_number &&(entered_number<=limit_to )&& (entered_number>=limit_from))
        {
            TextView resultField=findViewById(R.id.resultField);
            resultField.setText("Jūs spėjote: "+entered_number+", bet skaičius turi būti didesnis");
        }
        else if((entered_number>limit_to )|| (entered_number<limit_from))
        {
            TextView resultField=findViewById(R.id.resultField);
            resultField.setText("Už bandymą sukčiauti, netekote ėjimo.");
        }
        if(turns==0 && entered_number!=random_number)
        {
            Intent intent = new Intent(this, LoseActivity.class);
            score=0;
            Bundle bundle = new Bundle();
            bundle.putInt("skaicius",random_number);
            bundle.putInt("score",score);
            dbhandler.addEntry(username,Integer.toString(score));
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
        updateFields();
    }

    boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void onGuessClick(View view)
    {
        EditText inputField = findViewById(R.id.guessField);
        String inputString = inputField.getText().toString();
        TextView resultField=findViewById(R.id.resultField);
        if(tryParseInt(inputString)){
            entered_number = Integer.parseInt(inputString);
            checkGuess(entered_number);
            current_turn++;
            turns--;
        }
        else
            resultField.setText("Įveskite tik skaičių, jūs įvedėte: "+inputString);
    }
}
