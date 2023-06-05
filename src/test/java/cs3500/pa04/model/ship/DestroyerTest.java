package cs3500.pa04.model.ship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DestroyerTest {

  Destroyer destroyer = new Destroyer();

  @Test
  public void testGetSize() {
    assertEquals(4, destroyer.getSize());
  }

}