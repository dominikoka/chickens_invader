import java.awt.image.BufferedImage;

public class Rocket {
  private int x;
  private int y;
  private int attackSpeed;
  int speedOfAttacker = attackSpeed;
  private int life;
  BufferedImage img;
  int nbProjectile;


  public Rocket(int x, int y, int life, int attackSpeed, BufferedImage img, int nbProjectile,BufferedImage rocketImg)
  {
    this.x=x;
    this.y=y;
    this.life = life;
    this.attackSpeed = attackSpeed;
    this.img = img;
    this.nbProjectile = nbProjectile;
    this.img = rocketImg;
  }

  public BufferedImage setImg(BufferedImage img){return this.img = img;}
  public BufferedImage getImg(){return this.img;}
  //public void BufferedImage setImg(BufferedImage skin){this.img = skin;}
  public void moveLeft()
  {
    this.x = x-5;
  }
  public void moveRight()
  {
    this.x=x+5;
  }
  public int getX()
  {
    return this.x;
  }
  public void setX(int x)
  {
    this.x = x;
  }
  public void setY(int y)
  {
    this.y = y;
  }
  public int getY()
  {
    return this.y;
  }
  public int getMyLife()
  {
    return this.life;
  }
  public void loseLife()
  {
    this.life=life-1;
  }
  public void setLife(int life){this.life = life;}
  public void increaseLife()
  {
    this.life=life+1;
  }
  public int getAttackSpeed()
  {
    return this.attackSpeed;
  }
  public int getNbProjectile(){return this.nbProjectile;}
  public void setNbProjectile(int nb){this.nbProjectile=nb;}





  //public int getAttackSpeed(){return this.attackSpeed;}
}
