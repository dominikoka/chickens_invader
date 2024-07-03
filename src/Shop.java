import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Shop {


  BufferedImage backgroundSetUp;
  Rocket rocket;
  Boolean ifShopIsOpen = false;
  BufferedImage shopImg;
  List<List<BufferedImage>> items = new ArrayList<List<BufferedImage>>();
  List<Integer> prices = new ArrayList<>();


  public Shop( Screen screen, List<List<BufferedImage>> items, List<Integer> prices,BufferedImage shopBckg) {
    this.backgroundSetUp = screen.getBBufferedImage();
    this.shopImg = shopBckg;
    this.prices = prices;
  }

  public List<Integer> getPrices()
  {
    return prices;
  }

  public void setPrices(List<Integer> prices)
  {
    this.prices = prices;
  }

  public void rocketSkin(BufferedImage skin,int projectile)
  {
    rocket.setImg(skin);
    rocket.setNbProjectile(projectile);
  }
  public BufferedImage getShopImg()
  {
    return shopImg;
  }

  public void backGround(BufferedImage image)
  {
    backgroundSetUp = image;
  }
  public void setShopOpen(Boolean open)
  {
    this.ifShopIsOpen = open;
  }
  public Boolean getShopOpen()
  {
    return ifShopIsOpen;
  }


  public int changeEKeyToKeyboardNb(int keyboardNb)
  {
    if(keyboardNb>=49 && keyboardNb<=60)
    {
      return keyboardNb -49;
    }
    return -1;
  }
  public boolean ifYouHaveMoney(int keyboardNb,int score, List<Integer> price) {
    return  keyboardNb == 49 && price.getFirst()<=score || keyboardNb == 50 && price.get(1)<=score || keyboardNb == 51&& price.get(2)<=score  || keyboardNb == 52&& price.get(3)<=score || keyboardNb == 53&& price.get(4)<=score || keyboardNb ==54&& price.get(5)<=score ||keyboardNb ==55&& price.get(6)<=score||keyboardNb ==56&& price.get(7)<=score||keyboardNb ==57&& price.get(8)<=score;
  }
}


