package cs3500.pa04.view;

import cs3500.pa04.model.coordinate.BattleSalvoCoord;
import cs3500.pa04.model.coordinate.Coord;
import java.util.List;

/**
 * View Class to represent the screen during a single round of BattleSalvo
 */
public class RoundView implements View {
  int allowedShots;

  int boardSizeX;

  int boardSizeY;

  /**
   * Empty constructor for a RoundView
   */
  public RoundView() {}

  /**
   * Constructor for a RoundView
   *
   * @param allowedShots the number of allowed shots for the manual player during this round
   * @param boardSizeX the width of the board
   * @param boardSizeY the height of the board
   */
  public RoundView(int allowedShots, int boardSizeX, int boardSizeY) {
    this.allowedShots = allowedShots;
    this.boardSizeX = boardSizeX;
    this.boardSizeY = boardSizeY;
  }

  /**
   * Prompts the user to enter their shots for this round
   */
  @Override
  public void display() {
    System.out.println("Please Enter " + allowedShots + " Shots: ");
  }

  /**
   * Prompts the user to re-enter a list of shots if the initial list was invalid
   */
  public void handleInvalidInput() {
    System.out.println("Invalid Shots. Please Enter " + allowedShots + " Shots.");
  }

  /**
   * Displays a player's successful hits on the opponents board from the previous round
   *
   * @param successfulHits the list of successful hits
   * @param playerName the player's successful hits to be displayed
   */
  public void displaySuccessfulHits(List<Coord> successfulHits, String playerName) {
    System.out.println(playerName + " Successful Hits: ");
    for (Coord c : successfulHits) {
      System.out.println("[" + String.valueOf(c.getX()) + " " + String.valueOf(c.getY()) + "]");
    }
  }

  /**
   * Prints the Opponent's board data
   *
   * @param opponentShipCoordinates coordinates where the opponent has placed ships
   * @param alreadyShot coordinates the user has already shot on the opponent's board
   */
  public void printOpponentBoard(List<Coord> opponentShipCoordinates, List<Coord> alreadyShot) {
    System.out.println("Opponent Board Data: ");
    for (int i = 1; i <= boardSizeY; i++) {
      for (int j = 1; j <= boardSizeX; j++) {
        Coord currentCoord = new BattleSalvoCoord(j, i);
        if (opponentShipCoordinates.contains(currentCoord) && alreadyShot.contains(currentCoord)) {
          System.out.print(" H ");
        } else if (alreadyShot.contains(currentCoord)) {
          System.out.print(" M ");
        } else {
          System.out.print(" 0 ");
        }
      }
      System.out.println();
    }
    System.out.println();
  }

  /**
   * Displays the user's board data
   *
   * @param userShipCoordinates coordinates where the user has placed ships
   * @param opponentShots coordinates the opponent has already shot on the user's board
   */
  public void printUserBoard(List<Coord> userShipCoordinates, List<Coord> opponentShots) {
    System.out.println("Your Board: ");
    for (int i = 1; i <= boardSizeY; i++) {
      for (int j = 1; j <= boardSizeX; j++) {
        Coord currentCoord = new BattleSalvoCoord(j, i);
        if (userShipCoordinates.contains(currentCoord) && opponentShots.contains(currentCoord)) {
          System.out.print(" H ");
        } else if (opponentShots.contains(currentCoord)) {
          System.out.print(" M ");
        } else if (userShipCoordinates.contains(currentCoord)) {
          System.out.print(" S ");
        } else {
          System.out.print(" 0 ");
        }
      }
      System.out.println();
    }
    System.out.println();
  }
}
