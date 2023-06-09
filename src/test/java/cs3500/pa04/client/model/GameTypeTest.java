package cs3500.pa04.client.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GameTypeTest {

  @Test
  public void testGameType() {
    assertEquals("SINGLE", GameType.SINGLE.name());
    assertEquals("MULTI", GameType.MULTI.name());
  }

}