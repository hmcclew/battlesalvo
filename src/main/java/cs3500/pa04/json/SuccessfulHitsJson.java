package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json record for successful shots
 *
 * @param coordinates the coordinates of the successful shots the player took
 */
public record SuccessfulHitsJson(
    @JsonProperty("coordinates") CoordJson[] coordinates) {

}
