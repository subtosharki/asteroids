public class SpaceShip extends Polygon
{
  private int lives;
  private int score;
  private int rotationSpeed;
  public static Point shapes[] = new Point[]{new Point(0,0),new Point(20,10),new Point(0,20), new Point(5,10)};
  private static Point startPoint = new Point(400,300);
  private static final double THRUST_VALUE = .5;
  private Point[] points;
  /**
  * Sets all of the instance variables
  */
  public SpaceShip()
  {
    super(shapes,startPoint,0,new double[]{0,0});
    lives = 3;
    score = 0;
    rotationSpeed = 0;
  }
  /**
  * Returns the numebr of lives yet
  * @return: int
  */
  public int getLives() {
    return lives;
  }
  /**
  * Returns the score
  * @return: int
  */
  public int getScore() {
    return score;
  }
  /*public void thrust() {
    double[] newVelocity = new double[2];
    double[] oldVelocity = getVelocity();
    newVelocity[0] = oldVelocity[0] + THRUST_VALUE*Math.cos(getRotation()*Math.PI/180);
    newVelocity[1] = oldVelocity[1] + THRUST_VALUE*Math.sin(getRotation()*Math.PI/180);
    setVelocity(newVelocity);
  }*/
  /**
  * Creates a shot using the PewPew class
  */
  public PewPew shoot() {
    points = getPoints();
    return new PewPew(new Point(points[1].x-1,points[1].y), getRotation());
  }
  /**
  * Sees if the SpaceShip has been hit
  */
  public void removeLife() {
    lives--;
  }
  public void setRotationSpeed(int rs)
  {
    rotationSpeed = rs;
  }
  public int getRotationSpeed(){
    return rotationSpeed;
  }
  public void addScore(int s)
  {
    score+=s;
  }
  public void collide(Polygon other)
  {
    removeLife();
    if(lives>0)
    {
      resetShip();
      
    }
  }
}