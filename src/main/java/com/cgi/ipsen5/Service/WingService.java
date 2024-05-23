package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Dao.ReservationDao;
import com.cgi.ipsen5.Dao.WingDao;
import com.cgi.ipsen5.Model.Reservation;
import com.cgi.ipsen5.Model.Wing;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class WingService {
    private final WingDao wingDao;
    private final ReservationDao reservationDao;

    public WingService(WingDao wingDao, ReservationDao reservationDao) {
        this.wingDao = wingDao;
        this.reservationDao = reservationDao;
    }

//    public boolean checkWingWorkplaceAvailability(UUID wingId,
//                                                  LocalDateTime startDateTime,
//                                                  LocalDateTime endDateTime) {
//        Wing wing = this.wingDao.findWingById(wingId);
//        if (wing == null) {
//            return false;
//        }
//        int totalWorkplaces = wing.getTotalWorkplaces();
//        List<Reservation> reservationsBetweenStartAndEndDateTime = this.reservationDao
//                .findReservationsBetween(wing.getId(), startDateTime, endDateTime);
//        totalWorkplaces -= reservationsBetweenStartAndEndDateTime.size();
//        return totalWorkplaces > 0;
//    }
}
