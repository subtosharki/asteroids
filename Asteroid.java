public class Asteroid extends Polygon
{
  private int size;

  private static Point[] size2 = new Point[]{new Point(13,0),new Point(0,15),new Point(0,40),new Point(13,50),new Point(31,50),new Point(38,38),new Point(50,34),new Point(50,15),new Point(38,15),new Point(31,0)};

  private static Point[] size3 = new Point[]{new Point(25,0),new Point(0,30),new Point(0,80),new Point(25,100),new Point(63,100),new Point(75,75),new Point(100,68),new Point(100,30),new Point(75,30),new Point(68,0)};

  private static Point[] size1 = new Point[]{new Point(6,0),new Point(0,8),new Point(0,20),new Point(7,25),new Point(15,25),new Point(19,19),new Point(25,17), new Point(25,8),new Point(19,8), new Point(15,0)};
  private static Point[][] shapes = new Point[][]{size1, size2, size3};

  private static double startDirection = Math.random()*360;
  //private static Point startL = new Point((Math.random()*800),(Math.random() * 600));
  private static Point startL = new Point(300,100);
  /**
  * Intializes all the instance variables
  */

  //TODO: Fix paramiters 
  // direction = Math.random()*360 in asteroids
  // position = Math.radnom in asteroids
  public Asteroid(Point location, double direction)
  {
    super(size3,location,direction,new double[]{1,0});
    size = 3;
  }
  /**
  * Intializes all the instance variables based on the 
  * paramiter s that sets thet size
  * @paramater: int s
  */
  public Asteroid(int s,Point location,double direction)
  {
    super(shapes[s-1],location,direction,new double[]{1,0});
    size = s;
  }
  public Asteroid(int s)
  {
    super(shapes[s-1],startL,startDirection,new double[]{1,0});
    size=s;
  }
  public int getSize() {
    return size;
  }
  public void collide(Polygon other)
  {
    
  }
}
