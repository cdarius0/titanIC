package com.example.button;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
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
    private static int[][] player1Locations = new int[8][8];
    private static boolean flag_overlap=false;
    @Override
    public void onBackPressed() {
        for(int i=0;i<8;i++)
            for(int j=0;j<8;j++)
                player1Locations[i][j]=0;
        finish();
        openMainActivity();
    }
    public static int getLocationPlayer1(int x,int y){
        return player1Locations[x][y];
    }
    public static void setLocationPlayer1ToZero( int x, int y){
        player1Locations[x][y]=0;
    }

    public static void precisePosition(ImageView boat,int [][]a,Context context) {//tries to put boat exactly on squares
        int size = (int) (boat.getHeight() / 150.0);
        if (boat.getY() <= 1181.0) {//check ship position horizontal if not under the matrix then do the following
            for (int i = 0; i < 7; i++) {
                if (boat.getX() == (66.0 + i * 105.0) || boat.getX() == (66.0 + (i + 1) * 105.0))//ship is on the right spot
                    break;
                if (boat.getX() > (66.0 + i * 105.0) && boat.getX() < (66.0 + (i + 1) * 105.0)) {//ship is in between squares
                    if ((boat.getX() - (66.0 + i * 105.0)) < ((66.0 + (i + 1) * 105.0) - boat.getX()))
                        boat.setX((float) (66.0 + i * 105.0));
                    else boat.setX((float) (66.0 + (i + 1) * 105.0));
                    break;
                }

                if (boat.getX() > 801.0) {//ship is outside to the right of squares
                    boat.setX((float) 801.0);
                    break;
                }
                if (boat.getX() < 66.0) {//ship is outside to the left of squares
                    boat.setX((float) 66.0);
                    break;
                }
            }

            for (int i = 0; i < 7; i++) {//check ship position vertical
                // Toast.makeText(this,"Height from "+boat.getY()+" to "+(boat.getY()+boat.getHeight()),Toast.LENGTH_SHORT).show();
                if (boat.getY() == (341.0 + i * 105.0) || boat.getY() == (341.0 + (i + 1) * 105.0)) {//ship is on the right spot vertically
                    break;
                }
                if (boat.getY() > 341.0 + i * 105.0 && boat.getY() < (341.0 + (i + 1) * 105.0)) {//ship is in between squares vertically
                    if (boat.getY() - (341.0 + i * 105.0) < (341.0 + (i + 1) * 105.0) - boat.getY())
                        boat.setY((float) (341.0 + i * 105.0));
                    else boat.setY((float) (341.0 + (i + 1) * 105.0));

                    break;
                }
                if (boat.getY() + boat.getHeight() > 1076) {//ship is outside and below the field
                    boat.setY((float) (1076 - size * 105.0));
                    break;
                }
                if (boat.getY() < 341.0) {//ship is outside and above the field
                    boat.setY((float) 341.0);
                    break;
                }

            }
            int i = (int) ((boat.getX() - 66.0) / 105.0);
            int j = (int) ((boat.getY() - 341.0) / 105.0);
            for (int k1 = 0; k1 <= size; k1++)
                a[j + k1][i] ++;
//            StringBuilder str = new StringBuilder();
//            for (int i1 = 0; i1 < 8; i1++) {
//                for (int j1 = 0; j1< 8; j1++)
//                    str.append("[").append(a[i1][j1]).append("]");
//                str.append("\n");
//            }
//            Toast.makeText(context, str.toString(), Toast.LENGTH_SHORT/2).show();
//
        }

    }

        private static boolean insideMatrix(ImageView boat){
            return boat.getX() >= 66.0 && boat.getX() <= 801 && boat.getY() >= 341 && boat.getY() <= 1076 && (boat.getX()-66.0)%105==0 && (boat.getY()-341)%105==0;
        }

        public static void removePositionFromMatrix(ImageView boat,int [][]a, Context context){
        if (insideMatrix(boat)){
            int size = (int) (boat.getHeight() / 150.0);
            int i = (int) ((boat.getX() - 66.0) / 105.0);
            int j = (int) ((boat.getY() - 341.0) / 105.0);
            for (int jj = j; jj <= (j+size); jj++)
                a[jj][i]--;
        }
        }


    public static void overlap(ImageView boat, int[][] a,Context context) {//checks if this boat overlaps with any other.
        int size = (int) (boat.getHeight() / 150.0);
        int i = (int) ((boat.getX() - 66.0) / 105.0);
        int j = (int) ((boat.getY() - 341.0) / 105.0);
        int count = 0;
        int jp = 0, ip = 0;
        int flagset = 0;
        int signI = 1;
        int signJ = 1;
        boolean placed = true;
        for (int k1 = 0; k1 <= size; k1++)
            if(a[j+k1][i]>1) {
                placed = false;
//                Toast.makeText(context, "Position Changed", Toast.LENGTH_SHORT).show();
               // flag_overlap=true;
            }
        while (!placed) {
            for (int ii = 0; ii <= ip; ii++)
                for (int jj = 0; jj <= jp; jj++)
                    if (((i + (ii * signI)) >= 0) && ((i + (ii * signI)) < 8) && ((j + (jj * signJ)) >= 0) && ((j + (jj * signJ) + size) < 8))
                        for (int k = 0; k <= size; k++) {
                            if (a[j + jj * signJ + k][i + ii * signI] != 0)
                                break;
                            if (k == size) {
                                jp = jj;
                                ip = ii;
                                for (int k1 = 0; k1 <= size; k1++)
                                    a[j + k1][i] --;
                                boat.setX((float) ((i + ip * signI) * 105.0 + 66.0));
                                boat.setY((float) ((j + jp * signJ) * 105.0 + 341.0));
                                for (int k1 = 0; k1 <= size; k1++)
                                    a[j + k1 + jp * signJ][i + ip * signI]++;

                                return;
                            }
                        }//end of if
            if (count == 3 ) {
                count = 0;
                switch (flagset++) {
                    case 0:
                        signI = -1;
                        break;
                    case 1:
                        signI = 1;
                        signJ = -1;
                        break;
                    case 2:
                        signI = -1;
                        break;

                }
            }
            switch (count++) {
                case 0:
                    ip++;
                    break;
                case 1:
                    ip--;
                    jp++;
                    break;
                case 2:
                    ip++;
                    break;

            }


        }//while end


    }

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
            for(int i=0;i<8;i++)
                for(int j=0;j<8;j++)
                    player1Locations[i][j]=0;
                finish();
                openMainActivity();
            }
        });


        et = findViewById(R.id.edittext1);
        buttonToPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Play.this, Play2.class);
                st = et.getText().toString();
                i.putExtra("Value1", st);
                if (confirmPos())
                    if (checkOverlap(player1Locations,getApplicationContext())) {
                        if(confirmInput()) {
                            startActivity(i);
                            finish();

                        }
                    }
            }
        });


        rootLayout = findViewById(R.id.playPage);
        ivs1_0 = rootLayout.findViewById(R.id.ship10);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivs1_0.setLayoutParams(layoutParams);
        ivs1_0.setY(1450);
        ivs1_0.setX(100);
        ivs1_0.setOnTouchListener(new ChoiceTouchListener());


        ivs1_1 = rootLayout.findViewById(R.id.ship11);
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivs1_1.setLayoutParams(layoutParams1);
        ivs1_1.setY(1450);
        ivs1_1.setX(205);
        ivs1_1.setOnTouchListener(new ChoiceTouchListener());

        ivs2_0 = rootLayout.findViewById(R.id.ship20);
        RelativeLayout.LayoutParams layoutParams20 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivs2_0.setLayoutParams(layoutParams20);
        ivs2_0.setY(1450);
        ivs2_0.setX(310);
        ivs2_0.setOnTouchListener(new ChoiceTouchListener());

        ivs2_1 = rootLayout.findViewById(R.id.ship21);
        RelativeLayout.LayoutParams layoutParams21 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivs2_1.setLayoutParams(layoutParams21);
        ivs2_1.setY(1450);
        ivs2_1.setX(415);
        ivs2_1.setOnTouchListener(new ChoiceTouchListener());

        ivs2_2 = rootLayout.findViewById(R.id.ship22);
        RelativeLayout.LayoutParams layoutParams22 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivs2_2.setLayoutParams(layoutParams22);
        ivs2_2.setY(1450);
        ivs2_2.setX(520);
        ivs2_2.setOnTouchListener(new ChoiceTouchListener());

        ivs3_0 = rootLayout.findViewById(R.id.ship30);
        RelativeLayout.LayoutParams layoutParams30 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivs3_0.setLayoutParams(layoutParams30);
        ivs3_0.setY(1450);
        ivs3_0.setX(625);
        ivs3_0.setOnTouchListener(new ChoiceTouchListener());


    }

    public final class ChoiceTouchListener implements View.OnTouchListener {

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(final View view, MotionEvent event) {
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN://SELECTED ON TOUCH
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    _xDelta1_0 = X - lParams.leftMargin;
                    _yDelta1_0 = Y - lParams.topMargin;
                  //  if(!flag_overlap)
                        removePositionFromMatrix((ImageView) view, player1Locations, getApplicationContext());
                 //   flag_overlap=false;
                    break;
                case MotionEvent.ACTION_UP: {//RELEASE
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            precisePosition((ImageView) view,player1Locations,getApplicationContext());
                        }
                    }, 50);

                     if( view.getX()>=66.0 && view.getX()<=801.0 && view.getY()>=341.0 && view.getY()+view.getHeight()<=1076.0 )
                         handler.postDelayed(new Runnable() {
                             @Override
                             public void run() {
                                overlap((ImageView) view,player1Locations,getApplicationContext());
                             }
                         }, 100);

                }
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
    public static boolean checkOverlap(int [][]a,Context context){
        for(int i=0;i<8;i++)
            for (int j = 0; j < 8; j++)
                if (a[i][j] != 0 && a[i][j] != 1) {
                    Toast.makeText(context, "Boats can't overlap", Toast.LENGTH_SHORT).show();
                    return false;
                }
    return true;
    }


    private boolean confirmPos() {
        findViewById(R.id.playPage);
        if (validatePosition(ivs1_0) && validatePosition(ivs1_1)) {
            if (validatePosition(ivs2_0) && validatePosition(ivs2_1) && validatePosition(ivs2_2)) {
                if (validatePosition(ivs3_0)) {

                    return true;
                }
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
        // Toast.makeText(this,"A1: "+topleft.getX()+" - "+topleft.getY(),Toast.LENGTH_SHORT).show();
        // Toast.makeText(this,"H8: "+bottomright.getX()+" - "+bottomright.getY(),Toast.LENGTH_SHORT).show();

        return im.getX() >= topleft.getX() && im.getX() <= bottomright.getX() && im.getY() >= topleft.getY() && im.getY() + im.getHeight() <= bottomright.getY() + bottomright.getHeight();
    }

    public boolean confirmInput() {
        if (!validateUsername1())
            return false;
        et = findViewById(R.id.edittext1);

        String input = "Username1: " + et.getText().toString();

        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
        return true;
    }

    private boolean validateUsername1() {
        et = findViewById(R.id.edittext1);
        String username1 = et.getText().toString().trim();

        if (username1.isEmpty()) {
            et.setError("Field can't be empty.");
            return false;
        } else if (username1.length() > 15) {
            et.setError("Fewer than 15 characters.");
            return false;
        } else {
            et.setError(null);
            return true;
        }

    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
