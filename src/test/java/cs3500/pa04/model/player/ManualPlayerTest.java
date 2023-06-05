package cs3500.pa04.model.player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.controller.BattleSalvoController;
import org.junit.jupiter.api.Test;

class ManualPlayerTest {

  BattleSalvoController controller = new BattleSalvoController();

  ManualPlayer player = new ManualPlayer(controller);

  @Test
  public void testName() {
    assertEquals("Manual Player", player.name());
  }

}