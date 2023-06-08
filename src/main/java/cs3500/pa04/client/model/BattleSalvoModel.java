package cs3500.pa04.client.model;

import cs3500.pa04.client.model.coordinate.Coord;
import cs3500.pa04.client.model.player.AbstractPlayer;
import cs3500.pa04.client.model.player.AutomatedPlayer;
import cs3500.pa04.client.model.player.ManualPlayer;
import cs3500.pa04.client.model.ship.ShipType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class to represent the model for a game of BattleSalvo
 */
public class BattleSalvoModel {
  private int boardSizeX;
  private int boardSizeY;
  private List<Coord> manualFiredShots;
  private List<Coord> automatedFiredShots;
  private Map<ShipType, Integer> specifications;

  /**
   * Constructor for a BattleSalvoModel
   *
   * @param boardSizeX the width of the board
   * @param boardSizeY the height of the board
   * @param specifications the specifications for a player board including
   *                       ship types and their count
   */
  public BattleSalvoModel(int boardSizeX, int boardSizeY, Map<ShipType, Integer> specifications) {
    this.boardSizeX = boardSizeX;
    this.boardSizeY = boardSizeY;
    this.specifications = specifications;
    this.manualFiredShots = new ArrayList<>();
    this.automatedFiredShots = new ArrayList<>();
  }

  /**
   * Calls the appropriate methods for each player to take shots
   *
   * @param manualPlayer the manual player
   * @param automatedPlayer the automated player
   */
  public void takeShots(ManualPlayer manualPlayer, AutomatedPlayer automatedPlayer) {
    manualFiredShots = manualPlayer.takeShots();
    automatedFiredShots = getAutomatedFiredShots(automatedPlayer);
  }

  /**
   * updates both players with damage done to their board
   *
   * @param manualPlayer the manual player
   * @param automatedPlayer the automated player
   */
  public void updatePlayers(ManualPlayer manualPlayer, AutomatedPlayer automatedPlayer) {
    List<Coord> damagedAutomated = automatedPlayer.reportDamage(manualFiredShots);
    List<Coord> damagedManual = manualPlayer.reportDamage(automatedFiredShots);

    manualPlayer.updateRemainingShips(damagedManual);
    automatedPlayer.updateRemainingShips(damagedAutomated);
    manualPlayer.successfulHits(damagedAutomated);
    automatedPlayer.successfulHits(damagedManual);
  }

  /**
   * Determines if the end game condition has been met and either player has no remaining ships
   *
   * @param manualPlayer the manual player
   * @param automatedPlayer the automated player
   * @return true if the game has ended
   */
  public boolean endGame(ManualPlayer manualPlayer, AutomatedPlayer automatedPlayer) {
    return manualPlayer.getNumRemainingShips() == 0 || automatedPlayer.getNumRemainingShips() == 0;
  }

  /**
   * Runs a single round of BattleSalvo, including prompting users for shots and updating the game
   * state accordingly
   *
   * @param manualPlayer the manual player
   * @param automatedPlayer the automated player
   */
  public void setupRound(ManualPlayer manualPlayer, AutomatedPlayer automatedPlayer) {
    updateAllowedShots(manualPlayer);
    updateAllowedShots(automatedPlayer);
  }

  /**
   * Determines the winner of the game based on remaining ship counts and calls the appropriate
   * end game methods for players
   *
   * @param manualPlayer the manual player
   * @param automatedPlayer the automated player
   */
  public void determineWinner(ManualPlayer manualPlayer, AutomatedPlayer automatedPlayer) {
    if (manualPlayer.getNumRemainingShips() == 0 && automatedPlayer.getNumRemainingShips() == 0) {
      manualPlayer.endGame(GameResult.DRAW, "Both Players Sunk All Ships Last Round");
      automatedPlayer.endGame(GameResult.DRAW, "Both Players Sunk All Ships Last Round");
    } else if (automatedPlayer.getNumRemainingShips() == 0) {
      manualPlayer.endGame(GameResult.WIN, "You sunk all of your opponent's Ships!");
      automatedPlayer.endGame(GameResult.LOSE, "All of your ships have been sunk.");
    } else if (manualPlayer.getNumRemainingShips() == 0) {
      manualPlayer.endGame(GameResult.LOSE, "All of your ships have been sunk.");
      automatedPlayer.endGame(GameResult.WIN, "You sunk all of your opponent's Ships!");
    }
  }

  /**
   * Initiates taking shots for an automated player
   *
   * @param automatedPlayer the automated player
   * @return the list of shots fired by the automated player
   */
  public List<Coord> getAutomatedFiredShots(AutomatedPlayer automatedPlayer) {
    boolean validAutomated = false;
    automatedFiredShots = new ArrayList<>();
    while (!validAutomated) {
      automatedFiredShots = automatedPlayer.takeShots();
      if (validCoordinates(automatedFiredShots, automatedPlayer)) {
        for (Coord shot : automatedFiredShots) {
          automatedPlayer.addToAlreadyShot(shot);
        }
        validAutomated = true;
      }
    }
    return automatedFiredShots;
  }

  /**
   * Updates the number of allowed shots each user can fire during each round
   *
   * @param player the player who's allowed shots are being updated
   */
  public void updateAllowedShots(AbstractPlayer player) {
    int openCoordinates = (boardSizeX * boardSizeY) - player.getAlreadyShot().size();
    int allowedShots = Math.min(player.getNumRemainingShips(), openCoordinates);
    player.setAllowedShots(allowedShots);
  }

  /**
   * determines if the attempted shots from players are valid coordinates within this game
   *
   * @param firedShots the shots fired during this round by the player
   * @param player     the player whose shots are being assessed
   * @return true if all shots fired by this player are valid
   */
  public boolean validCoordinates(List<Coord> firedShots, AbstractPlayer player) {
    boolean validShot = true;
    for (Coord shot : firedShots) {
      if (shot.getX() <= 0 || shot.getY() <= 0 || shot.getX() > boardSizeX
          ||
          shot.getY() > boardSizeY || player.getAlreadyShot().contains(shot)) {
        validShot = false;
      }
    }
    return validShot;
  }

  /**
   * Sets up the board for both players at the start of the game
   *
   * @param player the player whos board is to be set up
   */
  public void setupPlayers(AbstractPlayer player) {
    player.setup(boardSizeY, boardSizeX, specifications);
  }

}
