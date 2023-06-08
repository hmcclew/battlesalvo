package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ShipJson(
    @JsonProperty("coord") CoordJson c,
    @JsonProperty("length") int length,
    @JsonProperty("direction") String direction) {

}
