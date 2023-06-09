package cs3500.pa04.model.ship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.client.model.ship.Battleship;
import org.junit.jupiter.api.Test;

class BattleshipTest {

  Battleship battleship = new Battleship();

  @Test
  public void testGetSize() {
    assertEquals(5, battleship.getSize());
  }

}