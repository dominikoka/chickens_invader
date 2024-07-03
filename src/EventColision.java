import java.util.ArrayList;
import java.util.List;

public class EventColision {

  private int objectNumber = 0;

  public void checkCollisionWithRocket(List<Projectile> enemyDmg, int x, int y) {
    this.objectNumber = 99;
    for (int i = 0; i < enemyDmg.size(); i++) {
      if (enemyDmg.get(i).getX() + 20 > x && enemyDmg.get(i).getX() - 20 < x && enemyDmg.get(i).getY() + 20 > y && enemyDmg.get(i).getY() - 20 < y) {
        this.objectNumber = i;
      }
    }
  }
  public boolean checkCollisionWithBonus(Bonus bonus, Rocket rocket) {
    return bonus.getX() + 20 > rocket.getX() && bonus.getX() - 20 < rocket.getX() && bonus.getY() + 20 > rocket.getY() && bonus.getY() - 20 < rocket.getY();
  }
  List<Integer> projectileCollisionWithEnemy = new ArrayList<Integer>();
  List<Integer> enemyCollisionWithProjectile = new ArrayList<Integer>();

  public void checkCollisionWithEnemy(List<Projectile> myRocketDmg, List<Enemy> enemyCordinate) {
    projectileCollisionWithEnemy = new ArrayList<>();
    enemyCollisionWithProjectile = new ArrayList<>();
    this.objectNumber = 99;
    for (int i = 0; i < enemyCordinate.size(); i++) {
      for (int j = 0; j < myRocketDmg.size(); j++) {
        if (enemyCordinate.get(i).getX() + 20 > myRocketDmg.get(j).getX() && enemyCordinate.get(i).getX() - 20 < myRocketDmg.get(j).getX() && enemyCordinate.get(i).getY() + 20 > myRocketDmg.get(j).getY() && enemyCordinate.get(i).getY() - 20 < myRocketDmg.get(j).getY()) {
          enemyCollisionWithProjectile.add(i);
          projectileCollisionWithEnemy.add(j);
          objectNumber = i;
        }
      }
    }
  }
  public List<Integer> getProjectileCollisionWithEnemy()
  {
    return projectileCollisionWithEnemy;
  }
  public List<Integer> getEnemyCollisionWithProjectile()
  {
    return enemyCollisionWithProjectile;
  }


  public int getObjNb()
  {
    return this.objectNumber;
  }
}