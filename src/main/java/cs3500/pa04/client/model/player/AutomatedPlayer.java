package cs3500.pa04.client.model.player;

import cs3500.pa04.client.model.GameResult;
import cs3500.pa04.client.model.coordinate.BattleSalvoCoord;
import cs3500.pa04.client.model.coordinate.Coord;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class to represent an automated (AI) player for a game of BattleSalvo
 */
public class AutomatedPlayer extends AbstractPlayer {


  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return "swiftiesunite";
  }



  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */

  @Override
  public List<Coord> takeShots() {
    List<Coord> firedShots = new ArrayList<>();
    Random random = new Random();
    int count = 0;
    while (count < getAllowedShots()) {
      int x = random.nextInt(getBoardSizeX() + 1);
      int y = random.nextInt(getBoardSizeY() + 1);
      Coord firedShot = new BattleSalvoCoord(x, y);
      if (!firedShots.contains(firedShot)) {
        firedShots.add(firedShot);
        count++;
      }
    }
    return firedShots;
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
    System.out.println("YOU " + result.toString() + "! " + reason);
  }
}
