package com.covidsquid.dev.model;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel.DynamoDBAttributeType;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Setter
@Builder
@Data
@DynamoDBTable(tableName = Location.TABLENAME)
public class Location {

  public static final String TABLENAME = "locations-dev";
  public static final String PARENT_ID_STRING = "parentId";
  public static final String ID_STRING = "id";
  public static final String NAME_STRING = "name";
  public static final String ADDRESS_STRING = "address";
  public static final String PICTURE_URL_STRING = "pictureUrl";
  public static final String LAST_UPDATED_STRING = "lastUpdated";
  public static final String CROWD_RATING_STRING = "crowdRating";
  public static final String MASK_RATING_STRING = "maskRating";
  public static final String CLEAN_RATING_STRING = "cleanRating";
  public static final String TOTAL_RATING_STRING = "totalRating";
  public static final String NUM_RATING_STRING = "numRatings";
  public static final String LAST_FIVE_RATINGS_STRING = "lastFiveRatings";
  public static final String ROLLING_AVERAGE_RATING_STRING = "rollingAverageRating";
  public static final String TAGS_STRING = "tags";

  @Id
  public LocationId locationId;

  @DynamoDBHashKey(attributeName = PARENT_ID_STRING)
  public String getParentId() {
    return locationId != null ? locationId.getParentId() : null;
  }

  public void setParentId( String parentId ) {
    if (locationId == null) {
      locationId = LocationId.builder().build();
    }
    locationId.setParentId(parentId);
  }

  @DynamoDBRangeKey(attributeName = ID_STRING)
  public String getId() {
    return locationId != null ? locationId.getId() : null;
  }

  public void setId( String id ) {
    if (locationId == null) {
      locationId = LocationId.builder().build();
    }
    locationId.setId(id);
  }

  private String name;

  @DynamoDBAttribute(attributeName = NAME_STRING)
  public String getName() {
    return name != null ? name : "undefined";
  }

  private String address;

  @DynamoDBAttribute(attributeName = ADDRESS_STRING)
  public String getAddress() {
    return address != null ? address : "undefined";
  }

  private String pictureUrl;

  @DynamoDBAttribute(attributeName = PICTURE_URL_STRING)
  public String getPictureUrl() {
    return pictureUrl != null ? pictureUrl : "undefined";
  }

  private String lastUpdated;

  @DynamoDBAttribute(attributeName = LAST_UPDATED_STRING)
  public String getLastUpdated() {
    return lastUpdated != null ? lastUpdated : "undefined";
  }

  private String crowdRating;

  @DynamoDBAttribute(attributeName = CROWD_RATING_STRING)
  public String getCrowdRating() {
    return crowdRating != null ? crowdRating : "3";
  }

  private String maskRating;

  @DynamoDBAttribute(attributeName = MASK_RATING_STRING)
  public String getMaskRating() {
    return maskRating != null ? maskRating : "3";
  }

  private String cleanRating;

  @DynamoDBAttribute(attributeName = CLEAN_RATING_STRING)
  public String getCleanRating() {
    return cleanRating != null ? cleanRating : "3";
  }

  private String totalRating;

  @DynamoDBAttribute(attributeName = TOTAL_RATING_STRING)
  public String getTotalRating() {
    return totalRating != null ? totalRating : "3";
  }

  private String numRatings;

  @DynamoDBAttribute(attributeName = NUM_RATING_STRING)
  public String getNumRatings() {
    return numRatings != null ? numRatings : "0";
  }

  private List<String> lastFiveRatings;

  @DynamoDBAttribute(attributeName = LAST_FIVE_RATINGS_STRING)
  @DynamoDBTyped(DynamoDBAttributeType.L)
  public List<String> getLastFiveRatings() {
    return lastFiveRatings != null ? lastFiveRatings : new ArrayList<>();
  }

  private String rollingAverageRating;

  @DynamoDBAttribute(attributeName = ROLLING_AVERAGE_RATING_STRING)
  public String getRollingAverageRating() {
    return rollingAverageRating != null ? rollingAverageRating : "0";
  }

  private List<String> tags;

  @DynamoDBAttribute(attributeName = TAGS_STRING)
  @DynamoDBTyped(DynamoDBAttributeType.L)
  public List<String> tags() {
    return tags != null ? tags : new ArrayList<>();
  }
}
