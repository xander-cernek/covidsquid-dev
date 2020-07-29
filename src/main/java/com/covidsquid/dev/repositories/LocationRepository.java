package com.covidsquid.dev.repositories;

import com.covidsquid.dev.model.Location;
import com.covidsquid.dev.model.LocationId;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface LocationRepository extends CrudRepository<Location, LocationId> {
}
