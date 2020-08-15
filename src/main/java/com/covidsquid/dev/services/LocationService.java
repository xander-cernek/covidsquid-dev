package com.covidsquid.dev.services;

import java.util.List;

import com.covidsquid.dev.model.Location;

import org.springframework.stereotype.Service;

@Service
public class LocationService {

    public Location update(Location oldLocation, Location newLocation) {
    if (newLocation.getName() != null) { oldLocation.setName(newLocation.getName()); }
    if (newLocation.getAddress() != null) { oldLocation.setAddress(newLocation.getAddress()); }
    if (newLocation.getCleanRating() != null) { oldLocation.setCleanRating(newLocation.getCleanRating()); }
    if (newLocation.getCrowdRating() != null) { oldLocation.setCrowdRating(newLocation.getCrowdRating()); }
    if (newLocation.getLastFiveRatings() != null) { oldLocation.setLastFiveRatings(newLocation.getLastFiveRatings()); }
    if (newLocation.getLastUpdated() != null) { oldLocation.setLastUpdated(newLocation.getLastUpdated()); }
    if (newLocation.getMaskRating() != null) { oldLocation.setMaskRating(newLocation.getMaskRating()); }
    if (newLocation.getNumRatings() != null) { oldLocation.setNumRatings(newLocation.getNumRatings()); }
    if (newLocation.getPictureUrl() != null) { oldLocation.setPictureUrl(newLocation.getPictureUrl()); }
    if (newLocation.getTotalRating() != null) { oldLocation.setTotalRating(newLocation.getTotalRating()); }
    if (newLocation.getRollingAverageRating() != null) { oldLocation.setRollingAverageRating(newLocation.getRollingAverageRating()); }
    return oldLocation;
  }

  public Location rate(Location oldLocation, Location newLocation) {
    if (newLocation.getCleanRating() != null) { oldLocation.setCleanRating(newLocation.getCleanRating()); }
    if (newLocation.getCrowdRating() != null) { oldLocation.setCrowdRating(newLocation.getCrowdRating()); }
    if (newLocation.getLastFiveRatings() != null) { oldLocation.setLastFiveRatings(newLocation.getLastFiveRatings()); }
    if (newLocation.getLastUpdated() != null) { oldLocation.setLastUpdated(newLocation.getLastUpdated()); }
    if (newLocation.getMaskRating() != null) { oldLocation.setMaskRating(newLocation.getMaskRating()); }
    if (newLocation.getNumRatings() != null) { oldLocation.setNumRatings(newLocation.getNumRatings()); }
    if (newLocation.getTotalRating() != null) { oldLocation.setTotalRating(newLocation.getTotalRating()); }
    //Increment num ratings TODO: make num ratings string
    Integer numRatings = Integer.parseInt(newLocation.getNumRatings());
    numRatings += 1;
    oldLocation.setNumRatings(String.valueOf(numRatings));
    //Roll last 5 ratings
    List<String> ratings = newLocation.getLastFiveRatings();
    if (ratings.size() == 5) {
      ratings.remove(0);
    }
    ratings.add(newLocation.getTotalRating());
    oldLocation.setLastFiveRatings(ratings);
    oldLocation.setRollingAverageRating(averageList(ratings));
    return oldLocation;
  }

  private String averageList(List<String> list) {
    float average = 0;
    if (list.isEmpty()) {
      return "0";
    }
    for (String value : list) {
      average += Integer.parseInt(value);
    }
    return String.valueOf(average / list.size());
  }
}
