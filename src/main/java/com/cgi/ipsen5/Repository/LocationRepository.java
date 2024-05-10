package com.cgi.ipsen5.Repository;

import com.cgi.ipsen5.Model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {
}
