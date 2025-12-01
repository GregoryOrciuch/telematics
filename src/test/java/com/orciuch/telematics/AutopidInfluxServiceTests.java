package com.orciuch.telematics;

import com.influxdb.client.WriteApi;
import com.influxdb.client.write.Point;
import com.orciuch.telematics.model.WicanPayload;
import com.orciuch.telematics.svc.AutopidInfluxService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AutopidInfluxServiceTests {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private AutopidInfluxService autopidInfluxService;

    @Mock
    private WriteApi writeApi;

    @Captor
    ArgumentCaptor<Point> pointCaptor;

    @Test
    public void writeAutopidToInfluxFullTest() throws Exception{

        String fullJson = loadJsonFromClasspath("examples/full.json");
        WicanPayload payload = objectMapper.readValue(fullJson, WicanPayload.class);

        autopidInfluxService.writeAutopidToInflux(payload);

        verify(writeApi, times(1)).writePoint(any(),any(),pointCaptor.capture());

        Point point = pointCaptor.getValue();

        assertNotNull(point);
        assertTrue(point.hasFields());
    }

    @Test
    public void writeAutopidToInfluxSmallTest() throws Exception{

        String fullJson = loadJsonFromClasspath("examples/small.json");
        WicanPayload payload = objectMapper.readValue(fullJson, WicanPayload.class);

        autopidInfluxService.writeAutopidToInflux(payload);

        verify(writeApi, times(1)).writePoint(any(),any(),pointCaptor.capture());

        Point point = pointCaptor.getValue();

        assertNotNull(point);
        assertTrue(point.hasFields());
    }


    private String loadJsonFromClasspath(String path) throws Exception {
        ClassPathResource resource = new ClassPathResource(path);
        byte[] bytes = resource.getInputStream().readAllBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
