package cs3500.pa04.client.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.client.view.ShipSetupView;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipSetupViewTest {

  private ShipSetupView shipSetupView = new ShipSetupView(7);

  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  @BeforeEach
  public void setUp() {
    System.setOut(new PrintStream(outputStreamCaptor));
    System.setOut(System.out);
  }

  @Test
  public void testDisplay() {
    shipSetupView.display();
    String expectedOutput = "Please enter your fleet in the order [Carrier, Battleship, "
        + "Destroyer, Submarine].\nRemember, your fleet may not exceed size 7.\n";
    assertEquals(expectedOutput, outputStreamCaptor.toString());
  }

  @Test
  public void testHandleInvalidInput() {
    shipSetupView.handleInvalidInput();
    String expectedOutput = "Uh Oh! You've entered invalid fleet sizes.\n"
        + "Please enter your fleet in order [Carrier, Battleship, "
        + "Destroyer, Submarine].\nRemember, your fleet may not exceed size 7.\n";
    assertEquals(expectedOutput, outputStreamCaptor.toString());
  }
}