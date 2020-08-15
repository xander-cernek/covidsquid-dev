package com.covidsquid.dev.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.Setter;

@Setter
@Data
@DynamoDBTable(tableName = Rating.TABLENAME)
public class Rating {

  public static final String TABLENAME = "ratings-dev";
  public static final String PARENT_ID_STRING = "parentId";
  public static final String USER_ID_STRING = "userId";
  public static final String LOCATION_ID_STRING = "locationId";
  public static final String LOCATION_NAME_STRING = "locationName";
  public static final String RATING_STRING = "rating";

  @Id
  public RatingId ratingId;

  @DynamoDBHashKey(attributeName = PARENT_ID_STRING)
  public String getParentId() {
    return ratingId != null ? ratingId.getParentId() : null;
  }

  public void setParentId( String parentId ) {
    if (ratingId == null) {
      ratingId = RatingId.builder().build();
    }
    ratingId.setParentId(parentId);
  }

  @DynamoDBRangeKey(attributeName = USER_ID_STRING)
  public String getUserId() {
    return ratingId != null ? ratingId.getUserId() : null;
  }

  public void setUserId( String id ) {
    if (ratingId == null) {
      ratingId = RatingId.builder().build();
    }
    ratingId.setUserId(id);
  }

  private String locationId;

  @DynamoDBAttribute(attributeName = LOCATION_ID_STRING)
  public String getLocationId() {
    return locationId != null ? locationId : "undefined";
  }

  private String locationName;

  @DynamoDBAttribute(attributeName = LOCATION_NAME_STRING)
  public String getLocationName() {
    return locationName != null ? locationName : "undefined";
  }

  private String rating;

  @DynamoDBAttribute(attributeName = RATING_STRING)
  public String getRating() {
    return rating != null ? rating : "undefined";
  }
}
