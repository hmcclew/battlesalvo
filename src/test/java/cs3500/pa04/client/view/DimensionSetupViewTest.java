package cs3500.pa04.client.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.client.view.DimensionSetupView;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DimensionSetupViewTest {

  private DimensionSetupView dimensionSetupView;

  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  @BeforeEach
  public void setUp() {
    dimensionSetupView = new DimensionSetupView();
    System.setOut(new PrintStream(outputStreamCaptor));
    System.setOut(System.out);
  }

  @Test
  public void testDisplay() {
    dimensionSetupView.display();
    String expectedOutput = "Hello! Welcome to the BattleSalvo Game\n"
        + "Please enter a valid height and width below: \n";
    assertEquals(expectedOutput, outputStreamCaptor.toString());
  }

  @Test
  public void testHandleInvalidInput() {
    dimensionSetupView.handleInvalidInput();
    String expectedOutput = "Uh Oh! You've entered invalid dimensions.\n"
        + "Please remember that height and width must be in the range (6, 15), inclusive. "
        + "Try again!\n";
    assertEquals(expectedOutput, outputStreamCaptor.toString());
  }
}