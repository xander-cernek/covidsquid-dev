package com.covidsquid.dev.util;

import java.util.Map;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.covidsquid.dev.model.Rating;
import com.covidsquid.dev.model.RatingId;

import org.springframework.stereotype.Service;

@Service
public class RatingMapSerializer {

  public Rating deserializeFromMap(Map<String, AttributeValue> map) {
    Rating result = new Rating();
    RatingId ratingId = new RatingId(
      map.get(Rating.PARENT_ID_STRING).getS(),
      map.get(Rating.USER_ID_STRING).getS()
    );
    result.setRatingId(ratingId);
    result.setParentId(ratingId.getParentId());
    result.setUserId(ratingId.getUserId());
    result.setLocationId(map.get(Rating.LOCATION_ID_STRING).getS());
    result.setLocationName(map.get(Rating.LOCATION_NAME_STRING).getS());
    result.setRating(map.get(Rating.RATING_STRING).getS());
    return result;
  }
}
