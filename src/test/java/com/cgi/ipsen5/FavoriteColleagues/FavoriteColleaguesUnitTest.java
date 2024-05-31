package com.cgi.ipsen5.FavoriteColleagues;

import com.cgi.ipsen5.Dao.UserDao;
import com.cgi.ipsen5.Dto.User.UserFavoriteColleaguesDTO;
import com.cgi.ipsen5.Exception.UserNotFoundException;
import com.cgi.ipsen5.Model.User;
import com.cgi.ipsen5.Service.FavoriteColleagueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FavoriteColleaguesUnitTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private FavoriteColleagueService favoriteColleagueService;

    private UUID employeeId;
    private UUID favoritedColleagueId;
    private User employee;
    private User favoriteColleague;

    @BeforeEach
    void setUp() {
        employeeId = UUID.randomUUID();
        favoritedColleagueId = UUID.randomUUID();
        employee = new User();
        employee.setId(employeeId);
        favoriteColleague = new User();
        favoriteColleague.setId(favoritedColleagueId);
    }

    @Test
    public void testAddFavoriteColleagueSuccess() {
        when(userDao.findById(employeeId)).thenReturn(Optional.of(employee));

        favoriteColleagueService.addFavoriteColleague(employeeId, favoritedColleagueId);

        verify(userDao).addFavorite(employee, favoritedColleagueId);
    }

    @Test
    public void testGetFavoriteColleaguesFromEmployeeSuccess() {
        UUID colleagueId1 = UUID.randomUUID();
        UUID colleagueId2 = UUID.randomUUID();
        User colleague1 = new User();
        colleague1.setId(colleagueId1);
        User colleague2 = new User();
        colleague2.setId(colleagueId2);

        List<User> favoriteColleagues = new ArrayList<>();
        favoriteColleagues.add(colleague1);
        favoriteColleagues.add(colleague2);

        when(userDao.getFavoritesOfEmployee(employeeId)).thenReturn(favoriteColleagues);

        List<UserFavoriteColleaguesDTO> result = favoriteColleagueService.getFavoriteColleaguesFromEmployee(employeeId);

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(dto -> dto.getIdOfFavorite().equals(colleagueId1)));
        assertTrue(result.stream().anyMatch(dto -> dto.getIdOfFavorite().equals(colleagueId2)));
    }

    @Test
    public void testAddFavoriteColleagueUserNotFound() {
        when(userDao.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            favoriteColleagueService.addFavoriteColleague(employeeId, favoritedColleagueId);
        });

        verify(userDao, never()).addFavorite(any(User.class), any(UUID.class));
    }

    @Test
    public void testGetFavoriteColleaguesFromEmployeeUserNotFound() {
        when(userDao.getFavoritesOfEmployee(employeeId)).thenReturn(new ArrayList<>());

        List<UserFavoriteColleaguesDTO> result = favoriteColleagueService.getFavoriteColleaguesFromEmployee(employeeId);

        assertTrue(result.isEmpty());
    }
}
