package com.cgi.ipsen5.Controller;

import com.cgi.ipsen5.Dao.FloorDao;
import com.cgi.ipsen5.Model.ApiResponse;
import com.cgi.ipsen5.Model.Building;
import com.cgi.ipsen5.Model.Floor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/floor")
@RequiredArgsConstructor
public class FloorController {
    private final FloorDao floorDao;

    @GetMapping
    public ApiResponse<List<Floor>> get() {
        return new ApiResponse<>(this.floorDao.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ApiResponse<Floor> getFloorById(@PathVariable UUID id) {
        return new ApiResponse<>(this.floorDao.getById(id), HttpStatus.OK);
    }
}
