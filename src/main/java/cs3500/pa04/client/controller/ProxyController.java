package cs3500.pa04.client.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.client.model.coordinate.Coord;
import cs3500.pa04.client.model.player.Player;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.MessageJson;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ProxyController implements Controller {

  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final Player player;
  private final ObjectMapper mapper = new ObjectMapper();

  public ProxyController(Socket server, InputStream in, PrintStream out, Player player) {
    this.server = server;
    this.in = in;
    this.out = out;
    this.player = player;
  }

  /**
   * Runs the controller
   */
  @Override
  public void run() {

  }

  private void delegateMessage(MessageJson message) {

  }

  private void handleJoin(JsonNode arguments) {

  }

  private void handleSetup(JsonNode arguments) {

  }

  private void handleTakeShots(JsonNode arguments) {

  }

  private void handleReportDamage(JsonNode arguments) {

  }

  private void handleSuccessfulHits(JsonNode arguments) {

  }

  private void handleEndGame(JsonNode arguments) {

  }

  private List<Coord> getPlayerShots(FleetJson args) {
    return new ArrayList<>();
  }

}
