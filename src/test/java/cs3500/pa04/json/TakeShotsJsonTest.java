package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * to test TakeShotsJson
 */
class TakeShotsJsonTest {

  /**
   * tests take shots json
   */
  @Test
  public void testTakeShotsJson() {
    CoordJson coordJson = new CoordJson(6, 7);
    CoordJson[] coordJsons = new CoordJson[]{coordJson};
    VolleyJson volleyJson = new VolleyJson(coordJsons);
    TakeShotsJson takeShotsJson = new TakeShotsJson(volleyJson);

    assertNotNull(takeShotsJson);
    assertEquals(volleyJson, takeShotsJson.coordinates());
  }
}
