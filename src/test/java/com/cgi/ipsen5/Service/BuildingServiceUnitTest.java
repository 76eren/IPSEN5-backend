package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Dao.BuildingDao;
import com.cgi.ipsen5.Exception.BuildingNotFoundException;
import com.cgi.ipsen5.Model.Building;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BuildingServiceUnitTest {

    @Mock
    private BuildingDao buildingDao;

    private BuildingService SUT;

    private Building dummyBuilding;

    @BeforeEach
    public void setUp() {
        this.SUT = new BuildingService(buildingDao);
        this.dummyBuilding = new Building(UUID.randomUUID(),"testAddress", "testBuilding");
    }

    @Test
    public void should_return_building_when_found_by_name() {
        when(this.buildingDao.getBuildingByName("testBuilding")).thenReturn(Optional.of(dummyBuilding));
        Building result = this.SUT.getBuildingByName("testBuilding");

        assertNotNull(result);
        assertEquals(dummyBuilding, result);
    }

    @Test
    public void should_throw_exception_when_building_not_found() {
        when(this.buildingDao.getBuildingByName("building")).thenReturn(Optional.empty());
        assertThrows(BuildingNotFoundException.class, () -> {
            this.SUT.getBuildingByName("building");
        });
    }
}
