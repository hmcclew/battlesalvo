package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json to represent a game ending
 *
 * @param result the result of the game
 * @param reason the reason for the game ending
 */
public record EndGameJson(
    @JsonProperty("result") String result,
    @JsonProperty("reason") String reason) {

}
