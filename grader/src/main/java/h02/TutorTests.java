package h02;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.World;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@TestForSubmission("h02")
public class TutorTests {
  // ----------------------------- H1 --------------------------------

  @Test
  @DisplayName("H1_T1: allRobots array has correct size")
  public void testAllRobotsSize() {
    assertTrue(TestFlags.getFlagLengthAllR1());
    assertTrue(TestFlags.getFlagLengthAllR2());
    assertTrue(TestFlags.getFlagLengthAllR3());
    assertTrue(TestFlags.getFlagLengthAllR4());
    assertTrue(TestFlags.getFlagLengthAllR5());
  }

  @Test
  @DisplayName("H1_T2: allRobots array filled correctly (robots on world's diagonal, lower half looking right, upper half looking left")
  public void testAllRobotsDiag() {          // can only get points if length of array is correct
    assertTrue(TestFlags.getFlagLengthAllR1() && TestFlags.getFlagDiagArr1());
    assertTrue(TestFlags.getFlagLengthAllR2() && TestFlags.getFlagDiagArr2());
    assertTrue(TestFlags.getFlagLengthAllR3() && TestFlags.getFlagDiagArr3());
    assertTrue(TestFlags.getFlagLengthAllR4() && TestFlags.getFlagDiagArr4());
    assertTrue(TestFlags.getFlagLengthAllR5() && TestFlags.getFlagDiagArr5());
  }

  @Test
  @DisplayName("H1_T3: paces initialized correctly (correct length according to allRobots, filled with integers from [1...5])")
  public void testInitPaces() {
    ThreadLocalRandomTester.initializeOriginal();

    int[] paces = null;

    World.setSize(6, 6);
    Robot[] allRobots = new Robot[]{
      new Robot(0, 0, Direction.UP, 1000),
      new Robot(1, 1, Direction.UP, 1000),
      new Robot(2, 2, Direction.UP, 1000),
      new Robot(3, 3, Direction.UP, 1000),
      new Robot(4, 4, Direction.UP, 1000),
      new Robot(5, 5, Direction.UP, 1000),
    };
    List<Integer> numbers = new LinkedList<>();
    for (int i=0; i < 10; i++) {                    // to maximize chance of detecting wrong item
                                                    // detecting a false negative is very unlikely, but theoretically possible
      paces = Main.initializePaces(allRobots);
      numbers.addAll(Arrays.stream(paces).boxed().collect(Collectors.toList()));
      assertTrue(checkPacesInterval(paces) && allRobots.length == paces.length);
    }
    assertTrue(containsAllInterval(numbers));

    World.setSize(3, 3);
    allRobots = new Robot[]{
      new Robot(0, 0, Direction.UP, 1000),
      new Robot(1, 1, Direction.UP, 1000),
      new Robot(2, 2, Direction.UP, 1000)
    };
    numbers = new LinkedList<>();
    for (int i=0; i < 20; i++) {
      paces = Main.initializePaces(allRobots);
      numbers.addAll(Arrays.stream(paces).boxed().collect(Collectors.toList()));
      assertTrue(checkPacesInterval(paces) && allRobots.length == paces.length);
    }
    assertTrue(containsAllInterval(numbers));

    World.setSize(1, 1);
    allRobots = new Robot[]{
      new Robot(0, 0, Direction.UP, 1000)
    };
    numbers = new LinkedList<>();
    for (int i=0; i < 30; i++) {
      paces = Main.initializePaces(allRobots);
      numbers.addAll(Arrays.stream(paces).boxed().collect(Collectors.toList()));
      assertTrue(checkPacesInterval(paces) && allRobots.length == paces.length);
    }
    assertTrue(containsAllInterval(numbers));
    ThreadLocalRandomTester.removeCurrentTester();
  }

  public static boolean containsAllInterval(List<Integer> numbers) {
    return numbers.contains(1) && numbers.contains(2) && numbers.contains(3) && numbers.contains(4) && numbers.contains(5);
  }

