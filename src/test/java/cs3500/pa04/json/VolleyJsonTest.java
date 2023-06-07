package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * to test volley json record
 */
class VolleyJsonTest {
  /**
   * test volley json record
   */
  @Test
  public void testVolleyJson() {
    CoordJson coordJson = new CoordJson(6, 8);
    CoordJson[] coordJsons = new CoordJson[]{coordJson};
    VolleyJson volleyJson = new VolleyJson(coordJsons);

    assertNotNull(volleyJson);
    assertEquals(coordJsons, volleyJson.coordinates());
  }
}