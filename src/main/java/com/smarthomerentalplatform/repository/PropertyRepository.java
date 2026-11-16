package com.smarthomerentalplatform.repository;

import com.smarthomerentalplatform.repository.entity.Property;
import com.smarthomerentalplatform.repository.status.PropertyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    Page<Property> findByStatus(PropertyStatus status, Pageable pageable);

    Page<Property> findByStatusAndDistrictId(PropertyStatus status, Long districtId, Pageable pageable);

    Page<Property> findByOwnerId(Long ownerId, Pageable pageable);

    Page<Property> findByOwnerIdAndStatus(Long ownerId, PropertyStatus status, Pageable pageable);
}
