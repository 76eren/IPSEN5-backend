package com.cgi.ipsen5.Repository;

import com.cgi.ipsen5.Model.ReservationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationHistoryRepository extends JpaRepository<ReservationHistory, Long> {
}
