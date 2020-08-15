package com.covidsquid.dev.services;

import java.util.Collections;
import java.util.List;

import com.covidsquid.dev.model.GoogleLocation;
import com.covidsquid.dev.model.ParentLocation;
import com.covidsquid.dev.util.GoogleConnector;
import com.covidsquid.dev.util.GoogleResponseParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ParentLocationService {

  @Autowired
  public GoogleConnector googleConnector;

  public List<GoogleLocation> getGoogleMapsInfo(ParentLocation parentLocation, String tag) {
    try {
      return GoogleResponseParser.parseResponse(googleConnector.buildGoogleMapsRequest(parentLocation, tag));
    } catch (Exception e) {
      log.error("Error when calling maps API", e);
    }
    return Collections.emptyList();
  }
}
