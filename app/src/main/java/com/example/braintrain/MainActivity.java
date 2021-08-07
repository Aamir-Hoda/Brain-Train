package com.example.braintrain;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import androidx.gridlayout.widget.GridLayout;

import static java.util.Arrays.asList;
/*import android.widget.GridLayout; -- Remember to use either this mentioned library or the above library, otherwise it'll differ from the layout.xml file.*/

public class MainActivity extends AppCompatActivity {

    GridLayout objAnsGridLayout, objDetailsGridLayout;
    TextView objOutcomeTextView, objTimerTextView, objQuestionTextView, objScoreTextView;
    Button objPlayAgainBtn, objLaunchBtn, objAnsButton1, objAnsButton2, objAnsButton3, objAnsButton4;

    int timer=30, correctScore = 0, totalRounds=0, /*numbers[]={1,2,3,4,5,6,7,8,9,10},*/ num1, num2, num3;

    ArrayList<Integer> result;

    Random objRandom = new Random();
    CountDownTimer countDown;

    /*int[] list = new int[] {1,2,3,4,5,6};
 new Random().ints(0, list.length).limit(10).forEach(p -> System.out.println(list[p]));*/

    public void launch(View view)
    {
        Log.i("Start", "PLAY TIME!");

        objLaunchBtn.setVisibility(View.GONE);
        objDetailsGridLayout.setVisibility(View.VISIBLE);
        objAnsGridLayout.setVisibility(View.VISIBLE);
        startTimer();

        /*Another approach could have been to add all my components (textViews, buttons, etc.) in another constraint layout, & simply make that constraint layout
        * visible back again.*/
    }

    public void question()
    {
        num1 = objRandom.nextInt(16); //num1 = objRandom.nextInt(numbers.length);
        num2 = objRandom.nextInt(16);
        num3 = objRandom.nextInt(16);
        objQuestionTextView.setText(Integer.toString(num1)+"         \n+\n         "+Integer.toString(num2));

        result = new ArrayList<Integer>(asList(num1+num2, num1-num2+(num3%2), (num1+num2)-num3, (num1-num2)+num3));
        Collections.shuffle(result);
        //SINCE, THE ARRAYLIST IS BEING SHUFFLED EACH TIME, SO WE'LL EVENTUALLY HAVE DIFFERENT ORDER OF OPTIONS EVERY TIME

        /*Another approach could have been to again select random numbers for the incorrect options, while ensuring that the "correct answer" is not re-selected as the
        incorrect answer.*/

        objAnsButton1.setText(Integer.toString(result.get(0)));   objAnsButton1.setTag(result.get(0));
        objAnsButton2.setText(Integer.toString(result.get(1)));   objAnsButton2.setTag(result.get(1));
        objAnsButton3.setText(Integer.toString(result.get(2)));   objAnsButton3.setTag(result.get(2));
        objAnsButton4.setText(Integer.toString(result.get(3)));   objAnsButton4.setTag(result.get(3));
    }

    public void nextQuestion(View view) //FUNCTION CALLED UPON ANSWER BUTTON CLICK
    {
        objOutcomeTextView.setVisibility(View.VISIBLE);
        if((int)(view.getTag())==(num1+num2))
        {
            objOutcomeTextView.setText("CORRECT  :)");
            objOutcomeTextView.setTextColor(Color.GREEN);
            correctScore++;
        }
        else
        {
            objOutcomeTextView.setText("INCORRECT  :(");
            objOutcomeTextView.setTextColor(Color.RED);
        }
        totalRounds++;
        objScoreTextView.setText(Integer.toString(correctScore)+"/"+Integer.toString(totalRounds));

        question();
    }

    public void resetGame(View view)
    {
        //WILL RESET THE GAME FOR USER
        objPlayAgainBtn.setVisibility(View.INVISIBLE);
        objScoreTextView.setText("0/0");
        objOutcomeTextView.setText("");

        objAnsButton1.setClickable(true);  //ALL BUTTONS IN ANSWER GRID LAYOUT WILL BECOME UNCLICKABLE
        objAnsButton2.setClickable(true);
        objAnsButton3.setClickable(true);
        objAnsButton4.setClickable(true);

        correctScore=0;
        totalRounds=0;
        result.clear();

        /*countDown.start();*/ startTimer();
    }

    public void startTimer()
    {
        countDown = new CountDownTimer(30000, 1000) //timer = 30 SECONDS
        {
            public void onTick(long l)
            {
                objTimerTextView.setText(Integer.toString(timer)+"s");
                timer--;
            }
            public void onFinish()
            {
                objPlayAgainBtn.setVisibility(View.VISIBLE);
                objOutcomeTextView.setTextColor(Color.BLUE);
                objOutcomeTextView.setText("Training Completed!");
                objTimerTextView.setText("0s");
                timer=30;
                objAnsButton1.setClickable(false);  //ALL BUTTONS IN ANSWER GRID LAYOUT WILL BECOME UNCLICKABLE
                objAnsButton2.setClickable(false);
                objAnsButton3.setClickable(false);
                objAnsButton4.setClickable(false);
            }
        };
        countDown.start();
        question();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        objAnsGridLayout = (GridLayout) findViewById(R.id.ansGridLayout);
        objDetailsGridLayout = (GridLayout) findViewById(R.id.detailsGridLayout);

        objTimerTextView = (TextView) findViewById(R.id.timerTextView);
        objQuestionTextView = (TextView) findViewById(R.id.questionTextView);
        objScoreTextView = (TextView) findViewById(R.id.scoreTextView);
        objOutcomeTextView = (TextView) findViewById(R.id.outcomeTextView);

        objLaunchBtn = (Button) findViewById(R.id.launchBtn);
        objPlayAgainBtn = (Button)  findViewById(R.id.playAgainBtn);
        objAnsButton1 = (Button) findViewById(R.id.ansButton1);
        objAnsButton2 = (Button) findViewById(R.id.ansButton2);
        objAnsButton3 = (Button) findViewById(R.id.ansButton3);
        objAnsButton4 = (Button) findViewById(R.id.ansButton4);
    }
}