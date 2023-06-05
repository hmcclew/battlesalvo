package cs3500.pa04.client.view;

/**
 * View Class to display game results
 */
public class PostGameView implements View {

  /**
   * Displays appropriate content at the end of each game
   */
  @Override
  public void display() {
    System.out.println("This game of BattleSalvo has ended!");
  }

  /**
   * Displays a message appropriate if the manual player wins
   *
   * @param reason the reason for the game ending
   */
  public void handleWin(String reason) {
    System.out.println("Congratulations! You have won!");
    System.out.println(reason);
  }

  /**
   * Displays a message appropriate if the manual player loses
   *
   * @param reason the reason for the game ending
   */
  public void handleLoss(String reason) {
    System.out.println("You lost! Better luck next time!");
    System.out.println(reason);
  }

  /**
   * Displays a message appropriate if the game ends in a tie
   *
   * @param reason the reason for the game ending
   */
  public void handleTie(String reason) {
    System.out.println("The game ended in a tie!");
    System.out.println(reason);
  }
}
