package cs3500.pa04.client.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParser;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.client.model.player.Player;

import java.io.InputStream;
import java.io.OutputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import static org.mockito.Mockito.*;

class ProxyControllerTest {

  @Test
  public void testRun() throws IOException {
    String serverMessage = "{\"method-name\":\"end-game\",\"arguments\":{\"result\":\"WIN\"," +
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
    serverInputStream.close();
  }
}