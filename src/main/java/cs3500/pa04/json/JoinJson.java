package cs3500.pa04.json;

import static cs3500.pa04.client.model.GameType.SINGLE;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.client.model.GameType;

public record JoinJson(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") GameType gameType) {

  public boolean isSingleGameType() {
    return true;
  }
}
