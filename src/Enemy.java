import java.awt.image.BufferedImage;
import java.io.IOException;

public class Enemy {

  private double x;
  private double y;
  private int stability;
  BufferedImage image;

  public Enemy(int x, int y, int stability, BufferedImage img) throws IOException {
    this.x = x;
    this.y = y;
    this.stability = stability;
    this.image = img;
  }

  public BufferedImage getImage()
  {
    return this.image;
  }
  public int getStability()
  {
    System.out.println(stability);
    return this.stability;
  }
  public void removeStability()
  {
    this.stability = stability-1;
    System.out.println(stability);
  }
  public double getX() {
    return this.x;
  }
  public void setX(double x) {
    this.x = x;
  }
  public void setY(double y) {
    this.y = y;
  }
  public double getY() {
    return this.y;
  }
  int rangeCounterShake =0;
  int valueChangerShake = 1;
  int valueYChangerShake = -1;
  boolean loadInputCoordinatesShake =true;
  double inputYShake;
  int inputXShake;
  public void shake(int range)
  {
    if(loadInputCoordinatesShake)
    {
      inputYShake = this.y;
      loadInputCoordinatesShake = false;
      inputXShake =range/2;
    }
    double c = (double) range /2;
    rangeCounterShake += valueChangerShake;
    this.x += valueChangerShake;
    if(rangeCounterShake ==range || rangeCounterShake ==0)
    {
      valueChangerShake *=-1;
      valueYChangerShake *=-1;
    }
    double pitagorasC = (Math.pow(c, 2));
    double pitagorasB = Math.pow(inputXShake, 2);
    double pitagorasA = valueChangerShake *(Math.sqrt(pitagorasC-pitagorasB));
    inputXShake += valueYChangerShake;
  }

  int valueChangerMoveEnemy = 1;
  int rangeMoveEnemy = 0;
  public void move(int range)
  {
    rangeMoveEnemy+=valueChangerMoveEnemy;
    this.x+= valueChangerMoveEnemy;
    if(rangeMoveEnemy==range || rangeMoveEnemy  ==0)
    {
      valueChangerMoveEnemy*=-1;
    }
  }


}
