package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

/**
 * to test the MessageJson class
 */
class MessageJsonTest {
  /**
   * test MessageJson
   */
  @Test
  public void testMessageJson() {
    MessageJson messageJson = new MessageJson("example",
        new ObjectMapper().createObjectNode());
    JsonNode jsonNode = JsonUtils.serializeRecord(messageJson);
    MessageJson deserializedMessageJson = new ObjectMapper().convertValue(jsonNode,
        MessageJson.class);
    assertEquals("example", deserializedMessageJson.methodName());
    assertEquals(messageJson.arguments(), deserializedMessageJson.arguments());
  }
}