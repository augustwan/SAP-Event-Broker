package com.trailfilter.controller;

import com.trailfilter.model.TrailData;
import com.trailfilter.model.TrailType;
import com.trailfilter.service.TrailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@Slf4j
public class TrailController {

    private final TrailService trailService;

    public TrailController(TrailService trailService) {
        this.trailService = trailService;
    }

    @Operation(summary = "Get filtered trails",
               description = "Retrieve trails filtered by type and optional fishing feature")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of filtered trails"),
            @ApiResponse(responseCode = "400",
                         description = "Invalid input parameters",
                        content = @Content(mediaType = "application/json",
                                           examples = @ExampleObject(value = "{ \"error\": \"Invalid input\", \"status\": 400, \"message\": \"Trail type must be either BIKE or WALKING\" }"))
            ),
            @ApiResponse(responseCode = "404",
                         description = "No trails found matching criteria",
                         content = @Content(mediaType = "application/json",
                                            examples = @ExampleObject(value = "{ \"error\": \"Trail not found\", \"status\": 404, \"message\": \"Not able to find trails\" }"))
            )
    })
    @GetMapping(value = "/trails", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<TrailData> getFilteredTrails(

            @Parameter(description = "Type of trail (BIKE or WALK)",
                       required = true,
                       schema = @Schema(implementation = TrailType.class))
            @RequestParam TrailType trailType,
            @Parameter(description = "Whether the trail allows fishing (optional)")
            @RequestParam(required = false) Boolean fishing) {

        log.info("getFilteredTrails trailType={}, fishing={}", trailType, fishing);
        return trailService.filterTrails(trailType, fishing);
    }

}
