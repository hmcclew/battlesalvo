package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

      driver.main(null);

      System.setErr(originalErr);

      String expectedErrorMessage = "An Unexpected Error Occurred. Please Try Again.";
      assertTrue(errStream.toString().contains(expectedErrorMessage));
    }
  }

}