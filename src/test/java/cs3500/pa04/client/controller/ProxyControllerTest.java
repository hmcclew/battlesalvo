package cs3500.pa04.client.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cs3500.pa04.client.model.player.Player;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import org.junit.jupiter.api.Test;

class ProxyControllerTest {

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
}