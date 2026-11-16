package com.smarthomerentalplatform.repository;

import com.smarthomerentalplatform.repository.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Long> {
    List<District> findByProvinceIdAndActiveTrue(Long provinceId);
}
