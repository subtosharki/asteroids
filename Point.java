/*
CLASS: Point
DESCRIPTION: Ah, if only real-life classes were this straight-forward. We'll
             use 'Point' throughout the program to store and access 
             coordinates.
*/

class Point {
  double x;
  double y;
  public Point(double inX, double inY) { x = inX; y = inY; }

protected Object clone() throws CloneNotSupportedException 
{     return new Point(x, y); }
}