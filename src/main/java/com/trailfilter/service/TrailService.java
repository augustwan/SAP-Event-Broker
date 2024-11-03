package com.trailfilter.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.trailfilter.model.TrailData;
import com.trailfilter.model.TrailType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.*;
import java.util.List;

@Service
@Slf4j
public class TrailService {

    private List<TrailData> trailData;

    private TrailService() {
        loadTrailsFromCsv();
    }

    private void loadTrailsFromCsv() {
        if (trailData == null || trailData.isEmpty()) {
            log.info("Starting to read CSV file from resources.");
            try (Reader reader = new InputStreamReader(new ClassPathResource("BoulderTrailHeads.csv").getInputStream())) {
                CsvToBean<TrailData> csvToBean = new CsvToBeanBuilder<TrailData>(reader)
                        .withType(TrailData.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                trailData = csvToBean.parse();
                log.info("Successfully parsed CSV file. Number of records: {}", trailData.size());

            } catch (IOException e) {
                log.error("Error reading CSV file from resources", e);
                throw new RuntimeException(e);
            }

            if (trailData.isEmpty()) {
                log.warn("No records found in the CSV file.");
            }
        }
    }

    public Flux<TrailData> filterTrails(TrailType trailType, Boolean fishing) {
        return Flux.fromIterable(trailData)
                .filter(trail -> filterTrailType(trailType, trail)) //decided if it's bike/walking trail
                .filter(trail -> fishing == null || trail.isFishing() == fishing); //allowing finishing or not
    }

    private boolean filterTrailType(TrailType trailType, TrailData trailData) {
        return (trailType == TrailType.BIKE && trailData.isBikeTrail())
                //Assuming the trail can be considered as walking trail if it is not designated as horse trail.
                || (trailType == TrailType.WALKING && !isDesignatedHorseTrail(trailData));
    }

    private boolean isDesignatedHorseTrail(TrailData trailData) {
        return trailData.getHorseTrail() != null && trailData.getHorseTrail().contains("Designated");
    }

}
