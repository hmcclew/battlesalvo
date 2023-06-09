package cs3500.pa04.client.model.player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.client.BattleSalvoController;
import cs3500.pa04.client.model.coordinate.BattleSalvoCoord;
import cs3500.pa04.client.model.coordinate.Coord;
import cs3500.pa04.client.model.ship.ShipType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class AbstractPlayerTest {

  AbstractPlayer manualPlayer = new ManualPlayer(new BattleSalvoController());

  private Map<ShipType, Integer> specifications = new HashMap<>();
  Coord c1 = new BattleSalvoCoord(1, 1);
  Coord c2 = new BattleSalvoCoord(6, 6);

  List<Coord> coords = new ArrayList<>(Arrays.asList(c1, c2));

  @Test
  public void testGetSuccessfulHits() {
    manualPlayer.successfulHits(coords);
    List<Coord> hits = manualPlayer.getSuccessfulHits();
    assertEquals(coords, hits);
  }

  @Test
  public void testGetShipOccupiedCoordinates() {
    assertEquals(new ArrayList<>(), manualPlayer.getShipOccupiedCoordinates());
  }

  @Test
  public void testReportDamage() {
    specifications.put(ShipType.CARRIER, 6);
    manualPlayer.setup(6, 6, specifications);
    List<Coord> damage = manualPlayer.reportDamage(coords);
    assertEquals(c1, damage.get(0));
  }

  @Test
  public void testUpdateRemainingShips() {
    specifications.put(ShipType.CARRIER, 6);
    manualPlayer.setup(6, 6, specifications);
    manualPlayer.updateRemainingShips(coords);
  }

}