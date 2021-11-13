package h02;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.World;

public class TestFlags {

  private final static Robot[][] allRobots = new Robot[5][];
  private final static Boolean[] flagLengthAll = new Boolean[5];
  private final static Boolean[] flagDiagArr = new Boolean[5];
  private final static int[] cols = {4, 4, 9, 9, 7};
  private final static int[] rows = {4, 5, 6, 5, 7};

  public static Boolean getFlagLengthAll(int i) {
    if (flagLengthAll[i] != null) {
      return flagLengthAll[i];
    }
    if (allRobots[i] == null) {
      World.setSize(cols[i], rows[i]);
      allRobots[i] = Main.initializeRobots(cols[i], rows[i]);
    }
    return flagLengthAll[i] = sameSize(allRobots[i], correctArraySize(cols[i], rows[i]));
  }

  public static Boolean getFlagDiagArr(int i) {
    if (flagDiagArr[i] != null) {
      return flagDiagArr[i];
    }
    if (allRobots[i] == null) {
      World.setSize(cols[i], rows[i]);
      allRobots[i] = Main.initializeRobots(cols[i], rows[i]);
    }
    return flagDiagArr[i] = checkAllRobots(allRobots[i]);
  }

  public static boolean checkAllRobots(Robot[] allRobots) {
    if (allRobots == null) {
      return false;
    }
    for (Robot bot : allRobots) {
      if (bot.getX() != bot.getY()) {
        return false;
      }
      if (bot.getX() >= allRobots.length / 2) {
        if (bot.getDirection() != Direction.LEFT) {
          return false;
        }
      } else if (bot.getDirection() != Direction.RIGHT) {
        return false;
      }
    }
    return true;
  }

  public static <T> boolean sameSize(T[] t, int l) {
    if (t == null) {
      return false;
    }
    return t.length == l;
  }

  public static int correctArraySize(int cols, int rows) {
    int min = Math.min(cols, rows);      // min soll natürliche Zahl sein. Sollte durch getRandomWorldSize sichergestellt werden
    return (min % 2 == 0) ? min : min - 1;
  }
}
