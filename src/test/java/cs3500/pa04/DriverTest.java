package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class DriverTest {

  Driver driver = new Driver();

  @Test
  public void testMainException() {
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
  public void testRunClientException() {
    try {
      throw new RuntimeException("Simulated unexpected error");
    } catch (Exception e) {
      ByteArrayOutputStream errStream = new ByteArrayOutputStream();
      PrintStream originalErr = System.err;
      System.setErr(new PrintStream(errStream));

      String arr[] = {"0.0", "2840"};
      driver.main(arr);

      System.setErr(originalErr);

      String expectedErrorMessage = "An Unexpected error occurred";
      assertTrue(errStream.toString().contains(expectedErrorMessage));
    }
  }

}