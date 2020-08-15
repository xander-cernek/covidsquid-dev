package com.covidsquid.dev.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
@DynamoDBDocument
@AllArgsConstructor
@NoArgsConstructor
public class RatingId {

  @DynamoDBHashKey
  public String parentId;

  @DynamoDBRangeKey
  public String userId;
}
