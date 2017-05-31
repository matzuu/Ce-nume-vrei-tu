package example.bogdan.proiect_mds;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.display.DisplayManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Math.sqrt;


public class InGameActivity extends AppCompatActivity {

    private int INFINIT = 100000; //infinit pt cazul in care pantele sunt paralele
    private double MARJA = 1;
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
            double ang = 0;
            try
            {

                Scanner sc = new Scanner(getAssets().open("Level" + lvl + ".txt"));
                Point p = new Point(sc.nextDouble(), sc.nextDouble());
                ang = sc.nextDouble();
                ball = new Ball(p,ang);
                Point []winVect = new Point[5];
                for(int  i = 0; i < 4; i++)
                {
                    winVect[i] = new Point(sc.nextDouble(), sc.nextDouble());
                }
                winVect[4] = winVect[0];
                victory = new Wall(winVect);
//                sc.nextLine();

                while(sc.hasNext())
                {
                    Point []vect = new Point[5];
                    for(int i = 0; i < 4; i++)
                    {
                        double x = sc.nextDouble();
                        double y = sc.nextDouble();
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
//            DisplayMetrics dm = new DisplayMetrics();
//            getWindowManager().getDefaultDisplay().getMetrics(dm);
//            Toast("" + dm.heightPixels + " " + dm.widthPixels);
            updateCollisionPoints(ang);
           // String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Proiect_MDS2";
//            File dir = new File(path);
//            dir.mkdirs();
//            File file = new File(path + "/ceva.txt");
//            Save(file,listWall.get(0).toString());


        }



        private void Toast(String content) {
            Toast myToast = Toast.makeText(getApplicationContext(),content, Toast.LENGTH_LONG);
            myToast.show();
        }


        public void moveOneStep(ArrayList<Wall> listWall)
        {
            //Point p = new Point(ball.getCenter().x + ball.getSpeedX(), ball.getCenter().y + ball.getSpeedY());
            //ball.setCenter(p);
            ball.getCenter().x = ball.getCenter().x + ball.getSpeedX();
            ball.getCenter().y = ball.getCenter().y + ball.getSpeedY();
            int n = listWall.size();
//            for(int i = 0; i < 4; i++)
//            {
//                double dist = distance(ball.getCenter(), victory.cp[i]);
//
//                if(dist <= ball.getRadius() + 1 || dist <= ball.getRadius() -1)
//                {
//                    Toast("WIN!!");
//                    t.stop();
//                }
//
//            }
            for(int i = 0; i < n; i++)
            {
                Wall auxWall = listWall.get(i);
                for(int j = 0; j < 4; j++)
                {

                    double dist = distance(ball.getCenter(), auxWall.cp[j]);

                    if(dist <= ball.getRadius() + 1 || dist <= ball.getRadius() -1)
                    {

                        bounce(auxWall,j);
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
        private void bounce(Wall wall, int i)
        {
            double angle = (wall.p[i+1].y - wall.p[i].y) / (wall.p[i+1].x - wall.p[i].x);

            ball.setSpeedX((double) (ball.getSpeed()* Math.cos(angle)));
            ball.setSpeedY((double) (ball.getSpeed()* Math.sin(angle)));
            updateCollisionPoints(angle);



        }

        private void updateCollisionPoints(double angle) {

            int n = listWall.size();
            double ballSlope = (double) Math.tan(Math.acos(ball.getSpeedX()/ball.getSpeed()));
            for(int i = 0; i < n ;i++)
            {
                Wall auxWall = listWall.get(i);
                for(int j = 0; j < 4; j++)
                {

                    double lineSlope;
                    double ceva = auxWall.p[j + 1].x - auxWall.p[j].x;
                    if(ceva == 0)
                        lineSlope = 0;
                    else
                        lineSlope = (auxWall.p[j+1].y - auxWall.p[j].y) / (ceva);

                    if(lineSlope - ballSlope == 0)
                    {
                        listWall.get(i).cp[j].x = listWall.get(i).cp[j].y = INFINIT; //cand directia de mers a mingii si peretele sunt paraleli
                    }
                    else
                    {

                        double c1 = (-ball.getCenter().y + auxWall.p[j].y + ballSlope * ball.getCenter().x - lineSlope * auxWall.p[j].x)
                                / (-lineSlope + ballSlope);

                        double c2 = ballSlope * (c1 - ball.getCenter().x) + ball.getCenter().y;

                        Point p = new Point (c1,c2);
                        listWall.get(i).setCp(p, j);

                        //listWall.get(i).cp[j].x = (ball.getCenter().y - auxWall.p[j].y - ballSlope * ball.getCenter().x + lineSlope * auxWall.p[j].x) / (lineSlope - ballSlope);
                        //listWall.get(i).cp[j].y = ballSlope * (listWall.get(i).cp[j].x - ball.getCenter().x) + ball.getCenter().y;
                        
                    }
                }

            }
           // Toast(listWall.get(0).toString());
        }

        private double distance(Point center, Point point) {
            return (double) sqrt( (center.x -  point.x) * (center.x - point.x) +
                    (center.y -  point.y) * (center.y -  point.y ));
        }

        public void start()
        {
            t.start();
        }

        @Override
        protected void onDraw(Canvas c) {

            moveOneStep(listWall);
            canvas = c;
            Paint p = new Paint();
            p.setAntiAlias(true);
            p.setColor(Color.BLACK);
            p.setStrokeWidth(4.5f);
            invalidate();
            canvas.drawCircle((float)ball.getCenter().x, (float)ball.getCenter().y, (float)ball.getRadius(),p);
//            Toast("" + listWall.get(1).p[0].x + listWall.get(1).p[0].y);
            int n = listWall.size();
            for(int i = 0; i < n; i++)
            {
                for(int j = 0; j < 4; j++)
                    // canvas.drawCircle(listWall.get(i).p[i].x, listWall.get(i).p[i].y,5,p);
                    canvas.drawLine((float)listWall.get(i).p[j].x, (float)listWall.get(i).p[j].y,
                            (float) listWall.get(i).p[j+1].x, (float)listWall.get(i).p[j+1].y, p);

            }

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
