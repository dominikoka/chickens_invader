import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;


class Graphic extends JPanel implements MouseListener, KeyListener {
  public Graphic() throws IOException {
    int sizeMap = 800;
    setPreferredSize(new Dimension(sizeMap, sizeMap));
  }

  int lvlNb = 0;
  int endScore;
  Level lvl;
  int sizeEnemy = 20;
  int enemyQuantityRow = 4;
  int stability = 2;
  int projectileDmg = 4;
  int speedRocketAttack = 40;
  int shopKeyboardNumber = 0;
  String nameSave = "saveScore";
  //List<Enemy> enemiesCordinatesObj = new ArrayList<>();
  //List<Projectile> projectileRocketDmg = new ArrayList<>();
  //List<Projectile> enemiesDmgCoordinatesObj = new ArrayList<>();
  //List<Projectile> enemiesDmgCoordinates = new ArrayList<>();
  final BufferedImage chickenImg_1 = ImageIO.read(new File("src/img/chicken_1_20x20.png"));
  final BufferedImage chickenImg_2 = ImageIO.read(new File("src/img/chicken_2_20x20.png"));
  final BufferedImage projectile_1 = ImageIO.read(new File("src/img/egg_1_30x30.png"));
  final BufferedImage projectile_2 = ImageIO.read(new File("src/img/egg_2_30x30.png"));
  final BufferedImage bullet_1 = ImageIO.read(new File("src/img/bullet_1_15x15.png"));
  final BufferedImage life = ImageIO.read(new File("src/img/heart_1_15x15.png"));
  final BufferedImage rocket_1 = ImageIO.read(new File("src/img/rocket_1.png"));
  final BufferedImage rocket_2 = ImageIO.read(new File("src/img/rocket_2.png"));
  final BufferedImage rocket_3 = ImageIO.read(new File("src/img/rocket_3.png"));
  final BufferedImage rocket_4 = ImageIO.read(new File("src/img/rocket_4.png"));
  final BufferedImage rocket_5 = ImageIO.read(new File("src/img/rocket_5.png"));
  final BufferedImage rocket_6 = ImageIO.read(new File("src/img/rocket_6.png"));
  final BufferedImage bckg_1 = ImageIO.read(new File("src/img/backg_1.jpg"));
  final BufferedImage bckg_2 = ImageIO.read(new File("src/img/backg_2.jpg"));
  final BufferedImage bckg_3 = ImageIO.read(new File("src/img/backg_3.jpg"));
  final BufferedImage bckg_4 = ImageIO.read(new File("src/img/backg_4.jpg"));
  final BufferedImage bckg_1FHD = ImageIO.read(new File("src/img/backg_1FHD.jpg"));
  final BufferedImage bckg_2FHD = ImageIO.read(new File("src/img/backg_2FHD.jpg"));
  final BufferedImage bckg_3FHD = ImageIO.read(new File("src/img/backg_3FHD.jpg"));
  final BufferedImage bckg_4FHD = ImageIO.read(new File("src/img/backg_4FHD.jpg"));
  final BufferedImage bckg_1ICON = ImageIO.read(new File("src/img/backg_1ICON.jpg"));
  final BufferedImage bckg_2ICON = ImageIO.read(new File("src/img/backg_2ICON.jpg"));
  final BufferedImage bckg_3ICON = ImageIO.read(new File("src/img/backg_1ICON.jpg"));
  final BufferedImage bckg_4ICON = ImageIO.read(new File("src/img/backg_4ICON.jpg"));
  BufferedImage shopBckg_1FHD = ImageIO.read(new File("src/img/shopFHD.jpg"));
  BufferedImage shopBckg_1800x800 = ImageIO.read(new File("src/img/shop800x800.jpg"));
  List<BufferedImage> shopBckg = new ArrayList<>(Arrays.asList(shopBckg_1800x800, shopBckg_1FHD));
  List<Integer> buyedItems = new ArrayList<>();
  int threadSleep = 10;
  boolean shopMenu = false;
  //numer ktory zmienia wczytywanie tla grafiki 0,1
  List<Integer> changeResolution = new ArrayList<>();
  List<Integer> score = new ArrayList<>();


  //size screen
  //jakies bonusy apteczka
  List<Integer> shopPrice = new ArrayList<>(Arrays.asList(5, 6, 7, 8, 9, 0, 3, 3, 0));

