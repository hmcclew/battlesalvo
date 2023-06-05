package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.controller.BattleSalvoController;
import cs3500.pa04.model.player.AutomatedPlayer;
import cs3500.pa04.model.player.ManualPlayer;
import cs3500.pa04.model.ship.ShipType;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BattleSalvoModelTest {

  private Map<ShipType, Integer> specifications = new HashMap<>();
  private BattleSalvoModel model;
  private AutomatedPlayer automatedPlayer = new AutomatedPlayer();
  private ManualPlayer manualPlayer = new ManualPlayer(new BattleSalvoController());

  private AutomatedPlayer automatedPlayer2 = new AutomatedPlayer();
  private ManualPlayer manualPlayer2 = new ManualPlayer(new BattleSalvoController());
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  @BeforeEach
  public void setup() {
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    model = new BattleSalvoModel(6, 6, specifications);
    manualPlayer.setup(6, 6, specifications);
    automatedPlayer.setup(6, 6, specifications);
    automatedPlayer2.setup(6, 6, new HashMap<>());
    manualPlayer2.setup(6, 6, new HashMap<>());
    System.setOut(new PrintStream(outputStreamCaptor));
    System.setOut(System.out);
  }

  @Test
  public void testTakeShots() {
    assertEquals(0, automatedPlayer.getAlreadyShot().size());

    model.takeShots(manualPlayer, automatedPlayer);

    assertEquals(4, automatedPlayer.getAlreadyShot().size());
  }

  @Test
  public void testUpdatePlayers() {
    model.updatePlayers(manualPlayer, automatedPlayer);

    assertEquals(4, manualPlayer.getNumRemainingShips());
    assertEquals(4, automatedPlayer.getNumRemainingShips());
  }

  @Test
  public void testEndGame() {
    assertFalse(model.endGame(manualPlayer, automatedPlayer));
    assertTrue(model.endGame(manualPlayer, automatedPlayer2));
  }

  @Test
  public void testSetupRound() {
    manualPlayer.setAllowedShots(6);
    automatedPlayer.setAllowedShots(10);

    model.setupRound(manualPlayer, automatedPlayer);

    assertEquals(4, manualPlayer.getAllowedShots());
    assertEquals(4, automatedPlayer.getAllowedShots());
  }

  @Test
  public void testSetupPlayers() {
    manualPlayer = new ManualPlayer(new BattleSalvoController());
    automatedPlayer = new AutomatedPlayer();

    model.setupPlayers(manualPlayer);
    model.setupPlayers(automatedPlayer);

    assertEquals(4, manualPlayer.getAllowedShots());
    assertEquals(4, automatedPlayer.getAllowedShots());
  }

  @Test
  public void testDetermineWinnerManual() {
    model.determineWinner(manualPlayer2, automatedPlayer);
    String expectedOutput = "This game of BattleSalvo has ended!\n"
        +
        "You lost! Better luck next time!\n"
        +
        "All of your ships have been sunk.\n";
    assertEquals(expectedOutput, outputStreamCaptor.toString());
  }

  @Test
  public void testDetermineWinnerAutomated() {
    model.determineWinner(manualPlayer, automatedPlayer2);
    String expectedOutput2 = "This game of BattleSalvo has ended!\n"
        +
        "Congratulations! You have won!\n"
        +
        "You sunk all of your opponent's Ships!\n";
    assertEquals(expectedOutput2, outputStreamCaptor.toString());
  }

  @Test
  public void testDetermineWinnerTie() {
    model.determineWinner(manualPlayer2, automatedPlayer2);
    String expectedOutput2 = "This game of BattleSalvo has ended!\n"
        +
        "The game ended in a tie!\n"
        +
        "Both Players Sunk All Ships Last Round\n";
    assertEquals(expectedOutput2, outputStreamCaptor.toString());
  }
}