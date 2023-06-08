package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json record take shots
 *
 * @param coordinates the coordinates of the shots the player took
 */
public record TakeShotsJson(
    @JsonProperty("coordinates") CoordJson[] coordinates) {
}
