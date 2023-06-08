package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json Record to represent a fleet of ships
 *
 * @param fleet the array of ship jsons in this fleet
 */
public record FleetJson(
    @JsonProperty("fleet") ShipJson[] fleet) {
}
