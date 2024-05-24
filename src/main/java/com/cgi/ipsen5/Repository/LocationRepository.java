package com.cgi.ipsen5.Repository;

import com.cgi.ipsen5.Model.Floor;
import com.cgi.ipsen5.Model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {
    List<Location> findAllByWingId(UUID wingId);
    List<Floor> findAllByWing_FloorId(UUID id);
}
