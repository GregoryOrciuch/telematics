package com.orciuch.telematics.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * DTO for incoming POST /ingest payload from WiCAN/OBD device.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WicanPayload {

    /**
     * Example config entry: "0C-EngineRPM": {"class":"speed","unit":"rpm"}
     * JSON property name 'class' maps to Java field 'clazz'
     */
    public static class ConfigEntry {
        @JsonProperty("class")
        private String clazz;
        private String unit;

        public ConfigEntry() {}

        public ConfigEntry(String clazz, String unit) {
            this.clazz = clazz;
            this.unit = unit;
        }

        public String getClazz() { return clazz; }
        public void setClazz(String clazz) { this.clazz = clazz; }
        public String getUnit() { return unit; }
        public void setUnit(String unit) { this.unit = unit; }

        @Override
        public String toString() {
            return "ConfigEntry{class=" + clazz + ", unit=" + unit + '}';
        }
    }

    // Maps key like "0C-EngineRPM" -> ConfigEntry
    private Map<String, ConfigEntry> config = new HashMap<>();

    // Maps status keys -> values (mostly strings)
    private Map<String, String> status = new HashMap<>();

    // Maps telemetry keys -> numeric values (or strings). Keep Object to accept ints/doubles/strings.
    private Map<String, Object> autopid_data = new HashMap<>();

    // Catch-all for any other top-level properties not defined above
    private Map<String, Object> other = new HashMap<>();

    public WicanPayload() {}

    public Map<String, ConfigEntry> getConfig() { return config; }
    public void setConfig(Map<String, ConfigEntry> config) { this.config = config; }

    public Map<String, String> getStatus() { return status; }
    public void setStatus(Map<String, String> status) { this.status = status; }

    public Map<String, Object> getAutopid_data() { return autopid_data; }
    public void setAutopid_data(Map<String, Object> autopid_data) { this.autopid_data = autopid_data; }

    @JsonAnySetter
    public void setOther(String name, Object value) {
        // ignore if one of the known ones
        if ("config".equals(name) || "status".equals(name) || "autopid_data".equals(name)) return;
        other.put(name, value);
    }

    @JsonAnyGetter
    public Map<String, Object> any() {
        return other;
    }

    public Map<String, Object> getOther() { return other; }

    @Override
    public String toString() {
        return "WicanPayload{" +
                "config=" + config +
                ", status=" + status +
                ", autopid_data=" + autopid_data +
                ", other=" + other +
                '}';
    }
}
