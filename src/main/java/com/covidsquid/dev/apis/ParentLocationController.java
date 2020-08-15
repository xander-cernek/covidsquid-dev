package com.covidsquid.dev.apis;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.covidsquid.dev.model.GoogleLocation;
import com.covidsquid.dev.model.Location;
import com.covidsquid.dev.model.ParentLocation;
import com.covidsquid.dev.model.RatingStatisticsResponse;
import com.covidsquid.dev.repositories.ParentLocationRepository;
import com.covidsquid.dev.services.ParentLocationService;
import com.covidsquid.dev.services.RatingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/job")
@RestController
@CrossOrigin
public class ParentLocationController {

  @Autowired
  ParentLocationRepository parentLocationRepository;

  @Autowired
  ParentLocationService parentLocationService;

  @Autowired
  RatingService ratingService;

  @ResponseBody
  @RequestMapping(value="/ping", method=RequestMethod.GET, produces="application/json")
  public Boolean ping() {
    return true;
  }

  @RequestMapping(value="/create", method=RequestMethod.POST, produces="application/json")
  public void createParentLocation(@RequestBody ParentLocation parentLocation) {
    parentLocationRepository.save(parentLocation);
  }

  @ResponseBody
  @RequestMapping(value="/get", method=RequestMethod.GET, produces="application/json")
  public Optional<ParentLocation> getParentLocation(@RequestParam String parentId) {
    return parentLocationRepository.findById(parentId);
  }

  @ResponseBody
  @RequestMapping(value="/getLocations", method=RequestMethod.GET, produces="application/json")
  public List<GoogleLocation> getLocationsAroundParent(@RequestParam String parentId, @RequestParam String tag) {
    Optional<ParentLocation> pOptional = getParentLocation(parentId);
    if (pOptional.isPresent()) {
      return parentLocationService.getGoogleMapsInfo(pOptional.get(), tag);
    }
    return Collections.emptyList();
  }

  @ResponseBody
  @RequestMapping(value="/ratingsStream", method=RequestMethod.GET, produces="application/json")
  public List<Location> getRatingsStream(@RequestParam String parentId) {
    try {
      validateParentId(parentId);
      return null;
    } catch (Exception e) {
      log.error("Error in getRatingsStream: {}", e);
      return null;
    }
  }

  @RequestMapping(value="/updateStats/{parentId}", method=RequestMethod.POST, produces="application/json")
  public void updateStats(@PathVariable(required=true, name="parentId") String parentId) {
    try {
      validateParentId(parentId);
      RatingStatisticsResponse response = ratingService.getRatingStatsByParentId(parentId);
      log.info("Result from updateStats: Num ratings: {}, Avg rating: {}", response.getNumRatings(), response.getAverageRating());
    } catch (Exception e) {
      log.error("Error in updateStats: {}", e);
    }
  }

  private void validateParentId(String parentId) throws Exception {
    Optional<ParentLocation> pOptional = getParentLocation(parentId);
    if (!pOptional.isPresent()) {
      log.error("ParentId could not be found: {}", parentId);
      throw new Exception("Invalid parentId");
    }
  }
}
