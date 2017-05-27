package example.bogdan.proiect_mds;



@SuppressWarnings("ALL")
class Wall
{
    public Point[] getP() {
        return p;
    }

    public void setP(Point[] p) {
        this.p = p;
    }

    public Point[] getCp() {
        return cp;
    }

    public void setCp(Point[] cp) {
        this.cp = cp;
    }

    Point []p = new Point[5];
    Point []cp; //collision points

    public Wall(Point []points)
    {
        for(int i = 0; i < 4; i++)
            p[i] = points[i];
        p[4] = p[0];
    }



}
