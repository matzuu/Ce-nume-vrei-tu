package example.bogdan.proiect_mds;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private float curVolume;
    private float maxVolume;
    private float volume;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageButton playbutton = (ImageButton) findViewById(R.id.PlayButton);
        playbutton.setOnClickListener(this); // calling onClick() method
//        ToggleButton soundbutton = (ToggleButton) findViewById(R.id.toggleSound);
//        soundbutton.setOnClickListener(this);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        volumeSounds();

        //LevelSelector();
    }


    public void volumeSounds()
    {
        AudioManager audioManager;
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        curVolume = (float)audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        maxVolume = (float)audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    //asdfasdfasdfasdf
        volume = curVolume / maxVolume;
    }


    public void onClick(View v) {

        final MediaPlayer menuButtonSound = MediaPlayer.create(this, R.raw.menu_button);
        switch (v.getId()) {

            case R.id.toggleSound:

                ToggleButton toggle = (ToggleButton)findViewById(R.id.toggleSound);
                toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            menuButtonSound.setVolume(0,0);
                        } else {
                            menuButtonSound.setVolume(volume,volume);
                        }
                    }
                });// BACKEND: aici bagi ce vrei sa se intample cand dai click pe butoane.
                break;

            case R.id.PlayButton:
                menuButtonSound.start();
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
