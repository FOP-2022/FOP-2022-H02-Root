package h02;

import org.sourcegrade.jagr.api.rubric.*;
import org.sourcegrade.jagr.api.testing.ClassTransformer;
import org.sourcegrade.jagr.api.testing.RubricConfiguration;

import java.util.concurrent.ThreadLocalRandom;

@RubricForSubmission("h02")
public class H02_RubricProvider implements RubricProvider {

  // ---------------------------------------- H1 ----------------------------------------

  public static final Criterion H1_T1 = Criterion.builder()
    .shortDescription("H1_T1: Das Array allRobots wird korrekt erstellt (korrekte Größe).")
    .grader(
      Grader.testAwareBuilder()
        .requirePass(JUnitTestRef.ofMethod(() -> TutorTests.class.getMethod("testAllRobotsSize")))
        .pointsPassedMax()
        .pointsFailedMin()
        .build()
    ).build();

  public static final Criterion H1_T2 = Criterion.builder()
    .shortDescription("H1_T2: allRobots wird korrekt gefüllt (vor allem Roboter auf Diagonale mit korrekter Blickrichtung).")
    .grader(
      Grader.testAwareBuilder()
        .requirePass(JUnitTestRef.ofMethod(() -> TutorTests.class.getMethod("testAllRobotsDiag")))
        .pointsPassedMax()
        .pointsFailedMin()
        .build()
    ).build();

  public static final Criterion H1_T3 = Criterion.builder()
    .shortDescription("H1_T3: Das Array paces wird korrekt erstellt (korrekte Länge) und die eingetragenen Zahlen liegen im Intervall [1...5].")
    .grader(
      Grader.testAwareBuilder()
        .requirePass(JUnitTestRef.ofMethod(() -> TutorTests.class.getMethod("testInitPaces")))
        .pointsPassedMax()
        .pointsFailedMin()
        .build()
    ).build();

  public static final Criterion H1_M1 = Criterion.builder()
    .shortDescription("H1_M1: Die Arrays allRobots und paces werden korrekt (durch Aufruf von initializeRobots und initializePaces) deklariert und initialisiert. Zudem wird in initializePaces die verbindliche Anforderung bzgl. Zufallszahlen erfüllt.")
    .build();

  public static final Criterion H1 = Criterion.builder()
    .shortDescription("H1: Initialisierungen vor der Hauptschleife")
    .addChildCriteria(H1_T1, H1_T2, H1_T3, H1_M1)
    .build();

//---------------------------------------- H3 ----------------------------------------

  public static final Criterion H3 = Criterion.builder()
    .shortDescription("H3_M1: Die Hauptschleife wird mit korrekter Abbruchbedingung erstellt.")
    .build();

  // ---------------------------------------- H3.1 ----------------------------------------

  public static final Criterion H3_1_M1 = Criterion.builder()
    .shortDescription("H3_1_M1: Es wird eine äußere for-Schleife über die Indizes von allRobots erstellt. In jedem Durchlauf wird ein Roboter des Arrays betrachtet / bewegt..")
    .build();

  public static final Criterion H3_1_M2 = Criterion.builder()
    .shortDescription("H3_1_M2: Es wird eine innere for-Schleife erstellt, welche paces[i] Durchläufe hat. ")
    .build();

  public static final Criterion H3_1_T1 = Criterion.builder()
    .shortDescription("H3_1_T1: Für einfache Movements (ein Roboter, paces = [1]) funktioniert moveForward korrekt.")
    .grader(
      Grader.testAwareBuilder()
        .requirePass(JUnitTestRef.ofMethod(() -> TutorTests.class.getMethod("testSimpleMoves")))
        .pointsPassedMax()
        .pointsFailedMin()
        .build()
    ).build();

  public static final Criterion H3_1_T2 = Criterion.builder()
    .shortDescription("H3_1_T2: Für komplexere Movements (mehrere Roboter, verschiedene paces), sowie für den Fall dass paces ein Null-Array ist, funktioniert moveForward korrekt.")
    .grader(
      Grader.testAwareBuilder()
        .requirePass(JUnitTestRef.ofMethod(() -> TutorTests.class.getMethod("testComplexMoves")))
        .pointsPassedMax()
        .pointsFailedMin()
        .build()
    ).build();

  public static final Criterion H3_1 = Criterion.builder()
    .shortDescription("H3.1: Vorwärtsgehen der Roboter")
    .addChildCriteria(H3_1_M1, H3_1_M2, H3_1_T1, H3_1_T2)
    .build();

