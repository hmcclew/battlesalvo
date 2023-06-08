package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EndGameJson(
    @JsonProperty("result") String result,
    @JsonProperty("reason") String reason) {

}
