package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * to test SetupJson
 */
class SetupJsonTest {

  /**
   * test setup json
   */
  @Test
  public void testSetUpJson() {
    CoordJson coordJson = new CoordJson(4, 3);
    ShipJson shipJson = new ShipJson(coordJson, 4, "vertical");
    ShipJson[] shipJsons = new ShipJson[]{shipJson};
    SetupJson setupJson = new SetupJson(shipJsons);

    assertNotNull(setupJson);
    assertEquals(shipJsons, setupJson.fleet());
  }

}