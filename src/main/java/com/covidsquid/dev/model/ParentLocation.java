package com.covidsquid.dev.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.Data;
import lombok.Setter;

@Setter
@Data
@DynamoDBTable(tableName = ParentLocation.TABLENAME)
public class ParentLocation {

  public static final String TABLENAME = "parent-locations-dev";
  public static final String PARENT_ID_STRING = "parentId";
  public static final String LATITUDE_STRING = "latitude";
  public static final String LONGITUDE_STRING = "longitude";
  public static final String RADIUS_STRING = "radius";
  public static final String NUM_RATINGS_STRING = "numRatings";
  public static final String AVERAGE_RATING_STRING = "averageRating";

  private String parentId;

  @DynamoDBHashKey(attributeName = PARENT_ID_STRING)
  public String getParentId() {
    return parentId != null ? parentId : "undefined";
  }

  private String latitude;

  @DynamoDBAttribute(attributeName = LATITUDE_STRING)
  public String getLatitude() {
    return latitude != null ? latitude : "undefined";
  }

  private String longitude;

  @DynamoDBAttribute(attributeName = LONGITUDE_STRING)
  public String getLongitude() {
    return longitude != null ? longitude : "undefined";
  }

  private String radius;

  @DynamoDBAttribute(attributeName = RADIUS_STRING)
  public String getRadius() {
    return radius != null ? radius : "undefined";
  }

  private String numRatings;

  @DynamoDBAttribute(attributeName = NUM_RATINGS_STRING)
  public String getNumRatings() {
    return numRatings != null ? numRatings : "undefined";
  }

  private String averageRating;

  @DynamoDBAttribute(attributeName = AVERAGE_RATING_STRING)
  public String getAverageRating() {
    return averageRating != null ? averageRating : "undefined";
  }
}
