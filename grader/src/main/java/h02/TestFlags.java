package h02;

import fopbot.*;

public class TestFlags {
  private static Robot[] allRobots1 = null;
  private static Robot[] allRobots2 = null;
  private static Robot[] allRobots3 = null;
  private static Robot[] allRobots4 = null;
  private static Robot[] allRobots5 = null;

  private static Boolean flagLengthAllR1 = null;
  private static Boolean flagLengthAllR2 = null;
  private static Boolean flagLengthAllR3 = null;
  private static Boolean flagLengthAllR4 = null;
  private static Boolean flagLengthAllR5 = null;

  private static Boolean flagDiagArr1 = null;
  private static Boolean flagDiagArr2 = null;
  private static Boolean flagDiagArr3 = null;
  private static Boolean flagDiagArr4 = null;
  private static Boolean flagDiagArr5 = null;

  public static Boolean getFlagLengthAllR1() {
    if (flagLengthAllR1 != null) {
      return flagLengthAllR1;
    }
    int cols = 4;
    int rows = 4;
    if (allRobots1 == null) {
      World.setSize(cols, rows);
      allRobots1 = Main.initializeRobots(cols, rows);
    }
    return flagLengthAllR1 = sameSize(allRobots1, correctArraySize(cols, rows));
  }

  public static Boolean getFlagLengthAllR2() {
    if (flagLengthAllR2 != null) {
      return flagLengthAllR2;
    }
    int cols = 4;
    int rows = 5;
    if (allRobots2 == null) {
      World.setSize(cols, rows);
      allRobots2 = Main.initializeRobots(cols, rows);
    }
    return flagLengthAllR2 = sameSize(allRobots2, correctArraySize(cols, rows));
  }

  public static Boolean getFlagLengthAllR3() {
    if (flagLengthAllR3 != null) {
      return flagLengthAllR3;
    }
    int cols = 9;
    int rows = 6;
    if (allRobots3 == null) {
      World.setSize(cols, rows);
      allRobots3 = Main.initializeRobots(cols, rows);
    }
    return flagLengthAllR3 = sameSize(allRobots3, correctArraySize(cols, rows));
  }

  public static Boolean getFlagLengthAllR4() {
    if (flagLengthAllR4 != null) {
      return flagLengthAllR4;
    }
    int cols = 9;
    int rows = 5;
    if (allRobots4 == null) {
      World.setSize(cols, rows);
      allRobots4 = Main.initializeRobots(cols, rows);
    }
    return flagLengthAllR4 = sameSize(allRobots4, correctArraySize(cols, rows));
  }

  public static Boolean getFlagLengthAllR5() {
    if (flagLengthAllR5 != null) {
      return flagLengthAllR5;
    }
    int cols = 7;
    int rows = 7;
    if (allRobots5 == null) {
      World.setSize(cols, rows);
      allRobots5 = Main.initializeRobots(cols, rows);
    }
    return flagLengthAllR5 = sameSize(allRobots5, correctArraySize(cols, rows));
  }

  public static Boolean getFlagDiagArr1() {
    if (flagDiagArr1 != null) {
      return flagDiagArr1;
    }
    int cols = 4;
    int rows = 4;
    if (allRobots1 == null) {
      World.setSize(cols, rows);
      allRobots1 = Main.initializeRobots(cols, rows);
    }
    return flagDiagArr1 = checkAllRobots(allRobots1);
  }

  public static Boolean getFlagDiagArr2() {
    if (flagDiagArr2 != null) {
      return flagDiagArr2;
    }
    int cols = 4;
    int rows = 5;
    if (allRobots2 == null) {
      World.setSize(cols, rows);
      allRobots2 = Main.initializeRobots(cols, rows);
    }
    return flagDiagArr2 = checkAllRobots(allRobots2);
  }

  public static Boolean getFlagDiagArr3() {
    if (flagDiagArr3 != null) {
      return flagDiagArr3;
    }
    int cols = 9;
    int rows = 6;
    if (allRobots3 == null) {
      World.setSize(cols, rows);
      allRobots3 = Main.initializeRobots(cols, rows);
    }
    return flagDiagArr3 = checkAllRobots(allRobots3);
  }

  public static Boolean getFlagDiagArr4() {
    if (flagDiagArr4 != null) {
      return flagDiagArr4;
    }
    int cols = 9;
    int rows = 5;
    if (allRobots4 == null) {
      World.setSize(cols, rows);
      allRobots4 = Main.initializeRobots(cols, rows);
    }
    return flagDiagArr4 = checkAllRobots(allRobots4);
  }

  public static Boolean getFlagDiagArr5() {
    if (flagDiagArr5 != null) {
      return flagDiagArr5;
    }
    int cols = 7;
    int rows = 7;
    if (allRobots5 == null) {
      World.setSize(cols, rows);
      allRobots5 = Main.initializeRobots(cols, rows);
    }
    return flagDiagArr5 = checkAllRobots(allRobots5);
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
