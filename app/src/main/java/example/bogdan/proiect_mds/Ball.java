package example.bogdan.proiect_mds;

class Ball
{

    private Point center;
    private double speedX;
    private double speedY;
    private final double speed = 2;
    private final double radius = 5;

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }


    public double getSpeedX() {
        return speedX;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }


    public Ball(Point p, double angleInDegree)
    {
        center = p;
        this.speedX = (double) (speed * Math.cos(Math.toRadians(angleInDegree)));
        this.speedY = (double) (-speed * Math.sin(Math.toRadians(angleInDegree)));
    }


    public double getRadius() {
        return radius;
    }
}
