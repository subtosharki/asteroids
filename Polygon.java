/*
CLASS: Polygon
DESCRIPTION: A polygon is a sequence of points in space defined by a set of
             such points, an offset, and a rotation. The offset is the
             distance between the origin and the center of the shape.
             The rotation is measured in degrees, 0-360.
USAGE: You are intended to instantiate this class with a set of points that
       forever defines its shape, and then modify it by repositioning and
       rotating that shape. In defining the shape, the relative positions
       of the points you provide are used, in other words: {(0,1),(1,1),(1,0)}
       is the same shape as {(9,10),(10,10),(10,9)}.
NOTE: You don't need to worry about the "magic math" details.
*/
import java.awt.Graphics;
import java.awt.Color;

abstract class Polygon {
  private Point[] shape;   // An array of points.
  private Point position;   // The offset mentioned above.
  private double rotation; // Zero degrees is due east.
  
  // added instance variables by dresslerk
  private double speed;
  private double[] velocity;

  public Polygon(Point[] inShape, Point inPosition, double inRotation, double[] v) {
    shape = inShape;
    position = inPosition;
    rotation = inRotation;
    speed = 5; // added by dresslerk
    velocity = v;
    
    // First, we find the shape's top-most left-most boundary, its origin.
    try{
      Point origin = (Point)shape[0].clone();
      for (Point p : shape) {
        if (p.x < origin.x) origin.x = p.x;
        if (p.y < origin.y) origin.y = p.y;
      }
      
      // Then, we orient all of its points relative to the real origin.
      for (Point p : shape) {
        p.x -= origin.x;
        p.y -= origin.y;
      }
  }
  catch (CloneNotSupportedException e)
  {
    e.printStackTrace();
  }
  }
  public Polygon(Point[] shape)
  {
    this(shape, new Point(400, 300), 0, new double[]{0,0});
  }
  //added by dresslerk
  public void move() {
    position.x += velocity[0];
    position.y += velocity[1];

    // Keeps shape on the board by wrap-around
    if(position.x>800)
      position.x=0;
    if(position.x<0)
      position.x=800;
    if(position.y>600)
      position.y=0;
    if(position.y<0)
      position.y=600;
  }
  
  //added by dresslerk
  public abstract void collide(Polygon other);

  // "getPoints" applies the rotation and offset to the shape of the polygon.
  public Point[] getPoints() {
    Point center = findCenter();
    Point[] points = new Point[shape.length];
    int i=0;
    for (Point p : shape) {
      int x = (int)(((p.x-center.x) * Math.cos(Math.toRadians(rotation)))
               - ((p.y-center.y) * Math.sin(Math.toRadians(rotation)))
               + center.x/2 + position.x);
      int y = (int)(((p.x-center.x) * Math.sin(Math.toRadians(rotation)))
               + ((p.y-center.y) * Math.cos(Math.toRadians(rotation)))
               + center.y/2 + position.y);
      points[i] = new Point(x,y);
      i++;
    }
    return points;
  }
  
  public double[] getVelocity() {
    return velocity; 
  }

  // "contains" implements some magical math (i.e. the ray-casting algorithm).
  public boolean contains(Point point) {
    Point[] points = getPoints();
    double crossingNumber = 0;
    for (int i = 0, j = 1; i < shape.length; i++, j=(j+1)%shape.length) {
      if ((((points[i].x < point.x) && (point.x <= points[j].x)) ||
           ((points[j].x < point.x) && (point.x <= points[i].x))) &&
          (point.y > points[i].y + (points[j].y-points[i].y)/
           (points[j].x - points[i].x) * (point.x - points[i].x))) {
        crossingNumber++;
      }
    }
    return crossingNumber%2 == 1;
  }
  
  public void rotate(int degrees) {rotation = (rotation+degrees)%360;}
  
  /*
  The following methods are private access restricted because, as this access
  level always implies, they are intended for use only as helpers of the
  methods in this class that are not private. They can't be used anywhere else.
  */
  
  // "findArea" implements some more magic math.
  private double findArea() {
    double sum = 0;
    for (int i = 0, j = 1; i < shape.length; i++, j=(j+1)%shape.length) {
      sum += shape[i].x*shape[j].y-shape[j].x*shape[i].y;
    }
    return Math.abs(sum/2);
  }
  
  // "findCenter" implements another bit of math.
  private Point findCenter() {
    Point sum = new Point(0,0);
    for (int i = 0, j = 1; i < shape.length; i++, j=(j+1)%shape.length) {
      sum.x += (shape[i].x + shape[j].x)
               * (shape[i].x * shape[j].y - shape[j].x * shape[i].y);
      sum.y += (shape[i].y + shape[j].y)
               * (shape[i].x * shape[j].y - shape[j].x * shape[i].y);
    }
    double area = findArea();
    return new Point(Math.abs(sum.x/(6*area)),Math.abs(sum.y/(6*area)));
  }

  //dresslekr additions

  public void paint(Graphics g)
  {
    g.setColor(Color.white);
    // shape in the correct position with correct angle
    Point[] drawShape = getPoints();
    //Using drawPolygon function
    int[] pointX = new int[drawShape.length];
    int[] pointY = new int[drawShape.length];
    for(int i=0;i<drawShape.length;i++)
    {
      pointX[i]=(int)drawShape[i].x;
      pointY[i]=(int)drawShape[i].y;
    }
    g.drawPolygon(pointX,pointY,drawShape.length);
  }
  public void setVelocity(double[] v)
  {
    velocity=v;
  }
  public double getRotation(){
    return rotation;
  }
  public void thrust(double THRUST_VALUE) {
    double[] newVelocity = new double[2];
    double[] oldVelocity = getVelocity();
    newVelocity[0] = oldVelocity[0] + THRUST_VALUE*Math.cos(getRotation()*Math.PI/180);
    newVelocity[1] = oldVelocity[1] + THRUST_VALUE*Math.sin(getRotation()*Math.PI/180);
    setVelocity(newVelocity);
  }
  public void resetShip()
  {
    position.x = 400;
    position.y = 300;
    rotation = 0;
    setVelocity(new double[]{0,0});
  }
  public Point getPosition()
  {
    return position;
  }
}