package com.cgi.ipsen5.Repository;

import com.cgi.ipsen5.Model.Building;
import com.cgi.ipsen5.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BuildingRepository extends JpaRepository<Building, UUID> {
    Optional<Building> findBuildingByName(String buildingName);
}
