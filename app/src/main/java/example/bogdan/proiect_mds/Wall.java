package example.bogdan.proiect_mds;



@SuppressWarnings("ALL")
class Wall
{
    Point []p;
    Point []cp; //collision points

    public Wall(Point []points)
    {
        for(int i = 0; i < 4; i++)
            p[i] = points[i];
        p[4] = p[0];
    }



}
