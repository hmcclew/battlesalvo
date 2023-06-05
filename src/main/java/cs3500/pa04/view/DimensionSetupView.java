package cs3500.pa04.view;

/**
 * View class to represent setting up board dimensions in a game of BattleSalvo
 */
public class DimensionSetupView implements View {

  /**
   * Prompts the user to enter a height and width used for board size
   */
  @Override
  public void display() {
    System.out.println("Hello! Welcome to the BattleSalvo Game");
    System.out.println("Please enter a valid height and width below: ");
  }

  /**
   * Prompts the user to re-enter a board size if the initial input was invalid
   */
  public void handleInvalidInput() {
    System.out.println("Uh Oh! You've entered invalid dimensions.");
    System.out.println("Please remember that height and width must be "
        +
        "in the range (6, 15), inclusive. Try again!");
  }
}
