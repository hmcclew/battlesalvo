package cs3500.pa04.model.ship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CarrierTest {

  Carrier carrier = new Carrier();

  @Test
  public void testGetSize() {
    assertEquals(6, carrier.getSize());
  }

}