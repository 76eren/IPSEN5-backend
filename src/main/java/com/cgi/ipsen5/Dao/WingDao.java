package com.cgi.ipsen5.Dao;

import com.cgi.ipsen5.Model.Floor;
import com.cgi.ipsen5.Model.Wing;
import com.cgi.ipsen5.Repository.WingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class WingDao {
    private final WingRepository wingRepository;

    public void save(Wing wing) {
        this.wingRepository.save(wing);
    }

    public ArrayList<Wing> findAll() {
        return (ArrayList<Wing>) this.wingRepository.findAll();
    }
}