  // ---------------------------------------- H3.2 ----------------------------------------

  public static final Criterion H3_2_M1 = Criterion.builder()
    .shortDescription("H3_2_M1: Es wird geeignet geprüft, ob noch mindestens drei Elemente in allRobots vorhanden sind. Außerdem wird paces korrekt geupdated (Aufruf von swapPaces).")
    .build();

  public static final Criterion H3_2_T1 = Criterion.builder()
    .shortDescription("H3_2_T1: generateThreeDistinctInts funktioniert korrekt. Es werden drei distinkte natürliche Zufallszahlen i1, i2, i3 im Intervall [0, allRobots.length-1] erstellt.")
    .grader(
      Grader.testAwareBuilder()
        .requirePass(JUnitTestRef.ofMethod(() -> TutorTests.class.getMethod("testGenerateThreeDistinctInts")))
        .pointsPassedMax()
        .build()
    )
    .build();

  public static final Criterion H3_2_T2 = Criterion.builder()
    .shortDescription("H3_2_T2: orderThreeInts ordnet in einem Array aus drei distinkten Integer diese aufsteigend nach der Größe und gibt das sortierte Array zurück.")
    .grader(
      Grader.testAwareBuilder()
        .requirePass(JUnitTestRef.ofMethod(() -> TutorTests.class.getMethod("testOrderThreeInts")))
        .pointsPassedMax()
        .pointsFailedMin()
        .build()
    ).build();

  public static final Criterion H3_2_T3 = Criterion.builder()
    .shortDescription("H3_2_T3: swapPaces funktioniert korrekt.")
    .grader(
      Grader.testAwareBuilder()
        .requirePass(JUnitTestRef.ofMethod(() -> TutorTests.class.getMethod("testSwapPaces")))
        .pointsPassedMax()
        .pointsFailedMin()
        .build()
    ).build();

  public static final Criterion H3_2 = Criterion.builder()
    .shortDescription("H3.2: Vertauschen der Schrittweiten")
    .addChildCriteria(H3_2_M1, H3_2_T1, H3_2_T2, H3_2_T3)
    .build();

  // ---------------------------------------- H3.3 ----------------------------------------
  public static final Criterion H3_3_M1 = Criterion.builder()
    .shortDescription("H3_3_M1: Es wird korrekt festgestellt, ob der aktuelle Durchlauf der while-Schleife ein reduzierender Durchlauf ist oder nicht (bspw. Mit einer Laufvariable, die Iterationen in while-Schleife zählt)")
    .build();

  public static final Criterion H3_3_M2 = Criterion.builder()
    .shortDescription("H3_3_M2: Der deleteIndex wird randomisiert erzeugt (Methode Random.nextInt(), richtiges Intervall). reduceRobots und reducePaces werden beide mit diesem Index aufgerufen und updaten allRobots bzw. paces.")
    .build();

  public static final Criterion H3_3_T1 = Criterion.builder()
    .shortDescription("H3_3_T1: reduceRobots funktioniert korrekt.")
    .grader(
      Grader.testAwareBuilder()
        .requirePass(JUnitTestRef.ofMethod(() -> TutorTests.class.getMethod("testReduceRobots")))
        .pointsPassedMax()
        .pointsFailedMin()
        .build()
    ).build();

  public static final Criterion H3_3_T2 = Criterion.builder()
    .shortDescription("H3_3_T2: reducePaces funktioniert korrekt.")
    .grader(
      Grader.testAwareBuilder()
        .requirePass(JUnitTestRef.ofMethod(() -> TutorTests.class.getMethod("testReducePaces")))
        .pointsPassedMax()
        .pointsFailedMin()
        .build()
    ).build();
  public static final Criterion H3_3 = Criterion.builder()
    .shortDescription("H3.3: Roboterzahl schrittweise reduzieren")
    .addChildCriteria(H3_3_M1, H3_3_M2, H3_3_T1, H3_3_T2)
    .build();

  // ---------------------------------------- Rubric ----------------------------------------

  public static final Rubric RUBRIC = Rubric.builder()
    .title("H02")
    .addChildCriteria(H1, H3, H3_1, H3_2, H3_3)
    .build();

  @Override
  public Rubric getRubric() {
    return RUBRIC;
  }

  @Override
  public void configure(RubricConfiguration configuration) {
    configuration.addTransformer(ClassTransformer.replacement(ThreadLocalRandomTester.class, ThreadLocalRandom.class));
  }
}
