package h02;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.World;

import java.util.concurrent.ThreadLocalRandom;

import static fopbot.Direction.LEFT;
import static fopbot.Direction.RIGHT;

public class Main {
  public static final byte NUMBER_OF_STEPS_BETWEEN_REDUCTIONS = 10;

  public static int getRandomWorldSize() {
    return 4 + ThreadLocalRandom.current().nextInt(6);
  }

  // ---------------- DO NOT CHANGE ANYTHING ABOVE THIS LINE ---------------

  public static void main(String[] args) {
    int cols = getRandomWorldSize();
    int rows = getRandomWorldSize();
    World.setSize(cols, rows);
    World.setDelay(10);
    World.setVisible(true);
    System.out.println("Size of world: " + cols + "x" + rows);

    // Task 1
    Robot[] allRobots = initializeRobots(cols, rows);
    int[] paces = initializePaces(allRobots);

    int loopCounter = 0;

    // Task 3

    while (allRobots.length > 0) {
      loopCounter++;

      // Task 3.1
      moveForward(allRobots, paces);

      // Task 3.2
      if (allRobots.length > 2) {
        int[] dist = generateThreeDistinctInts(allRobots);
        swapPaces(paces, dist[0], dist[1], dist[2]);
      }

      // Task 3.3
      if (loopCounter % NUMBER_OF_STEPS_BETWEEN_REDUCTIONS == 0) {
        int deleteIndex = ThreadLocalRandom.current().nextInt(allRobots.length);
        allRobots = reduceRobots(allRobots, deleteIndex);
        paces = reducePaces(paces, deleteIndex);
      }
    }
  }

  /**
   * Initializes allRobots array for given World size.
   *
   * @param cols number of columns in World
   * @param rows number of rows in World
   * @return correctly initialized allRobots array (i.e. correct size, correct positions, correct directions)
   */
  public static Robot[] initializeRobots(int cols, int rows) {
    int min = Math.min(cols, rows);
    int arraySize = (min % 2 == 0) ? min : min - 1;
    Robot[] allRobots = new Robot[arraySize];
    Direction d = RIGHT;
    int coinsInit = 1000;

    for (int i = 0; i < arraySize; i++) {
      if (i >= arraySize / 2) {
        d = LEFT;
      }
      allRobots[i] = new Robot(i, i, d, coinsInit);
    }

    return allRobots;
  }

  /**
   * Initializes paces array for given allRobots array.
   * <p>
   * Assumes allRobots array was initialized correctly.
   * </p>
   *
   * @param allRobots previously initialized allRobots array
   * @return correctly initialized paces array (i.e. correct size, filled w/ random integers from [1...5])
   */
  public static int[] initializePaces(Robot[] allRobots) {
    int arraySize = allRobots.length;
    int[] paces = new int[arraySize];
    for (int i = 0; i < arraySize; i++) {
      paces[i] = ThreadLocalRandom.current().nextInt(1, 6);
    }
    return paces;
  }

  /**
   * Moves all robots from allRobots array corresponding to the number of steps to take for each robot (from paces).
   *
   * @param allRobots allRobots array from _main, contains all robots which should be moved by moveForward
   * @param paces     corresponding paces array to allRobots
   */
  public static void moveForward(Robot[] allRobots, int[] paces) {
    for (int i = 0; i < allRobots.length; i++) {
      Robot r = allRobots[i];
      for (int m = 0; m < paces[i]; m++) {
        while (!canMove(r)) {
          r.turnLeft();
        }
        r.move();
      }
    }
  }

  /**
   * Generates three distinct integers from index set of allRobots.
   *
   * @param allRobots allRobots array from main method
   * @return array containing three distinct integers as described above
   */
  public static int[] generateThreeDistinctInts(Robot[] allRobots) {
    int numberOfBots = allRobots.length;
    int i1 = ThreadLocalRandom.current().nextInt(numberOfBots);
    int i2 = ThreadLocalRandom.current().nextInt(numberOfBots);
    while (i1 == i2) {
      i2 = ThreadLocalRandom.current().nextInt(numberOfBots);
    }
    int i3 = ThreadLocalRandom.current().nextInt(numberOfBots);
    while (i3 == i1 || i3 == i2) {
      i3 = ThreadLocalRandom.current().nextInt(numberOfBots);
    }
    return new int[]{i1, i2, i3};
  }

