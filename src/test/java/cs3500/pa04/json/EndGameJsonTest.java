package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.*;

import cs3500.pa04.client.model.GameResult;
import org.junit.jupiter.api.Test;

/**
 * to test the EndGameJson record
 */
class EndGameJsonTest {

  /**
   * tests EndGameJson
   */
  @Test
  public void testEndGameJson() {
    EndGameJson endGameJson = new EndGameJson("DRAW", "all battleships have sunk");
    assertNotNull(endGameJson);
    assertEquals(GameResult.DRAW, GameResult.valueOf(endGameJson.result()));
    assertEquals("all battleships have sunk", endGameJson.reason());
  }
}