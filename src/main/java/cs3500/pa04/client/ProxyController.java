package cs3500.pa04.client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.client.model.BattleSalvoModel;
import cs3500.pa04.client.model.GameResult;
import cs3500.pa04.client.model.GameType;
import cs3500.pa04.client.model.coordinate.BattleSalvoCoord;
import cs3500.pa04.client.model.coordinate.Coord;
import cs3500.pa04.client.model.player.AbstractPlayer;
import cs3500.pa04.client.model.player.AutomatedPlayer;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Proxy Controller for game of BattleSalvo that communicates
 * with the server
 */
public class ProxyController implements Controller {
  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final Player player;
  private final ObjectMapper mapper;
  private BattleSalvoModel model;

  /**
   * Constructor for a proxycontroller
   *
   * @param server the server
   * @param player the player playing
   * @throws IOException if the
   */
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
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }

    } catch (IOException e) {
      System.err.println("Disconnected from server or parsing exception");
      e.printStackTrace();
    }
  }

  /**
   * Delegates to the correct method handler based on the message from the server
   *
   * @param message the formatted json sent from the server
   */
  private void delegateMessage(MessageJson message) {
    String name = message.methodName();
    JsonNode arguments = message.arguments();

    if ("join".equals(name)) {
      handleJoin();
    } else if ("setup".equals(name)) {
      handleSetup(arguments);
    } else if ("take-shots".equals(name)) {
      handleTakeShots();
    } else if ("report-damage".equals(name)) {
      handleReportDamage(arguments);
    } else if ("successful-hits".equals(name)) {
      handleSuccessfulHits(arguments);
    } else if ("end-game".equals(name)) {
      handleEndGame(arguments);
    } else {
      throw new IllegalStateException("Invalid message name");
    }
  }


  /**
   * Handles the join method request from the server
   */
  private void handleJoin() {
    JoinJson joinJson = new JoinJson("swiftiesunite", GameType.SINGLE.toString());
    JsonNode jsonResponse = JsonUtils.serializeRecord(joinJson);
    MessageJson messageJson = new MessageJson("join", jsonResponse);

    JsonNode messageResponse = JsonUtils.serializeRecord(messageJson);
    this.out.println(messageResponse);
  }

  /**
   * Parses the specifications sent from the server
   *
   * @param iterator an iterator used to create values for the specifications map
   * @param specifications the specifications sent to players for setup
   */
  private void parseSpecifications(Iterator<Map.Entry<String, JsonNode>> iterator,
                                   Map<ShipType, Integer> specifications) {
    while (iterator.hasNext()) {
      Map.Entry<String, JsonNode> entry = iterator.next();
      ShipType shipType = ShipType.valueOf(entry.getKey().toUpperCase());
      int shipCount = entry.getValue().asInt();
      specifications.put(shipType, shipCount);
    }
  }

  /**
   * Formats player setups as a ship json array
   *
   * @param playerSetup the list of ships in the player's setup
   * @param shipJsonArray the array of ship jsons to be filled
   */
  private void formatAsShipJson(List<Ship> playerSetup, ShipJson[] shipJsonArray) {
    for (int i = 0; i < playerSetup.size(); i++) {
      Ship ship = playerSetup.get(i);
      int length = ship.getSize();
      String direction = ship.getDirection();
      Coord startCoord = ship.getPlacement().get(length - 1);
      CoordJson coordJson = new CoordJson(startCoord.getX(), startCoord.getY());
      ShipJson shipJson = new ShipJson(coordJson, length, direction);
      shipJsonArray[i] = shipJson;
    }
  }

  /**
   * Handles the setup method request from the server
   */
  private void handleSetup(JsonNode arguments) {
    int width = arguments.path("width").asInt();
    int height = arguments.path("height").asInt();
    Iterator<Map.Entry<String, JsonNode>> iterator =
        arguments.path("fleet-spec").fields();
    Map<ShipType, Integer> specifications = new HashMap<>();

    parseSpecifications(iterator, specifications);
    model = new BattleSalvoModel(width, height, specifications);
    List<Ship> playerSetup = player.setup(height, width, specifications);
    ShipJson[] shipJsonArray = new ShipJson[playerSetup.size()];

    formatAsShipJson(playerSetup, shipJsonArray);

    SetupJson setupJson = new SetupJson(shipJsonArray);

    JsonNode jsonResponse = JsonUtils.serializeRecord(setupJson);
    MessageJson messageJson = new MessageJson("setup", jsonResponse);

    JsonNode messageResponse = JsonUtils.serializeRecord(messageJson);
    this.out.println(messageResponse);
  }

  /**
   * Formates a list of coordinates into an array of coordinate jsons
   *
   * @param shots the list of coordinates to be converted to jsons
   * @param coordJsonArray the array to be updated and filled
   */
  private void formatShotsToCoordJsonArray(List<Coord> shots, CoordJson[] coordJsonArray) {
    for (int i = 0; i < shots.size(); i++) {
      Coord c = shots.get(i);
      CoordJson coordJson = new CoordJson(c.getX(), c.getY());
      coordJsonArray[i] = coordJson;
    }
  }

  /**
   * Handles the take-shot method request from the server
   */
  private void handleTakeShots() {
    model.updateAllowedShots((AbstractPlayer) player);
    List<Coord> shotsTaken = model.getAutomatedFiredShots((AutomatedPlayer) player);

    CoordJson[] coordJson = new CoordJson[shotsTaken.size()];

    formatShotsToCoordJsonArray(shotsTaken, coordJson);

    TakeShotsJson takeShotsJson = new TakeShotsJson(coordJson);
    JsonNode jsonResponse = JsonUtils.serializeRecord(takeShotsJson);
    MessageJson messageJson = new MessageJson("take-shots", jsonResponse);

    JsonNode messageResponse = JsonUtils.serializeRecord(messageJson);
    this.out.println(messageResponse);
  }

  /**
   * Parses the coordinate jsons sent from the server into a list of shots
   *
   * @param coordinates the json sent from the server
   * @param shots the list of shots to be filled
   */
  private void parseCoordinateArguments(JsonNode coordinates, List<Coord> shots) {
    for (JsonNode coordinateNode : coordinates) {
      int x = coordinateNode.get("x").asInt();
      int y = coordinateNode.get("y").asInt();
      Coord c = new BattleSalvoCoord(x, y);
      shots.add(c);
    }
  }

  /**
   * Handles the report-damage method request from the server
   */
  private void handleReportDamage(JsonNode arguments) {
    List<Coord> opponentShots = new ArrayList<>();
    JsonNode coordinates = arguments.path("coordinates");

    parseCoordinateArguments(coordinates, opponentShots);

    List<Coord> damage = player.reportDamage(opponentShots);

    CoordJson[] coordJsonArray = new CoordJson[damage.size()];

    formatShotsToCoordJsonArray(damage, coordJsonArray);
    ReportDamageJson reportDamageJson = new ReportDamageJson(coordJsonArray);

    JsonNode jsonResponse = JsonUtils.serializeRecord(reportDamageJson);
    MessageJson messageJson = new MessageJson("report-damage", jsonResponse);

    JsonNode messageResponse = JsonUtils.serializeRecord(messageJson);
    this.out.println(messageResponse);
  }

  /**
   * Handles the successful-hits method request from the server
   */
  private void handleSuccessfulHits(JsonNode arguments) {
    List<Coord> successfulShots = new ArrayList<>();
    JsonNode coordinates = arguments.path("coordinates");

    parseCoordinateArguments(coordinates, successfulShots);

    player.successfulHits(successfulShots);
    MessageJson messageJson = new MessageJson("successful-hits",
        mapper.createObjectNode());
    this.out.println(JsonUtils.serializeRecord(messageJson));
  }

  /**
   * Handles the end-game method request from the server
   */
  private void handleEndGame(JsonNode arguments) {
    String result = arguments.get("result").asText();
    String reason = arguments.get("reason").asText();
    GameResult gameResult = GameResult.valueOf(result.toUpperCase());

    player.endGame(gameResult, reason);
    try {
      server.close();
    } catch (IOException e) {
      System.err.println("Unable to close server");
    }
  }

}
