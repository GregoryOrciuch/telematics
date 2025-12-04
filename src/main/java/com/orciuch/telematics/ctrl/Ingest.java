package com.orciuch.telematics.ctrl;


import com.orciuch.telematics.model.LastData;
import com.orciuch.telematics.model.WicanPayload;
import com.orciuch.telematics.svc.AutopidInfluxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingest")
public class Ingest {

    private final AutopidInfluxService influxService;

    public Ingest(AutopidInfluxService influxService) {
        this.influxService = influxService;
    }

    // creating a logger
    Logger logger = LoggerFactory.getLogger(Ingest.class);

    @GetMapping
    public ResponseEntity<LastData> showOK() {
        logger.info("Showing lastData.");
        return new ResponseEntity<>(influxService.getLastPayload(), HttpStatus.OK);
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
        if(payload.getStatus() != null && !payload.getStatus().isEmpty()){
            logger.info("Received x-api-key: " + xApiKey);
            logger.info("Received Full frame: "+payload);
        }
        logger.debug("Ingesting:"+payload);
        return ResponseEntity.ok().build();
    }
}