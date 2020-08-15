package com.covidsquid.dev.repositories;

import com.covidsquid.dev.model.Rating;
import com.covidsquid.dev.model.RatingId;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface RatingRepository extends CrudRepository<Rating, RatingId> {
}
