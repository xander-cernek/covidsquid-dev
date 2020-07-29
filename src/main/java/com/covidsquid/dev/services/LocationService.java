package com.covidsquid.dev.services;


import java.util.List;

import com.covidsquid.dev.model.Location;

import org.springframework.stereotype.Service;

@Service
public class LocationService {

    public Location update(Location oldLocation, Location newLocation) {
    if (newLocation.getAddress() != null) { oldLocation.setAddress(newLocation.getAddress()); }
    if (newLocation.getCleanRating() != null) { oldLocation.setCleanRating(newLocation.getCleanRating()); }
    if (newLocation.getCrowdRating() != null) { oldLocation.setCrowdRating(newLocation.getCrowdRating()); }
    if (newLocation.getLastFiveRatings() != null) { oldLocation.setLastFiveRatings(newLocation.getLastFiveRatings()); }
    if (newLocation.getLastUpdated() != null) { oldLocation.setLastUpdated(newLocation.getLastUpdated()); }
    if (newLocation.getMaskRating() != null) { oldLocation.setMaskRating(newLocation.getMaskRating()); }
    if (newLocation.getNumRatings() != null) { oldLocation.setNumRatings(newLocation.getNumRatings()); }
    if (newLocation.getPictureUrl() != null) { oldLocation.setPictureUrl(newLocation.getPictureUrl()); }
    if (newLocation.getTotalRating() != null) { oldLocation.setTotalRating(newLocation.getTotalRating()); }
    return oldLocation;
  }

  public Location rate(Location oldLocation, Location newLocation) {
    if (newLocation.getCleanRating() != null) { oldLocation.setCleanRating(newLocation.getCleanRating()); }
    if (newLocation.getCrowdRating() != null) { oldLocation.setCrowdRating(newLocation.getCrowdRating()); }
    if (newLocation.getLastFiveRatings() != null) { oldLocation.setLastFiveRatings(newLocation.getLastFiveRatings()); }
    if (newLocation.getMaskRating() != null) { oldLocation.setMaskRating(newLocation.getMaskRating()); }
    if (newLocation.getNumRatings() != null) { oldLocation.setNumRatings(newLocation.getNumRatings()); }
    if (newLocation.getTotalRating() != null) { oldLocation.setTotalRating(newLocation.getTotalRating()); }
    //Increment num ratings TODO: make num ratings string
    Integer numRatings = Integer.parseInt(newLocation.getNumRatings());
    numRatings += 1;
    newLocation.setNumRatings(String.valueOf(numRatings));
    //Roll last 5 ratings
    List<String> ratings = newLocation.getLastFiveRatings();
    if (!ratings.isEmpty()) {
      if (ratings.size() == 5) {
        ratings.remove(0);
      }
      ratings.add(newLocation.getTotalRating());
    }
    return newLocation;
  }
}
