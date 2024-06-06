package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Dao.ReservationDao;
import com.cgi.ipsen5.Dao.WingDao;
import com.cgi.ipsen5.Exception.WingNotFoundException;
import com.cgi.ipsen5.Model.Wing;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WingService {
    private final WingDao wingDao;

    public WingService(WingDao wingDao) {
        this.wingDao = wingDao;
    }

    public void wingExistsById(UUID wingId) {
        if (!this.wingDao.existsById(wingId)) {
            throw new WingNotFoundException("Wing not found");
        }
    }

    public Wing getWingById(UUID wingId) {
        Wing wing = this.wingDao.findWingById(wingId);

        if(wing == null){
            throw new WingNotFoundException("Wing not found");
        }

        return wing;
    }
}
