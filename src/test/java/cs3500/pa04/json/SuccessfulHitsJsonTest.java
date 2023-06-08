package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * to test the successful hits json
 */
class SuccessfulHitsJsonTest {
  /**
   * tests successful hits json
   */
  @Test
  public void testSuccessfulHitsJson() {
    CoordJson coordJson = new CoordJson(7, 8);
    CoordJson[] coordJsons = new CoordJson[]{coordJson};
    SuccessfulHitsJson successfulHitsJson = new SuccessfulHitsJson(coordJsons);

    assertNotNull(coordJsons);
    assertEquals(coordJsons, successfulHitsJson.coordinates());
  }

}