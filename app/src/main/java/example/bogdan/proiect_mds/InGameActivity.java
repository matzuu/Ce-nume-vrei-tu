package example.bogdan.proiect_mds;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Math.sqrt;


public class InGameActivity extends AppCompatActivity {

    private int INFINIT = 100000; //infinit pt cazul in care pantele sunt paralele

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
        ArrayList<Wall> listWall = new ArrayList<>();
        Wall victory;
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
                Point []winVect = new Point[5];
                for(int  i = 0; i < 4; i++)
                {
                    winVect[i] = new Point(sc.nextFloat(), sc.nextFloat());
                }
                winVect[4] = winVect[0];
                victory = new Wall(winVect);
//                sc.nextLine();

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

            updateCollisionPoints(ang);
        }

        private void Toast(String content) {
            Toast myToast = Toast.makeText(getApplicationContext(),content, Toast.LENGTH_LONG);
            myToast.show();
        }


        public void moveOneStep(ArrayList<Wall> listWall)
        {
            Point p = new Point(ball.getCenter().x + ball.getSpeedX(), ball.getCenter().y + ball.getSpeedY());
            ball.setCenter(p);
            int n = listWall.size();
            for(int i = 0; i < n; i++)
            {
                Wall auxWall = listWall.get(i);
                for(int j = 0; j < 4; j++)
                {
                    if(distance(ball.getCenter(), auxWall.cp[j]) <= ball.getRadius())
                    {
                        bounce(auxWall,j,listWall);
                        return;
                    }
                }
            }
//            for(int j = 0; j < 4; j++)
//            {
//                if(distance(ball.getCenter(), victory.cp[j]) <= ball.getRadius())
//                {
//                    j = 5;
//
//                    //win(); // o idee ar fi sa fac metoda asta bool si in cazul in care castig , unde apelez metoda verific daca a
//                    // returnat true sau false si daca a returnat true voi face updateCollisionPoints la ecran
//                }
//            }
        }
        private void bounce(Wall wall, int i, ArrayList<Wall> listWall)
        {
            float angle = (wall.p[i+1].y - wall.p[i].y) / (wall.p[i+1].x - wall.p[i].x);

            ball.setSpeedX((float) ( ball.getSpeed()* Math.cos(angle)));
            ball.setSpeedY((float) (-ball.getSpeed()* Math.sin(angle)));

            updateCollisionPoints(angle);


        }

        private void updateCollisionPoints( float angle) {

            int n = listWall.size();
            for(int i = 0; i < n ;i++)
            {
                Wall auxWall = listWall.get(i);
                for(int j = 0; j < 4; j++)
                {

                    float ballSlope = (float) Math.tan(Math.acos(ball.getSpeedX()/ball.getSpeed()));
                    float lineSlope = (auxWall.p[j+1].y - auxWall.p[j].y) / (auxWall.p[j+1].x - auxWall.p[j].x); //panta ciudata
                    if(lineSlope - ballSlope == 0)
                    {
                        listWall.get(i).cp[j].x = listWall.get(i).cp[j].y = INFINIT;
                    }
                    else
                    {
                        float c1 = (ball.getCenter().y - auxWall.p[j].y - ballSlope * ball.getCenter().x + lineSlope * auxWall.p[j].x) / (lineSlope - ballSlope);
                        float c2 = ballSlope * (c1 - ball.getCenter().x) + ball.getCenter().y;
                        Point p = new Point (c1,c2);
                        listWall.get(i).setCp(p, j);

                        //listWall.get(i).cp[j].x = (ball.getCenter().y - auxWall.p[j].y - ballSlope * ball.getCenter().x + lineSlope * auxWall.p[j].x) / (lineSlope - ballSlope);
                        //listWall.get(i).cp[j].y = ballSlope * (listWall.get(i).cp[j].x - ball.getCenter().x) + ball.getCenter().y;
                        
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
            moveOneStep(listWall);

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
