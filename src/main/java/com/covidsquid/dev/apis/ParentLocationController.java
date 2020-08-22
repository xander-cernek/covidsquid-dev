package com.covidsquid.dev.apis;

import java.util.List;
import java.util.Optional;

import com.covidsquid.dev.model.ParentLocation;
import com.covidsquid.dev.model.Rating;
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

  @Autowired
  LocationController locationController;

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
  @RequestMapping(value="/ratingsStream", method=RequestMethod.GET, produces="application/json")
  public List<Rating> getRatingsStream(@RequestParam String parentId) {
    try {
      validateParentId(parentId);
      return ratingService.getAllRatingsByParentId(parentId);
    } catch (Exception e) {
      log.error("Error in getRatingsStream: {}", e);
      return null;
    }
  }

  @RequestMapping(value="/postLocations", method=RequestMethod.POST)
  public void saveCovidSquidLocations(@RequestParam String parentId, @RequestParam String tag) {
    Optional<ParentLocation> pOptional = getParentLocation(parentId);
    if (pOptional.isPresent()) {
      locationController.createLocations(parentLocationService.googleMapsInfoToLocation(pOptional.get(), tag));
    }
  }

  @RequestMapping(value="/updateStats/{parentId}", method=RequestMethod.POST, produces="application/json")
  public RatingStatisticsResponse updateStats(@PathVariable(required=true, name="parentId") String parentId) {
    try {
      validateParentId(parentId);
      return ratingService.getRatingStatsByParentId(parentId);
    } catch (Exception e) {
      log.error("Error in updateStats: {}", e);
    }
    return null;
  }

  private void validateParentId(String parentId) throws Exception {
    Optional<ParentLocation> pOptional = getParentLocation(parentId);
    if (!pOptional.isPresent()) {
      log.error("ParentId could not be found: {}", parentId);
      throw new Exception("Invalid parentId");
    }
  }
}
