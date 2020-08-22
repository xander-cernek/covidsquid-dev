package com.covidsquid.dev.util;

import java.util.Map;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.covidsquid.dev.model.Rating;
import com.covidsquid.dev.model.RatingId;

import org.springframework.stereotype.Service;

@Service
public class RatingMapSerializer {

  public Rating deserializeFromMap(Map<String, AttributeValue> map) {
    return Rating.builder()
      .ratingId(RatingId.builder()
        .parentId(map.get(Rating.PARENT_ID_STRING).getS())
        .userId(map.get(Rating.USER_ID_STRING).getS())
        .build())
      .locationId(map.get(Rating.LOCATION_ID_STRING).getS())
      .locationName(map.get(Rating.LOCATION_NAME_STRING).getS())
      .rating(map.get(Rating.RATING_STRING).getS())
      .build();
  }
}
