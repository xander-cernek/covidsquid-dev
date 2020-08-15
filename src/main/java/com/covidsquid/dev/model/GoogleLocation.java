package com.covidsquid.dev.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;

@Getter
public class GoogleLocation {

  @JsonIgnore
  private String business_status;
  @JsonIgnore
  private List<Object> geometry;
  @JsonIgnore
  private String icon;
  private String name;
  @JsonIgnore
  private List<Object> photos;
  @JsonIgnore
  private String place_id;
  @JsonIgnore
  private Map<String, String> plus_code;
  @JsonIgnore
  private String rating;
  @JsonIgnore
  private String reference;
  @JsonIgnore
  private String scope;
  @JsonIgnore
  private List<String> types;
  @JsonIgnore
  private int user_ratings_total;
  private String vicinity;
  @JsonIgnore
  private Map<String, String> opening_hours;
  @JsonIgnore
  private int price_level;
}
