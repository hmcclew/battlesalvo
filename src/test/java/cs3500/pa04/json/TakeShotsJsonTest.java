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
    TakeShotsJson takeShotsJson = new TakeShotsJson(coordJsons);

    assertNotNull(takeShotsJson);
    assertEquals(coordJsons, takeShotsJson.coordinates());
  }
}
