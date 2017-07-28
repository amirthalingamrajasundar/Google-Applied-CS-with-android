package com.example.home.scarnes_dice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int USER_SCORE;
    private int COMP_SCORE;
    private int TURN_SCORE;
    private String temp = new String();


    private Random rm = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        USER_SCORE = 0;
        COMP_SCORE = 0;
        TURN_SCORE = 0;

        updateTextView();

    }

    public void updateTextView(){

        TextView usertextView = (TextView)findViewById(R.id.user_score);
        TextView comptextView = (TextView)findViewById(R.id.comp_score);
        TextView turnview = (TextView)findViewById(R.id.turn_score);

        temp = getResources().getString(R.string.user_score) + USER_SCORE;
        usertextView.setText(temp);
        temp = getResources().getString(R.string.comp_score) + COMP_SCORE;
        comptextView.setText(temp);
        temp = getResources().getString(R.string.turn_score) + TURN_SCORE;
        turnview.setText(temp);
    }

    public void updateImageView(int x){

        ImageView image = (ImageView) findViewById(R.id.imageView);

        switch(x){
            case 1:
                image.setBackgroundResource(R.drawable.dice1);
                break;
            case 2:
                image.setBackgroundResource(R.drawable.dice2);
                break;
            case 3:
                image.setBackgroundResource(R.drawable.dice3);
                break;
            case 4:
                image.setBackgroundResource(R.drawable.dice4);
                break;
            case 5:
                image.setBackgroundResource(R.drawable.dice5);
                break;
            case 6:
                image.setBackgroundResource(R.drawable.dice6);
                break;
            default:
                image.setBackgroundResource(R.drawable.dice1);
                break;


        }
    }

    public void roll(View v){
        int temp;
        temp = (int)rm.nextInt(6);
        temp = temp + 1;

        if (temp == 1){
            TURN_SCORE = 0;
            updateTextView();
            updateImageView(1);
            computerTurn();
        }
        if(temp != 1){
            TURN_SCORE = TURN_SCORE + temp;
            updateTextView();
            updateImageView(temp);
        }
        if((USER_SCORE >= 100)||(COMP_SCORE>=100)){
            if(USER_SCORE>=100){
                Toast.makeText(this,"you won",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"computer won",Toast.LENGTH_LONG).show();
            }
            USER_SCORE = 0;
            COMP_SCORE = 0;
            TURN_SCORE = 0;

        }

    }

    public void hold(View v){
        USER_SCORE = USER_SCORE + TURN_SCORE;
        TURN_SCORE = 0;
        updateTextView();
        computerTurn();
        TURN_SCORE = 0;
        updateTextView();
        if((USER_SCORE >= 100)||(COMP_SCORE>=100)){
            if(USER_SCORE>=100){
                Toast.makeText(this,"you won",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"computer won",Toast.LENGTH_LONG).show();
            }
            USER_SCORE = 0;
            COMP_SCORE = 0;
            TURN_SCORE = 0;

        }
    }

    public void reset(View v){
        USER_SCORE = 0;
        COMP_SCORE = 0;
        TURN_SCORE = 0;
        updateTextView();
        updateImageView(1);
    }
    public void computerTurn(){
        int status = 1;
        int temp=0;

        while(status != 0) {
            temp = rm.nextInt(6);
            temp = temp + 1;
            if(temp == 1){
                TURN_SCORE = 0;
                temp = -1;
                break;
            }
            TURN_SCORE = TURN_SCORE + temp;
            updateImageView(temp);
            updateTextView();
            status = rm.nextInt(2);
            temp = 1;
        }
        if(temp != -1){
            COMP_SCORE = COMP_SCORE + TURN_SCORE;
        }
        TURN_SCORE = 0;
        updateTextView();
        updateImageView(1);
        if((USER_SCORE >= 100)||(COMP_SCORE>=100)){
            if(USER_SCORE>=100){
                Toast.makeText(this,"you won",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"computer won",Toast.LENGTH_LONG).show();
            }
            USER_SCORE = 0;
            COMP_SCORE = 0;
            TURN_SCORE = 0;

        }
    }
}
