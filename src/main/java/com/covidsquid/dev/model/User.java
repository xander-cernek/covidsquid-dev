package com.covidsquid.dev.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@DynamoDBTable(tableName = User.TABLENAME)
public class User {

  public static final String TABLENAME = "users-dev";
  public static final String USERNAME_STRING = "username";
  public static final String PASSWORD_STRING = "password";
  public static final String EMAIL_STRING = "email";
  public static final String SALT_STRING = "salt";
  public static final String REGISTERED_STRING = "registered";
  public static final String REGISTER_ID_STRING = "registerId";
  public static final String SESSION_ID_STRING = "sessionId";

  @DynamoDBHashKey(attributeName = EMAIL_STRING)
  private String email;

  public String getEmail() {
    return email != null ? email : "undefined";
  }

  @DynamoDBAttribute(attributeName = USERNAME_STRING)
  private String username;

  public String getUsername() {
    return username != null ? username : "undefined";
  }

  @DynamoDBAttribute(attributeName = PASSWORD_STRING)
  private String password;

  public String getPassword() {
    return password != null ? password : "undefined";
  }

  @DynamoDBAttribute(attributeName = SALT_STRING)
  private String salt;

  public String getSalt() {
    return salt != null ? salt : "undefined";
  }

  @DynamoDBAttribute(attributeName = REGISTERED_STRING)
  private Boolean registered;

  public Boolean getRegistered() {
    return registered != null ? registered : false;
  }

  @DynamoDBAttribute(attributeName = REGISTER_ID_STRING)
  private String registerId;

  public String getRegisterId() {
    return registered != null ? registerId : "undefined";
  }

  @DynamoDBAttribute(attributeName = SESSION_ID_STRING)
  private String sessionId;

  public String getSessionId() {
    return sessionId;
  }
}
