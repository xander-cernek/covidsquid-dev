package com.covidsquid.dev.apis;

import java.util.List;
import java.util.Optional;

import com.covidsquid.dev.model.Location;
import com.covidsquid.dev.model.LocationId;
import com.covidsquid.dev.repositories.LocationDao;
import com.covidsquid.dev.repositories.LocationRepository;
import com.covidsquid.dev.services.LocationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/location")
@RestController
@CrossOrigin
public class LocationController {

  @Autowired
  LocationRepository locationRepository;

  @Autowired
  LocationDao locationDao;

  @Autowired
  LocationService locationService;

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
  public void rateLocation(@RequestBody Location location) {
    LocationId id = LocationId.builder()
    .parentId(location.getParentId())
    .id(location.getId())
    .build();
    Optional<Location> result = locationRepository.findById(id);
    if (result.isPresent()) {
      Location payload = result.get();
      payload = locationService.rate(payload, location);
      locationRepository.save(payload);
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
  public List<Location> getAllLocationsByParentId(@RequestParam String parentId) {
    return locationDao.getLocationsByParentId(parentId);
  }


}
