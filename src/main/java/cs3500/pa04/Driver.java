package cs3500.pa04;

import cs3500.pa04.client.controller.BattleSalvoController;
import cs3500.pa04.client.controller.ProxyController;
import cs3500.pa04.client.model.player.AutomatedPlayer;
import cs3500.pa04.client.model.player.ManualPlayer;
import java.io.IOException;
import java.net.Socket;

/**
 * This is the main driver of this project.
 */
public class Driver {

  public static void runClient(String host, int port) {
    try {
      Socket server = new Socket(host, port);
      BattleSalvoController controller = new BattleSalvoController();
      ProxyController proxyController = new ProxyController(server,
          new AutomatedPlayer(), controller);
      proxyController.run();
    } catch (IOException e) {
      System.err.println("An Unexpected error occurred");
    }
  }

  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {

    if (args.length != 0 && args.length != 2) {
      throw new IndexOutOfBoundsException("Please provide either 0 or 2 command-line args");
    }

    if (args.length == 0) {
      try {
        BattleSalvoController controller = new BattleSalvoController();
        controller.run();
      } catch (Exception e) {
        System.err.println("An Unexpected Error Occurred. Please Try Again.");
      }
    }

    if (args.length == 2) {
      try {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        Driver.runClient(host, port);
      } catch (Exception e) {
        System.err.println("Please ensure the host and port are correct");
      }
    }
  }
}