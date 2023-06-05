package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FleetSpecJson(
  @JsonProperty("CARRIER") int numCarrier,
  @JsonProperty("BATTLESHIP") int numBattleShip,
  @JsonProperty("DESTROYER") int numDestroyer,
  @JsonProperty("SUBMARINE") int numSubmarine) {
}
