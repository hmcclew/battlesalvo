package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json record for game setup
 *
 * @param fleet the fleet setup by the player given
 *              specifications from the server
 */
public record SetupJson(
    @JsonProperty("fleet") ShipJson[] fleet) {

}
