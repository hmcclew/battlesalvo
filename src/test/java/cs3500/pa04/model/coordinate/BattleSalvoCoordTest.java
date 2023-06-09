package cs3500.pa04.model.coordinate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.client.model.coordinate.BattleSalvoCoord;
import cs3500.pa04.client.model.coordinate.Coord;
import cs3500.pa04.client.model.ship.Submarine;
import org.junit.jupiter.api.Test;

class BattleSalvoCoordTest {

  Coord c1 = new BattleSalvoCoord(3, 4);
  Coord c2 = new BattleSalvoCoord(4, 5);
  Coord c3 = new BattleSalvoCoord(4, 5);

  Submarine submarine = new Submarine();

  @Test
  public void testGetValues() {
    assertEquals(3, c1.getX());
    assertEquals(4, c2.getX());

    assertEquals(4, c1.getY());
    assertEquals(5, c2.getY());
  }

  @Test
  public void testGetHasHitShip() {
    assertFalse(c2.getHasHitShip());
    assertFalse(c1.getHasHitShip());
  }

  @Test
  public void testHitCoordinate() {
    assertFalse(c1.getHasHitShip());

    c1.hitCoordinate();

    assertTrue(c1.getHasHitShip());
  }

  @Test
  public void testEquals() {
    assertTrue(c2.equals(c3));
    assertFalse(c1.equals(c3));
    assertFalse(c2.equals(c1));
    assertFalse(c2.equals(submarine));
  }

}