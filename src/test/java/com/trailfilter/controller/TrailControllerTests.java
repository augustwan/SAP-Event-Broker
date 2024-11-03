package com.trailfilter.controller;

import com.trailfilter.model.TrailData;
import com.trailfilter.model.TrailType;
import com.trailfilter.service.TrailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

@WebFluxTest(TrailController.class)
public class TrailControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TrailService trailService;

    @BeforeEach
    public void setUp() {
        // Set up the service mock to return a list of trails
        when(trailService.filterTrails(TrailType.BIKE, null))
                .thenReturn(Flux.just(new TrailData("1", true, false, true, "Public", "001", "Easy", "123 Trail Ave", false, true, "Possible", "Flagstaff Summit West")));
        when(trailService.filterTrails(TrailType.WALKING, true))
                .thenReturn(Flux.empty());
    }

    @Test
    public void testGetFilteredTrailsByBike() {
        webTestClient.get()
                .uri("/trails?trailType=BIKE")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TrailData.class)
                .hasSize(1)
                .contains(new TrailData("1", true, false, true, "Public", "001", "Easy", "123 Trail Ave", false, true, "Possible", "Flagstaff Summit West"));
    }

    @Test
    public void testGetFilteredTrailsByWalkingWithFishing() {
        webTestClient.get()
                .uri("/trails?trailType=WALKING&fishing=true")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TrailData.class)
                .hasSize(0);
    }

}