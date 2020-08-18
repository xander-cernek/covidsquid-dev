package com.covidsquid.dev.services;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.covidsquid.dev.model.GoogleLocation;
import com.covidsquid.dev.model.Location;
import com.covidsquid.dev.model.LocationId;
import com.covidsquid.dev.model.ParentLocation;
import com.covidsquid.dev.util.GoogleConnector;
import com.covidsquid.dev.util.GoogleResponseParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ParentLocationService {

  @Autowired
  public GoogleConnector googleConnector;

  public List<GoogleLocation> getGoogleMapsInfo(ParentLocation parentLocation, String tag) {
    try {
      return GoogleResponseParser.parseResponse(googleConnector.buildGoogleMapsRequest(parentLocation, tag));
    } catch (Exception e) {
      log.error("Error when calling maps API", e);
    }
    return Collections.emptyList();
  }

  public List<Location> googleMapsInfoToLocation(ParentLocation parentLocation, String tag) {
    List<GoogleLocation> googleLocations = getGoogleMapsInfo(parentLocation, tag);
    List<Location> result = new ArrayList<>();
    List<String> tags = new ArrayList<>();
    tags.add(tag);
    for (GoogleLocation gLocation : googleLocations) {
      String uniqueID = UUID.randomUUID().toString();
      Location record = Location.builder()
      .locationId(
        LocationId.builder()
        .parentId(parentLocation
        .getParentId())
        .id(uniqueID)
        .build())
      .address(gLocation.getVicinity())
      .name(gLocation.getName())
      .tags(tags)
      .build();
      result.add(record);
    }
    return result;
  }
}
