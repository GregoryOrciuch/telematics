package com.orciuch.telematics.svc;

import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.orciuch.telematics.model.LastData;
import com.orciuch.telematics.model.TrackerEnvelope;
import com.orciuch.telematics.model.WicanPayload;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AutopidInfluxService {

    private final static String DEVICE_ID_STRING = "DEVICE_ID_STRING";
    private final static String LAST_PAYLOAD = "LAST_PAYLOAD";
    private final static String LAST_TIMESTAMP = "LAST_TIMESTAMP";

    private final WriteApi writeApi;
    private final String bucket;
    private final String org;

    private final Map<String, Object> cache = new ConcurrentHashMap<>();

    public AutopidInfluxService(WriteApi writeApi,
                                @org.springframework.beans.factory.annotation.Value("${influx.bucket}") String bucket,
                                @org.springframework.beans.factory.annotation.Value("${influx.org}") String org) {
        this.writeApi = writeApi;
        this.bucket = bucket;
        this.org = org;
    }

    /**
     * Write autopid_data as a single point to InfluxDB.
     * measurement: "autopid"
     * fields: each entry from autopid_data
     * tags: device_id from status (if present)
     */
    public void writeAutopidToInflux(WicanPayload payload) {
        if (payload == null || payload.getAutopid_data() == null || payload.getAutopid_data().isEmpty()) {
            return;
        }

        Map<String, Object> fields = new HashMap<>();
        payload.getAutopid_data().forEach((k, v) -> {
            String fieldKey = sanitizeFieldName(k);
            if (v == null) {
                // skip nulls or write explicit null handling
                return;
            }

            // try to parse numbers encoded as strings
            Object numericParsed = tryParseNumberFromString(v);
            if (numericParsed != null) {
                fields.put(fieldKey, numericParsed);
            } else {
                fields.put(fieldKey, v.toString());
            }

        });

        Point point = Point
                .measurement("autopid")
                .addFields(fields)
                .time(Instant.now(), WritePrecision.MS);

        // optional: add device_id as tag if present and remember it.
        String deviceId = null;
        Map<String, String> status = payload.getStatus();
        if (status != null && !status.isEmpty()) {
            deviceId = status.get("device_id");
            cache.put(DEVICE_ID_STRING, deviceId);
        }
        else {
            //try to get it from cache
            Object obj = cache.get(DEVICE_ID_STRING);
            if (obj != null) {
                deviceId = obj.toString();
            }
        }

        if (deviceId != null && !deviceId.isEmpty()) {
            point.addTag("device_id", deviceId);
        }

        //save last processed wican payload
        cache.put(LAST_PAYLOAD, payload);
        cache.put(LAST_TIMESTAMP, Instant.now());

        // non blocking api
        writeApi.writePoint(bucket, org, point);
    }

    public LastData getLastPayload() {
        Object o = cache.get(LAST_PAYLOAD);
        if( o != null) {
            LastData lastData = new LastData();
            lastData.setWicanPayload((WicanPayload) cache.get(LAST_PAYLOAD));
            lastData.setLastTimestamp((Instant) cache.get(LAST_TIMESTAMP));
            return lastData;
        }
        else {
            return new LastData();
        }
    }

    public void writeTraccarEnvToInflux(TrackerEnvelope trackerEnvelope, String deviceId) {
        if (trackerEnvelope == null || trackerEnvelope.getPosition() == null || deviceId == null || deviceId.isBlank()) {
            return;
        }

        TrackerEnvelope.Position position = trackerEnvelope.getPosition();
        if (StreamFields.latitudeMissing(position) || StreamFields.longitudeMissing(position)
                || StreamFields.altitudeMissing(position) || StreamFields.speedMissing(position)) {
            return;
        }

        Map<String, Object> fields = new HashMap<>();
        fields.put("GPS_LAT", position.getLatitude().floatValue());
        fields.put("GPS_LNG", position.getLongitude().floatValue());
        fields.put("GPS_ALTITUDE", position.getAltitude().floatValue());
        fields.put("GPS_SPEED", position.getSpeed().floatValue());

        Point point = Point
                .measurement("autopid")
                .addFields(fields)
                .time(Instant.now(), WritePrecision.MS);

        point.addTag("device_id", deviceId);

        // non blocking api
        writeApi.writePoint(bucket, org, point);
    }

    private static String sanitizeFieldName(String raw) {
        // Replace spaces and chars not allowed by line protocol with underscore
        return raw == null ? "" : raw.replaceAll("[^A-Za-z0-9_]", "_");
    }

    private static Number tryParseNumberFromString(Object v) {

        if (v instanceof Number number) {
            //cast all to float
            try {
                return number.floatValue();
            } catch (NumberFormatException ignored) {
                return null;
            }

        }
        else {
            //is not number
            return null;
        }
    }

    private static final class StreamFields {
        private static boolean latitudeMissing(TrackerEnvelope.Position position) {
            return Objects.isNull(position.getLatitude());
        }

        private static boolean longitudeMissing(TrackerEnvelope.Position position) {
            return Objects.isNull(position.getLongitude());
        }

        private static boolean altitudeMissing(TrackerEnvelope.Position position) {
            return Objects.isNull(position.getAltitude());
        }

        private static boolean speedMissing(TrackerEnvelope.Position position) {
            return Objects.isNull(position.getSpeed());
        }
    }
}
