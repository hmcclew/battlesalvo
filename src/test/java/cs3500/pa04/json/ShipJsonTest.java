package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * create tests for ShipJson
 */
class ShipJsonTest {

  /**
   * to test ship json
   */
  @Test
  public void testShipJson() {
    CoordJson coordJson = new CoordJson(4,5);
    ShipJson shipJson = new ShipJson(coordJson, 5, "VERTICAL");

    assertNotNull(shipJson);
    assertEquals(coordJson, shipJson.c());
    assertEquals(5, shipJson.length());
    assertEquals("VERTICAL", shipJson.direction());
  }
}