package cs3500.pa04.model.coordinate;

/**
 * Interface to represent a coordinate on a board
 */
public interface Coord {
  /**
   * Getter method for the x coordinate
   *
   * @return the x coordinate
   */
  int getX();

  /**
   * Getter method for the y coordinate
   *
   * @return the y coordinate
   */
  int getY();

  /**
   * Getter method for hasHitShip
   *
   * @return the value for hasHitShip
   */
  boolean getHasHitShip();

  /**
   * Updates the hasHitShip field to be true
   */
  void hitCoordinate();
}
