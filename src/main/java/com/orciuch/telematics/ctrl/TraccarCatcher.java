package com.orciuch.telematics.ctrl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/traccar")
public class TraccarCatcher {

    // creating a logger
    Logger logger = LoggerFactory.getLogger(TraccarCatcher.class);

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Void> eatFromTraccar(@RequestBody String payload,
                                               @RequestHeader("x-api-key") String xApiKey) {

        if(xApiKey != null && !xApiKey.isEmpty() ){
            logger.info("Received from Traccar: "+payload);
        }

        return ResponseEntity.ok().build();
    }
}
