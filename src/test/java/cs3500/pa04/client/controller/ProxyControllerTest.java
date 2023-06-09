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

import cs3500.pa04.client.ProxyController;
import cs3500.pa04.client.model.player.Player;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import org.junit.jupiter.api.Test;

class ProxyControllerTest {
  Socket mockSocket = mock(Socket.class);
  Player mockPlayer = mock(Player.class);

  @Test
  public void testRun() throws IOException {
    String serverMessage = "{\"method-name\":\"end-game\",\"arguments\":{\"result\":\"WIN\","
        +
        "\"reason\":\"You win\"}}";
    ByteArrayInputStream serverInputStream = new ByteArrayInputStream(serverMessage.getBytes());
    ByteArrayOutputStream serverOutputStream = new ByteArrayOutputStream();

    Socket mockSocket = mock(Socket.class);
    Player mockPlayer = mock(Player.class);

    when(mockSocket.getInputStream()).thenReturn(serverInputStream);
    when(mockSocket.getOutputStream()).thenReturn(serverOutputStream);

    ProxyController controller = new ProxyController(mockSocket, mockPlayer);
    controller.run();

    verify(mockSocket, times(1)).getInputStream();
    verify(mockSocket, times(1)).getOutputStream();
    verify(mockPlayer, times(1)).endGame(any(), anyString());
    mockSocket.close();
  }

  @Test
  public void testHandleJoin() throws IOException {
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
  public void testHandleSetup() throws IOException {
    String serverMessage = "{\"method-name\":\"setup\",\"arguments\":{\"width\":5,\"height\":5,\"" +
        "fleet-spec\":{\"BATTLESHIP\":1}}}";
    testProxyController(serverMessage);
    verify(mockPlayer, times(1)).setup(anyInt(), anyInt(), anyMap());
  }

  @Test
  public void testHandleTakeShots() throws IOException {
    String serverMessage = "{\"method-name\":\"take-shots\",\"arguments\":{}}";
    testProxyController(serverMessage);
    verify(mockPlayer, times(1)).takeShots();
  }

  @Test
  public void testHandleReportDamage() throws IOException {
    String serverMessage = "{\"method-name\":\"report-damage\",\"arguments\":" +
        "{\"coordinates\":[{\"x\":1,\"y\":2}]}}";
    testProxyController(serverMessage);
    verify(mockPlayer, times(1)).reportDamage(anyList());
  }

  @Test
  public void testHandleSuccessfulHits() throws IOException {
    String serverMessage = "{\"method-name\":\"successful-hits\",\"arguments\":{\"coordinates\":" +
        "[{\"x\":1,\"y\":2}]}}";
    testProxyController(serverMessage);
    verify(mockPlayer, times(1)).successfulHits(anyList());
  }

  private void testProxyController(String serverMessage) throws IOException {
    ByteArrayInputStream serverInputStream = new ByteArrayInputStream(serverMessage.getBytes());
    ByteArrayOutputStream serverOutputStream = new ByteArrayOutputStream();

    Socket mockSocket = mock(Socket.class);
    Player mockPlayer = mock(Player.class);

    when(mockSocket.getInputStream()).thenReturn(serverInputStream);
    when(mockSocket.getOutputStream()).thenReturn(serverOutputStream);

    ProxyController controller = new ProxyController(mockSocket, mockPlayer);
    controller.run();

    verify(mockSocket, times(1)).getInputStream();
    verify(mockSocket, times(1)).getOutputStream();

    serverInputStream.close();
    serverOutputStream.close();
    mockSocket.close();
  }
}