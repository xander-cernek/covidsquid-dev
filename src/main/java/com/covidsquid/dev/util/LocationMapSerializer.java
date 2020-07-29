package com.covidsquid.dev.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.covidsquid.dev.model.Location;
import com.covidsquid.dev.model.LocationId;

import org.springframework.stereotype.Service;

@Service
public class LocationMapSerializer {

  public Location deserializeFromMap(Map<String, AttributeValue> map) {
    Location result = new Location();
    LocationId locationId = new LocationId(
      map.get(Location.PARENT_ID_STRING).getS(),
      map.get(Location.ID_STRING).getS()
    );
    result.setLocationId(locationId);
    result.setParentId(Location.PARENT_ID_STRING);
    result.setId(Location.ID_STRING);
    result.setAddress(map.get(Location.ADDRESS_STRING).getS());
    result.setPictureUrl(map.get(Location.PICTURE_URL_STRING).getS());
    result.setLastUpdated(map.get(Location.LAST_UPDATED_STRING).getS());
    result.setCrowdRating(map.get(Location.CROWD_RATING_STRING).getS());
    result.setMaskRating(map.get(Location.MASK_RATING_STRING).getS());
    result.setCleanRating(map.get(Location.CLEAN_RATING_STRING).getS());
    result.setTotalRating(map.get(Location.TOTAL_RATING_STRING).getS());
    result.setNumRatings(map.get(Location.NUM_RATING_STRING).getS());
    result.setLastFiveRatings(
      deserializeList(map.get(Location.LAST_FIVE_RATINGS_STRING).getL())
    );
    return result;
  }

  private List<String> deserializeList(List<AttributeValue> list) {
    List<String> result = new ArrayList<>();
    for (AttributeValue item : list) {
      result.add(item.getS());
    }
    return result;
  }

  private Map<String, String> deserializeMap(Map<String, AttributeValue> map) {
    Map<String, String> result = new HashMap<>();
    map.entrySet().stream()
    .collect(Collectors.toMap(
      e -> e.getKey(),
      e -> e.getValue().getS()
    ));
    return result;
  }
}
