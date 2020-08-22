package com.covidsquid.dev.repositories;

import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.covidsquid.dev.config.DynamoDBConfig;
import com.covidsquid.dev.model.Rating;
import com.covidsquid.dev.model.RatingStatisticsResponse;
import com.covidsquid.dev.util.RatingMapSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RatingDao {

	private String tableName = Rating.TABLENAME;

  /* I have no idea why they do it this way, but you need a mapping for the
  key and the value. */

  //Mapping for key
  private static String PARENT_KEY_KEY = "#parentId";
  private static String PARENT_KEY_VALUE = "parentId";

  //Mapping for value
  private static String PARENT_VALUE_KEY = ":parentId";

  //For QueryRequest
  private static String PARENT_KEY_CONDITION_EXPRESSION
    = PARENT_KEY_KEY + " = " + PARENT_VALUE_KEY;

  @Autowired
  private DynamoDBConfig dynamoDBConfig;

  @Autowired
  private RatingMapSerializer ratingMapSerializer;

  public List<Rating> getRatingsByParentId(String parentId) {
    List<Rating> result = new ArrayList<>();
    Iterator<Map<String, AttributeValue>> queryResult = queryByParentId(parentId);
    while (queryResult.hasNext()) {
      result.add(ratingMapSerializer.deserializeFromMap(queryResult.next()));
    }
    return result;
  }

  public RatingStatisticsResponse getRatingStatisticsByParentId(String parentId) {
    long numRatings = 0;
    Double ratingTotal = 0.0;
    Iterator<Map<String, AttributeValue>> queryResult = queryByParentId(parentId);
    while (queryResult.hasNext()) {
      Rating rating = ratingMapSerializer.deserializeFromMap(queryResult.next());
      numRatings++;
      ratingTotal += Double.valueOf(rating.getRating());
    }
    Double averageRating = ratingTotal / numRatings;
    return RatingStatisticsResponse.builder()
            .numRatings(String.valueOf(numRatings))
            .averageRating(String.valueOf(averageRating))
            .build();
  }

  private Iterator<Map<String, AttributeValue>> queryByParentId(String parentId) {
    return amazonDynamoDB().query(queryRequest(parentId)).getItems().iterator();
  }

  private QueryRequest queryRequest(String parentId) {
    return new QueryRequest()
    .withTableName(tableName)
    .withKeyConditionExpression(PARENT_KEY_CONDITION_EXPRESSION)
    .withExpressionAttributeNames(expressionAttributeNames())
    .withExpressionAttributeValues(expressionAttributeValues(parentId));
  }

  private Map<String, String> expressionAttributeNames() {
    Map<String, String> result = new HashMap<>();
    result.put(PARENT_KEY_KEY, PARENT_KEY_VALUE);
    return result;
  }

  private Map<String, AttributeValue> expressionAttributeValues(String parentId) {
    Map<String, AttributeValue> result = new HashMap<>();
    result.put(PARENT_VALUE_KEY, new AttributeValue().withS(parentId));
    return result;
  }

  private AmazonDynamoDB amazonDynamoDB() {
    return dynamoDBConfig.amazonDynamoDB();
  }
}
