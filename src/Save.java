import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Save {
  private final String name;

  public Save(String name) {
    this.name = name + ".txt";
  }

  public void save(int score) throws IOException {

    PrintWriter writer = new PrintWriter(new FileWriter(name, true));
    writer.println(score);
    writer.close();
  }

  public List<Integer> load() throws IOException {
    List<Integer> result = new ArrayList<Integer>();
    Scanner scanner = new Scanner(new FileReader(name));
    while (scanner.hasNext()) {
      result.add(Integer.parseInt(scanner.nextLine()));
    }
    for (int i = 0; i < result.size(); i++) {
      for (int j = i + 1; j < result.size(); j++) {
        int first = result.get(i);
        if (first < result.get(j)) {
          result.set(i, result.get(j));
          result.set(j, first);
        }
      }
    }
    result = result.subList(0,5);
    return result;
  }


}
