package h02;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ThreadLocalSeq {

  private final List<Integer> sequence;
  int currentNumber;
  int currentRepetition;

  public ThreadLocalSeq(int[] sequence) {
    this.sequence = Arrays.stream(sequence).boxed().collect(Collectors.toList());
    currentNumber = 0;
    currentRepetition = 0;
  }

  public int next() {
    if (currentNumber == sequence.size()) {
      throw new IndexOutOfBoundsException("End of sequence reached.");
    }
    return sequence.get(currentNumber++);
  }
}
