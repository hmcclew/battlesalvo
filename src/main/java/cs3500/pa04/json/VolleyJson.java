package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON Record for a Volley
 *
 * @param coordinates an array of coordinates
 */
public record VolleyJson(
    @JsonProperty("coordinates") CoordJson[] coordinates) {
}
