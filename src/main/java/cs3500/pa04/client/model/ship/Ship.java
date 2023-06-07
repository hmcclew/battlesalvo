package cs3500.pa04.client.model.ship;

import cs3500.pa04.client.model.coordinate.Coord;
import java.util.List;

/**
 * Interface to represent a type of ship in BattleSalvo
 */
public interface Ship {

  String getDirection();
  /**
   * Gets the size of a ship
   *
   * @return the size of a ship
   */
  int getSize();

  /**
   * Initiates the coordinate placements for a ship
   *
   * @param height              the height of the board
   * @param width               the width of the board
   * @param occupiedCoordinates a list of coordinates already occupied by another ship
   */
  void generatePlacement(int height, int width, List<Coord> occupiedCoordinates);

  /**
   * Get the placement of this ship
   *
   * @return the list of coordinates where this ship is set
   */
  List<Coord> getPlacement();

  /**
   * Clears the placement of this ship
   */
  void clearPlacement();
}
