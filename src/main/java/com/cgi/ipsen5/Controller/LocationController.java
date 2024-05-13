package com.cgi.ipsen5.Controller;

import com.cgi.ipsen5.Dao.LocationDao;
import com.cgi.ipsen5.Dto.Reserve.ReserveCreateDTO;
import com.cgi.ipsen5.Model.ApiResponse;
import com.cgi.ipsen5.Model.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/reserve")
@RequiredArgsConstructor
public class LocationController {
    private final LocationDao reserveDao;

    @PostMapping("/{id}")
    public ApiResponse<?> reserve(@RequestBody ReserveCreateDTO reserveCreateDTO) {
        Location location = reserveDao.create(reserveCreateDTO);

        return new ApiResponse<>(location); // TODO: Make this return a DTO instead of the entity
    }


}

