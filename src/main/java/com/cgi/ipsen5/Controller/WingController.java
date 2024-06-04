package com.cgi.ipsen5.Controller;

import com.cgi.ipsen5.Dao.WingDao;
import com.cgi.ipsen5.Exception.WingNotFoundException;
import com.cgi.ipsen5.Model.ApiResponse;
import com.cgi.ipsen5.Model.Building;
import com.cgi.ipsen5.Model.Wing;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/wing")
@RequiredArgsConstructor
public class WingController {
    private final WingDao wingDao;

    @GetMapping
    public ApiResponse<List<Wing>> getAllWings() {
        return new ApiResponse<>(this.wingDao.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ApiResponse<Wing> getWingById(@PathVariable UUID id) {
        return new ApiResponse<>(this.wingDao.findWingById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/floor/{id}")
    public ApiResponse<List<Wing>> getWingByFloorNumber(@PathVariable UUID id) {
        return new ApiResponse<>(this.wingDao.findAllByFloorNumber(id), HttpStatus.OK);
    }

    @ExceptionHandler(WingNotFoundException.class)
    public ApiResponse<String> handleException(Exception e) {
        return new ApiResponse<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
