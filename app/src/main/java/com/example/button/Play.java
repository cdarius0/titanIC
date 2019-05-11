package com.example.button;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Play extends Activity {

    EditText et;
    String st;

    private ImageView ivs1_0;
    private ImageView ivs1_1;
    private ImageView ivs2_0;
    private ImageView ivs2_1;
    private ImageView ivs2_2;
    private ImageView ivs3_0;
    private ViewGroup rootLayout;
    private int _xDelta1_0;
    private int _yDelta1_0;

    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_play);






        Button button = findViewById(R.id.backButton);
        Button buttonToPlayer2 = findViewById(R.id.bToPlayer2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });
        et=findViewById(R.id.edittext1);
        buttonToPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Play.this, Play2.class);
                st=et.getText().toString();
                i.putExtra("Value1",st);
                if(confirmPos() )
                    if(confirmInput())
                    {startActivity(i);
                finish();}
               //openPlayer2Activity();
            }
        });


        rootLayout= findViewById(R.id.playPage);
        ivs1_0 = rootLayout.findViewById(R.id.ship10);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivs1_0.setLayoutParams(layoutParams);
        ivs1_0.setY(1450);
        ivs1_0.setOnTouchListener(new ChoiceTouchListener());

        ivs1_1 = rootLayout.findViewById(R.id.ship11);
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivs1_1.setLayoutParams(layoutParams1);
        ivs1_1.setY(1450);
        ivs1_1.setX(120);
        ivs1_1.setOnTouchListener(new ChoiceTouchListener());

        ivs2_0 = rootLayout.findViewById(R.id.ship20);
        RelativeLayout.LayoutParams layoutParams20 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivs2_0.setLayoutParams(layoutParams20);
        ivs2_0.setY(1450);
        ivs2_0.setX(240);
        ivs2_0.setOnTouchListener(new ChoiceTouchListener());

        ivs2_1 = rootLayout.findViewById(R.id.ship21);
        RelativeLayout.LayoutParams layoutParams21 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivs2_1.setLayoutParams(layoutParams21);
        ivs2_1.setY(1450);
        ivs2_1.setX(360);
        ivs2_1.setOnTouchListener(new ChoiceTouchListener());

        ivs2_2 = rootLayout.findViewById(R.id.ship22);
        RelativeLayout.LayoutParams layoutParams22 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivs2_2.setLayoutParams(layoutParams22);
        ivs2_2.setY(1450);
        ivs2_2.setX(480);
        ivs2_2.setOnTouchListener(new ChoiceTouchListener());

        ivs3_0 = rootLayout.findViewById(R.id.ship30);
        RelativeLayout.LayoutParams layoutParams30 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivs3_0.setLayoutParams(layoutParams30);
        ivs3_0.setY(1450);
        ivs3_0.setX(600);
        ivs3_0.setOnTouchListener(new ChoiceTouchListener());


    }

    public final class ChoiceTouchListener implements View.OnTouchListener{

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
            switch(event.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    _xDelta1_0 = X - lParams.leftMargin;
                    _yDelta1_0 = Y - lParams.topMargin;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams =(RelativeLayout.LayoutParams) view.getLayoutParams();
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

    public void openPlayer2Activity(){
        Intent intent = new Intent(this,Play2.class);
     //   if(confirmInput())
        startActivity(intent);
    }
    private boolean confirmPos(){

        if(validatePosition(ivs1_0)&&validatePosition(ivs1_1))
        {
            if(validatePosition(ivs2_0)&& validatePosition(ivs2_1) && validatePosition(ivs2_2))
            {
                if(validatePosition(ivs3_0))
                    return true;
                else
                {
                    Toast.makeText(this,"Big ship is not in the water",Toast.LENGTH_SHORT).show();
                    return false;
                }

            }
            else
            {
                Toast.makeText(this,"Medium ship is not in the water",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else Toast.makeText(this,"Small ship is not in the water",Toast.LENGTH_SHORT).show();
        return false;

    }
    private boolean validatePosition(ImageView im){
        ImageView topleft=rootLayout.findViewById(R.id.A1);
        ImageView bottomright=rootLayout.findViewById(R.id.H8);
        return im.getX() >= topleft.getX() && im.getX() <= bottomright.getX() && im.getY() >= topleft.getY() && im.getY() + im.getHeight() <= bottomright.getY() + bottomright.getHeight();
    }
    public boolean confirmInput() {
        if (!validateUsername1())
            return false;
        et=findViewById(R.id.edittext1);

        String input="Username1: "+et.getText().toString();

        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
        return true;
    }
    private String username1;
    private boolean validateUsername1() {
        et=findViewById(R.id.edittext1);
        username1 = et.getText().toString().trim();

        if (username1.isEmpty()) {
            et.setError("Field can't be empty!!!");
            return false;
        } else if (username1.length() > 15) {
            et.setError("Fewer than 15 characters!!!");
            return false;
        } else {
            et.setError(null);
            return true;
        }

    }
    public void openMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }
}
