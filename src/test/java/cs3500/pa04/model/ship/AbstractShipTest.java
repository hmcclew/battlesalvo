package cs3500.pa04.model.ship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.model.coordinate.BattleSalvoCoord;
import cs3500.pa04.model.coordinate.Coord;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

class AbstractShipTest {

  private static class TestShip extends AbstractShip {
    public TestShip(Random random) {
      this.random = random;
      random.setSeed(4);
    }

    /**
     * Gets the size of a ship
     *
     * @return the size of a ship
     */
    @Override
    public int getSize() {
      return 2;
    }
  }

  Random random = new Random();
  TestShip ship = new TestShip(random);

  @Test
  public void testGeneratePlacement() {
    ship.generatePlacement(6, 6, new ArrayList<>());
    List<Coord> placement = ship.getPlacement();

    Coord c1 = placement.get(0);
    Coord c2 = placement.get(1);

    assertEquals(c1, new BattleSalvoCoord(3, 2));
    assertEquals(c2, new BattleSalvoCoord(3, 1));
    assertEquals(placement, new ArrayList<>(Arrays.asList(c1, c2)));

    ship.clearPlacement();

    ship.generatePlacement(3, 3, new ArrayList<>());
    List<Coord> placement2 = ship.getPlacement();

    Coord c3 = placement2.get(0);
    Coord c4 = placement2.get(1);

    assertEquals(c3, new BattleSalvoCoord(3, 3));
    assertEquals(c4, new BattleSalvoCoord(2, 3));
    assertEquals(placement2, new ArrayList<>(Arrays.asList(c3, c4)));
  }

  @Test
  public void testClearPlacement() {
    ship.clearPlacement();

    assertEquals(ship.getPlacement(), new ArrayList());
  }

}