package cs3500.pa04.client.model.ship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.client.model.ship.Destroyer;
import org.junit.jupiter.api.Test;

class DestroyerTest {

  Destroyer destroyer = new Destroyer();

  @Test
  public void testGetSize() {
    assertEquals(4, destroyer.getSize());
  }

}