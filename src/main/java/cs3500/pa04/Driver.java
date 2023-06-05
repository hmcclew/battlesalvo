package cs3500.pa04;

import cs3500.pa04.controller.BattleSalvoController;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    try {
      BattleSalvoController controller = new BattleSalvoController();
      controller.run();
    } catch (Exception e) {
      System.err.println("An Unexpected Error Occurred. Please Try Again.");
    }
  }
}