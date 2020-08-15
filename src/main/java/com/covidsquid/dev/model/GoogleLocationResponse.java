package com.covidsquid.dev.model;

import java.util.List;

import lombok.Getter;

@Getter
public class GoogleLocationResponse {

  private List<String> html_attributions;
  private String next_page_token;
  private List<GoogleLocation> results;
  private String status;
}
