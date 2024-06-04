package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Dao.UserDao;
import com.cgi.ipsen5.Dao.WingDao;
import com.cgi.ipsen5.Model.Wing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class StandardLocationService {
    private final UserDao userDao;
    private final WingDao wingDao;

    public Wing getStandardLocation(UUID userId) {
        return this.userDao.getStandardLocation(userId);
    }

    public void setStandardLocation(UUID userId, UUID wingId) {
        Wing standardLocation = this.wingDao.findWingById(wingId);
        this.userDao.setStandardLocation(userId, standardLocation);
    }
}