  // ----------------------------- H3.1 --------------------------------

  @Test
  @DisplayName("H3_1_T1: moveForward correct for simple movements of single robot with paces =[1]")
  public void testSimpleMoves() {
    //t1: simple movement: one robot, no rotations, pace 1
    World.setSize(3, 3);
    World.setVisible(false);
    Robot[] movePointer = new Robot[]{
      new Robot(0, 2, Direction.DOWN, 0)
    };
    int[] p = new int[]{1};
    assertTrue(takeStep(movePointer, new int[]{0}, new int[]{1}, new Direction[]{Direction.DOWN}, p));
    assertTrue(takeStep(movePointer, new int[]{0}, new int[]{0}, new Direction[]{Direction.DOWN}, p));

    //t2: simple rotation: one bot, pace 1
    World.setSize(3, 3);
    World.setVisible(false);
    movePointer = new Robot[]{
      new Robot(0, 0, Direction.LEFT, 0)
    };
    p = new int[]{1};
    assertTrue(takeStep(movePointer, new int[]{1}, new int[]{0}, new Direction[]{Direction.RIGHT}, p));
    assertTrue(takeStep(movePointer, new int[]{2}, new int[]{0}, new Direction[]{Direction.RIGHT}, p));
    assertTrue(takeStep(movePointer, new int[]{2}, new int[]{1}, new Direction[]{Direction.UP}, p));
    assertTrue(takeStep(movePointer, new int[]{2}, new int[]{2}, new Direction[]{Direction.UP}, p));
    assertTrue(takeStep(movePointer, new int[]{1}, new int[]{2}, new Direction[]{Direction.LEFT}, p));
    assertTrue(takeStep(movePointer, new int[]{0}, new int[]{2}, new Direction[]{Direction.LEFT}, p));
  }

  @Test
  @DisplayName("H3_1_T2: moveForward correct for movements of multiple robots with different paces & paces = [0, 0, ...]")
  public void testComplexMoves() {
    // IF tests take too long, we can cut off some 'takeStep's

    // t3: going straight with different paces
    World.setSize(7, 5);
    World.setVisible(false);

    Robot[] movePointer = new Robot[]{
      new Robot(1, 4, Direction.RIGHT, 0),
      new Robot(1, 2, Direction.DOWN, 0),
      new Robot(4, 2, Direction.LEFT, 0),
      new Robot(0, 3, Direction.RIGHT, 0)
    };

    int[] p = new int[]{1, 1, 2, 3};

    assertTrue(takeStep(movePointer, new int[]{2, 1, 2, 3}, new int[]{4, 1, 2, 3},
      new Direction[]{Direction.RIGHT, Direction.DOWN, Direction.LEFT, Direction.RIGHT}, p));
    assertTrue(takeStep(movePointer, new int[]{3, 1, 0, 6}, new int[]{4, 0, 2, 3},
      new Direction[]{Direction.RIGHT, Direction.DOWN, Direction.LEFT, Direction.RIGHT}, p));

    // t4: going straight + rotations with different paces
    World.setSize(7, 6);
    World.setVisible(false);
    movePointer = new Robot[]{
      new Robot(3, 4, Direction.UP, 0),
      new Robot(3, 3, Direction.RIGHT, 0),
      new Robot(0, 2, Direction.DOWN, 0),
      new Robot(6, 2, Direction.DOWN, 0),
      new Robot(4, 5, Direction.DOWN, 0),
      new Robot(2, 3, Direction.DOWN, 0)
    };
    p = new int[]{1, 2, 1, 1, 4, 3};
    assertTrue(takeStep(movePointer, new int[]{3, 5, 0, 6, 4, 2}, new int[]{5, 3, 1, 1, 1, 0},
      new Direction[]{Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.DOWN, Direction.DOWN, Direction.DOWN}, p));
    assertTrue(takeStep(movePointer, new int[]{2, 6, 0, 6, 6, 5}, new int[]{5, 4, 0, 0, 1, 0},
      new Direction[]{Direction.LEFT, Direction.UP, Direction.DOWN, Direction.DOWN, Direction.UP, Direction.RIGHT}, p));
    assertTrue(takeStep(movePointer, new int[]{1, 5, 1, 6, 6, 6}, new int[]{5, 5, 0, 1, 5, 2},
      new Direction[]{Direction.LEFT, Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.UP, Direction.UP}, p));

    // t5: do nothing when paces are only zeros
    World.setSize(4, 4);
    World.setVisible(false);
    movePointer = new Robot[]{
      new Robot(1, 3, Direction.RIGHT, 0),
      new Robot(1, 2, Direction.DOWN, 0),
      new Robot(0, 1, Direction.LEFT, 0),
      new Robot(0, 3, Direction.RIGHT, 0)
    };
    p = new int[]{0, 0, 0, 0};
    assertTrue(takeStep(movePointer, new int[]{1, 1, 0, 0}, new int[]{3, 2, 1, 3}, new Direction[]{Direction.RIGHT, Direction.DOWN, Direction.LEFT, Direction.RIGHT}, p));
  }

