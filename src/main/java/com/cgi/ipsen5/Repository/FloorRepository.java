package com.cgi.ipsen5.Repository;

import com.cgi.ipsen5.Model.Floor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FloorRepository extends JpaRepository<Floor, UUID> {
    Optional<List<Floor>> findAllByBuildingId(UUID buildingId);
}
