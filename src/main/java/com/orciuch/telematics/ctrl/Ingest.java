package com.orciuch.telematics.ctrl;


import com.orciuch.telematics.model.LastData;
import com.orciuch.telematics.model.WicanPayload;
import com.orciuch.telematics.svc.AutopidInfluxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ingest")
public class Ingest {

    private static final Logger logger = LoggerFactory.getLogger(Ingest.class);

    private final AutopidInfluxService influxService;

    public Ingest(AutopidInfluxService influxService) {
        this.influxService = influxService;
    }

    @GetMapping
    public ResponseEntity<LastData> showOK() {
        logger.info("Showing lastData.");
        return ResponseEntity.ok(influxService.getLastPayload());
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Void> ingest(
            @RequestBody WicanPayload payload,
            @RequestHeader("x-api-key") String xApiKey
    ) {
        // example usage:
        // payload.getAutopid_data().get("0C-EngineRPM");
        // payload.getConfig().get("0C-EngineRPM").getClazz();
        influxService.writeAutopidToInflux(payload);
        Map<String, String> status = payload.getStatus();
        if (status != null && !status.isEmpty()) {
            logger.info("Received ingest payload for device {}", status.get("device_id"));
        }
        logger.debug("Ingesting payload: {}", payload);
        return ResponseEntity.ok().build();
    }
}
