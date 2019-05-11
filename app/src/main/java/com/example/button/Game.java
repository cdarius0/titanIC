package com.example.button;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

public class Game extends Activity {

    TextView tv,tv2;
    String st,st2;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_game);

        tv = findViewById(R.id.user1);
        st = Objects.requireNonNull(getIntent().getExtras()).getString("Value");
        tv.setText(st);

        tv2 = findViewById(R.id.user2);
        st2 = Objects.requireNonNull(getIntent().getExtras()).getString("Value2");
        tv2.setText(st2);
    }
}