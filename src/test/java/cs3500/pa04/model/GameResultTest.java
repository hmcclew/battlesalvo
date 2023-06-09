package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.client.model.GameResult;
import org.junit.jupiter.api.Test;

class GameResultTest {
  @Test
  public void testGameResult() {
    assertEquals("WIN", GameResult.WIN.name());
    assertEquals("LOSE", GameResult.LOSE.name());
    assertEquals("DRAW", GameResult.DRAW.name());
  }

}