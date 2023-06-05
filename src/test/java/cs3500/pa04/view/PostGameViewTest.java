package cs3500.pa04.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostGameViewTest {

  private PostGameView postGameView = new PostGameView();

  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  @BeforeEach
  public void setUp() {
    System.setOut(new PrintStream(outputStreamCaptor));
    System.setOut(System.out);
  }

  @Test
  public void testDisplay() {
    postGameView.display();
    String expectedOutput = "This game of BattleSalvo has ended!\n";
    assertEquals(expectedOutput, outputStreamCaptor.toString());
  }

  @Test
  public void testHandleWin() {
    postGameView.handleWin("Reason");
    String expectedOutput = "Congratulations! You have won!\nReason\n";
    assertEquals(expectedOutput, outputStreamCaptor.toString());
  }

  @Test
  public void testHandleLoss() {
    postGameView.handleLoss("Reason");
    String expectedOutput = "You lost! Better luck next time!\nReason\n";
    assertEquals(expectedOutput, outputStreamCaptor.toString());
  }

  @Test
  public void testHandleTie() {
    postGameView.handleTie("Reason");
    String expectedOutput = "The game ended in a tie!\nReason\n";
    assertEquals(expectedOutput, outputStreamCaptor.toString());
  }
}