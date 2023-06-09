package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class DriverTest {

  Driver driver = new Driver();

  @Test
  public void testMainExceptionNoArgs() {
    try {
      throw new RuntimeException("Simulated unexpected error");
    } catch (Exception e) {
      ByteArrayOutputStream errStream = new ByteArrayOutputStream();
      PrintStream originalErr = System.err;
      System.setErr(new PrintStream(errStream));

      driver.main(new String[0]);

      System.setErr(originalErr);

      String expectedErrorMessage = "An Unexpected Error Occurred. Please Try Again.";
      assertTrue(errStream.toString().contains(expectedErrorMessage));
    }
  }

  @Test
  public void testMainExceptionTwoArgs() {
    try {
      throw new RuntimeException("Simulated unexpected error");
    } catch (Exception e) {
      ByteArrayOutputStream errStream = new ByteArrayOutputStream();
      PrintStream originalErr = System.err;
      System.setErr(new PrintStream(errStream));

      String[] args = new String[] {"0.0", "blue"};
      driver.main(args);

      System.setErr(originalErr);

      String expectedErrorMessage = "Please ensure the host and port are correct";
      assertTrue(errStream.toString().contains(expectedErrorMessage));
    }
  }

  @Test
  public void testRunClientException() {
    try {
      throw new RuntimeException("Simulated unexpected error");
    } catch (Exception e) {
      ByteArrayOutputStream errStream = new ByteArrayOutputStream();
      PrintStream originalErr = System.err;
      System.setErr(new PrintStream(errStream));

      String[] args = new String[] {"0.0", "2840"};
      driver.main(args);

      System.setErr(originalErr);

      String expectedErrorMessage = "An Unexpected error occurred";
      assertTrue(errStream.toString().contains(expectedErrorMessage));
    }
  }

  @Test
  public void testInvalidCommandLineArgs() {
    String[] args = {"arg1", "arg2", "arg3"};
    assertThrows(IndexOutOfBoundsException.class, () -> Driver.main(args));
  }

}