package cs3500.pa04.client.view;

/**
 * View Class to represent setting up a fleet during a game of BattleSalvo
 */
public class ShipSetupView implements View {

  int maxFleetSize;

  /**
   * Constructor for a ShipSetupView
   *
   * @param maxFleetSize the maximum size of the fleet to be setup
   */
  public ShipSetupView(int maxFleetSize) {
    this.maxFleetSize = maxFleetSize;
  }

  /**
   * Prompts the user to enter a fleet of a specific size
   */
  @Override
  public void display() {
    System.out.println("Please enter your fleet in the order [Carrier, Battleship, "
        +
        "Destroyer, Submarine].");
    System.out.println("Remember, your fleet may not exceed size " + maxFleetSize + ".");
  }

  /**
   * Prompts the user if an invalid fleet is entered
   */
  public void handleInvalidInput() {
    System.out.println("Uh Oh! You've entered invalid fleet sizes.");
    System.out.println("Please enter your fleet in order [Carrier, Battleship, "
        +
        "Destroyer, Submarine].");
    System.out.println("Remember, your fleet may not exceed size " + maxFleetSize + ".");
  }
}
