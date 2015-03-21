package batallatech.catchphrase;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class CatchPhraseMain extends Activity implements View.OnClickListener {

    Button startSkip;
    ImageButton minus1;
    ImageButton minus2;
    ImageButton add1;
    ImageButton add2;
    TextView score1;
    TextView score2;
    TextView wordBankView;

    int scoreInt1 = 0;
    int scoreInt2 = 0;
    int roundNum = 0;
    static final int maxRound = 7;
    static final int oneSecond = 1000;
    static final int delayTime = 6 * oneSecond;
    boolean stopTimer = true;
    String[] words;
    final private static String TAG = "Catch Phrase Main";
    int wordIndex = 0;
    int prevWord = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch_phrase_main);

        Initialize();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_catch_phrase_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.startnext:
                if (startSkip.getText().toString().matches(getString(R.string.next))){
                    roundNum++;
                    stopTimer = true;
                    if (roundNum % 2 == 0)
                        ActiveTeam(true);
                    else
                        ActiveTeam(false);
                }

                if(startSkip.getText().equals(getResources().getString(R.string.start))) {
                    startSkip.setText(getString(R.string.next));
                    ActiveTeam(true);

                }

                break;
            case R.id.minus1:
                stopTimer = true;
                roundNum++;
                if(scoreInt1 >0){
                    scoreInt1 = scoreInt1 - 1;
                    score1.setText(""+scoreInt1);
                }
                ActiveTeam(false);
                break;
            case R.id.add1:
                stopTimer = true;
                roundNum++;
                scoreInt1++;
                score1.setText(""+scoreInt1);
                if(scoreInt1 == maxRound)
                Winner(getString(R.string.team1));
                if(scoreInt1 != 7)
                ActiveTeam(false);
                break;
            case R.id.minus2:
                stopTimer = true;
                roundNum++;
                if(scoreInt2 >0){
                    scoreInt2 = scoreInt2 - 1;
                    score2.setText("" + scoreInt2);
                    ActiveTeam(true);
                }
                break;
            case R.id.add2:
                stopTimer = true;
                roundNum++;
                scoreInt2++;
                score2.setText(""+scoreInt2);
                if(scoreInt2 == maxRound)
                Winner(getString(R.string.team2));
                if(scoreInt1 != 7)
                ActiveTeam(true);
                break;
            default:
        }
    }

    private void Initialize(){
        startSkip = (Button) findViewById(R.id.startnext);
        startSkip.setOnClickListener(this);
        minus1 = (ImageButton) findViewById(R.id.minus1);
        minus2 = (ImageButton) findViewById(R.id.minus2);
        minus1.setOnClickListener(this);
        minus2.setOnClickListener(this);
        add1 = (ImageButton) findViewById(R.id.add1);
        add2 = (ImageButton) findViewById(R.id.add2);
        add1.setOnClickListener(this);
        add2.setOnClickListener(this);
        score1 = (TextView) findViewById(R.id.score1);
        score2 = (TextView)findViewById(R.id.score2);
        wordBankView = (TextView) findViewById(R.id.wordBankView);

        add2.setClickable(false);
        minus2.setClickable(false);
        add1.setClickable(false);
        minus1.setClickable(false);

        words = getResources().getStringArray(R.array.wordsList);


    }

    private void Winner(String team){
        stopTimer = true;
        new MaterialDialog.Builder(this)
                .title(getResources().getString(R.string.winner))
                .content(team + " is the winner!")
                .positiveText(getResources().getString(R.string.yay))
                .negativeText(getString(R.string.reset))
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        Reset();
                    }
                })
                .show();

        add2.setClickable(false);
        minus2.setClickable(false);
        add1.setClickable(false);
        minus1.setClickable(false);

    }

    private void Reset(){
        scoreInt1 = 0;
        scoreInt2 = 0;
        score1.setText(""+0);
        score2.setText(""+0);

    }

    private void ActiveTeam(boolean team1){

    if(team1){
        add2.setClickable(false);
        minus2.setClickable(false);
        add1.setClickable(true);
        minus1.setClickable(true);
        TeamDialog(getString(R.string.team1));
    }else{
        add2.setClickable(true);
        minus2.setClickable(true);
        add1.setClickable(false);
        minus1.setClickable(false);
        TeamDialog(getString(R.string.team2));
    }
}

    private void TeamDialog(String team){
        String content;

        if (stopTimer == false){
            content = "Oops, ran out of time," + team + "'s turn";
        }else
            content = team + "'s turn!";

        new MaterialDialog.Builder(this)
                .content(content)
                .positiveText(getString(R.string.ok))
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        stopTimer = false;
                        NewWord();
                        Timer();

                    }
                })
                .show();
    }

    private void Timer(){

         new Handler().postDelayed(new Runnable() {
            // Using handler with postDelayed called runnable run method
            @Override
            public void run() {
                // Do in background
                if(stopTimer == false) {
                    roundNum++;
                    if (roundNum % 2 == 0)
                        ActiveTeam(true);
                    else
                        ActiveTeam(false);
                }
                stopTimer = true;
            }
        }, delayTime); // wait for delayTime seconds

    }

    private void NewWord(){
        prevWord = wordIndex;
        NumberFormat form = new DecimalFormat("#0");
         wordIndex = Integer.parseInt(""+form.format(Math.floor(Math.random()*1654662154) % words.length));

        if(wordIndex == prevWord)
            wordIndex = wordIndex+1;


        Log.i(TAG,""+ words.length);
        Log.i(TAG,""+wordIndex);
        wordBankView.setText(words[wordIndex]);

    }



}
