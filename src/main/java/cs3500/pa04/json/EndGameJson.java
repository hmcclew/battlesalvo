package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.client.model.GameResult;

public record EndGameJson(
    @JsonProperty("result") GameResult gameResult,
    @JsonProperty("reason") String reason) {

  public GameResult gameResult() {
    return GameResult.DRAW;
  }
}
