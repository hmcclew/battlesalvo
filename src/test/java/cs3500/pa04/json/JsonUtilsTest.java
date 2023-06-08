package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

/**
 * to test JsonUtils record
 */
class JsonUtilsTest {

  /**
   * test serializeRecord
   */
  @Test
  public void testSerializeRecord() {
    Record record = new CoordJson(2, 6);
    JsonNode jsonNode = JsonUtils.serializeRecord(record);
    assertNotNull(jsonNode);
  }
}