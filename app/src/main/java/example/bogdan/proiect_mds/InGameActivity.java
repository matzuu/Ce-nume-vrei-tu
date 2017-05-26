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

import java.util.LinkedList;


public class InGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(new SampleView(this));
    }

    private static class SampleView extends View implements Runnable {

        Canvas canvas;
        Thread t = new Thread();
        Ball ball;
        LinkedList<Wall> listWall;
        public SampleView(Context context) {
            super(context);
            setFocusable(true);

        }

        @Override
        protected void onDraw(Canvas c) {
            canvas = c;
            Paint p = new Paint();
            p.setAntiAlias(true);
            p.setColor(Color.BLACK);
            p.setStrokeWidth(4.5f);
            invalidate();
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
