package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Dao.BuildingDao;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingDao buildingDao;
}