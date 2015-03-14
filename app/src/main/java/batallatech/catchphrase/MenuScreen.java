package batallatech.catchphrase;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;


public class MenuScreen extends Activity implements View.OnClickListener{

   private Button menuPlayButton;
   private Button menuConnectButton;
   private Button menuRulesButton;
   private Button menuExitButton;
   private Button leaderboardButton;

   private Intent intent;
   GlobalMethods global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);

        Initialize();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_screen, menu);
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
            case R.id.menuPlayButton:
                intent = new Intent(getApplicationContext(), CatchPhraseMain.class);
                startActivity(intent);
                break;
            case R.id.menuConnectButton:
                global.cheers("This will connect to your Friends Device");
                break;
            case R.id.menuRules:
                RulesPopup();
                break;
            case R.id.menuExit:
                finish();
                break;
            case R.id.leaderboardButton:
                global.cheers("This will bring you to past battles");
                break;
            default:
        }
    }

    private void Initialize(){
        menuPlayButton = (Button) findViewById(R.id.menuPlayButton);
        menuPlayButton.setOnClickListener(this);
        menuConnectButton = (Button) findViewById(R.id.menuConnectButton);
        menuConnectButton.setOnClickListener(this);
        menuRulesButton = (Button) findViewById(R.id.menuRules);
        menuRulesButton.setOnClickListener(this);
        menuExitButton = (Button) findViewById(R.id.menuExit);
        menuExitButton.setOnClickListener(this);
        leaderboardButton = (Button) findViewById(R.id.leaderboardButton);
        leaderboardButton.setOnClickListener(this);

        global = new GlobalMethods(this);
    }

    private void RulesPopup(){
        new MaterialDialog.Builder(this)
                .title(getResources().getString(R.string.rules))
                .content(getResources().getString(R.string.rulescontent))
                .positiveText(getResources().getString(R.string.gotit))
                .show();

    }














}
