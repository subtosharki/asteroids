public class PewPew extends Polygon
{
  private static Point[] shape = new Point[]{new Point(0,0),new Point(10,0),new Point(10,2),new Point(0,2)};
  private static final double THRUST_VALUE = 5;
  public int timer = 150;
  /**
  * Sets the instance variables
  */
  public PewPew(Point location, double r) {
    super(shape,location,r,new double[]{0,0});
  }
  public void thrust()
  {
    double[] newVelocity = new double[2];
    double[] oldVelocity = getVelocity();
    newVelocity[0] = oldVelocity[0] + THRUST_VALUE*Math.cos(getRotation()*Math.PI/180);
    newVelocity[1] = oldVelocity[1] + THRUST_VALUE*Math.sin(getRotation()*Math.PI/180);
    setVelocity(newVelocity);
  }
  public void collide(Polygon other)
  {
    //TODO;
  }
}