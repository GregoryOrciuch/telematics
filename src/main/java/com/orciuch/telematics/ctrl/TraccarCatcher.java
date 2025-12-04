package com.orciuch.telematics.ctrl;

import com.orciuch.telematics.model.TrackerEnvelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/traccar")
public class TraccarCatcher {

    // creating a logger
    Logger logger = LoggerFactory.getLogger(TraccarCatcher.class);

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Void> eatFromTraccar(@RequestBody TrackerEnvelope payload,
                                               @RequestHeader("x-api-key") String xApiKey) {

        if(xApiKey != null && !xApiKey.isEmpty() ){

            Optional.ofNullable(payload.getDevice())
                    .map(TrackerEnvelope.Device::getAttributes)
                    .map(a -> a.get("autopid_device_id"))
                    .map(String.class::cast)
                    .ifPresent(id -> logger.info("Detected device id: {}", id));
        }

        return ResponseEntity.ok().build();
    }
}
