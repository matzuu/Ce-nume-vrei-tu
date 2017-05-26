package example.bogdan.proiect_mds;
import java.util.*;

import static java.lang.Math.sqrt;

class Ball
{
    private Point center;
    private double radius;
    private double speedX, speedY;
    private final double speed = 10;

    public Ball(Point p,double radius, double angleInDegree)
    {
        center = p;
        this.speedX = (speed * Math.cos(Math.toRadians(angleInDegree)));
        this.speedY = (-speed * Math.sin(Math.toRadians(angleInDegree)));
        this.radius = radius;

    }


    public void moveOneStep(List<Wall> wallList)
    {
        center.x += speedX;
        center.y += speedY;
        int n = wallList.size();
        for(int i = 0; i < n - 1; i++)
        {
            Wall auxWall = wallList.get(i);
            for(int j = 0; j < 4; j++)
            {
                if(dist(center, auxWall.cp[j]) <= radius)
                {
                    bounce(auxWall,j,wallList);
                    return;
                }
            }
        }
        Wall auxWall = wallList.get(n-1);
        for(int j = 0; j < 4; j++)
        {
            if(dist(center, auxWall.cp[j]) <= radius)
            {
                win(); // o idee ar fi sa fac metoda asta bool si in cazul in care castig , unde apelez metoda verific daca a
                        // returnat true sau false si daca a returnat true voi face updateCollisionPoints la ecran
            }
        }
    }

    private void win()
    {
        speedX = speedY = 0;
        //de facut ceva pt printat mesaj

    }

    private void bounce(Wall wall, int i, List<Wall> wallList)
    {
        double angle = (wall.p[i+1].y - wall.p[i].y) / (wall.p[i+1].x - wall.p[i].x);
        speedX = (speed * Math.cos(angle));
        speedY = (-speed * Math.sin(angle));

        updateCollisionPoints(wallList, angle);


    }

    private void updateCollisionPoints(List<Wall> wallList, double angle) {
        int i;
        int n = wallList.size();
        for(i = 0; i < n ;i++) //nu imi mai pasa de i deci pot sa il folosesc ca sa imi dau updateCollisionPoints la lista de colission points
        {
            Wall auxWall = wallList.get(i);
            for(int j = 0; j < n; j++)
            {
                auxWall.cp[j].x = (auxWall.p[i].y - center.y - auxWall.p[i].x + center.x) /
                                    (speedX - angle);
                auxWall.cp[j].y = speedX * ( auxWall.cp[j].x - center.x) +  center.y;
            }

        }
    }

    private double dist(Point center, Point point) {
        return sqrt( (center.x - point.x) * (center.x - point.x) +
                (center.y - point.y) * (center.y - point.y) );
    }


}
