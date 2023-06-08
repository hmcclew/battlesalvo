package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.*;

import cs3500.pa04.client.model.GameType;
import org.junit.jupiter.api.Test;

/**
 * to test the JoinJson record
 */
class JoinJsonTest {

  /**
   * tests JoinJson
   */
  @Test
  public void testJoinJson() {
    JoinJson joinJson = new JoinJson("Kevin", "SINGLE");

    assertNotNull(joinJson);
    assertEquals("Kevin", joinJson.name());
    assertEquals(GameType.SINGLE, GameType.valueOf(joinJson.gameType()));
  }

}