package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * to test the CoordJson record
 */
class CoordJsonTest {

  /**
   * tests CoordJson
   */
  @Test
  public void testCoordJson() {
    CoordJson coordJson = new CoordJson(2, 4);
    assertNotNull(coordJson);
    assertEquals(2, coordJson.x());
    assertEquals(4, coordJson.y());
  }

}