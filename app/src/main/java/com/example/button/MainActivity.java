package com.example.button;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Button rulesButton = findViewById(R.id.rulesButton);
        rulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRules();
            }

        });

        Button playButton = findViewById(R.id.button);
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                openPlay();
            }
        });

    }
    public void openPlay(){
        Intent intent= new Intent(this, Play.class);
        startActivity(intent);
    }

    public void openRules(){
        Intent intent= new Intent(this, Rules.class);
        startActivity(intent);
    }
}
