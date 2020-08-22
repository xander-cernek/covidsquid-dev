package com.covidsquid.dev.services;

import java.util.List;
import java.util.Optional;

import com.covidsquid.dev.model.Location;
import com.covidsquid.dev.model.Rating;
import com.covidsquid.dev.model.RatingId;
import com.covidsquid.dev.model.RatingStatisticsResponse;
import com.covidsquid.dev.repositories.RatingDao;
import com.covidsquid.dev.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

  @Autowired
  RatingRepository ratingRepository;

  @Autowired
  RatingDao ratingDao;

  public void createRating(Rating rating) {
    ratingRepository.save(rating);
  }

  public Optional<Rating> getRating(String parentId, String userId) {
    RatingId ids = RatingId.builder().parentId(parentId).userId(userId).build();
    return ratingRepository.findById(ids);
  }

  public List<Rating> getAllRatingsByParentId(String parentId) {
    return ratingDao.getRatingsByParentId(parentId);
  }

  public Rating getRatingFromLocation(Location location, String userId) {
    return Rating.builder()
            .locationId(location.getId())
            .locationName(location.getName())
            .rating(location.getTotalRating())
            .ratingId(RatingId.builder()
              .parentId(location.getParentId())
              .userId(userId)
              .build())
            .build();
  }

  public RatingStatisticsResponse getRatingStatsByParentId(String parentId) {
    List<Rating> results = getAllRatingsByParentId(parentId);
    int numRatings = results.size();
    int ratingSum = 0;
    for (Rating rating : results) {
      ratingSum += Integer.parseInt(rating.getRating());
    }
    double averageRating = ratingSum / numRatings;
    return RatingStatisticsResponse.builder()
      .numRatings(String.valueOf(numRatings))
      .averageRating(String.valueOf(averageRating))
      .build();
  }
}
