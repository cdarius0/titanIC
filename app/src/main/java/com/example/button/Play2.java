package com.example.button;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Objects;

public class Play2 extends Activity {

    EditText et_;
    String st_;
    private ViewGroup rootLayout;
    private int _xDelta1_0;
    private int _yDelta1_0;
    private ImageView ivs1_0;
    private ImageView ivs1_1;
    private ImageView ivs2_0;
    private ImageView ivs2_1;
    private ImageView ivs2_2;
    private ImageView ivs3_0;
    private static int[][] player2Locations = new int[8][8];
    String st;
    public static void setLocationPlayer2ToZero( int x, int y){
        player2Locations[x][y]=0;
    }
    public static void setLocationPlayer2( int x, int y, int value){
        player2Locations[x][y]=value;
    }
    public static int getLocationPlayer2(int x,int y){
        return player2Locations[x][y];
    }
    @Override
    public void onBackPressed() {
        for(int i=0;i<8;i++)
            for(int j=0;j<8;j++) {
                Play.setLocationPlayer1ToZero(i,j);
                player2Locations[i][j]=0;
            }
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_play2);


        et_ = findViewById(R.id.edittext2);
        Button buttonToGame = findViewById(R.id.bToGame);


        buttonToGame.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                Intent i_ = new Intent(Play2.this, Game.class);
                st = Objects.requireNonNull(getIntent().getExtras()).getString("Value1");
                st_ = et_.getText().toString();
                i_.putExtra("Value2", st_);
                i_.putExtra("Value", st);
                if (confirmPos())
                    if(Play.checkOverlap(player2Locations,getApplicationContext()))
                        if (confirmInput()) {
                        startActivity(i_);
                        finish();
                        }
            }
        });


        rootLayout = findViewById(R.id.playPage2);
        ivs1_0 = rootLayout.findViewById(R.id.ship1_0);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivs1_0.setLayoutParams(layoutParams);
        ivs1_0.setY(1450);
        ivs1_0.setOnTouchListener(new ChoiceTouchListener());

        ivs1_1 = rootLayout.findViewById(R.id.ship1_1);
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivs1_1.setLayoutParams(layoutParams1);
        ivs1_1.setY(1450);
        ivs1_1.setX(120);
        ivs1_1.setOnTouchListener(new ChoiceTouchListener());

        ivs2_0 = rootLayout.findViewById(R.id.ship2_0);
        RelativeLayout.LayoutParams layoutParams20 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivs2_0.setLayoutParams(layoutParams20);
        ivs2_0.setY(1450);
        ivs2_0.setX(240);
        ivs2_0.setOnTouchListener(new ChoiceTouchListener());


        ivs2_1 = rootLayout.findViewById(R.id.ship2_1);
        RelativeLayout.LayoutParams layoutParams21 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivs2_1.setLayoutParams(layoutParams21);
        ivs2_1.setY(1450);
        ivs2_1.setX(360);
        ivs2_1.setOnTouchListener(new ChoiceTouchListener());

        ivs2_2 = rootLayout.findViewById(R.id.ship2_2);
        RelativeLayout.LayoutParams layoutParams22 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivs2_2.setLayoutParams(layoutParams22);
        ivs2_2.setY(1450);
        ivs2_2.setX(480);
        ivs2_2.setOnTouchListener(new ChoiceTouchListener());

        ivs3_0 = rootLayout.findViewById(R.id.ship3_0);
        RelativeLayout.LayoutParams layoutParams30 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivs3_0.setLayoutParams(layoutParams30);
        ivs3_0.setY(1450);
        ivs3_0.setX(600);
        ivs3_0.setOnTouchListener(new ChoiceTouchListener());


    }

    public final class ChoiceTouchListener implements View.OnTouchListener {

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(final View view, MotionEvent event) {
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    _xDelta1_0 = X - lParams.leftMargin;
                    _yDelta1_0 = Y - lParams.topMargin;
                    Play.removePositionFromMatrix((ImageView) view, player2Locations, getApplicationContext());

                    break;
                case MotionEvent.ACTION_UP: {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Play.precisePosition((ImageView) view,player2Locations,getApplicationContext());
                        }
                    }, 50);

                    if( view.getX()>=66.0 && view.getX()<=801.0 && view.getY()>=341.0 && view.getY()+view.getHeight()<=1076.0 )
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Play.overlap((ImageView) view,player2Locations,getApplicationContext());
                            }
                        }, 100);
                }
                break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.leftMargin = X - _xDelta1_0;
                    layoutParams.topMargin = Y - _yDelta1_0;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    view.setLayoutParams(layoutParams);
                    break;
            }
            rootLayout.invalidate();
            return true;
        }
    }

    private boolean confirmPos() {

        if (validatePosition(ivs1_0) && validatePosition(ivs1_1)) {
            if (validatePosition(ivs2_0) && validatePosition(ivs2_1) && validatePosition(ivs2_2)) {
                if (validatePosition(ivs3_0))
                    return true;
                else {
                    Toast.makeText(this, "Big ship is not in the water", Toast.LENGTH_SHORT).show();
                    return false;
                }

            } else {
                Toast.makeText(this, "Medium ship is not in the water", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else Toast.makeText(this, "Small ship is not in the water", Toast.LENGTH_SHORT).show();
        return false;

    }

    private boolean validatePosition(ImageView im) {
        ImageView topleft = rootLayout.findViewById(R.id.A1);
        ImageView bottomright = rootLayout.findViewById(R.id.H8);
        return im.getX() >= topleft.getX() && im.getX() <= bottomright.getX() && im.getY() >= topleft.getY() && im.getY() + im.getHeight() <= bottomright.getY() + bottomright.getHeight();
    }

    public boolean confirmInput() {
        if (!validateUsername2())
            return false;
        et_ = findViewById(R.id.edittext2);

        String input = "Username2: " + et_.getText().toString();

        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
        return true;
    }

    private boolean validateUsername2() {
        et_ = findViewById(R.id.edittext2);
        String username2 = et_.getText().toString().trim();

        if (username2.isEmpty()) {
            et_.setError("Field can't be empty.");
            return false;
        } else if (username2.length() > 15) {
            et_.setError("Fewer than 15 characters.");
            return false;
        } else {
            et_.setError(null);
            return true;
        }

    }



}
