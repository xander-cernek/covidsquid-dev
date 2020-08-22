package com.covidsquid.dev.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetLocationResponse {

  private String id;

  private String parentId;

  private String name;

  private String address;

  private String numRatings;

  private String rollingAverageRating;

  private List<String> tags;
}
