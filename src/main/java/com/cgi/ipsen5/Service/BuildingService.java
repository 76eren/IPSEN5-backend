package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Dao.BuildingDao;
import com.cgi.ipsen5.Dto.Reserve.Building.BuildingDTO;
import com.cgi.ipsen5.Model.Building;
import com.cgi.ipsen5.Mapper.BuildingMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingDao buildingDao;
}