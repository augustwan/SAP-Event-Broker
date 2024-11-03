package com.trailfilter.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrailData {

    @CsvBindByName(column = "FID")
    private String FID;

    @CsvBindByName(column = "RESTROOMS")
    private boolean restRoom;

    @CsvBindByName(column = "PICNIC")
    private boolean picnic;

    @CsvBindByName(column = "FISHING")
    private boolean fishing;

    @CsvBindByName(column = "AccessType")
    private String accessType;

    @CsvBindByName(column = "AccessID")
    private String accessID;

    @CsvBindByName(column = "Class")
    private String trailClass;

    @CsvBindByName(column = "Address")
    private String address;

    @CsvBindByName(column = "Fee")
    private boolean fee;

    @CsvBindByName(column = "BikeTrail")
    private boolean bikeTrail;

    @CsvBindByName(column = "HorseTrail")
    private String horseTrail;

    @CsvBindByName(column = "AccessName")
    private String accessName;

}
