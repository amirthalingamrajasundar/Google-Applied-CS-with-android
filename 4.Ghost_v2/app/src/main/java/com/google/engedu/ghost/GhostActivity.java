package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();
    private Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("test","ghost activity oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            dictionary = new FastDictionary(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
        reset = (Button)findViewById(R.id.restart_button);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStart(v);
            }
        });
        onStart(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d("test","ghost activity oncreateoptionmenu");
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
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
        Log.d("test","ghost activity onOptionItemsselected");
        return super.onOptionsItemSelected(item);
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        Log.d("test","ghost activity onStart");
        userTurn = random.nextBoolean();
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }

    private void computerTurn() {
        Log.d("test", "ghost activity computerTurn");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        TextView ghosttext = (TextView) findViewById(R.id.ghostText);
        String s = ghosttext.getText().toString();
        String comp_string;
        char[] temp = s.toCharArray();
        label.setText(COMPUTER_TURN);
        if(s.length()==0){
            Random rm = new Random();
            comp_string = s +((char)(rm.nextInt(26)+1+96));
            ghosttext.setText(comp_string);
            userTurn = true;
            label.setText(USER_TURN);
            return;
        }

        if(dictionary.isWord(s)) {
            label.setText("You loose. "+s+" is a valid word. Please restart to try again!!!");
            ghosttext.setText("");
        }
        else {
            comp_string = dictionary.getAnyWordStartingWith(s);
            if(comp_string != null){
                temp = comp_string.toCharArray();
                s = s + temp[s.length()];
                ghosttext.setText(s);
            }else{
                label.setText("OOPS you bluff. "+s+" is not valid. Please restart to try again!!!");
                ghosttext.setText("");
                return;
            }
            userTurn = true;
            label.setText(USER_TURN);

        }
    }

    public void challangefun(View v){
        TextView textView = (TextView) findViewById(R.id.ghostText);
        TextView t = (TextView)findViewById(R.id.gameStatus);
        String s = textView.getText().toString();
        if(dictionary.isWord(s)){
            t.setText("You win!!!!!. Restart to play again!!");
        }
        else{
            s=dictionary.getAnyWordStartingWith(s);
            textView.setText("");
            t.setText("OOPS "+s+" can be formed. Please restart to try again!!!");
        }

    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        char c;
        String s;
        if(event.getKeyCode()>=29 && event.getKeyCode()<=54){
            c = (char)(keyCode+68);
            TextView ghosttext = (TextView) findViewById(R.id.ghostText);
            s = ghosttext.getText().toString();
            s = s + c;
            ghosttext.setText(s);
            computerTurn();


        }
        else{

            Toast.makeText(this,"random key is pressed",Toast.LENGTH_LONG).show();

        }
        return super.onKeyUp(keyCode, event);
    }
}
