package cs3500.pa04.model.ship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SubmarineTest {

  Submarine submarine = new Submarine();

  @Test
  public void testGetSize() {
    assertEquals(3, submarine.getSize());
  }

}