  List<BufferedImage> shopImg800 = new ArrayList<>(Arrays.asList(rocket_2, rocket_3, rocket_4, rocket_5, rocket_6, rocket_1, bckg_2, bckg_4, bckg_1));
  List<BufferedImage> shopImgFHD = new ArrayList<>(Arrays.asList(rocket_2, rocket_3, rocket_4, rocket_5, rocket_6, rocket_1, bckg_2FHD, bckg_4FHD, bckg_1FHD));
  List<BufferedImage> shopImgIcon = new ArrayList<>(Arrays.asList(rocket_2, rocket_3, rocket_4, rocket_5, rocket_6, rocket_1, bckg_2ICON, bckg_4ICON, bckg_1ICON));
  List<List<BufferedImage>> shopImg = new ArrayList<>(Arrays.asList(shopImg800, shopImgFHD, shopImgIcon));

  //int clickedBtn=-1;

  EventColision c1 = new EventColision();
  Screen screen = new Screen(bckg_1);
  Rocket rocket = new Rocket(400, 700, 1, 15, rocket_1, 5, rocket_1);
  Bonus bonus = new Bonus();
  Shop shop = new Shop(screen, shopImg, shopPrice, shopBckg_1800x800);
  Save save = new Save(nameSave);
  int clickedUniqueBtn = 0;
  boolean ifNbClicked = false;
  int clickedBtn = 0;
  int clickedBtn2Back = 0;
  Boolean takeScore = true;
  List<Integer> choosedRockedAndBackGround = new ArrayList<>(Arrays.asList(5, 9));
  BufferedImage backgroundImg = screen.getBackground();

  public void processor() throws InterruptedException, IOException {
    while (true) {
      takeScore = true;
      lvlNb++;
      lvl = new Level(createEnemies(stability, enemyQuantityRow, chickenImg_1, chickenImg_2), createProjectile(projectileDmg, projectile_1, projectile_2), rocket, c1, screen, bonus, threadSleep, shop, shopImg);
      lvl.startLevel(5, 1, 5, 5, 10, lvlNb);
      while (!lvl.endLvl()) {
        int widthWindows = getWidth();
        int heightWindows = getHeight();
        changeResolution = lvl.changeResolution(widthWindows, heightWindows, choosedRockedAndBackGround.get(1));

        screen.setBackground(shopImg.get(changeResolution.get(0)).get(changeResolution.get(1) - 1));
        //screen.setBackground(shopImg.get(1).get(6));

        repaint();

        if (lvl.endGame()) {
          if (!shopMenu) {
            clickedBtn2Back = clickedBtn;
            lvl.update(clickedBtn);
            if (clickedBtn == clickedBtn2Back) {
              clickedBtn = 0;
              // screen.setBackground(backgroundImg);
            }
          }
          {
            shopBckg_1800x800 = ImageIO.read(new File("src/img/shop800x800.jpg"));
            if (shop.getShopOpen()) {
              backgroundImg = screen.getBackground();
              //System.out.printf("jestes we sklepie klikles    0101010101010101010");
              //screen.setBackground(shopBckg.get(changeResolution.getFirst()));
              if (ifNbClicked) {
                int buyedNbItem = lvl.shopItemNumberToChange(shopKeyboardNumber, screen, shopPrice, buyedItems);
                if (buyedNbItem > 6) {
                  choosedRockedAndBackGround.set(1, buyedNbItem + 1);
                  backgroundImg = shopImg.getFirst().get(buyedNbItem);
                }
                if (buyedNbItem >= 0) {
                  choosedRockedAndBackGround.set(0, buyedNbItem);
                  shopPrice.set(buyedNbItem, 0);
                }
                ifNbClicked = false;
              }
            }
          }
        } else {


          endScore = lvl.getScore();
          if (takeScore) {
            save.save(lvl.getScore());
            score = save.load();
            takeScore = false;
          }
          repaint();
        }
        //enemiesCordinatesObj = lvl.getEnemiesCordinates();
        //projectileRocketDmg = lvl.getProjectileRocketDmg();
        //enemiesDmgCoordinates = lvl.getEnemiesDmg();
        //System.out.println(lvl.showStatsRocketLive());
        Thread.sleep(threadSleep);

//        if(speedOfAttacker!=0)
//        {
//          canIAttack = false;
//        speedOfAttacker--;}
//        else {
//          canIAttack=true;
//        }

      }
      enemyQuantityRow += 2;
      stability += 2;
      projectileDmg *= 2;

      //lv1(12,20,20,2,12);
    }
  }

