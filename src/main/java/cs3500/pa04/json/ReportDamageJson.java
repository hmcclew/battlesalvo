package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ReportDamageJson(
    @JsonProperty("volley") VolleyJson[] volley) {
}
