package example.bogdan.proiect_mds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageButton playbutton = (ImageButton) findViewById(R.id.PlayButton);
        playbutton.setOnClickListener(this); // calling onClick() method
        Button soundbutton = (Button) findViewById(R.id.toggleSound);
        soundbutton.setOnClickListener(this);
        Button musicbutton = (Button) findViewById(R.id.toggleMusic);
        musicbutton.setOnClickListener(this);
        Button misc = (Button) findViewById(R.id.toggleButton3);
        misc.setOnClickListener(this);
        //LevelSelector();
    }


    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.toggleSound:
                // BACKEND: aici bagi ce vrei sa se intample cand dai click pe butoane.
                break;

            case R.id.toggleMusic:
                // do your code
                break;

            case R.id.toggleButton3:
                // do your code
                break;

            case R.id.PlayButton:
                Intent changeMenu = new Intent (MainActivity.this,LevelSelectActivity.class);
                startActivity(changeMenu);
                break;

            default:
                break;
        }
    }

    /*
    public Button PlayButton;

    public void LevelSelector(){
        PlayButton= (Button) findViewById(R.id.PlayButton);
        PlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeMenu = new Intent (MainActivity.this,LevelSelectActivity.class);
                startActivity(changeMenu);
            }

        });
    }
    */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }
}
