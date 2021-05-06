/*
CLASS: Asteroids
DESCRIPTION: Extending Game, Asteroids is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.
*/
import java.awt.*;
import java.awt.event.*;

class Asteroids extends Game implements KeyListener {
  private SpaceShip ship;
  private int asteroid3MaxNum = 5;
  private Asteroid[] asteroids3 = new Asteroid[asteroid3MaxNum];
  private Asteroid[] asteroids2 = new Asteroid[asteroid3MaxNum*2];
  private Asteroid[] asteroids1 = new Asteroid[asteroid3MaxNum*4];
  private Polygon thrust;
  private PewPew[] pewpew = new PewPew[5];
  private double rx;
  private double ry;
  private double y;
  private boolean thrusting = false;
  private boolean respawnClear = false;
  public Asteroids() {
    super("Asteroids!",800,600);
    for(int i=0;i<asteroids3.length;i++)
    {
      rx = Math.random()*800;
      ry = Math.random()*600;
      y = Math.random()*360;
      asteroids3[i] = new Asteroid(3,new Point(rx,ry),y);
      asteroids3[i].thrust(.5);
    }
    ship = new SpaceShip();
  }
  
	public void paint(Graphics brush) {
    brush.setColor(Color.black);
    brush.fillRect(0,0,width,height);

    //dresslerk additions
    if (ship != null) {
      ship.paint(brush);
      ship.move();
      ship.rotate(ship.getRotationSpeed());
    }
    asteroids3Things(brush);
    asteroids2Things(brush);
    asteroids1Things(brush);
    for(int u=0;u<pewpew.length;u++)
    {
      if(pewpew[u] != null)
      {
        pewpew[u].paint(brush);
        pewpew[u].timer-=1;
        pewpew[u].move();
        if(pewpew[u].timer<=0)
        {
          pewpew[u] = null;
        }
      }
    }
    drawHeats(brush);
    /*if(thrusting == true)
    {
      brush.setColor(Color.red);
      Point[] points = ship.getPoints();
      double[] pointX = new double[]{points[0].x+1,points[3].x,points[2].x-1,points[3].x-10};
      double[] pointY = new double[]{points[0].y,points[1].y,points[2].y-2,points[3].y};
      Point[] shape = new Point[]{new Point(pointX[0],pointY[0]),new Point(pointX[1],pointY[1]),new Point(pointX[2],pointY[2]),new Point(pointX[3],pointY[3])};
      thrust = new Polygon(shape,new Point(pointX[0],pointY[0]),ship.getRotation(),ship.getVelocity());
      thrust.paint(brush);
      thrust.thrust(.5);
    }*/
  }
  
//dresslerk methods
  public void keyReleased(KeyEvent e) 
  {
    if(e.getKeyCode() == KeyEvent.VK_LEFT)
      ship.setRotationSpeed(0);
    if(e.getKeyCode() == KeyEvent.VK_RIGHT)
      ship.setRotationSpeed(0);
    if(e.getKeyCode() == KeyEvent.VK_UP)
      thrusting = false;
  }
  public void keyPressed(KeyEvent e) 
  {
    if(e.getKeyCode() == KeyEvent.VK_SPACE){
      /*pewpew = null;
      pewpew = ship.shoot();
      pewpew.thrust(5);*/
      for(int a=0;a<pewpew.length;a++)
      {
        if(pewpew[a]==null)
        {
          pewpew[a]=ship.shoot();
          pewpew[a].thrust(5);
          return;
        }
      }
    }
    if(e.getKeyCode() == KeyEvent.VK_LEFT)
      ship.setRotationSpeed(-5);
    if(e.getKeyCode() == KeyEvent.VK_RIGHT)
      ship.setRotationSpeed(5);
    if(e.getKeyCode() == KeyEvent.VK_UP)
    {
      ship.thrust(.5);
      thrusting = true;
    }
    if(e.getKeyCode() == KeyEvent.VK_DOWN)
    {
      ship.removeLife();
    }
  }
  public void keyTyped(KeyEvent e) {}
	public static void main (String[] args) {
    Asteroids game = new Asteroids();
    
    game.addKeyListener(game);
    game.repaint();
  }
  public void drawFullHeart(Graphics g, int startX)
  {
    int[] pointsX = new int[]{startX,10+startX,20+startX,10+startX};
    int[] pointsY = new int[]{25,5,25,20};
    g.fillPolygon(pointsX,pointsY,pointsX.length);
  }
  public void drawBlankHeart(Graphics g, int startX)
  {
    int[] pointsX = new int[]{startX,10+startX,20+startX,10+startX};
    int[] pointsY = new int[]{25,5,25,20};
    g.drawPolygon(pointsX,pointsY,pointsX.length);
  }
  public void drawHeats(Graphics brush)
  {
    if(ship.getLives() == 3)
    {
      drawFullHeart(brush, 760);
      drawFullHeart(brush, 730);
      drawFullHeart(brush, 700);
    }
    else if(ship.getLives() == 2)
    {
      drawFullHeart(brush, 760);
      drawFullHeart(brush, 730);
      drawBlankHeart(brush, 700);
    }
    else if(ship.getLives()==1)
    {
      drawFullHeart(brush, 760);
      drawBlankHeart(brush, 730);
      drawBlankHeart(brush, 700);
    }
    else
    {
      drawBlankHeart(brush, 760);
      drawBlankHeart(brush, 730);
      drawBlankHeart(brush, 700);
      brush.drawChars(new char[]{'G','A','M','E',' ','O','V','E','R'},0,9,400,300);
      ship.setVelocity(new double[]{0,0});
      ship.setRotationSpeed(0);
      pewpew = null;
      for(int o=0;o<asteroids3.length;o++)
      {
        if(asteroids3[o]!=null)
          asteroids3[o].setVelocity(new double[]{0,0});
      }
    }
  }
  public boolean checkCollision(Polygon first, Polygon second)
  {
    for(Point p : second.getPoints())
    {
      if(first.contains(p))
        return true;
    }
    for(Point p : first.getPoints())
    {
      if(second.contains(p))
        return true;
    }
    return false;
  }
  public void asteroids3Things(Graphics brush)
  {
    for(int i=0;i<asteroids3.length;i++)
    {
      if(asteroids3[i]!=null)
      {
        asteroids3[i].paint(brush);
        asteroids3[i].move();
        
        if(checkCollision(ship,asteroids3[i]))
        {
          while(respawnClear==false)
          {
            asteroids3[i].move();
            respawnClear = checkRespwan();
          }
          ship.collide(asteroids3[i]);
          respawnClear=false;
        }
        for(int q =0;q<pewpew.length;q++)
        {
          if(pewpew[q]!=null)
          {
            if(checkCollision(asteroids3[i],pewpew[q]))
            {
              pewpew[q]=null;
              makeMedAst(asteroids3[i]);
              asteroids3[i]=null;
              return;
            }
          }
        }
      }
    }
  }
  public void asteroids2Things(Graphics brush)
  {
    for(int i=0;i<asteroids2.length;i++)
    {
      if(asteroids2[i]!=null)
      {
        asteroids2[i].paint(brush);
        asteroids2[i].move();
        if(checkCollision(ship,asteroids2[i]))
        {
          while(respawnClear==false)
          {

            respawnClear = checkRespwan();
          }
          ship.collide(asteroids2[i]);
          makeSmallAst(asteroids2[i]);
          asteroids2[i] = null;
          respawnClear = false;
        }
        for(int x=0;x<pewpew.length;x++)
        {
          if(pewpew[x]!=null)
          {
            if(checkCollision(asteroids2[i],pewpew[x]))
            {
              pewpew[x]=null;
              makeSmallAst(asteroids2[i]);
              asteroids2[i]=null;
              return;
            }
          }
        }
      }
    }
  }
  public void asteroids1Things(Graphics brush)
  {
    for(int i=0;i<asteroids1.length;i++)
    {
      if(asteroids1[i]!=null)
      {
        asteroids1[i].paint(brush);
        asteroids1[i].move();
        if(checkCollision(ship,asteroids1[i]))
        {
          while(respawnClear==false)
          {
            asteroids1[i].move();
            respawnClear = checkRespwan();
          }
          ship.collide(asteroids1[i]);
          respawnClear=false;
        }
        for(int x=0;x<pewpew.length;x++)
        {
          if(pewpew[x]!=null)
          {
            if(checkCollision(asteroids1[i],pewpew[x]))
            {
              pewpew[x]=null;
              makeBigAst();
              asteroids1[i]=null;
              return;
            }
          }
        }
      }
    }
  }
  public void makeBigAst()
  {
    for(int i=0;i<asteroids3.length;i++)
    {
      if(asteroids3[i]==null)
      {
        rx = Math.random()*800;
        ry = Math.random()*600;
        y = Math.random()*360;
        asteroids3[i] = new Asteroid(3,new Point(rx,ry),y);
        asteroids3[i].thrust(.5);
      }
      return;
    }
  }
  public void makeMedAst(Asteroid a)
  {
    for(int x=0; x<asteroids2.length;x++)
    {
      if(asteroids2[x]==null)
      {
        asteroids2[x] = new Asteroid(2,a.getPosition(),a.getRotation());
        asteroids2[x].thrust(.5);
        for(int i=0; i<asteroids2.length;i++)
        {
          if(asteroids2[i]==null)
          {
            Point p = a.getPosition();
            double pX = p.x;
            double pY= p.y;
            double rot = 0-a.getRotation();
            asteroids2[i] = new Asteroid(2,new Point(pX+75,pY),rot);
            asteroids2[i].thrust(.5);
            return;
          }
        }
      }
    }
  }
  public void makeSmallAst(Asteroid a)
  {
    for(int x=0; x<asteroids1.length;x++)
    {
      if(asteroids1[x]==null)
      {
        asteroids1[x] = new Asteroid(1,a.getPosition(),a.getRotation());
        asteroids1[x].thrust(.5);
        for(int i=0; i<asteroids1.length;i++)
        {
          if(asteroids1[i]==null)
          {
            Point p = a.getPosition();
            double pX = p.x;
            double pY= p.y;
            double rot = 0-a.getRotation();
            asteroids1[i] = new Asteroid(1,new Point(pX+25,pY),rot);
            asteroids1[i].thrust(.5);
            return;
          }
        }
      }
    }
  }
  public boolean checkRespwan()
  {
    Point[] respawn = new Point[]{new Point(400,275),new Point(425,300),new Point(400,325),new Point(375,300)};
    for(int r=0;r<respawn.length;r++)
    {
      for(int b=0;b<asteroids3.length;b++)
      {
        if(asteroids3[b]!=null)
        {
          if(asteroids3[b].contains(respawn[r]))
          {
            asteroids3[b].move();
            return false;
          }
        }
      }
      for(int m=0;m<asteroids2.length;m++)
      {
        if(asteroids2[m]!=null)
        {
          if(asteroids2[m].contains(respawn[r]))
          {  
            asteroids2[m].move();
            return false;
          }
        }
      }
      for(int s=0; s<asteroids1.length;s++)
      {
        if(asteroids1[s]!=null)
        {
          if(asteroids1[s].contains(respawn[r]))
          {  
            asteroids1[s].move();
            return false;
          }
        }
      }
    }
    return true;
  }
}