package h02;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class ThreadLocalRandomTester {
  private static final InheritableThreadLocal<ThreadLocalRandomTester> factory = new InheritableThreadLocal<>();
  private int[] sequence;
  private ThreadLocalSeq threadLocalSeq;
  private int allRobotsLength;
  private final boolean replaceTester;

  public static void runWithSeq(int[] sequence, int allRobotsLength, Runnable block) {
    initialize(sequence, allRobotsLength);
    block.run();
    removeCurrentTester();
  }

  public static void initialize(int[] sequence, int allRobotsLength) {
    factory.set(new ThreadLocalRandomTester(sequence, allRobotsLength));
  }

  public ThreadLocalRandomTester(int[] sequence, int allRobotsLength) {
    this.sequence = sequence;
    this.allRobotsLength = allRobotsLength;
    threadLocalSeq = new ThreadLocalSeq(sequence);
    replaceTester = true;
  }

  public static void initializeOriginal() {
    factory.set(new ThreadLocalRandomTester());
  }

  public ThreadLocalRandomTester() {
    replaceTester = false;
  }

  public static void removeCurrentTester() {
    factory.remove();
  }

  /**
   * Replaces {@link ThreadLocalRandom#current()}
   */
  public static ThreadLocalRandomTester current() {
    return factory.get();
  }

  public int[] currentSequence() {
    return sequence;
  }

  private <T> T nextInBounds(int lower, int upper, String name, Supplier<T> replaced, BiFunction<Integer, Integer, T> fallback) {
    if (replaceTester) {
      if (lower != 0) {
        throw new IllegalArgumentException(
          String.format("First parameter of %s must be 0, bust received %o. Test manually.",
            name, lower));
      }
      if (upper != allRobotsLength) {
        throw new IllegalArgumentException(
          String.format("Second parameter of %s must be allRobots.length (=%o), but received %o. Test manually." +
            name, allRobotsLength, upper));
      }
      return replaced.get();
    }
    return fallback.apply(lower, upper);
  }

  /**
   * Replaces {@link ThreadLocalRandom#nextInt(int, int)}
   */
  public int nextInt(int lower, int upper) {
    return nextInBounds(lower, upper, "nextInt(II)", threadLocalSeq::next, ThreadLocalRandom.current()::nextInt);
  }

  /**
   * Replaces {@link ThreadLocalRandom#ints(int, int)}
   */
  public IntStream ints(int lower, int upper) {
    return nextInBounds(lower, upper, "ints(II)", () -> Arrays.stream(sequence), ThreadLocalRandom.current()::ints);
  }

  /**
   * Replaces {@link ThreadLocalRandom#nextInt(int)}
   */
  public int nextInt(int a) {
    return nextInt(0, a);
  }

  /**
   * Replaces {@link ThreadLocalRandom#nextInt(int)}
   */
  public boolean nextBoolean() {
    return nextInt(0, 2) != 0;
  }

  /**
   * Replaces {@link ThreadLocalRandom#nextInt()}
   */
  public int nextInt() {
    return nextInt(0, 200);
  }

  /**
   * Replaces {@link ThreadLocalRandom#nextFloat()}
   */
  public float nextFloat() {
    if (replaceTester) {
      throw new UnsupportedOperationException("nextFloat() was called. Test manually.");
    }
    return ThreadLocalRandom.current().nextFloat();
  }

  /**
   * Replaces {@link ThreadLocalRandom#nextDouble()}
   */
  public double nextDouble() {
    if (replaceTester) {
      throw new UnsupportedOperationException("nextDouble() was called. Test manually.");
    }
    return ThreadLocalRandom.current().nextDouble();
  }

  /**
   * Replaces {@link ThreadLocalRandom#nextDouble(double, double)}
   */
  public double nextDouble(double a, double b) {
    if (replaceTester) {
      throw new UnsupportedOperationException("nextDouble(double, double) was called. Test manually.");
    }
    return ThreadLocalRandom.current().nextDouble(a, b);
  }

  /**
   * Replaces {@link ThreadLocalRandom#nextDouble(double)}
   */
  public double nextDouble(double a) {
    if (replaceTester) {
      throw new UnsupportedOperationException("nextDouble(double) was called. Test manually.");
    }
    return ThreadLocalRandom.current().nextDouble(a);
  }
}
