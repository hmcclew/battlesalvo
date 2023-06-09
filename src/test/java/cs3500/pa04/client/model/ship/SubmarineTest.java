package cs3500.pa04.client.model.ship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.client.model.ship.Submarine;
import org.junit.jupiter.api.Test;

class SubmarineTest {

  Submarine submarine = new Submarine();

  @Test
  public void testGetSize() {
    assertEquals(3, submarine.getSize());
  }

}