package com.orciuch.telematics.model;

import java.time.Instant;

public class LastData {
    private WicanPayload wicanPayload;
    private Instant lastTimestamp;

    public void setLastTimestamp(Instant lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }

    public Instant getLastTimestamp() {
        return lastTimestamp;
    }

    public void setWicanPayload(WicanPayload wicanPayload) {
        this.wicanPayload = wicanPayload;
    }

    public WicanPayload getWicanPayload() {
        return wicanPayload;
    }
}
