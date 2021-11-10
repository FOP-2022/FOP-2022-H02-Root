package h02;
import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomTester {
  private static final InheritableThreadLocal<ThreadLocalRandomTester> factory = new InheritableThreadLocal<>();
  private int[] sequence;
  private ThreadLocalSeq threadLocalSeq;
  private int allRobotsLength;
  private boolean replaceTester = false;

  //private final HashMap<IntSeq, ThreadLocalSeq> usedRanges = new HashMap<>();

  public static void initialize(int[] sequence, int allRobotsLength) {
    factory.set(new ThreadLocalRandomTester(sequence, allRobotsLength));
  }

  public ThreadLocalRandomTester(int[] sequence, int allRobotsLength) {
    this.sequence = sequence;
    this.allRobotsLength = allRobotsLength;
    this.threadLocalSeq = new ThreadLocalSeq(sequence);
    this.replaceTester = true;
  }

  public static void initializeOriginal() {
    factory.set(new ThreadLocalRandomTester());
  }

  public ThreadLocalRandomTester() {
    this.replaceTester = false;
  }

  public static void removeCurrentTester() { factory.remove(); }

  /**
   * Replaces {@link ThreadLocalRandom#current()}
   */
  public static ThreadLocalRandomTester current() {
    //System.out.println(factory.get());
    return factory.get();
  }

  public int[] currentSequence() { return this.sequence; }

  /**
   * Replaces {@link ThreadLocalRandom#nextInt(int, int)}
   */
  public int nextInt(int a, int b) {
    if (replaceTester) {
      //System.out.println(String.format("a = %o, b = %o", a, b));
      if (a != 0) {
        //return (int) (-Integer.MAX_VALUE * Math.random());
        throw new IllegalArgumentException(String.format("First parameter of nextInt must be 0, bust received %o. Test manually if method is correct.", a));
      }
      if (b != allRobotsLength) {
        throw new IllegalArgumentException(String.format("Second parameter of nextInt must be allRobots.length (=%o), bust received %o. Test manually if method is correct.",
          this.allRobotsLength, b));
      }
    /*
    int i = threadLocalSeq.next();
    System.out.println(i);
    return i;
    */
      return threadLocalSeq.next();
    }
    else { return ThreadLocalRandom.current().nextInt(a, b); }
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
    // throw Exception?
    return nextInt(0, 200);
  }

  /**
   * Replaces {@link ThreadLocalRandom#nextFloat()}
   */
  public float nextFloat() {
    if (replaceTester) {
      throw new UnsupportedOperationException("nextFloat() was called. Test manually.");
    }
    else { return ThreadLocalRandom.current().nextFloat(); }
  }

  /**
   * Replaces {@link ThreadLocalRandom#nextDouble()}
   */
  public double nextDouble() {
    if (replaceTester) {
      throw new UnsupportedOperationException("nextDouble() was called. Test manually.");
    }
    else { return ThreadLocalRandom.current().nextDouble(); }
  }

  /**
   * Replaces {@link ThreadLocalRandom#nextDouble(double, double)}
   */
  public double nextDouble(double a, double b) {
    if (replaceTester) {
      throw new UnsupportedOperationException("nextDouble(double, double) was called. Test manually.");
    }
    else { return ThreadLocalRandom.current().nextDouble(a, b); }
  }

  /**
   * Replaces {@link ThreadLocalRandom#nextDouble(double)}
   */
  public double nextDouble(double a) {
    if (replaceTester) {
      throw new UnsupportedOperationException("nextDouble(double) was called. Test manually.");
    }
    else { return ThreadLocalRandom.current().nextDouble(a); }
  }


}
