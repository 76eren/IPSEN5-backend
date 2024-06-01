package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Dao.ReservationHistoryDao;
import com.cgi.ipsen5.Dto.Report.NoShowResponseDTO;
import com.cgi.ipsen5.Dto.Report.RoomOccupancyResponseDTO;
import com.cgi.ipsen5.Exception.BuildingNotFoundException;
import com.cgi.ipsen5.Model.Building;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceUnitTest {
    @Mock
    private ReservationHistoryDao reservationHistoryDao;

    @Mock
    private BuildingService buildingService;

    private ReportService SUT;

    private Building dummyBuilding;

    private UUID dummyId;

    @BeforeEach
    public void setUp(){
        this.SUT = new ReportService(reservationHistoryDao, buildingService);
        this.dummyId = UUID.randomUUID();
        this.dummyBuilding = new Building(dummyId, "testAddress", "testBuilding");
    }

    @Test
    public void should_return_room_occupancy_by_building_and_year() {
        List<RoomOccupancyResponseDTO> roomOccupancyList = new ArrayList<>();

        RoomOccupancyResponseDTO room1 = mock(RoomOccupancyResponseDTO.class);
        lenient().when(room1.getRoom()).thenReturn("room 1");
        lenient().when(room1.getNumberOfUsages()).thenReturn(8L);
        lenient().when(room1.getDate()).thenReturn(LocalDateTime.now());

        roomOccupancyList.add(room1);

        when(buildingService.getBuildingByName("testBuilding")).thenReturn(this.dummyBuilding);
        when(reservationHistoryDao.getRoomOccupancyByBuildingAndYear(dummyId, 2024)).thenReturn(roomOccupancyList);

        List<RoomOccupancyResponseDTO> result = this.SUT.getRoomOccupancyByBuildingAndYear("testBuilding", 2024);

        assertNotNull(result);
        assertEquals(roomOccupancyList, result);
    }

    @Test
    public void should_return_no_shows_by_building_and_year() {
        List<NoShowResponseDTO> noShowList = new ArrayList<>();

        NoShowResponseDTO noShow = mock(NoShowResponseDTO.class);
        lenient().when(noShow.getEmployeeName()).thenReturn("employee");
        lenient().when(noShow.getNumberOfNoShows()).thenReturn(8L);
        lenient().when(noShow.getNumberOfReservations()).thenReturn(46L);

        noShowList.add(noShow);

        when(buildingService.getBuildingByName("testBuilding")).thenReturn(this.dummyBuilding);
        when(this.reservationHistoryDao.getNoShowsByBuildingAndYear(this.dummyId, 2023)).thenReturn(noShowList);

        List<NoShowResponseDTO> result = this.SUT.getNoShowsByBuildingAndYear("testBuilding", 2023);

        assertNotNull(result);
        assertEquals(noShowList, result);
    }

    @Test
    public void should_throw_exception_when_building_not_found_roomOccupancy() {
        when(buildingService.getBuildingByName("building")).thenThrow(new BuildingNotFoundException("Building not found"));
        assertThrows(BuildingNotFoundException.class, () -> {
            this.SUT.getRoomOccupancyByBuildingAndYear("building", 2022);
        });
    }

    @Test
    public void should_throw_exception_when_building_not_found_noShow() {
        when(buildingService.getBuildingByName("building")).thenThrow(new BuildingNotFoundException("Building not found"));
        assertThrows(BuildingNotFoundException.class, () -> {
            this.SUT.getNoShowsByBuildingAndYear("building", 2022);
        });
    }
}
