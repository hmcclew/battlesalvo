package cs3500.pa04.client.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.client.model.GameType;
import cs3500.pa04.client.model.coordinate.Coord;
import cs3500.pa04.client.model.player.Player;
import cs3500.pa04.client.model.ship.Ship;
import cs3500.pa04.client.model.ship.ShipType;
import cs3500.pa04.json.CoordJson;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupJson;
import cs3500.pa04.json.ShipJson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ProxyController implements Controller {
  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final Player player;
  private final ObjectMapper mapper;
  private BattleSalvoController battleSalvoController;

  public ProxyController(Socket server, Player player, BattleSalvoController controller)
      throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.player = player;
    this.mapper = new ObjectMapper();
    this.battleSalvoController = controller;
  }

  /**
   * Runs the controller
   */
  @Override
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
      System.err.println("Disconnected from server or parsing exception");
    }
  }

  private void delegateMessage(MessageJson message) {
    String name = message.messageName();
    JsonNode arguments = message.arguments();

    if ("join".equals(name)) {
      handleJoin(arguments);
    }
    else if ("setup".equals(name)) {
      handleSetup(arguments);
    }
    else if ("take-shots".equals(name)) {
      handleTakeShots(arguments);
    }
    else if ("report-damage".equals(name)) {
      handleReportDamage(arguments);
    }
    else if ("successful-hits".equals(name)) {
      handleSuccessfulHits(arguments);
    }
    else if ("end-game".equals(name)) {
      handleEndGame(arguments);
    }
    else {
      throw new IllegalStateException("Invalid message name");
    }
  }

  private void handleJoin(JsonNode arguments) {
    JoinJson clientResponse = new JoinJson("swiftiesunite", GameType.SINGLE);

    this.out.println(clientResponse);
  }

  private void parseSpecifications(Iterator<Map.Entry<String, JsonNode>> iterator,
                                   Map<ShipType, Integer> specifications) {
    while (iterator.hasNext()) {
      Map.Entry<String, JsonNode> entry = iterator.next();
      ShipType shipType = ShipType.valueOf(entry.getKey().toUpperCase());
      int shipCount = entry.getValue().asInt();
      specifications.put(shipType, shipCount);
    }
  }

  private void FormatAsShipJson(List<Ship> playerSetup, ShipJson[] shipJsonArray) {
    for (int i = 0; i < playerSetup.size(); i++) {
      Ship ship = playerSetup.get(i);
      int length = ship.getSize();
      String direction = ship.getDirection();
      Coord startCoord = ship.getPlacement().get(0);
      CoordJson coordJson = new CoordJson(startCoord.getX(), startCoord.getY());
      ShipJson shipJson = new ShipJson(coordJson, length, direction);
      shipJsonArray[i] = shipJson;
    }
  }
  private void handleSetup(JsonNode arguments) {
    int width = arguments.path("width").asInt();
    int height = arguments.path("height").asInt();
    Iterator<Map.Entry<String, JsonNode>> iterator =
        arguments.path("fleet-spec").fields();
    Map<ShipType, Integer> specifications = new HashMap<>();

    parseSpecifications(iterator, specifications);

    List<Ship> playerSetup = player.setup(width, height, specifications);
    ShipJson[] shipJsonArray = new ShipJson[playerSetup.size()];

    FormatAsShipJson(playerSetup, shipJsonArray);

    SetupJson setupJson = new SetupJson(shipJsonArray);

    this.out.println(setupJson);
  }

  private void handleTakeShots(JsonNode arguments) {

    List<Coord> shotsTaken = player.takeShots();
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
