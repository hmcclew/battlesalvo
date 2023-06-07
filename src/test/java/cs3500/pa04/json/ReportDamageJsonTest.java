package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * to test the ReportDamageJson
 */
class ReportDamageJsonTest {

  /**
   * test report damage json
   */
  @Test
  public void testReportDamageJson() {
    CoordJson coordJson = new CoordJson(4, 8);
    CoordJson[] coordJsons = new CoordJson[]{coordJson};
    VolleyJson volleyJson = new VolleyJson(coordJsons);
    ReportDamageJson reportDamageJson = new ReportDamageJson(volleyJson);

    assertNotNull(reportDamageJson);
    assertEquals(volleyJson, reportDamageJson.coordinates());
  }
}