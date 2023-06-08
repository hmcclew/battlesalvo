package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json to represent reportDamage
 *
 * @param coordinates the coordinates of damage to a player's fleet
 */
public record ReportDamageJson(
    @JsonProperty("coordinates") CoordJson[] coordinates) {
}
