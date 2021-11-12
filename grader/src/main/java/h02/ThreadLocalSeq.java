package h02;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ThreadLocalSeq {

  private final List<Integer> sequence;
  int currentNumber;
  int currentRepetition;

  public ThreadLocalSeq(int[] sequence) {
    this.currentNumber = 0;
    this.currentRepetition = 0;
    this.sequence = Arrays.stream(sequence).boxed().collect(Collectors.toList());
  }

  public int next() {
    if (currentNumber == sequence.size()) {
      throw new IndexOutOfBoundsException("End of sequence reached.");
    }
    return sequence.get(currentNumber++);
  }
}
