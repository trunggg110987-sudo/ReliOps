package com.smarthomerentalplatform.repository;

import com.smarthomerentalplatform.repository.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {
    List<Amenity> findByActiveTrue();
}