  // ----------------------------- H3.2 --------------------------------

  @Test
  @DisplayName("Extra for manual testing: If H_3_2_T1 fails, you can use this method. If it doesn't fail, chances are pretty good that generateThreeDistinctInts is correct nonetheless.")      // old version, leave in file for manual testing
  public void testGenerateThreeDistinctIntsManual() {           // for manual testing if testGenerateTreeDistinctInts (which is called by RubricProvider) fails
    World.setSize(7, 7);
    Robot[] r = new Robot[]{
      new Robot(3, 4, Direction.UP, 0),
      new Robot(3, 3, Direction.RIGHT, 0),
      new Robot(0, 2, Direction.DOWN, 0),
      new Robot(6, 2, Direction.DOWN, 0),
      new Robot(4, 5, Direction.DOWN, 0),
      new Robot(2, 3, Direction.DOWN, 0)
    };
    int[] d = Main.generateThreeDistinctInts(r);
    assertTrueGenDist(r, d);

    World.setSize(7, 7);
    Robot[] s = new Robot[]{
      new Robot(3, 4, Direction.UP, 0),
      new Robot(3, 3, Direction.RIGHT, 0),
      new Robot(0, 2, Direction.DOWN, 0),
      new Robot(6, 2, Direction.DOWN, 0),
      new Robot(4, 5, Direction.DOWN, 0),
      new Robot(2, 3, Direction.DOWN, 0),
      new Robot(3, 4, Direction.UP, 0),
      new Robot(3, 3, Direction.RIGHT, 0),
      new Robot(0, 2, Direction.DOWN, 0),
      new Robot(6, 2, Direction.DOWN, 0),
      new Robot(4, 5, Direction.DOWN, 0),
      new Robot(2, 3, Direction.DOWN, 0)
    };
    int[] e = Main.generateThreeDistinctInts(s);
    assertTrueGenDist(s, e);

    World.setSize(5, 5);
    Robot[] t = new Robot[]{
      new Robot(3, 4, Direction.UP, 0),
      new Robot(3, 3, Direction.RIGHT, 0),
      new Robot(0, 2, Direction.DOWN, 0)
    };
    int[] f = Main.generateThreeDistinctInts(t);
    assertTrueGenDist(t, f);
  }

  private void assertTrueGenDist(Robot[] r, int[] d) {
    assertTrue(correctInterval(d, r.length));
    assertTrue(distinctNumbers(d));
    assertTrue(d.length==3);
  }

