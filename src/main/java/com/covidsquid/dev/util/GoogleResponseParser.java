package com.covidsquid.dev.util;

import java.util.Collections;
import java.util.List;

import com.covidsquid.dev.model.GoogleLocation;
import com.covidsquid.dev.model.GoogleLocationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GoogleResponseParser {

  public static List<GoogleLocation> parseResponse(String response) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      GoogleLocationResponse parsedResponse = objectMapper.readValue(response, GoogleLocationResponse.class);
      return parsedResponse.getResults();
    } catch (Exception e) {
      log.error("There was an error parsing the response: {}", e);
    }
    return Collections.emptyList();
  }
}
