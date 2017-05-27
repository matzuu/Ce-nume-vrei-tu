package example.bogdan.proiect_mds;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.sqrt;


public class InGameActivity extends AppCompatActivity {

    public int INFINIT = 100000; //infinit pt cazul in care pantele sunt paralele

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
        LinkedList<Wall> listWall = new LinkedList<>();
        public SampleView(Context context)
        {
            super(context);
            setFocusable(true);
            int lvl = -1;
            Bundle b = getIntent().getExtras();
            if(b != null)
                lvl = b.getInt("key");

            reading(lvl);

        }


        private void reading(int lvl)
        {
            float ang = 0;
            try
            {

                Scanner sc = new Scanner(getAssets().open("Level" + lvl + ".txt"));
                Point p = new Point(sc.nextFloat(),sc.nextFloat());
                ang = sc.nextFloat();
                ball = new Ball(p,ang);
                sc.nextLine();

                while(sc.hasNext())
                {
                    Point []vect = new Point[5];
                    for(int i = 0; i < 4; i++)
                    {
                        float x = sc.nextFloat();
                        float y = sc.nextFloat();
                        vect[i] = new Point(x,y);
                    }
                    vect[4] = vect[0];
                    listWall.add(new Wall(vect));
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            updateCollisionPoints(listWall,ang);
        }

        private void Toast(String content) {
            Toast myToast = Toast.makeText(getApplicationContext(),content, Toast.LENGTH_LONG);
            myToast.show();
        }


        public void moveOneStep(LinkedList<Wall> wallList)
        {
            Point p = new Point(ball.getCenter().x + ball.getSpeedX(), ball.getCenter().y + ball.getSpeedY());
            ball.setCenter(p);
            int n = wallList.size();
            for(int i = 0; i < n - 1; i++)
            {
                Wall auxWall = wallList.get(i);
                for(int j = 0; j < 4; j++)
                {
                    if(distance(ball.getCenter(), auxWall.cp[j]) <= ball.getRadius())
                    {
                        bounce(auxWall,j,wallList);
                        return;
                    }
                }
            }
            Wall auxWall = wallList.get(n-1);
            for(int j = 0; j < 4; j++)
            {
                if(distance(ball.getCenter(), auxWall.cp[j]) <= ball.getRadius())
                {

                    //win(); // o idee ar fi sa fac metoda asta bool si in cazul in care castig , unde apelez metoda verific daca a
                    // returnat true sau false si daca a returnat true voi face updateCollisionPoints la ecran
                }
            }
        }
        private void bounce(Wall wall, int i, LinkedList<Wall> wallList)
        {
            float angle = (wall.p[i+1].y - wall.p[i].y) / (wall.p[i+1].x - wall.p[i].x);

            ball.setSpeedX((float) ( ball.getSpeed()* Math.cos(angle)));
            ball.setSpeedY((float) (-ball.getSpeed()* Math.sin(angle)));

            updateCollisionPoints(wallList, angle);


        }

        private void updateCollisionPoints(LinkedList<Wall> wallList, float angle) {

            int n = wallList.size();
            for(int i = 0; i < n ;i++)
            {
                Wall auxWall = wallList.get(i);
                for(int j = 0; j < 4; j++)
                {

                    float ballSlope = (float) Math.tan(Math.acos(ball.getSpeedX()/ball.getSpeed()));
                    float lineSlope = (auxWall.p[j+1].y - auxWall.p[j].y) / (auxWall.p[j+1].x - auxWall.p[j].x); //panta ciudata
                    if(lineSlope - ballSlope == 0)
                    {
                        wallList.get(i).cp[j].x = wallList.get(i).cp[j].y = INFINIT;
                    }
                    else
                    {
                        wallList.get(i).cp[j].x = (ball.getCenter().y - auxWall.p[j].y - ballSlope * ball.getCenter().x + lineSlope * auxWall.p[j].x) / (lineSlope - ballSlope);
                        wallList.get(i).cp[j].y = ballSlope * (wallList.get(i).cp[j].x - ball.getCenter().x) + ball.getCenter().y;
                        
                    }
                }

            }
        }

        private float distance(Point center, Point point) {
            return (float) sqrt( (center.x - point.x) * (center.x - point.x) +
                    (center.y - point.y) * (center.y - point.y) );
        }

        public void start()
        {
            t.start();
        }

        @Override
        protected void onDraw(Canvas c) {
            canvas = c;
            Paint p = new Paint();
            p.setAntiAlias(true);
            p.setColor(Color.BLACK);
            p.setStrokeWidth(4.5f);
            invalidate();
            canvas.drawCircle(ball.getCenter().x, ball.getCenter().y, ball.getRadius(),p);
//            Toast("" + listWall.get(1).p[0].x + listWall.get(1).p[0].y);
            int n = listWall.size();
            for(int i = 0; i < n; i++)
            {
                for(int j = 0; j < 4; j++)
                    // canvas.drawCircle(listWall.get(i).p[i].x, listWall.get(i).p[i].y,5,p);
                    canvas.drawLine(listWall.get(i).p[j].x, listWall.get(i).p[j].y,
                            listWall.get(i).p[j+1].x, listWall.get(i).p[j+1].y, p);

            }

        }

        @Override
        public void run()
        {
            try
            {
                Thread.sleep(100);
                //moveOneStep(listWall);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }


}
