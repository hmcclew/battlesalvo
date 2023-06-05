package cs3500.pa04.model.player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.client.model.coordinate.Coord;
import cs3500.pa04.client.model.player.AutomatedPlayer;
import java.util.List;
import org.junit.jupiter.api.Test;

class AutomatedPlayerTest {
  AutomatedPlayer player = new AutomatedPlayer();

  @Test
  public void testName() {
    assertEquals("AI Player", player.name());
  }

  @Test
  public void testTakeShots() {
    player.setAllowedShots(2);
    List<Coord> shots = player.takeShots();

    assertEquals(2, shots.size());

    player.setAllowedShots(5);
    shots = player.takeShots();

    assertEquals(5, shots.size());
  }

}