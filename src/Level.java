import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Level {
  //constructor variables
  List<Enemy> enemies; //load enemies
  List<List<Integer>> enemiesCoordinatesXY = new ArrayList<>();
  List<Projectile> projectiles = new ArrayList<>();
  List<Projectile> projectileRocketDmg = new ArrayList<>();

  //  Rocket rocket;
  final BufferedImage rocket_1 = ImageIO.read(new File("src/img/rocket_1.png"));
  Rocket rocket = new Rocket(400, 700, 1, 10, rocket_1, 1, rocket_1);

  EventColision collision;
  Screen screen;
  Shop shop;
  int numberAttacker;
  int threadSleep;
  Bonus bonus;
  int oneSecHelper;
  int speedOfAttackerCounter;
  int speedOfAttacker = speedOfAttackerCounter;
  int enemyQuantity;
  int rangeMove;
  int speed;
  int st;
  int speedFallDownDmg;
  //List<BufferedImage> enemyImg;
  int rocketNbProjectile;
  int lvlNb;
  List<List<BufferedImage>> rocketShop;
  Boolean shopOpen = false;
  int clickedNb = 0;


  public Level(List<Enemy> enemy, List<Projectile> projectiles, Rocket rocket, EventColision collision, Screen screen, Bonus bonus, int threadSleep, Shop sh, List<List<BufferedImage>> rocketShop) throws IOException {
    this.enemies = enemy;
    this.projectiles = projectiles;
    this.rocket = rocket;
    this.collision = collision;
    this.screen = screen;
    speedOfAttackerCounter = rocket.getAttackSpeed();
    speedOfAttacker = speedOfAttackerCounter;
    this.bonus = bonus;
    this.threadSleep = threadSleep;
    oneSecHelper = 1000 / this.threadSleep;
    this.rocketNbProjectile = rocket.getNbProjectile();
    this.shop = sh;
    this.rocketShop = rocketShop;
  }

  public String showStatsRocketLife() {
    return String.valueOf(this.screen.getMyLife());
  }


  public void startLevel(int enemyQuantity, int rangeMove, int speed, int st, int speedFallDownDmg, int levelNb) {
    this.enemyQuantity = enemyQuantity;
    this.rangeMove = rangeMove;
    this.speed = speed;
    this.st = st;
    this.speedFallDownDmg = speedFallDownDmg;
    screen.setBullet(rocket.getNbProjectile());
    this.lvlNb = levelNb;
  }


  BufferedImage background;
  int lastSizeProjectileRocket = 0;
  boolean checkNewObj = true;

  private Boolean checkIfIsNewProjectile(int newSizeProjectileRocket, int lastSizeProjectileRocket) {
    return newSizeProjectileRocket != lastSizeProjectileRocket;
  }

  private void projectileRocketDmgService() {
    int sizeProjectileRocket = projectileRocketDmg.size();
    if (rocketNbProjectile < 5) {
      if (!projectileRocketDmg.isEmpty() && checkIfIsNewProjectile(lastSizeProjectileRocket, sizeProjectileRocket)) {
         projectileRocketDmg = setPositionProjectiles(projectileRocketDmg, rocketNbProjectile);
        lastSizeProjectileRocket = projectileRocketDmg.size();
        checkNewObj = false;
      }
      for (Projectile projectile : projectileRocketDmg) {
        projectile.fallDown(-1, speedFallDownDmg, basicHeight, 0);
      }
      projectileRocketDmg = deleteProjectileEnemies(projectileRocketDmg);
      lastSizeProjectileRocket = projectileRocketDmg.size();
    } else {
      int range = -4;
      for (int i = 0; i < projectileRocketDmg.size(); i++) {
        projectileRocketDmg.get(i).fallDown(-1, speedFallDownDmg, basicHeight, range);
        range += 2;
        if (range == 6) {
          range = -4;
        }
        if (projectileRocketDmg.get(i).getY() < -200) {
          projectileRocketDmg.subList(0, rocketNbProjectile).clear();
          range = -4;
        }
      }
    }
  }

  public List<Projectile> setPositionProjectiles(List<Projectile> projectiles, int projectileNbProjectile) {
    if (projectileNbProjectile == 2) {
      projectiles.get(projectiles.size() - 1).setX((int) projectiles.get(projectiles.size() - 1).getX() - 10);
      projectiles.get(projectiles.size() - 2).setX((int) projectiles.get(projectiles.size() - 2).getX() + 10);
    }
    if (projectileNbProjectile == 3) {
      projectiles.get(projectiles.size() - 1).setX((int) projectiles.get(projectiles.size() - 1).getX() + 20);
      projectiles.get(projectiles.size() - 3).setX((int) projectiles.get(projectiles.size() - 3).getX() - 20);
    }
    return projectiles;
  }

  public List<Projectile> deleteProjectileEnemies(List<Projectile> projectiles) {
    for (int i = 0; i < projectiles.size(); i++) {
      if (projectiles.get(i).getY() < 0) {
        projectiles.remove(i);
      }
    }
    return projectiles;
  }

  public int shopItemNumberToChange(int keyboardNb, Screen screen, List<Integer> price, List<Integer> buyedItems) {
    int result = -1;
    int score = screen.getMyScore();
    if (shop.ifYouHaveMoney(keyboardNb, score, price) || buyedItems.contains(shop.changeEKeyToKeyboardNb(keyboardNb))) {
      int nbToDelete = price.get(shop.changeEKeyToKeyboardNb(keyboardNb));
      if (!buyedItems.contains(shop.changeEKeyToKeyboardNb(keyboardNb))) {
        screen.decreaseScore(nbToDelete);
      }
      if (keyboardNb <= 54) {
        rocket.setImg(rocketShop.getFirst().get(shop.changeEKeyToKeyboardNb(keyboardNb)));
      } else {
        int nB = shop.changeEKeyToKeyboardNb(keyboardNb);
        screen.setBackground(rocketShop.getFirst().get(shop.changeEKeyToKeyboardNb(keyboardNb)));
      }
      result = shop.changeEKeyToKeyboardNb(keyboardNb);
    }
    return result;
  }

  //bonus proccesor
  boolean chooseEnemiesBonus = true;
  int bonusCounter = 3;
  int counterbbb = 1;
  int loadBonus = 0;
  int counterBonusColor = 0;

  //bonus processor
  public void bonusProcessor() {
    if (bonus.getY() <= rocket.getY() && bonusCounter != -1) {
      if (chooseEnemiesBonus) {
        var choosenEnemiesBonus = bonus.chooseEnemyBonus(enemies);
        bonus = bonus.bonusUpdatePosition(choosenEnemiesBonus, bonus);
        chooseEnemiesBonus = false;
      }
      bonus = bonus.bonusMovement(bonus, rocket, bonusCounter);
      if (counterBonusColor % 10 == 0) {
        bonus = bonus.bonusMovementColor(bonus, rocket, bonusCounter);
        counterBonusColor = 0;
      }
      counterBonusColor++;
    } else {
      if (oneSecHelper * counterbbb % 2000 == 0 && bonusCounter != -1) {
        this.bonusCounter--;
      }
      if (bonus.getY() >= rocket.getY()) {
        counterbbb++;
      }
      bonus = bonus.bonusCounter(bonus, rocket, bonusCounter);
      if (bonusCounter == -1) {
        bonus = bonus.removeBonusAfterTime(bonus);
        if (loadBonus == 100) {
          chooseEnemiesBonus = true;
          bonusCounter = 3;
          loadBonus = 0;
          counterbbb = 1;
        }
        loadBonus++;
      }
      if (collision.checkCollisionWithBonus(bonus, rocket)) {
        var newBonus = bonus.setBonus(new Random().nextInt(4));
        if (newBonus != 0) {
          rocketNbProjectile = newBonus;
          rocket.setNbProjectile(rocketNbProjectile);
          screen.setBullet(newBonus);
        } else {
          rocket.increaseLife();
        }
        this.bonusCounter = -1;
      }
    }
  }

  Boolean loadEnemiesProjectile = true;

  public void update(int btn) throws InterruptedException {
    canIAttack(speedOfAttackerCounter);
    screen.setMyLife(rocket.getMyLife());
    //update enemies cordinate
    enemies = updatePosition(enemies, 20);
    //take enemies coordinates
    enemiesCoordinatesXY = EnemyCoordinatesXY(enemies);
    //bonus
    bonusProcessor();
    //SHOP

    //bullet create, load
    for (int i = 0; i < projectiles.size(); i++) {
      if (projectiles.get(i).getY() == -100 || projectiles.get(i).getX() == 0) {
        var chooseEnemytoDmg = chooseEnemyToDmg(enemiesCoordinatesXY);
        projectiles.get(i).setX(chooseEnemytoDmg.getFirst());
        projectiles.get(i).setY(chooseEnemytoDmg.getLast());
      } else {
        projectiles.get(i).fallDown(1, 5, basicHeight, 0);
      }
    }


    projectileRocketDmgService();


    collision.checkCollisionWithRocket(projectiles, rocket.getX(), rocket.getY());
    if (collision.getObjNb() != 99) {
      //System.out.println(c1.getObjNb() + "konflikt z rakieto");
      rocket.loseLife();
      var newC = chooseEnemyToDmg(enemiesCoordinatesXY);
      projectiles.get(collision.getObjNb()).setX(newC.get(0));
      projectiles.get(collision.getObjNb()).setY(newC.get(1));
    }
    collision.checkCollisionWithEnemy(projectileRocketDmg, enemies);
    if (collision.getObjNb() != 99) {
      for (int i = 0; i < collision.getEnemyCollisionWithProjectile().size(); i++) {
        int listOfProjectileCollision = collision.getProjectileCollisionWithEnemy().get(i);
        projectileRocketDmg.get(listOfProjectileCollision).setY(-100);
        var listDescendingCollisionEnemy = collision.getEnemyCollisionWithProjectile();
        listDescendingCollisionEnemy.sort(Collections.reverseOrder());
        var singleNbCollisionEnemy = listDescendingCollisionEnemy.get(i);
        enemies.get(singleNbCollisionEnemy).removeStability();
        //if(enemiesCordinatesObj.get(c1.getObjNb()).getStability() == 0)
        if (enemies.get(singleNbCollisionEnemy).getStability() == 0) {
          //aaenemiesCordinatesObj.remove(c1.getObjNb());
          enemies.remove((int) singleNbCollisionEnemy);
          screen.increaseScore();
        }
      }
    }
    if (rocket.getMyLife() == -1) {
      endGame = false;
    }
    //decrease time ready to fight
    if (speedOfAttackerCounter != 0) {
      speedOfAttackerCounter--;
    }


    //BTN SHOP

//  if(shop.getShopOpen()!=null && shop.getShopOpen())
//  {
//    background = screen.getBackground();
//    screen.setBackground(shop.getShopImg());
//    System.out.printf("tera je otwarty");
//    if(uniqueBtn)
//    {
//      System.out.printf("unikalny klawisz bebe");
//    }
//  }
//  else
//  {
//    screen.setBackground(background);
//
//  }
//  clickedNb = btn;
  }

  public Boolean ifClickedUniqueBtn(Integer x, Integer y) {
    return !Objects.equals(x, y);
  }

  public int getSpeedAttacker() {
    return speedOfAttackerCounter;
  }

  boolean checkResolution = true;
  boolean changeResolution = true;
  int basicWidth;
  int basicHeight;

  public boolean ifChangedResolution(int basicWidth, int basicHeight) {

    return false;
  }

  public List<Integer> changeResolution(int width, int height, int actuallBckg) {
    List<Integer> result = new ArrayList<>();
    int resolution;
    if (checkResolution) {
      basicWidth = width;
      basicHeight = height;
      checkResolution = false;
    }

    if (width != basicWidth) {
      double diffX = (double) width / basicWidth;
      double diffY = (double) height / basicHeight;
      for (int i = 0; i < enemies.size(); i++) {
        double enemiesX = enemies.get(i).getX();
        double enemiesY = enemies.get(i).getY();
        enemies.get(i).setX(enemiesX * diffX);
        enemies.get(i).setY(enemiesY * diffY);
        checkResolution = true;
      }
      rocket.setX((int) (rocket.getX() * diffX));
      rocket.setY((int) (rocket.getY() * diffY));
    }
    if (width <= 850) {
      resolution = 0;
    } else {
      resolution = 1;
    }
    result.add(resolution);
    result.add(actuallBckg);

    //TODO poprawic responsywność
    return result;
  }

  boolean endGame = true;

  public boolean endGame() {
    return endGame;
  }

  public int getScore() {
    return screen.getMyScore();
  }

  //public List<Enemy> getEnemiesCordinate()
//{
//  return this.enemiesCordinatesObj ;
//}
  public List<Projectile> getProjectileRocketDmg() {
    return this.projectileRocketDmg;
  }


  boolean ifNbEnemiesAreLoad = true;

  public List<List<Integer>> EnemyCoordinatesXY(List<Enemy> enemies) throws InterruptedException {
    List<Integer> singleEnemyCordinate = new ArrayList<Integer>();
    List<List<Integer>> enemiesCordinates = new ArrayList<>();
    for (Enemy enemy : enemies) {
      singleEnemyCordinate = new ArrayList<>();
      singleEnemyCordinate.add((int) enemy.getX());
      singleEnemyCordinate.add((int) enemy.getY());
      enemiesCordinates.add(singleEnemyCordinate);
    }
    return enemiesCordinates;
  }

  public List<Enemy> updatePosition(List<Enemy> enemies, int range) {
    List<Enemy> updatedEnemies = new ArrayList<>();
    for (Enemy enemy : enemies) {
      enemy.move(range);
      updatedEnemies.add(enemy);
    }
    return updatedEnemies;
  }

  public void newGame() {
    rocket.setLife(5);
    endGame = true;
  }


  //List<Projectile> enemyDmg = new ArrayList<>();
  private boolean projectiveEnemyLoad = true;

  public List<Projectile> getEnemiesProjectile() {
    return projectiles;
  }

//  public void enemiesDmg(int attackSpeed, int attackerNumber, List<Projectile> projectiles) {
//    List<Integer> enemyAttacker;
//   // enemyDmg = projectiles;
//    for (Projectile projectile : projectiles) {
////      if (projectiveEnemyLoad) {
////        enemyAttacker = chooseEnemyToDmg(enemiesCoordinatesXY);
////        projectile.setX(enemyAttacker.get(0));
////        projectile.setY(enemyAttacker.get(1));
//      //}
//      //else {
//        enemyAttacker = new ArrayList<>();
////        if (projectile.getY() == -100) {
////          enemyAttacker = chooseEnemyToDmg(enemiesCoordinatesXY);
////          projectile.setX(enemyAttacker.get(0));
////          projectile.setY(enemyAttacker.get(1));
////        }
//      //}
//    }
//  }

  public List<Integer> chooseEnemyToDmg(List<List<Integer>> enemiesCoordinatesXY) {
    int maxNbEnemy = enemiesCoordinatesXY.size();
    Random rd = new Random();
    return enemiesCoordinatesXY.get(rd.nextInt(maxNbEnemy));
  }

//  private List<Projectile> prepareObjEnemyDmg(int numberAttacker) {
//    return this.projectiles;
//  }

  public boolean endLvl() {
    return enemies.isEmpty();
  }

  public void addProjectileRocketDmg(Projectile pr) {
    projectileRocketDmg.add(pr);
  }

  public boolean canIAttack(int speed) {
    return speed == 0;
  }

  public void resetAttack(int speedAttackerCounter, Rocket rocket) {
    speedOfAttackerCounter = rocket.getAttackSpeed();
  }

  public List<Enemy> getEnemies() {
    return enemies;
  }

  public String getAttackReady() {
    return String.valueOf((speedOfAttacker - speedOfAttackerCounter) / (speedOfAttacker / 10));
  }


}
