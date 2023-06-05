package cs3500.pa04.client.controller;

import cs3500.pa04.client.controller.Controller;
import cs3500.pa04.client.model.BattleSalvoModel;
import cs3500.pa04.client.model.GameResult;
import cs3500.pa04.client.model.coordinate.BattleSalvoCoord;
import cs3500.pa04.client.model.coordinate.Coord;
import cs3500.pa04.client.model.player.AutomatedPlayer;
import cs3500.pa04.client.model.player.ManualPlayer;
import cs3500.pa04.client.model.ship.ShipType;
import cs3500.pa04.client.view.DimensionSetupView;
import cs3500.pa04.client.view.PostGameView;
import cs3500.pa04.client.view.RoundView;
import cs3500.pa04.client.view.ShipSetupView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Controller for a Game of BattleSalvo
 */
public class BattleSalvoController implements Controller {
  private BattleSalvoModel battleSalvoModel = new BattleSalvoModel(0, 0,
      new HashMap<>());
  private ManualPlayer manualPlayer = new ManualPlayer(this);
  private AutomatedPlayer automatedPlayer = new AutomatedPlayer();
  private Scanner scanner = new Scanner(System.in);
  private int boardSizeX;
  private int boardSizeY;
  private Map<ShipType, Integer> specifications = new HashMap<>();
  private ShipSetupView shipSetupView;

  /**
   * Runs the game by calling all appropriate methods within the model
   */
  public void run() {
    getValidBoard();
    getValidFleet();
    battleSalvoModel.setupPlayers(manualPlayer);
    battleSalvoModel.setupPlayers(automatedPlayer);
    while (!battleSalvoModel.endGame(manualPlayer, automatedPlayer)) {
      battleSalvoModel.setupRound(manualPlayer, automatedPlayer);
      displayCurrentState();
      battleSalvoModel.takeShots(manualPlayer, automatedPlayer);
      battleSalvoModel.updatePlayers(manualPlayer, automatedPlayer);
      displaySuccessfulPlayerHits();
    }
    battleSalvoModel.determineWinner(manualPlayer, automatedPlayer);
  }

  /**
   * Displays the player's successful hits at the end of each round
   */
  private void displaySuccessfulPlayerHits() {
    RoundView roundView = new RoundView();
    roundView.displaySuccessfulHits(manualPlayer.getSuccessfulHits(), manualPlayer.name());
    roundView.displaySuccessfulHits(automatedPlayer.getSuccessfulHits(), automatedPlayer.name());
  }

  /**
   * Parses the shots entered by the manual player
   *
   * @return the list of coordinates for shots fired by the manual player
   */
  private List<Coord> parseManualShots() {
    List<Coord> firedShots = new ArrayList<>();
    int count = 0;
    while (count < manualPlayer.getAllowedShots()) {
      int x = scanner.nextInt();
      int y = scanner.nextInt();
      Coord firedShot = new BattleSalvoCoord(x, y);
      firedShots.add(firedShot);
      count++;
    }
    return firedShots;
  }

  /**
   * Initiates taking shots for a manual player
   *
   * @return the list of shots fired by the manual player
   */
  public List<Coord> getManualFiredShots() {
    boolean validManual = false;
    List<Coord> manualFiredShots = new ArrayList<>();
    while (!validManual) {
      manualFiredShots = parseManualShots();

      if (!battleSalvoModel.validCoordinates(manualFiredShots, manualPlayer)) {
        RoundView roundView = new RoundView(manualPlayer.getNumRemainingShips(),
            boardSizeX, boardSizeY);
        roundView.handleInvalidInput();
      } else {
        for (Coord shot : manualFiredShots) {
          manualPlayer.addToAlreadyShot(shot);
        }
        validManual = true;
      }
    }
    return manualFiredShots;
  }

  /**
   * Displays the current state of the game at the beginning of each round
   */
  private void displayCurrentState() {
    RoundView roundView = new RoundView(manualPlayer.getAllowedShots(),
        boardSizeX, boardSizeY);
    roundView.printOpponentBoard(automatedPlayer.getShipOccupiedCoordinates(),
        manualPlayer.getAlreadyShot());
    roundView.printUserBoard(manualPlayer.getShipOccupiedCoordinates(),
        automatedPlayer.getAlreadyShot());
    roundView.display();
  }

  /**
   * Ensures that the dimensions entered by the manual player for board dimension are valid,
   * prompts the player again for new dimensions if they are not
   */
  private void getValidBoard() {
    DimensionSetupView dimensionSetupView = new DimensionSetupView();
    boolean validDimensions = false;
    dimensionSetupView.display();
    while (!validDimensions) {
      boardSizeX = scanner.nextInt();
      boardSizeY = scanner.nextInt();
      if (boardSizeX < 16 && boardSizeX > 5 && boardSizeY < 16 && boardSizeY > 5) {
        shipSetupView = new ShipSetupView(Math.min(boardSizeX, boardSizeY));
        validDimensions = true;
      } else {
        dimensionSetupView.handleInvalidInput();
      }
    }
  }

  /**
   * Ensures the fleet specifications entered by the manual player are valid, prompts the
   * player again for new specifications otherwise
   */
  private void getValidFleet() {
    boolean validFleet = false;
    shipSetupView.display();
    while (!validFleet) {
      int numCarrier = scanner.nextInt();
      int numBattleship = scanner.nextInt();
      int numDestroyer = scanner.nextInt();
      int numSubmarine = scanner.nextInt();
      if (numSubmarine + numCarrier + numDestroyer + numBattleship
          <=
          Math.min(boardSizeX, boardSizeY)) {
        specifications.put(ShipType.CARRIER, numCarrier);
        specifications.put(ShipType.BATTLESHIP, numBattleship);
        specifications.put(ShipType.DESTROYER, numDestroyer);
        specifications.put(ShipType.SUBMARINE, numSubmarine);
        battleSalvoModel = new BattleSalvoModel(boardSizeX, boardSizeY, specifications);
        validFleet = true;
      } else {
        shipSetupView.handleInvalidInput();
      }
    }
  }

  /**
   * handles the end game by calling the appropriate endgame method in the view depending on
   * the outcome
   *
   * @param result the GameResult that represents the manual player's result
   * @param reason the reason for the game ending
   */
  public void handleEndGame(GameResult result, String reason) {
    PostGameView postGameView = new PostGameView();
    postGameView.display();
    if (result.equals(GameResult.WIN)) {
      postGameView.handleWin(reason);
    } else if (result.equals(GameResult.DRAW)) {
      postGameView.handleTie(reason);
    } else if (result.equals(GameResult.LOSE)) {
      postGameView.handleLoss(reason);
    }
  }
}