  @Test
  @DisplayName("H3_2_T1: generateThreeDistinctInts correct")         // version which is used by RubricProvider
  public void testGenerateThreeDistinctInts() {
    ThreadLocalRandomTester.removeCurrentTester();

    World.setSize(7, 7);
    Robot[] r = new Robot[]{
      new Robot(3, 4, Direction.UP, 0),
      new Robot(3, 3, Direction.RIGHT, 0),
      new Robot(0, 2, Direction.DOWN, 0)
    };
    ThreadLocalRandomTester.initialize(new int[]{1, 2, 3}, r.length);
    int[] d = Main.generateThreeDistinctInts(r);
    Arrays.sort(d);
    assertTrue(Arrays.equals(d, new int[] {1, 2, 3}));
    ThreadLocalRandomTester.removeCurrentTester();

    World.setSize(7, 7);
    Robot[] s = new Robot[]{
      new Robot(3, 4, Direction.UP, 0),
      new Robot(3, 3, Direction.RIGHT, 0),
      new Robot(0, 2, Direction.DOWN, 0),
      new Robot(6, 2, Direction.DOWN, 0),
      new Robot(4, 5, Direction.DOWN, 0),
      new Robot(2, 3, Direction.DOWN, 0),
      new Robot(3, 4, Direction.UP, 0),
      new Robot(3, 3, Direction.RIGHT, 0),
      new Robot(0, 2, Direction.DOWN, 0),
      new Robot(6, 2, Direction.DOWN, 0),
      new Robot(4, 5, Direction.DOWN, 0),
      new Robot(2, 3, Direction.DOWN, 0)
    };
    ThreadLocalRandomTester.initialize(new int[]{1, 6, 7, 8}, s.length);
    int[] e = Main.generateThreeDistinctInts(s);
    Arrays.sort(e);
    assertTrue(Arrays.equals(e, new int[] {1, 6, 7}));
    ThreadLocalRandomTester.removeCurrentTester();

    World.setSize(7, 7);
    Robot[] t = new Robot[]{
      new Robot(3, 4, Direction.UP, 0),
      new Robot(3, 3, Direction.RIGHT, 0),
      new Robot(0, 2, Direction.DOWN, 0),
      new Robot(6, 2, Direction.DOWN, 0),
      new Robot(4, 5, Direction.DOWN, 0),
      new Robot(2, 3, Direction.DOWN, 0),
      new Robot(3, 4, Direction.UP, 0),
      new Robot(3, 3, Direction.RIGHT, 0),
      new Robot(0, 2, Direction.DOWN, 0),
      new Robot(6, 2, Direction.DOWN, 0),
      new Robot(4, 5, Direction.DOWN, 0),
      new Robot(2, 3, Direction.DOWN, 0)
    };
    ThreadLocalRandomTester.initialize(new int[]{7, 7, 4, 4, 3, 1}, t.length);
    int[] f = Main.generateThreeDistinctInts(t);
    Arrays.sort(f);
    assertTrue(Arrays.equals(f, new int[] {3, 4, 7}));
    ThreadLocalRandomTester.removeCurrentTester();

    World.setSize(7, 7);
    Robot[] u = new Robot[]{
      new Robot(3, 4, Direction.UP, 0),
      new Robot(3, 3, Direction.RIGHT, 0),
      new Robot(0, 2, Direction.DOWN, 0),
      new Robot(6, 2, Direction.DOWN, 0),
      new Robot(4, 5, Direction.DOWN, 0),
      new Robot(2, 3, Direction.DOWN, 0),
      new Robot(3, 4, Direction.UP, 0),
      new Robot(3, 3, Direction.RIGHT, 0),
      new Robot(0, 2, Direction.DOWN, 0),
      new Robot(6, 2, Direction.DOWN, 0),
      new Robot(4, 5, Direction.DOWN, 0),
      new Robot(2, 3, Direction.DOWN, 0)
    };
    ThreadLocalRandomTester.initialize(new int[]{4, 4, 4, 4, 4, 5, 5, 5, 5, 1, 2, 6, 2, 1}, u.length);
    int[] g = Main.generateThreeDistinctInts(u);
    Arrays.sort(g);
    assertTrue(Arrays.equals(g, new int[] {1, 4, 5}));
    ThreadLocalRandomTester.removeCurrentTester();

    ThreadLocalRandomTester.initializeOriginal();
  }


