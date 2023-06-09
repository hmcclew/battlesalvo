package cs3500.pa04.client.model.ship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.client.model.ship.Carrier;
import org.junit.jupiter.api.Test;

class CarrierTest {

  Carrier carrier = new Carrier();

  @Test
  public void testGetSize() {
    assertEquals(6, carrier.getSize());
  }

}