package cs3500.pa04.client.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class BattleSalvoControllerTest {

  @Test
  public void testRun() {
    String input = "6 6\n0 0 0 1\n1 1\n1 2\n1 3\n1 4\n1 5\n1 0\n"
        +
        "2 1\n2 2\n2 3\n2 4\n2 5\n2 0\n3 1\n3 2\n3 3\n3 4\n3 5\n3 0\n4 1\n4 2\n4 3\n4 5\n4 0\n"
        +
        "5 1\n5 2\n5 3\n5 4\n5 5\n5 0\n0 1\n0 2\n0 3\n0 3\n0 4\n0 5\n0 0\n";
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