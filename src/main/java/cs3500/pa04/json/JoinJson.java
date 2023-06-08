package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json to represent joining a game
 *
 * @param name the username of the player
 * @param gameType the GameType specified by the player
 */
public record JoinJson(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") String gameType) {

}
