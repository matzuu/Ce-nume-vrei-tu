package example.bogdan.proiect_mds;

class Ball
{

    private Point center;
    private float speedX;
    private float speedY;
    private final float speed = 1;
    private final float radius = 5;

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }


    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }


    public Ball(Point p, float angleInDegree)
    {
        center = p;
        this.speedX = (float) (speed * Math.cos(Math.toRadians(angleInDegree)));
        this.speedY = (float) (-speed * Math.sin(Math.toRadians(angleInDegree)));
    }


    public float getRadius() {
        return radius;
    }
}
