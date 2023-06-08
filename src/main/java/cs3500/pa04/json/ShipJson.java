package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json record to represent a ship
 *
 * @param c the starting coordinate of the ship
 * @param length the length of the ship
 * @param direction the orientation of the ship
 */
public record ShipJson(
    @JsonProperty("coord") CoordJson c,
    @JsonProperty("length") int length,
    @JsonProperty("direction") String direction) {

}
