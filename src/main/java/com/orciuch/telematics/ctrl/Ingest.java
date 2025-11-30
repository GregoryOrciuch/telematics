package com.orciuch.telematics.ctrl;

import java.util.concurrent.atomic.AtomicLong;

import com.orciuch.telematics.model.WicanPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingest")
public class Ingest {

    // creating a logger
    Logger logger = LoggerFactory.getLogger(Ingest.class);

    @GetMapping
    public ResponseEntity<String> showOK() {
        logger.info("Ingesting data");
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Void> ingest(@RequestBody WicanPayload payload) {
        // example usage:
        // payload.getAutopid_data().get("0C-EngineRPM");
        // payload.getConfig().get("0C-EngineRPM").getClazz();
        logger.info("Ingesting:"+payload);
        return ResponseEntity.ok().build();
    }
}