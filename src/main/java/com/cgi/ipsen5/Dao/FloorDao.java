package com.cgi.ipsen5.Dao;

import com.cgi.ipsen5.Model.Floor;
import com.cgi.ipsen5.Repository.FloorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FloorDao {
    private final FloorRepository floorRepository;

    public void save(Floor floor) {
        floorRepository.save(floor);
    }
}
