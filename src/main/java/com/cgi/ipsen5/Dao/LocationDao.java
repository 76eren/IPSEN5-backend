package com.cgi.ipsen5.Dao;


import com.cgi.ipsen5.Dto.Reserve.ReserveCreateDTO;
import com.cgi.ipsen5.Repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationDao {
    private final LocationRepository locationRepository;

    public void create(ReserveCreateDTO reserveCreateDTO) {

    }
}
