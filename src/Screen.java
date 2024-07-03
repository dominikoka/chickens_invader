import java.awt.image.BufferedImage;

public class Screen {

  int score = 0;
  private int myLife;
  //int time;
  int bullet;
  BufferedImage image;

  private int readyToAttack;

  public Screen(BufferedImage bckg1) {
    //setBackground(bckg1);
    this.image = bckg1;
  }


  public void setBackground(BufferedImage bckg) {
    this.image = bckg;
  }
  public BufferedImage getBackground() {
    return this.image;
  }
  public int getMyLife()
  {
    return  myLife;
  }
  public void setMyLife(int life)
  {
    this.myLife = life;
  }
  public String getBullet(){return String.valueOf(bullet);}
  public BufferedImage getBBufferedImage(){return image;}
  public void setBBufferedImage(BufferedImage image){this.image = image;}
  public void setBullet(int a){this.bullet = a;}

  public int getMyScore()
  {
    return score;
  }
  public void increaseScore()
  {
    this.score++;
  }
  public void decreaseScore(int nb)
  {
    this.score -=nb;
  }

}
