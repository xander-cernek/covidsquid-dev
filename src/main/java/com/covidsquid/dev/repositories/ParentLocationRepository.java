package com.covidsquid.dev.repositories;

import com.covidsquid.dev.model.ParentLocation;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface ParentLocationRepository extends CrudRepository<ParentLocation, String> {
}