  @Test
  @DisplayName("H3_2_T2: orderThreeInts correct")
  public void testOrderThreeInts() {
    int a = 1;
    int b = 2;
    int c = 3;
    int[] correctOrder = new int[]{a, b, c};
    assertTrueOrderThreeInts(a, b, c, correctOrder);

    a = 0;
    b = 17;
    c = 572;
    correctOrder = new int[]{a, b, c};
    assertTrueOrderThreeInts(a, b, c, correctOrder);
  }

  private void assertTrueOrderThreeInts(int a, int b, int c, int[] correctOrder) {
    assertTrue(isIntegerArrayEqual(Main.orderThreeInts(a, b, c), correctOrder));
    assertTrue(isIntegerArrayEqual(Main.orderThreeInts(a, c, b), correctOrder));
    assertTrue(isIntegerArrayEqual(Main.orderThreeInts(c, b, a), correctOrder));
    assertTrue(isIntegerArrayEqual(Main.orderThreeInts(c, a, b), correctOrder));
    assertTrue(isIntegerArrayEqual(Main.orderThreeInts(b, a, c), correctOrder));
    assertTrue(isIntegerArrayEqual(Main.orderThreeInts(b, c, a), correctOrder));
  }

  @Test
  @DisplayName("H3_2_T2: swapPaces correct")
  public void testSwapPaces() {
    int a = 0;
    int b = 3;
    int c = 4;
    int[] beforeArr = new int[]{14, 25, 676, 2, 9, 15, 67};
    int[] afterArr = new int[]{2, 25, 676, 9, 14, 15, 67};
    assertTrue(isIntegerArrayEqual(afterArr, Main.swapPaces(beforeArr, a, b, c)));
    assertTrue(isIntegerArrayEqual(afterArr, Main.swapPaces(beforeArr, a, c, b)));
    assertTrue(isIntegerArrayEqual(afterArr, Main.swapPaces(beforeArr, b, c, a)));

    a = 0;
    b = 3;
    c = 1;
    beforeArr = new int[]{1, 2, 3, 4};
    afterArr = new int[]{1, 2, 3, 4};
    assertTrue(isIntegerArrayEqual(afterArr, Main.swapPaces(beforeArr, a, b, c)));

    a = 2;
    b = 1;
    c = 0;
    beforeArr = new int[]{41, 12, 33, 4};
    afterArr = new int[]{12, 33, 41, 4};
    assertTrue(isIntegerArrayEqual(afterArr, Main.swapPaces(beforeArr, a, b, c)));
  }

  // ----------------------------- H3.3 --------------------------------

  @Test
  @DisplayName("H3_3_T1: reduceRobots correct")
  public void testReduceRobots() {
    World.setSize(3, 3);
    Robot b1 = new Robot(0, 0, Direction.UP, 1);
    Robot b2 = new Robot(1, 0, Direction.DOWN, 1);
    Robot b3 = new Robot(0, 2, Direction.LEFT, 1);
    Robot b4 = new Robot(0, 2, Direction.RIGHT, 1);
    Robot b5 = new Robot(0, 2, Direction.LEFT, 1);
    Robot b6 = new Robot(2, 2, Direction.UP, 1);
    Robot[] before = new Robot[]{b1, b2, b3};
    Robot[] after1 = new Robot[]{b2, b3};
    Robot[] after2 = new Robot[]{b1, b3};
    Robot[] after3 = new Robot[]{b1, b2};
    assertTrue(isRobotArrayEqualId(Main.reduceRobots(before, 0), after1));
    assertTrue(isRobotArrayEqualId(Main.reduceRobots(before, 1), after2));
    assertTrue(isRobotArrayEqualId(Main.reduceRobots(before, 2), after3));

    before = new Robot[]{b1, b2, b3, b4, b5, b6};
    after1 = new Robot[]{b1, b2, b4, b5, b6};
    after2 = new Robot[]{b1, b4, b5, b6};
    assertTrue(isRobotArrayEqualId(Main.reduceRobots(before, 2), after1));
    assertTrue(isRobotArrayEqualId(Main.reduceRobots(after1, 1), after2));

    assertTrue(isRobotArrayEqualId(Main.reduceRobots(new Robot[]{b1}, 0), new Robot[]{}));
  }

