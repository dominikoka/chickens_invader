import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Projectile {

  //boolean isLoadAttackEnemyCordinate = true;
  private double x;
  private double y;

  BufferedImage img;

  public Projectile(BufferedImage img)
  {
    this.img = img;
  }
  public Projectile(BufferedImage img, double x, double y)
  {
    this.img = img;
    this.x = x;
    this.y = y;
  }

  public void fallDown(int direction,int speed, int height, int moveX)
  {
    List<Integer> cordinates = new ArrayList<Integer>();
    this.y = y +(speed*direction);
    cordinates.add(1);
    if(this.y > height)
    {
      this.y = -100;
    }
    this.x += moveX;
  }



  public BufferedImage setImg(BufferedImage img){return this.img = img;}
  public BufferedImage getImg(){return this.img;}
  public double getX()
  {
    return this.x;
  }
  public void setX(double x) {
    this.x = x;
  }
  public double getY()
  {
    return this.y;
  }
  public void setY(double y) {
    this.y = y;
  }
}
