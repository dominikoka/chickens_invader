import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

public class Bonus {
  private int x;
  private int y;
  private BufferedImage image;
  private String symbolToShow;
  private Color color;

  public void fallDown(int speed)
  {
    y = y+ speed;
  }
  public int getX() {return this.x;}
  public void setX(int x) {this.x = x;}
  public int getY() {return this.y;}
  public void setY(int y) {this.y = y;}
  public Color getColor() {return this.color;}
  public void setColor(Color color) {this.color = color;}

  public String getSymbolToShow() {return this.symbolToShow;}
  public void setSymbolToShow(String symbol) {this.symbolToShow = symbol;}

  public Enemy chooseEnemyBonus(List<Enemy> enemies)
  {
    int range = new Random().nextInt(enemies.size());
    return enemies.get(range);
  }
  public Bonus bonusUpdatePosition(Enemy enemy, Bonus bonus){
    //var chosenEnemyToBonus = bonus.chooseEnemyBonus(enemies);
    bonus.setX((int) enemy.getX());
    bonus.setY((int) enemy.getY());
    bonus.setSymbolToShow("?");
    return bonus;
  }
  public Bonus bonusMovement(Bonus bonus, Rocket rocket, int bonusCounter)
  {
    if (bonus.getY() <= rocket.getY() && bonusCounter != -1) {
      bonus.fallDown(5);}
    return bonus;
  }
  public Bonus bonusMovementColor(Bonus bonus, Rocket rocket, int bonusCounter)
  {
    if (bonus.getY() <= rocket.getY() && bonusCounter != -1) {
      bonus.setColor(generateRandomColor());
    }
    return bonus;
  }
  public Bonus bonusCounter(Bonus bonus, Rocket rocket, int nbToShow)
  {
    if(bonus.getY() >= rocket.getY()) {
      bonus.setSymbolToShow(String.valueOf(nbToShow));
    }
    return bonus;
  }
  public Bonus removeBonusAfterTime(Bonus bonus )
  {
    bonus.setX(-111);
    bonus.setY(-111);
    return bonus;
  }
  public Color generateRandomColor() {
    int r = new Random().nextInt(255);
    int g = new Random().nextInt(255);
    int b = new Random().nextInt(255);
    return new Color(r, g, b);
  }
  public Integer setBonus(int randomNb) {
    int result = 0;
    if (randomNb == 0) {
      result =2;
    } else if (randomNb == 1) {
      result =3;
    } else if (randomNb == 2) {
      result =5;
    }
    return result;
  }


}
