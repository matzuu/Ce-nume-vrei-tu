package example.bogdan.proiect_mds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class LevelSelectActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        ImageButton one = (ImageButton) findViewById(R.id.ButtonNivel1);
        one.setOnClickListener(this); // calling onClick() method
        ImageButton two = (ImageButton) findViewById(R.id.ButtonNivel2);
        two.setOnClickListener(this);
        ImageButton three = (ImageButton) findViewById(R.id.ButtonNivel3);
        three.setOnClickListener(this);
        ImageButton four = (ImageButton) findViewById(R.id.ButtonNivel4);
        four.setOnClickListener(this);
        ImageButton five = (ImageButton) findViewById(R.id.ButtonNivel5);
        five.setOnClickListener(this);
        ImageButton six = (ImageButton) findViewById(R.id.ButtonNivel6);
        six.setOnClickListener(this);
        ImageButton menuButton = (ImageButton) findViewById(R.id.ButtonMainMenu);
        menuButton.setOnClickListener(this);


        //LevelSelector();
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ButtonNivel1:
                Intent intent1 = new Intent(LevelSelectActivity.this, InGameActivity.class);
                Bundle b1 = new Bundle();
                b1.putInt("key", 1);
                intent1.putExtras(b1);
                startActivity(intent1);
                break;

            case R.id.ButtonNivel2:
                Intent intent2 = new Intent(LevelSelectActivity.this, InGameActivity.class);
                Bundle b2 = new Bundle();
                b2.putInt("key", 2);
                intent2.putExtras(b2);
                startActivity(intent2);
                break;

            case R.id.ButtonNivel3:
                Intent intent3 = new Intent(LevelSelectActivity.this, InGameActivity.class);
                Bundle b3 = new Bundle();
                b3.putInt("key", 3);
                intent3.putExtras(b3);
                startActivity(intent3);
                break;

            case R.id.ButtonNivel4:
                Intent intent4 = new Intent(LevelSelectActivity.this, InGameActivity.class);
                Bundle b4 = new Bundle();
                b4.putInt("key", 4);
                intent4.putExtras(b4);
                startActivity(intent4);
                break;

            case R.id.ButtonNivel5:
                Intent intent5 = new Intent(LevelSelectActivity.this, InGameActivity.class);
                Bundle b5 = new Bundle();
                b5.putInt("key", 5);
                intent5.putExtras(b5);
                startActivity(intent5);
                break;

            case R.id.ButtonNivel6:
                Intent intent6 = new Intent(LevelSelectActivity.this, InGameActivity.class);
                Bundle b6 = new Bundle();
                b6.putInt("key", 6);
                intent6.putExtras(b6);
                startActivity(intent6);
                break;

            case R.id.ButtonMainMenu:
                Intent intentMenu = new Intent (LevelSelectActivity.this,MainActivity.class);
                startActivity(intentMenu);
                break;

            default:
                break;
        }
    }

    /*
    public Button BackButton;

    public void LevelSelector(){
        BackButton= (Button) findViewById(R.id.ButtonMainMenu);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeMenu = new Intent (LevelSelectActivity.this,MainActivity.class);
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
