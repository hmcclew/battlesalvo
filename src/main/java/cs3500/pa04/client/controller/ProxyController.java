package cs3500.pa04.client.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.client.model.GameResult;
import cs3500.pa04.client.model.GameType;
import cs3500.pa04.client.model.coordinate.BattleSalvoCoord;
import cs3500.pa04.client.model.coordinate.Coord;
import cs3500.pa04.client.model.player.Player;
import cs3500.pa04.client.model.ship.Ship;
import cs3500.pa04.client.model.ship.ShipType;
import cs3500.pa04.json.CoordJson;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.ReportDamageJson;
import cs3500.pa04.json.SetupJson;
import cs3500.pa04.json.ShipJson;
import cs3500.pa04.json.TakeShotsJson;
import cs3500.pa04.json.VolleyJson;
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

  public ProxyController(Socket server, Player player)
      throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.player = player;
    this.mapper = new ObjectMapper();
  }

  /**
   * Runs the controller
   */
  @Override
  public void run() {
    try {
      JsonNode message = mapper.readTree(in);
      String name = message.path("method-name").toString();
      JsonNode arguments = message.path("arguments");

      System.out.println("hi");
      System.out.println("hi");

      while (!this.server.isClosed()) {
        System.out.println("hi");
        MessageJson messageJson = new MessageJson(name, arguments);
        delegateMessage(messageJson);
      }

    } catch (IOException e) {
      System.err.println("Disconnected from server or parsing exception");
    }
  }

  private void delegateMessage(MessageJson message) {
    String name = message.messageName();
    JsonNode arguments = message.arguments();
    if ("join".equals(name)) {
      handleJoin();
    }
    else if ("setup".equals(name)) {
      handleSetup(arguments);
    }
    else if ("take-shots".equals(name)) {
      handleTakeShots();
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

  private void handleJoin() {
    JoinJson joinJson = new JoinJson("swiftiesunite", GameType.SINGLE);

    JsonNode jsonResponse = JsonUtils.serializeRecord(joinJson);
    this.out.println(jsonResponse);
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

    JsonNode jsonResponse = JsonUtils.serializeRecord(setupJson);
    this.out.println(jsonResponse);
  }

  private void formatShotsToCoordJsonArray(List<Coord> shots, CoordJson[] coordJsonArray) {
    for (int i = 0; i < shots.size(); i++) {
      Coord c = shots.get(i);
      CoordJson cJson = new CoordJson(c.getX(), c.getY());
      coordJsonArray[i] = cJson;
    }
  }

  private void handleTakeShots() {
    List<Coord> shotsTaken = player.takeShots();

    CoordJson[] coordJson = new CoordJson[shotsTaken.size()];

    formatShotsToCoordJsonArray(shotsTaken, coordJson);

    VolleyJson volleyJson = new VolleyJson(coordJson);
    TakeShotsJson takeShotsJson = new TakeShotsJson(volleyJson);

    JsonNode jsonResponse = JsonUtils.serializeRecord(takeShotsJson);
    this.out.println(jsonResponse);
  }

  private void parseCoordinateArguments(JsonNode coordinates, List<Coord> shots) {
    for (JsonNode coordinateNode : coordinates) {
      int x = coordinateNode.get("x").asInt();
      int y = coordinateNode.get("y").asInt();
      Coord c = new BattleSalvoCoord(x, y);
      shots.add(c);
    }
  }
  private void handleReportDamage(JsonNode arguments) {
    List<Coord> opponentShots = new ArrayList<>();
    JsonNode coordinates = arguments.path("coordinates");

    parseCoordinateArguments(coordinates, opponentShots);

    List<Coord> damage = player.reportDamage(opponentShots);
    CoordJson[] coordJsonArray = new CoordJson[damage.size()];

    formatShotsToCoordJsonArray(damage, coordJsonArray);
    VolleyJson volleyJson = new VolleyJson(coordJsonArray);
    ReportDamageJson reportDamageJson = new ReportDamageJson(volleyJson);

    JsonNode jsonResponse = JsonUtils.serializeRecord(reportDamageJson);
    this.out.println(jsonResponse);
  }

  private void handleSuccessfulHits(JsonNode arguments) {
    List<Coord> successfulShots = new ArrayList<>();
    JsonNode coordinates = arguments.path("coordinates");

    parseCoordinateArguments(coordinates, successfulShots);

    player.successfulHits(successfulShots);
  }

  private void handleEndGame(JsonNode arguments) {
    String result = arguments.path("result").toString();
    GameResult gameResult = GameResult.valueOf(result.toUpperCase());

    String reason = arguments.path("reason").toString();

    player.endGame(gameResult, reason);
  }

}