  private List<Projectile> createProjectile(int quantity, BufferedImage img1, BufferedImage img2) {
    List<Projectile> pr = new ArrayList<>();
    for (int i = 0; i < quantity; i++) {
      if (i % 2 == 0) {
        pr.add(new Projectile(img1));
      } else {
        pr.add(new Projectile(img2));
      }
    }
    return pr;
  }

  private List<Enemy> createEnemies(int stability, int enemyQuantityRow, BufferedImage img1, BufferedImage img2) throws IOException {
    List<Enemy> enemies = new ArrayList<>();
    List<Integer> enemiesY = new ArrayList<>(Arrays.asList(50, 100, 150, 200, 250, 300));
    List<Integer> enemiesX = new ArrayList<>(Arrays.asList(50, 120, 190, 260, 330, 400, 470, 540, 610, 680));
    int counter = 0;
    enemyQuantityRow *= 10;
    for (int i = 0; i < enemiesY.size(); i++) {
      for (int j = 0; j < enemiesX.size(); j++) {
        if (counter < enemyQuantityRow) {
          int sum = i + j;
          if (sum % 2 == 0) {
            enemies.add(new Enemy(enemiesX.get(j), enemiesY.get(i), stability, img1));
          } else {
            enemies.add(new Enemy(enemiesX.get(j), enemiesY.get(i), stability, img2));
          }
        } else {
          return enemies;
        }
        counter++;
      }
    }
    return enemies;
  }

