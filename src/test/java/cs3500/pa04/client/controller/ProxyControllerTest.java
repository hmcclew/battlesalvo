package cs3500.pa04.client.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import cs3500.pa04.client.model.BattleSalvoModel;
import cs3500.pa04.client.model.player.AutomatedPlayer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

/**
 * Tests the Proxy Controller
 */
class ProxyControllerTest {
  Socket mockSocket = mock(Socket.class);
  AutomatedPlayer mockPlayer = mock(AutomatedPlayer.class);
  ByteArrayOutputStream serverOutputStream = new ByteArrayOutputStream();
  String serverMessage2 = "{\"method-name\":\"end-game\",\"arguments\":{\"result\":\"WIN\","
      +
      "\"reason\":\"You win\"}}";

  @Test
  public void testHandleEndGame() {
    testProxyController(serverMessage2);

    verify(mockPlayer, times(1)).endGame(any(), anyString());

  }

  @Test
  public void testHandleTakeShots() {
    String serverMessage = "{\"method-name\":\"take-shots\",\"arguments\":{}}";

    testProxyController(serverMessage + serverMessage2);
    verify(mockPlayer, times(1)).takeShots();
  }

  @Test
  public void testHandleJoin() {
    String serverMessage = "{\"method-name\":\"join\",\"arguments\":{}}";

    testProxyController(serverMessage);
    verify(mockPlayer, never()).setup(anyInt(), anyInt(), anyMap());
    verify(mockPlayer, never()).takeShots();
    verify(mockPlayer, never()).reportDamage(anyList());
    verify(mockPlayer, never()).successfulHits(anyList());
    verify(mockPlayer, never()).endGame(any(), anyString());
    verifyNoMoreInteractions(mockPlayer);
  }

  @Test
  public void testHandleSetup() {
    String serverMessage = "{\"method-name\":\"setup\",\"arguments\":{\"width\":7,\"height\":7,\""
        +
        "fleet-spec\":{\"BATTLESHIP\":1, \"DESTROYER\":1, \"CARRIER\":1, \"SUBMARINE\":1}}}";

    testProxyController(serverMessage + serverMessage2);
    verify(mockPlayer, times(1)).setup(anyInt(), anyInt(), anyMap());
  }

  @Test
  public void testHandleReportDamage() {
    String serverMessage = "{\"method-name\":\"report-damage\",\"arguments\":"
        +
        "{\"coordinates\":[{\"x\":1,\"y\":2}]}}";

    testProxyController(serverMessage + serverMessage2);
    verify(mockPlayer, times(1)).reportDamage(anyList());
  }

  @Test
  public void testHandleSuccessfulHits() {
    String serverMessage = "{\"method-name\":\"successful-hits\",\"arguments\":{\"coordinates\":"
        +
        "[{\"x\":1,\"y\":2}]}}";

    testProxyController(serverMessage + serverMessage2);
    verify(mockPlayer, times(1)).successfulHits(anyList());
  }

  private void testProxyController(String serverMessage) {
    try {
      ByteArrayInputStream serverInputStream = new ByteArrayInputStream(serverMessage.getBytes());

      when(mockSocket.getInputStream()).thenReturn(serverInputStream);
      when(mockSocket.getOutputStream()).thenReturn(serverOutputStream);


      ProxyController controller = new ProxyController(mockSocket, mockPlayer);
      controller.model = new BattleSalvoModel(7, 7, new HashMap<>());
      controller.run();

      verify(mockSocket, times(1)).getInputStream();
      verify(mockSocket, times(1)).getOutputStream();
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }

  }
}