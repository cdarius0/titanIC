package com.example.button;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.NullPointerException;
import java.util.Objects;

public class Game extends Activity {

    TextView tv, tv2;
    String st, st2;
    ImageView Aa1,Ba2,Ha8;
    ImageView Aa11,Ba21,Ha81;
    ImageView arrow;
    private ImageView imageWavesOnTouch;

    boolean player1Round=true;//if false it's player2's round
    int countHitsOnPlayer1=0;
    int countHitsOnPlayer2=0;//the first to reach 11 on the other wins as that's how many squares all ships on one side have combined
    private ViewGroup rootLayout;
    @Override
    public void onBackPressed() {
        for(int i=0;i<8;i++)
            for(int j=0;j<8;j++) {
                Play.setLocationPlayer1ToZero(i,j);
                Play2.setLocationPlayer2ToZero(i,j);
            }
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void onMakeMove(int y,int x,int a,int playerToBeHit){//puts the image and returns miss or hit

        imageWavesOnTouch = new ImageView(Game.this);
        if(a==1) {
            imageWavesOnTouch.setImageResource(R.drawable.mini_waves_hit1);
            a=5;
        }
        else if(a==0) {
            imageWavesOnTouch.setImageResource(R.drawable.mini_waves_miss);
            a=-5;
        }
        if(playerToBeHit==2) {
            Play2.setLocationPlayer2(y, x, a);
            imageWavesOnTouch.setX(x*79+919);
        }
        else if(playerToBeHit==1) {
            Play.setLocationPlayer1(y, x, a);
            imageWavesOnTouch.setX(x*79+118);

        }
        imageWavesOnTouch.setY(y*79+184);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(79, 79);
        imageWavesOnTouch.setLayoutParams(layoutParams);
        rootLayout.addView(imageWavesOnTouch);

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        int x1,y1;
        int a;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://touch
                if (countHitsOnPlayer1 < 11 && countHitsOnPlayer2 < 11) {
//                    Toast.makeText(this, x + " - " + y, Toast.LENGTH_SHORT).show();
//                    arrow.setImageResource(R.drawable.white_arrow_right);

                    if (player1Round && x >= 919 && x < 1551 && y >= 184 && y < 816) {//player1's moves
                        x1 = (x - 919) / 79;
                        y1 = (y - 184) / 79;

                        imageWavesOnTouch = new ImageView(Game.this);


                        imageWavesOnTouch.setX(x1 * 79 + 919);
                        imageWavesOnTouch.setY(y1 * 79 + 184);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(79, 79);
                        imageWavesOnTouch.setLayoutParams(layoutParams);
                        rootLayout.addView(imageWavesOnTouch);
                        if (Math.abs(Play2.getLocationPlayer2(y1, x1)) == 5)
                            Toast.makeText(this, "You already hit there", Toast.LENGTH_SHORT / 10).show();
                        else {
                            if (Play2.getLocationPlayer2(y1, x1) == 1) {
                                onMakeMove(y1, x1, 1, 2);
                                countHitsOnPlayer2++;
                                Toast.makeText(this, "That's a hit!\n You get another shot.", Toast.LENGTH_SHORT / 15).show();
                            } else if (Play2.getLocationPlayer2(y1, x1) == 0) {
                                onMakeMove(y1, x1, 0, 2);
                                player1Round = false;
                                Toast.makeText(this, st2 + ", you missed. :(", Toast.LENGTH_SHORT / 15).show();
                                arrow.setImageResource(R.drawable.white_arrow);
                            }
                        }
                    }


                    else if (!player1Round && x >= 118 && x < 750 && y >= 184 && y < 816) {//player2's move
                        x1 = (x - 118) / 79;
                        y1 = (y - 184) / 79;
                        imageWavesOnTouch = new ImageView(Game.this);


                        imageWavesOnTouch.setX(x1 * 79 + 118);
                        imageWavesOnTouch.setY(y1 * 79 + 184);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(79, 79);
                        imageWavesOnTouch.setLayoutParams(layoutParams);
                        rootLayout.addView(imageWavesOnTouch);
                        if (Math.abs(Play.getLocationPlayer1(y1, x1)) == 5)
                            Toast.makeText(this, "You already hit there", Toast.LENGTH_SHORT / 10).show();
                        else {

                            if (Play.getLocationPlayer1(y1, x1) == 1) {
                                onMakeMove(y1, x1, 1, 1);
                                countHitsOnPlayer1++;
                                Toast.makeText(this, "That's a hit!\n You get another shot.", Toast.LENGTH_SHORT / 15).show();
                            }
                            else if (Play2.getLocationPlayer2(y1, x1) == 0) {
                                onMakeMove(y1, x1, 0, 1);
                                player1Round = true;
                                Toast.makeText(this, st + ", you missed. :(", Toast.LENGTH_SHORT / 15).show();
                                arrow.setImageResource(R.drawable.white_arrow_right);
                            }

                        }



                    }//player 2 moves
                }

                else if (countHitsOnPlayer1 == 11)
                    for(int i=0;i<10;i++)
                    Toast.makeText(this, "Congratulations " + st2 + "\n you sank all of your enemy's ships!",
                            Toast.LENGTH_LONG * 100).show();
                else if (countHitsOnPlayer2 == 11)
                    for(int i=0;i<10;i++)
                        Toast.makeText(this, "Congratulations " + st + "\n you sank all of your enemy's ships!",
                            Toast.LENGTH_LONG * 100).show();
//
//        Aa1=rootLayout.findViewById(R.id.A1);
//        Ba2=rootLayout.findViewById(R.id.B2);
//        Ha8=rootLayout.findViewById(R.id.H8);
//
//        Aa11=rootLayout.findViewById(R.id.A11);
//        Ba21=rootLayout.findViewById(R.id.B21);
//        Ha81=rootLayout.findViewById(R.id.H81);
//        for(int i=0;i<10;i++)
//        Toast.makeText(this, "Height:"+Aa1.getHeight()+"Width:"+Aa1.getWidth()+"\nA1:"+Aa1.getX()+" - "
//                +Aa11.getY()+"\nB2:"+Ba2.getX()+" - "+Ba2.getY()
//                +"\nH8:"+Ha8.getX()+" - "+Ha8.getY(), Toast.LENGTH_LONG).show();
//

                break;
            case MotionEvent.ACTION_MOVE://move
                break;
            case MotionEvent.ACTION_UP://release
                break;
        }
        return false;
    }