  @Test
  @DisplayName("H3_3_T2: reducePaces correct")
  public void testReducePaces() {
    int[] b = new int[]{1, 4, 5, 0, 9, 13, 1234, 44};
    int[] a1 = new int[]{4, 5, 0, 9, 13, 1234, 44};
    int[] a2 = new int[]{1, 4, 5, 0, 9, 13, 44};
    int[] a3 = new int[]{1, 4, 5, 0, 9, 13, 1234};
    int[] a4 = new int[]{1, 4, 5, 9, 13, 1234, 44};
    assertTrue(isIntegerArrayEqual(Main.reducePaces(b, 0), a1));
    assertTrue(isIntegerArrayEqual(Main.reducePaces(b, 6), a2));
    assertTrue(isIntegerArrayEqual(Main.reducePaces(b, 7), a3));
    assertTrue(isIntegerArrayEqual(Main.reducePaces(b, 3), a4));
    assertTrue(isIntegerArrayEqual(Main.reducePaces(new int[]{12}, 0), new int[]{}));
  }

  // -------------------------------- HELPER METHODS -------------------------------------------
  // most methods assume that they are not given null arrays -> otherwise false is returned

  public static boolean takeStep(Robot[] movePointer, int[] x, int[] y, Direction[] d, int[] p) {
    ArrayList<Robot> afterList = new ArrayList<>();
    for (int i = 0; i < x.length; i++) {
      afterList.add(new Robot(x[i], y[i], d[i], p[i]));
    }
    Robot[] after = afterList.toArray(new Robot[0]);
    Main.moveForward(movePointer, p);
    return isRobotArrayEqualMove(movePointer, after);
  }

  public static boolean isRobotArrayEqualMove(Robot[] robots1, Robot[] robots2) {
    if (robots1 == null || robots2 == null) {
      return false;
    }
    if (robots1.length != robots2.length) {
      return false;
    }
    for (int i = 0; i < robots1.length; i++) {
      if (!isBotEqualMove(robots1[i], robots2[i])) {
        return false;
      }
    }
    return true;
  }

  public static boolean isBotEqualMove(Robot b1, Robot b2) {
    return b1.getX() == b2.getX() &&
      b1.getY() == b2.getY() &&
      b1.getDirection() == b2.getDirection();
  }

  public static boolean checkPacesInterval(int[] paces) {
    if (paces == null) {
      return false;
    }
    for (int i : paces) {
      if (i < 1 || i > 5) {
        return false;
      }
    }
    return true;
  }

  public static boolean isIntegerArrayEqual(int[] arr1, int[] arr2) {
    if (arr1 == null || arr2 == null) {
      return false;
    }
    if (arr1.length != arr2.length) {
      return false;
    }
    for (int i = 0; i < arr1.length; i++) {
      if (arr1[i] != arr2[i]) {
        return false;
      }
    }
    return true;
  }

  public static boolean isRobotArrayEqualId(Robot[] robots1, Robot[] robots2) {
    if (robots1 == null || robots2 == null) {
      return false;
    }
    if (robots1.length != robots2.length) {
      return false;
    }
    for (int i = 0; i < robots1.length; i++) {
      if (!Objects.equals(robots1[i].getId(), robots2[i].getId())) {
        return false;
      }
    }
    return true;
  }

  public static boolean correctInterval(int[] dist, int upperBound) {
    for(int i : dist) {
      if (i < 0 || i > upperBound - 1) {
        return false;
      }
    }
    return true;
  }

  public static boolean distinctNumbers(int[] dist) {
    if (dist[0] == dist[1] || dist[0] == dist[2] || dist[1] == dist[2]) {
      return false;
    }
    return true;
  }

}
