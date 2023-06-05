package cs3500.pa04.model.ship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ShipTypeTest {

  @Test
  public void testShipType() {
    assertEquals("CARRIER", ShipType.CARRIER.name());
    assertEquals("BATTLESHIP", ShipType.BATTLESHIP.name());
    assertEquals("DESTROYER", ShipType.DESTROYER.name());
    assertEquals("SUBMARINE", ShipType.SUBMARINE.name());
  }

}