package h02;

import fopbot.*;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@Nested
@DisplayName("(RequiresJagr) H3_2_T1: generateThreeDistinctInts correct")         // version which is used by RubricProvider
@TestForSubmission("h02")
public class TutorTest_GenerateThreeDistinctInts {
  /**
   * Shared instance of zero-length robot array
   */
  private static final Robot[] NO_ROBOTS = new Robot[0];

  private static Robot[] generateRobots(int count, int worldWidth, int worldHeight) {
    if (count < 0) {
      throw new IllegalArgumentException("Count " + count + " must be positive");
    } else if (count == 0) {
      return NO_ROBOTS;
    }

    Random seeded = new Random(42);
    Robot[] robots = new Robot[count];
    for (int i = 0; i < count; i++) {
      int x = seeded.nextInt(worldWidth);
      int y = seeded.nextInt(worldHeight);
      Direction direction = Direction.values()[seeded.nextInt(4)];
      robots[i] = new Robot(x, y, direction, 0);
    }
    return robots;
  }

  private static Robot[] generateRobots(int count) {
    return generateRobots(count, World.getWidth(), World.getHeight());
  }

  private void assertTrueGenDist(Robot[] r, int[] d) {
    assertTrue(TutorTests.correctInterval(d, r.length));
    assertTrue(TutorTests.distinctNumbers(d));
    assertEquals(3, d.length);
  }

  @Test
  @DisplayName("(DoesNotRequireJagr) H3_2_T1: Sanity check for manual testing")
  public void testGenerateThreeDistinctIntsManual() {
    ThreadLocalRandomTester.initializeOriginal();
    World.setSize(7, 7);
    Robot[] r = generateRobots(6);
    int[] d = Main.generateThreeDistinctInts(r);
    assertTrueGenDist(r, d);

    World.setSize(7, 7);
    Robot[] s = generateRobots(12);
    int[] e = Main.generateThreeDistinctInts(s);
    assertTrueGenDist(s, e);

    World.setSize(5, 5);
    Robot[] t = generateRobots(3);
    int[] f = Main.generateThreeDistinctInts(t);
    assertTrueGenDist(t, f);
  }

  @Test
  @DisplayName("H3_2_T1_simple1")
  public void testSimple1() {
    World.setSize(7, 7);
    Robot[] r = generateRobots(3);
    ThreadLocalRandomTester.runWithSeq(new int[]{1, 2, 3}, r.length, () -> {
      int[] d = Main.generateThreeDistinctInts(r);
      Arrays.sort(d);
      assertArrayEquals(new int[]{1, 2, 3}, d);
    });
  }

  @Test
  @DisplayName("H3_2_T1_simple2")
  public void testSimple2() {
    World.setSize(7, 7);
    Robot[] robots = generateRobots(12);
    ThreadLocalRandomTester.runWithSeq(new int[]{1, 6, 7, 8}, robots.length, () -> {
      int[] distinctInts = Main.generateThreeDistinctInts(robots);
      Arrays.sort(distinctInts);
      assertArrayEquals(new int[]{1, 6, 7}, distinctInts);
    });
  }

  @Nested
  @TestClassOrder(ClassOrderer.ClassName.class)
  @DisplayName("H3_2_T1: TestDifferentApproaches - Only one approach required")
  public class TestDifferentApproaches {

    private final int[] SEQ_0 = {7, 7, 4, 4, 3, 1};
    private final int[] SEQ_1 = {0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 4, 7, 4, 3, 1};
    private final int[] SEQ_2 = {9, 5, 5, 5, 5, 5, 5, 5, 5, 1, 2, 6, 7, 8, 8, 8};
    private final int[] SEQ_3 = {1, 2, 1, 1, 2, 2, 1, 2, 1, 2, 1, 2, 0, 3, 4};

    private void verifyThreeDistinctInts(int[] seq, int[] expected, int robotCount) {
      Robot[] robots = generateRobots(robotCount);
      ThreadLocalRandomTester.runWithSeq(seq, robots.length, () -> {
        int[] distinctInts = Main.generateThreeDistinctInts(robots);
        Arrays.sort(distinctInts);
        assertArrayEquals(expected, distinctInts);
      });
    }

    /**
     * Tests a specific approach to this problem.
     * <p>
     * The approach tested here is:
     * <pre><code>
     *   int i0 = nextInt();
     *   int i1 = nextInt();
     *   while (i0 == i1) {
     *     i2 = nextInt();
     *   }
     *   int i2 = nextInt()
     *   while (i2 == i0 || i2 == i1) {
     *     i2 = nextInt()
     *   }
     * </code></pre>
     * <p>
     * Note: The approach tested here is the same as the one used in the tutor solution
     *
     * @see Main#generateThreeDistinctInts(Robot[])
     */
    @Nested
    @DisplayName("H3_2_T1: Approach 0")
    public class TestApproach0 {
      @Test
      public void testCase0() {
        new Exception().printStackTrace();
        World.setSize(7, 7);
        verifyThreeDistinctInts(SEQ_0, new int[]{3, 4, 7}, 12);
      }

      @Test
      public void testCase1() {
        World.setSize(7, 7);
        verifyThreeDistinctInts(SEQ_1, new int[]{0, 4, 7}, 12);
      }

      @Test
      public void testCase2() {
        World.setSize(7, 7);
        verifyThreeDistinctInts(SEQ_2, new int[]{1, 5, 9}, 12);
      }

      @Test
      public void testCase3() {
        World.setSize(7, 7);
        verifyThreeDistinctInts(SEQ_3, new int[]{0, 1, 2}, 5);
      }
    }

    /**
     * Tests a specific approach to this problem.
     * <p>
     * The approach tested here is:
     * <pre><code>
     *   int i0, i1, i2;
     *   do {
     *     i0 = nextInt();
     *     i1 = nextInt();
     *     i2 = nextInt();
     *   } while (i0 == i1 || i1 == i2 || i0 == i2);
     * </code></pre>
     * <p>
     */
    @Nested
    @DisplayName("H3_2_T1: Approach 1")
    public class TestApproach1 {
      @Test
      public void testCase0() {
        World.setSize(7, 7);
        verifyThreeDistinctInts(SEQ_0, new int[]{1, 3, 4}, 12);
      }

      @Test
      public void testCase1() {
        World.setSize(7, 7);
        verifyThreeDistinctInts(SEQ_1, new int[]{0, 4, 7}, 12);
      }

      @Test
      public void testCase2() {
        World.setSize(7, 7);
        verifyThreeDistinctInts(SEQ_2, new int[]{1, 2, 6}, 12);
      }

      @Test
      public void testCase3() {
        World.setSize(7, 7);
        verifyThreeDistinctInts(SEQ_3, new int[]{0, 3, 4}, 5);
      }
    }
  }
}
