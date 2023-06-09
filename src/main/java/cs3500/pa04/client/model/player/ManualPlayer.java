package cs3500.pa04.client.model.player;

import cs3500.pa04.client.BattleSalvoController;
import cs3500.pa04.client.model.GameResult;
import cs3500.pa04.client.model.coordinate.Coord;
import java.util.List;

/**
 * Class to represent a manual player controller for a game of BattleSalvo
 */
public class ManualPlayer extends AbstractPlayer {

  private BattleSalvoController controller;

  /**
   * Constructor for a manual player
   *
   * @param controller the BattleSalvoController being used for this game
   */
  public ManualPlayer(BattleSalvoController controller) {
    this.controller = controller;
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   **/
  @Override
  public String name() {
    return "Manual Player";
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    return controller.getManualFiredShots();
  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
    controller.handleEndGame(result, reason);
  }

}