  //  boolean lv1Switch = true;
//  public void lv1(int enemyQuantity, int rangeMove, int speed, int st, int speedFallDownDmg) throws IOException, InterruptedException {
//    if(lv1Switch)
//    {
//      lv1Switch=false;
//    }
//    logic(enemyQuantity, rangeMove,speed,st,speedFallDownDmg);
//  }
//  public void logic(int enemyQuantity, int rangeMove, int speed, int st,int speedFallDownDmg) throws InterruptedException {
//
//    lvl.enemies(enemyQuantity,rangeMove,speed,st);
//    enemiesCordinatesObj=lvl.getEnemiesCordinates();
//    lvl.enemiesDmg(1,1);
//    enemiesDmgCoordinates = lvl.getEnemiesDmg();
//
//
//    for(int i=0;i<projectileRocketDmg.size();i++)
//    {
//      projectileRocketDmg.get(i).fallDown(-1,speedFallDownDmg);
//      if(projectileRocketDmg.get(i).getY()<0)
//      {
//        projectileRocketDmg.remove(i);
//      }
//    }
//    c1.checkCollisionWithRocket(enemiesDmgCoordinates,rocket.getX(),rocket.getY());
//    if(c1.getObjNb()!=99){
//      System.out.println(c1.getObjNb() + "konflikt z rakieto");
//      var newC = lvl.chooseEnemyToDmg();
//      enemiesDmgCoordinates.get(c1.getObjNb()).setX(newC.get(0));
//      enemiesDmgCoordinates.get(c1.getObjNb()).setY(newC.get(1));
//    }
//    c1.checkCollisionWithEnemy(projectileRocketDmg,enemiesCordinatesObj);
//    if(c1.getObjNb()!=99){
//      projectileRocketDmg.remove(0);
//      System.out.println(c1.getObjNb() + " ktory objekt");
//      enemiesCordinatesObj.get(c1.getObjNb()).removeStability();
//      if(enemiesCordinatesObj.get(c1.getObjNb()).getStability() == -8)
//        enemiesCordinatesObj.remove(c1.getObjNb());
//    }
//  }
  Boolean scoreSwitch = true;


  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
//    if(getWidth()<850){
    g2.drawImage(screen.getBackground(), 0, 0, null);
//    else
//    {
//      g2.drawImage(bckg_1FHD,0,0,null);
//    }
//    for (Enemy singleEnemy : lvl.getEnemiesCordinates()) {
//      //g2.setPaint(new Color(111,94,215));
//      //g2.fill(new Ellipse2D.Double(singleEnemy.getX(), singleEnemy.getY(), sizeEnemy, sizeEnemy));
//      g2.drawImage(singleEnemy.getImage(), (int) singleEnemy.getX(),(int)singleEnemy.getY(),null);
//    }
    if (lvl.endGame() && !shopMenu) {
      for (int k = 0; k < lvl.getEnemies().size(); k++) {
        var singleEnemy = lvl.getEnemies().get(k);
        //g2.setPaint(new Color(111,94,215));
        //g2.fill(new Ellipse2D.Double(singleEnemy.getX(), singleEnemy.getY(), sizeEnemy, sizeEnemy));
        g2.drawImage(singleEnemy.getImage(), (int) singleEnemy.getX() - 10, (int) singleEnemy.getY(), null);
      }
      for (Projectile singleDmg : lvl.getEnemiesProjectile()) {
        //g2.fill(new Ellipse2D.Double(singleDmg.getX(), singleDmg.getY(), sizeEnemy-10, sizeEnemy-10));
        g2.drawImage(singleDmg.getImg(), (int) singleDmg.getX(), (int) singleDmg.getY(), null);
      }
      for (Projectile projectiveSngleRocket : lvl.getProjectileRocketDmg()) {
        //g2.fill(new Ellipse2D.Double(projectiveSngleRocket.getX(), projectiveSngleRocket.getY(), sizeEnemy-10, sizeEnemy-10));
        g2.drawImage(projectiveSngleRocket.getImg(), (int) projectiveSngleRocket.getX(), (int) projectiveSngleRocket.getY(), null);
      }

      //R O C K E T
      //g2.fill(new Ellipse2D.Double(rocket.getX(), rocket.getY(), sizeEnemy, sizeEnemy));
      g2.drawImage(rocket.getImg(), (int) rocket.getX() - 10, (int) rocket.getY(), null);

      //B O N U S
      g2.setPaint(bonus.getColor());
      g2.fill(new Ellipse2D.Double(bonus.getX(), bonus.getY(), sizeEnemy + 10, sizeEnemy + 10));
      g2.setPaint(new Color(255, 250, 250));
      Font font2 = new Font("Comic Sans MS", Font.BOLD, 22);
      g2.setFont(font2);
      g2.drawString(bonus.getSymbolToShow(), bonus.getX() + 9, bonus.getY() + 22);
      g2.setPaint(new Color(255, 250, 250));
      int fontSize = getWidth() / 40;
      Font f = new Font("Comic Sans MS", Font.BOLD, fontSize);
      g2.setFont(f);
      //show lifeRocket
      g2.drawImage(life, getWidth() - 103, (int) (getHeight() * 0.8) - 15, null);
      g2.drawString(lvl.showStatsRocketLife(), getWidth() - 83, (int) (getHeight() * 0.8));
      //show attackReady
      g2.drawString(lvl.getAttackReady(), getWidth() - 100, (int) (getHeight() * 0.8) + 50);
      //show bullet
      //g2.drawImage(bullet_1, 500,(int)(getHeight()*0.8)+50),null );
      g2.drawImage(bullet_1, getWidth() - 100, (int) (getHeight() * 0.8) + 90, null);
      g2.drawString(screen.getBullet(), getWidth() - 83, (int) (getHeight() * 0.8) + 106);

    } else {

      g2.setPaint(Color.WHITE);
      for (int i = 0; i < shopImg.get(2).size(); i++) {
        g2.drawImage(shopImg.get(2).get(i), getWidth() / 2 + (i * 50) - 250, 100, null);
        g2.drawString(String.valueOf(shopPrice.get(i)), getWidth() / 2 + (i * 50) - 240, 200);
        g2.drawString(String.valueOf(i + 1), getWidth() / 2 + (i * 50) - 240, 230);
      }
      g2.drawString("YOUR CHOOSED ROCKET" + String.valueOf(choosedRockedAndBackGround.getFirst()), getWidth() / 2 - (getWidth() / 4), 300);
      g2.drawString("YOUR CHOOSED BACKGROUND" + String.valueOf(choosedRockedAndBackGround.getLast()), getWidth() / 2 - (getWidth() / 4), 320);
      g2.setPaint(new Color(255, 250, 250));
      int fontSize = getWidth() / 18;
      Font f = new Font("Comic Sans MS", Font.BOLD, fontSize);

      g2.drawString("YOUR SCOREs " + String.valueOf(lvl.getScore()), getWidth() / 2 - (getWidth() / 4), 80);


      g2.drawString("Lists of bests ", getWidth() / 2 - (getWidth() / 4), 350);
//      int fontSizeS = getWidth()/25;
//      g2.setFont(new Font("Comic Sans MS", Font.BOLD, fontSize));
      //score.stream().sorted();
      for (int i = 0; i < score.size(); i++) {
        g2.drawString(String.valueOf(i + 1 + ". " + score.get(i)), getWidth() / 2 - (getWidth() / 4), 350 + ((i + 1) * 20));
      }
      scoreSwitch = false;


      //g2.drawImage(shopBckg_1800x800,0,0,null);
      //show score
      //g2.drawString(String.valueOf("YOUR SCORE: "+lvl.getScore()),getWidth()/2-(getWidth()/8),40);

    }
  }


  @Override
  public void keyTyped(KeyEvent e) {

  }

  Boolean changeClickedBtn = true;

  @Override
  public void keyPressed(KeyEvent e) {
    System.out.printf(String.valueOf(e.getKeyCode()));
    if (e.getKeyCode() != clickedUniqueBtn) {
      clickedUniqueBtn = e.getKeyCode();
      ifNbClicked = true;
    }


    clickedBtn = e.getKeyCode();
    if (e.getKeyCode() == 65 && !shopMenu) {
      rocket.moveLeft();
    } else if (e.getKeyCode() == 68 && !shopMenu) {
      rocket.moveRight();
    } else if (e.getKeyCode() == 32) {
      if (lvl.canIAttack(lvl.getSpeedAttacker()) && !shopMenu) {
        lvl.resetAttack(lvl.getSpeedAttacker(), rocket);
        for (int i = 0; i < rocket.getNbProjectile(); i++) {
          Projectile pr = new Projectile(bullet_1, rocket.getX(), rocket.getY());
//        Projectile pr2 = new Projectile(bullet_1, rocket.getX(), rocket.getY());
//        Projectile pr3 = new Projectile(bullet_1, rocket.getX(), rocket.getY());
//        Projectile pr4 = new Projectile(bullet_1, rocket.getX(), rocket.getY());
//        Projectile pr5 = new Projectile(bullet_1, rocket.getX(), rocket.getY());
          lvl.addProjectileRocketDmg(pr);
//        lvl.addProjectileRocketDmg(pr2);
//        lvl.addProjectileRocketDmg(pr3);
//        lvl.addProjectileRocketDmg(pr4);
//        lvl.addProjectileRocketDmg(pr5);

        }
        //TODO można w pętli wczytywać tyle obiektów ile jest pocisków na wejsciu
        // speedOfAttacker = speedRocketAttack;
      }
      // projectileRocketDmg.add(pr);
      //System.out.println(projectileRocketDmg.size());
    } else if (e.getKeyCode() == 80) {
      System.out.printf(String.valueOf(shopMenu) + " shopp");
      System.out.printf("");
      shopMenu = !shopMenu;
      if (shopMenu) {
        try {
          score = save.load();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }
      shop.setShopOpen(shopMenu);
      //System.out.printf(String.valueOf(shop.getShopOpen()) + "tutaj sie zmienia w klasie graphic");
    } else if (e.getKeyCode() == 82) {
      System.out.printf("ranking");
    } else if (e.getKeyCode() == 79) {
      try {
        lvl = new Level(createEnemies(stability, enemyQuantityRow, chickenImg_1, chickenImg_2), createProjectile(projectileDmg, projectile_1, projectile_2), rocket, c1, screen, bonus, threadSleep, shop, shopImg);
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
      lvl.startLevel(5, 1, 5, 5, 20, lvlNb);
      rocket.setLife(5);
      screen.setBackground(bckg_1);
      rocket.setImg(rocket_1);
      scoreSwitch = true;
      takeScore = true;
    }
    shopKeyboardNumber = e.getKeyCode();
  }

  @Override
  public void keyReleased(KeyEvent e) {

  }

  @Override
  public void mouseClicked(MouseEvent e) {

  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }
}