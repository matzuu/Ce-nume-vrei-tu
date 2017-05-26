package example.bogdan.proiect_mds;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.IntBuffer;
import java.util.LinkedList;
import java.util.Scanner;


public class InGameActivity extends AppCompatActivity {

    int n = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(new SampleView(this));
    }

    private class SampleView extends View implements Runnable {

        Canvas canvas;
        Thread t = new Thread();
        Ball ball;
        LinkedList<Wall> listWall;
        public SampleView(Context context) {
            super(context);
            setFocusable(true);
            int lvl = -1;
            Bundle b = getIntent().getExtras();
            if(b != null)
                lvl = b.getInt("key");
            String text;
            try
            {

                Scanner sc = new Scanner(getAssets().open("Level" + lvl + ".txt"));
                n = sc.nextInt();

            }
            catch(Exception e)
            {
                e.printStackTrace();

            }

        }

        private void Toast(String content) {
            Toast myToast = Toast.makeText(getApplicationContext(),content, Toast.LENGTH_LONG);
            myToast.show();
        }

        @Override
        protected void onDraw(Canvas c) {
            canvas = c;
            Paint p = new Paint();
            p.setAntiAlias(true);
            p.setColor(Color.BLACK);
            p.setStrokeWidth(4.5f);
            invalidate();
            String textceva = "!!" + n;
            Toast(textceva);
        }
        public void start()
        {
            t.start();
        }

        @Override
        public void run()
        {
            try
            {
                Thread.sleep(100);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }


}
