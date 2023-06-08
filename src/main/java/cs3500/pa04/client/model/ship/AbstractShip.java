package cs3500.pa04.client.model.ship;

import cs3500.pa04.client.model.coordinate.BattleSalvoCoord;
import cs3500.pa04.client.model.coordinate.Coord;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Abstract Class to represent a ship
 */
public abstract class AbstractShip implements Ship {
  protected Random random = new Random();
  List<Coord> placement = new ArrayList<>();
  private String direction;

  public String getDirection() {
    return this.direction;
  }

  /**
   * Clears the placement of this ship
   */
  public void clearPlacement() {
    placement.clear();
  }

  /**
   * Generates a random coordinate to be used as a starting point for this
   * ship's placement
   *
   * @param height the height of the board
   * @param width  the width of the baord
   * @return the generated Coordinate
   */
  private Coord getRandomCoordinate(int height, int width) {
    // + 1
    int x = random.nextInt(width);
    int y = random.nextInt(height);
    return new BattleSalvoCoord(x, y);
  }

  /**
   * Generates a random boolean to represent if this ship will be placed
   * vertically or horizontally on the board
   *
   * @return a random boolean, true if the ship will be vertical, false if horizontal
   */
  private boolean getRandomOrientation() {
    return random.nextBoolean();
  }

  /**
   * Initiates the coordinate placements for a ship
   *
   * @param height              the height of the board
   * @param width               the width of the board
   * @param occupiedCoordinates a list of coordinates already occupied by other ships
   */
  public void generatePlacement(int height, int width, List<Coord> occupiedCoordinates) {
    Coord startCoord = getRandomCoordinate(height, width);
    int startCordX = startCoord.getX();
    int startCordY = startCoord.getY();
    placement.add(startCoord);
    Boolean isVertical = getRandomOrientation();
    if (isVertical) {
      for (int i = 1; i < getSize(); i++) {
        Coord nextCoord = new BattleSalvoCoord(startCordX, startCordY - i);
        placement.add(nextCoord);
        direction = "VERTICAL";
      }
    } else {
      for (int i = 1; i < getSize(); i++) {
        Coord nextCoord = new BattleSalvoCoord(startCordX - i, startCordY);
        placement.add(nextCoord);
        direction = "HORIZONTAL";
      }
    }
  }

  /**
   * Get the placement of this ship
   *
   * @return the list of coordinates where this ship is set
   */
  public List<Coord> getPlacement() {
    return this.placement;
  }

  /**
   * Gets the size of a ship
   *
   * @return the size of a ship
   */
  public abstract int getSize();
}
