package com.covidsquid.dev.apis;

import java.util.List;
import java.util.Optional;

import com.covidsquid.dev.model.GetLocationResponse;
import com.covidsquid.dev.model.Location;
import com.covidsquid.dev.model.LocationId;
import com.covidsquid.dev.model.LocationRatingRequest;
import com.covidsquid.dev.model.Rating;
import com.covidsquid.dev.repositories.LocationDao;
import com.covidsquid.dev.repositories.LocationRepository;
import com.covidsquid.dev.repositories.RatingRepository;
import com.covidsquid.dev.services.LocationService;
import com.covidsquid.dev.services.RatingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api")
@RestController
@CrossOrigin
public class LocationController {

  @Autowired
  LocationRepository locationRepository;

  @Autowired
  LocationDao locationDao;

  @Autowired
  LocationService locationService;

  @Autowired
  RatingService ratingService;

  @Autowired
  RatingRepository ratingRepository;

  @ResponseBody
  @RequestMapping(value="/ping", method=RequestMethod.GET, produces="application/json")
  public Boolean ping() {
    return true;
  }

  @RequestMapping(value="/create", method=RequestMethod.POST, produces="application/json")
  public void createLocations(@RequestBody List<Location> locations) {
    for (Location location : locations) {
      locationRepository.save(location);
    }
  }

  @RequestMapping(value="/update", method=RequestMethod.POST, produces = "application/json")
  public void saveLocation(@RequestBody Location location) {
    LocationId id = LocationId.builder()
    .parentId(location.getParentId())
    .id(location.getId())
    .build();
    Optional<Location> result = locationRepository.findById(id);
    if (result.isPresent()) {
      Location payload = result.get();
      payload = locationService.update(payload, location);
      locationRepository.save(payload);
    }
  }

  @RequestMapping(value="/rate", method=RequestMethod.POST, produces = "application/json")
  public void rateLocation(@RequestBody LocationRatingRequest locationRatingRequest) {
    LocationId id = LocationId.builder()
    .parentId(locationRatingRequest.getLocation().getParentId())
    .id(locationRatingRequest.getLocation().getId())
    .build();
    Optional<Location> result = locationRepository.findById(id);
    if (result.isPresent()) {
      Location payload = result.get();
      payload = locationService.rate(payload, locationRatingRequest.getLocation());
      Rating ratingPayload = ratingService.getRatingFromLocation(payload, locationRatingRequest.getUserId());
      locationRepository.save(payload);
      ratingRepository.save(ratingPayload);
    }
  }

  @ResponseBody
  @RequestMapping(value="/get", method=RequestMethod.GET, produces="application/json")
  public Optional<Location> getLocation(@RequestParam String parentId, @RequestParam String id) {
    LocationId ids = LocationId.builder().parentId(parentId).id(id).build();
    return locationRepository.findById(ids);
  }

  @ResponseBody
  @RequestMapping(value="/getAll", method=RequestMethod.GET, produces="application/json")
  public List<GetLocationResponse> getAllLocationsByParentId(@RequestParam String parentId) {
    return locationDao.getLocationsByParentId(parentId);
  }
}
