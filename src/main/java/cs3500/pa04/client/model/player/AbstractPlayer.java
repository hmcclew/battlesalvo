package cs3500.pa04.client.model.player;

import cs3500.pa04.client.model.GameResult;
import cs3500.pa04.client.model.coordinate.Coord;
import cs3500.pa04.client.model.ship.AbstractShip;
import cs3500.pa04.client.model.ship.Battleship;
import cs3500.pa04.client.model.ship.Carrier;
import cs3500.pa04.client.model.ship.Destroyer;
import cs3500.pa04.client.model.ship.Ship;
import cs3500.pa04.client.model.ship.ShipType;
import cs3500.pa04.client.model.ship.Submarine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Abstract class to represent a player controller for a game of BattleSalvo
 */
public abstract class AbstractPlayer implements Player {
  private List<Coord> shipOccupiedCoordinates;
  private List<Coord> successfulHits;
  private List<Coord> alreadyShot;
  private List<Ship> playerFleet;
  private List<Ship> nonSunkShips;
  private int numRemainingShips;
  private int allowedShots;

  /**
   * Constructor for an abstract player
   */
  public AbstractPlayer() {
    shipOccupiedCoordinates = new ArrayList<>();
    alreadyShot = new ArrayList<>();
    playerFleet = new ArrayList<>();
    nonSunkShips = new ArrayList<>();
    successfulHits = new ArrayList<>();
    numRemainingShips = 0;
    allowedShots = 0;
  }

  /**
   * Getter method for the list of successful hits the player has had on the opponent's board
   *
   * @return the list of coordinates for successful hits the player has had on the opponent's board
   */
  public List<Coord> getSuccessfulHits() {
    return this.successfulHits;
  }

  /**
   * sets the number of allowed shots per round for this player
   *
   * @param allowedShots the number of updated allowed shots
   */
  public void setAllowedShots(int allowedShots) {
    this.allowedShots = allowedShots;
  }

  /**
   * Getter method for allowedShots
   *
   * @return the number of allowed shots
   */
  public int getAllowedShots() {
    return this.allowedShots;
  }

  /**
   * adds a shot to this instance's list of coordinates already fired at
   *
   * @param shot the shot to be added
   */
  public void addToAlreadyShot(Coord shot) {
    alreadyShot.add(shot);
  }

  /**
   * Getter method for shipOccupiedCoordinates
   *
   * @return all coordinates where this player has placed a ship
   */
  public List<Coord> getShipOccupiedCoordinates() {
    return shipOccupiedCoordinates;
  }

  /**
   * Getter method for the coordinates this player has already shot
   *
   * @return the list of coordinates on the opponent's board this player has shot at this game
   */
  public List<Coord> getAlreadyShot() {
    return alreadyShot;
  }

  /**
   * Getter method for the number of remaining ships
   *
   * @return the number of remaining ships
   */
  public int getNumRemainingShips() {
    return numRemainingShips;
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    for (Map.Entry<ShipType, Integer> entry : specifications.entrySet()) {
      ShipType shipType = entry.getKey();
      int numShipType = entry.getValue();

      for (int i = 0; i < numShipType; i++) {
        if (shipType.equals(ShipType.CARRIER)) {
          Carrier carrier = new Carrier();
          initiateShip(carrier, height, width);
          playerFleet.add(carrier);
          nonSunkShips.add(carrier);
        }
        if (shipType.equals(ShipType.BATTLESHIP)) {
          Battleship battleship = new Battleship();
          initiateShip(battleship, height, width);
          playerFleet.add(battleship);
          nonSunkShips.add(battleship);
        }
        if (shipType.equals(ShipType.DESTROYER)) {
          Destroyer destroyer = new Destroyer();
          initiateShip(destroyer, height, width);
          playerFleet.add(destroyer);
          nonSunkShips.add(destroyer);
        }
        if (shipType.equals(ShipType.SUBMARINE)) {
          Submarine submarine = new Submarine();
          initiateShip(submarine, height, width);
          playerFleet.add(submarine);
          nonSunkShips.add(submarine);
        }
      }
    }
    numRemainingShips = playerFleet.size();
    allowedShots = playerFleet.size();
    return playerFleet;
  }

  /**
   * Initiates a ship within the game by assigning it coordinates on this player's board
   *
   * @param ship the ship to be assigned coordinates
   * @param height the height of the board
   * @param width the width of the board
   */
  private void initiateShip(AbstractShip ship, int height, int width) {
    boolean validPlacement = false;
    while (!validPlacement) {
      ship.clearPlacement();
      ship.generatePlacement(height, width, shipOccupiedCoordinates);
      if (!occupiedPlacement(ship.getPlacement()) && !outOfBoundsPlacement(height, width,
          ship.getPlacement())) {
        validPlacement = true;
      }
    }
    for (Coord coord : ship.getPlacement()) {
      shipOccupiedCoordinates.add(coord);
    }
  }

  /**
   * Determines whether a ship's random placement falls within the board's size
   *
   * @param height the height of the board
   * @param width the width of the board
   * @param placement the coordinates where the ship has been placed
   * @return whether the placement falls out of bounds of the board
   */
  private boolean outOfBoundsPlacement(int height, int width, List<Coord> placement) {
    boolean outOfBounds = false;
    for (Coord coord : placement) {
      if (coord.getX() > width || coord.getY() > height
          || coord.getY() <= 0 || coord.getX() <= 0) {
        outOfBounds = true;
      }
    }
    return outOfBounds;
  }

  /**
   * Determines whether a ship's random placement is already occupied by another ship
   *
   * @param placement the coordinates where the ship has been placed
   * @return whether the placement falls on the placement of another ship
   */
  private boolean occupiedPlacement(List<Coord> placement) {
    boolean overlap = false;
    for (Coord coord : placement) {
      if (shipOccupiedCoordinates.contains(coord)) {
        overlap = true;
      }
    }
    return overlap;
  }

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that
   *         hit a ship on this board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> shotsThatHitShips = new ArrayList<>();
    for (Coord shot : opponentShotsOnBoard) {
      if (shipOccupiedCoordinates.contains(shot)) {
        shotsThatHitShips.add(shot);
      }
    }
    updateRemainingShips(shotsThatHitShips);
    return shotsThatHitShips;
  }

  /**
   * Updates this player's list of remaining ships based on the opponent's last attack
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   */
  public void updateRemainingShips(List<Coord> opponentShotsOnBoard) {
    List<Ship> sunkShips = new ArrayList<>();

    for (Ship ship : nonSunkShips) {
      for (Coord shot : opponentShotsOnBoard) {
        for (Coord shipCoord : ship.getPlacement()) {
          if (shipCoord.equals(shot)) {
            shipCoord.hitCoordinate();
          }
        }
      }
      boolean fullySunk = true;
      for (Coord c : ship.getPlacement()) {
        if (!c.getHasHitShip()) {
          fullySunk = false;
        }
      }
      if (fullySunk) {
        sunkShips.add(ship);
      }
    }
    nonSunkShips.removeAll(sunkShips);
    numRemainingShips = nonSunkShips.size();
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    successfulHits.clear();
    for (Coord shot : shotsThatHitOpponentShips) {
      successfulHits.add(shot);
    }
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public abstract String name();

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  public abstract List<Coord> takeShots();

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  public abstract void endGame(GameResult result, String reason);
}
