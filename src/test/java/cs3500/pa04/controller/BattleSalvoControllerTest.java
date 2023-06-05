package cs3500.pa04.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class BattleSalvoControllerTest {

  @Test
  public void testRun() {
    String input = "6 6\n0 0 0 1\n1 1\n1 2\n1 3\n1 4\n1 5\n1 6\n"
        +
        "2 1\n2 2\n2 3\n2 4\n2 5\n2 6\n3 1\n3 2\n3 3\n3 4\n3 5\n3 6\n4 1\n4 2\n4 3\n4 5\n4 6\n"
        +
        "5 1\n5 2\n5 3\n5 4\n5 5\n5 6\n6 1\n6 2\n6 3\n6 3\n6 4\n6 5\n6 6\n";
    ByteArrayInputStream inStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inStream);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    BattleSalvoController controller = new BattleSalvoController();

    controller.run();

    System.setOut(originalOut);

    System.setIn(System.in);
  }

}