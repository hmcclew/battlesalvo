package cs3500.pa04.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.client.model.coordinate.BattleSalvoCoord;
import cs3500.pa04.client.model.coordinate.Coord;
import cs3500.pa04.client.view.RoundView;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoundViewTest {

  private RoundView roundView = new RoundView(4, 6, 6);

  private RoundView roundView2 = new RoundView();

  private List<Coord> alreadyShot = new ArrayList<>();

  private List<Coord> opponentShips = new ArrayList<>();

  private List<Coord> userShips = new ArrayList<>();

  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  @BeforeEach
  public void setup() {
    Coord c1 = new BattleSalvoCoord(2, 2);
    Coord c2 = new BattleSalvoCoord(4, 7);
    Coord c3 = new BattleSalvoCoord(6, 9);
    alreadyShot.add(c1);
    opponentShips.add(c2);
    alreadyShot.add(c3);
    opponentShips.add(c3);
    Coord c4 = new BattleSalvoCoord(3, 5);
    Coord c5 = new BattleSalvoCoord(3, 6);
    opponentShips.add(c4);
    userShips.add(c4);
    userShips.add(c5);
    alreadyShot.add(c4);
    System.setOut(new PrintStream(outputStreamCaptor));
    System.setOut(System.out);
  }

  @Test
  public void testPrintOpponentBoard() {
    roundView.printOpponentBoard(opponentShips, alreadyShot);
    String expectedOutput = "Opponent Board Data: \n"
        +
        " 0  0  0  0  0  0 \n"
        +
        " 0  0  0  0  0  0 \n"
        +
        " 0  0  M  0  0  0 \n"
        +
        " 0  0  0  0  0  0 \n"
        +
        " 0  0  0  0  0  0 \n"
        +
        " 0  0  0  H  0  0 \n\n";
    assertEquals(expectedOutput, outputStreamCaptor.toString());
  }

  @Test
  public void testPrintUserBoard() {
    roundView.printUserBoard(userShips, alreadyShot);
    String expectedOutput = "Your Board: \n"
        +
        " 0  0  0  0  0  0 \n"
        +
        " 0  0  0  0  0  0 \n"
        +
        " 0  0  M  0  0  0 \n"
        +
        " 0  0  0  0  0  0 \n"
        +
        " 0  0  0  0  0  0 \n"
        +
        " 0  0  0  H  0  0 \n\n";
    assertEquals(expectedOutput, outputStreamCaptor.toString());
  }

  @Test
  public void testDisplay() {
    roundView.display();
    String expectedOutput = "Please Enter 4 Shots: \n";
    assertEquals(expectedOutput, outputStreamCaptor.toString());
  }

  @Test
  public void testHandleInvalidInput() {
    roundView.handleInvalidInput();
    String expectedOutput = "Invalid Shots. Please Enter 4 Shots.\n";
    assertEquals(expectedOutput, outputStreamCaptor.toString());
  }

  @Test
  public void testDisplaySuccessfulHits() {
    roundView.displaySuccessfulHits(alreadyShot, "Manual Player");
    String expectedOutput = "Manual Player Successful Hits: \n"
        +
        "[2 2]\n"
        +
        "[6 9]\n"
        +
        "[3 5]\n";
    assertEquals(expectedOutput, outputStreamCaptor.toString());
  }
}