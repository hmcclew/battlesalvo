package cs3500.pa04.model.player;

import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.coordinate.BattleSalvoCoord;
import cs3500.pa04.model.coordinate.Coord;
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
    return "AI Player";
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
      int x = random.nextInt(16);
      int y = random.nextInt(16);
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
  }
}
