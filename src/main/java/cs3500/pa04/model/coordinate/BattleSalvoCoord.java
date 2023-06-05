package cs3500.pa04.model.coordinate;

/**
 * Represents a coordinate on a BattleSalvo Board
 */
public class BattleSalvoCoord implements Coord {
  private int valX;
  private int valY;
  private boolean hasHitShip;

  /**
   * Constructor for a BattleSalvoCoord
   *
   * @param x the x coordinate
   * @param y the y coordinate
   */
  public BattleSalvoCoord(int x, int y) {
    this.valX = x;
    this.valY = y;
    this.hasHitShip = false;
  }

  /**
   * Getter method for the x coordinate
   *
   * @return the x coordinate
   */
  public int getX() {
    return this.valX;
  }

  /**
   * Getter method for the y coordinate
   *
   * @return the y coordinate
   */
  public int getY() {
    return this.valY;
  }

  /**
   * Getter method for hasHitShip
   *
   * @return the value for hasHitShip
   */
  public boolean getHasHitShip() {
    return this.hasHitShip;
  }

  /**
   * Updates the hasHitShip field to be true
   */
  public void hitCoordinate() {
    this.hasHitShip = true;
  }

  /**
   * Overrides the equals method to compare equality between
   * two coordinates based on their x and y value alone
   *
   * @param o the object to be compared
   * @return whether this coordinate and the parameter are equal
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof BattleSalvoCoord) {
      BattleSalvoCoord b = (BattleSalvoCoord) o;
      return (b.getX() == valX && b.getY() == valY);
    } else {
      return false;
    }
  }
}