    @SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_game);
        rootLayout = findViewById(R.id.gamePage);

//        imageWavesTouched.setX(118);
//        imageWavesTouched.setY(184);
//        RelativeLayout.LayoutParams params = new RelativeLayout
//                .LayoutParams(LayoutParams.MATCH_PARENT, 79);
//        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        imageWavesTouched.setLayoutParams(params);
//        params.setMargins(0,0,0,0);



        tv = findViewById(R.id.user1);
        st = Objects.requireNonNull(getIntent().getExtras()).getString("Value");
        tv.setText(st);

        tv2 = findViewById(R.id.user2);
        st2 = Objects.requireNonNull(getIntent().getExtras()).getString("Value2");
        tv2.setText(st2);
        Toast.makeText(this, st+"'s round", Toast.LENGTH_SHORT/4).show();
        arrow=(ImageView) findViewById(R.id.ArrowTurn);
        arrow.setImageResource(R.drawable.white_arrow_right);

//
//        imageWavesOnTouch = new ImageView(this);
//        RelativeLayout.LayoutParams params = new RelativeLayout
//                .LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//        imageWavesOnTouch.setImageResource(R.drawable.mini_waves_hit);
//        imageWavesOnTouch.setMaxHeight(30);
//        imageWavesOnTouch.setMaxWidth(30);
//        imageWavesOnTouch.setLayoutParams(params);
//        imageWavesOnTouch.setMaxWidth(30);
//        imageWavesOnTouch.setMaxHeight(30);
//        imageWavesOnTouch.setX(118);
//        imageWavesOnTouch.setY(184);
//        rootLayout.addView(imageWavesOnTouch);


    }
}