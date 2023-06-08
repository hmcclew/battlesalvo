package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * to test the FleetJson record
 */
class FleetJsonTest {

  /**
   * tests FleetJson
   */
  @Test
  public void testFleetJson() {
    CoordJson coordJson = new CoordJson(3, 6);
    ShipJson shipJson = new ShipJson(coordJson, 5, "HORIZONTAL");
    ShipJson[] fleet = new ShipJson[]{shipJson};
    FleetJson fleetJson = new FleetJson(fleet);
    assertNotNull(fleetJson);
    assertArrayEquals(fleet, fleetJson.fleet());
  }
}