  /**
   * Returns an integer array of size 3, in which i1, i2 and i3 are sorted in ascending order.
   * <p>
   * Assumes that i1, i2 and i3 are distinct.
   * </p>
   *
   * @param i1 first number
   * @param i2 second number
   * @param i3 third number
   * @return sorted array of i1, i2, i3 in ascending order
   */
  public static int[] orderThreeInts(int i1, int i2, int i3) {

    if (i2 < i1) {
      int temp = i2;
      i2 = i1;
      i1 = temp;
    }

    if (i3 < i1) {
      int temp = i3;
      i3 = i1;
      i1 = temp;
    }

    if (i3 < i2) {
      int temp = i2;
      i2 = i3;
      i3 = temp;
    }
    return new int[]{i1, i2, i3};
  }

  /**
   * Swaps entries from paces array as described in exercise sheet.
   * <p>
   * Assumes i1, i2 and i3 are three distinct indices from the index set of paces array.
   * Note that i1, i2 and i3 are not ordered.
   * </p>
   *
   * @param paces current paces array
   * @param i1    first index
   * @param i2    second index
   * @param i3    third index
   */
  public static void swapPaces(int[] paces, int i1, int i2, int i3) {
    int[] orderedI = orderThreeInts(i1, i2, i3);
    i1 = orderedI[0];
    i2 = orderedI[1];
    i3 = orderedI[2];

    int[] orderedPaces = orderThreeInts(paces[i1], paces[i2], paces[i3]);
    paces[i1] = orderedPaces[0];
    paces[i2] = orderedPaces[1];
    paces[i3] = orderedPaces[2];
  }

  /**
   * Reduces returns new array of robots which contains the same elements as allRobots, without the
   * robot at deleteIndex.
   * <p>
   * The new array's length is smaller by one compared to allRobots
   * </p>
   *
   * @param allRobots   allRobots array from _main
   * @param deleteIndex index at which the robot should be removed
   * @return reduced array (as described above)
   */
  public static Robot[] reduceRobots(Robot[] allRobots, int deleteIndex) {
    int newLength = allRobots.length - 1;
    Robot[] newAllRobots = new Robot[newLength];
    int newIndex = 0;
    for (int i = 0; i < allRobots.length; i++) {
      if (i == deleteIndex) {
        continue;
      }
      newAllRobots[newIndex] = allRobots[i];
      newIndex++;
    }
    return newAllRobots;
  }

  /**
   * Reduces returns new array of paces which contains the same elements as given paces array, without the
   * paces entry at deleteIndex.
   * <p>
   * The new array's length is smaller by one compared to given paces array
   * </p>
   *
   * @param paces       paces array from _main
   * @param deleteIndex index at which the paces entry should be removed
   * @return reduced array (as described above)
   */
  public static int[] reducePaces(int[] paces, int deleteIndex) {
    int newLength = paces.length - 1;
    int[] newPaces = new int[newLength];
    int newIndex = 0;
    for (int i = 0; i < paces.length; i++) {
      if (i == deleteIndex) {
        continue;
      }
      newPaces[newIndex] = paces[i];
      newIndex++;
    }
    return newPaces;
  }

  /**
   * Returns whether the robot is facing a wall (same as isFrontClear)
   *
   * @return false if robot is in front of a wall and looking towards this wall, else true
   */
  public static boolean canMove(Robot robot) {
    switch (robot.getDirection()) {
      case UP:
        return robot.getY() != World.getHeight() - 1;
      case RIGHT:
        return robot.getX() != World.getWidth() - 1;
      case DOWN:
        return robot.getY() != 0;
      case LEFT:
        return robot.getX() != 0;
      default:
        throw new IllegalArgumentException();
    }
  }
}
