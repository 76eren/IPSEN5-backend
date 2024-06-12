package com.cgi.ipsen5.FavoriteColleagues;

import com.cgi.ipsen5.Dao.UserDao;
import com.cgi.ipsen5.Dto.User.UserFavoriteColleagesResponseDTO;
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
        UserFavoriteColleagesResponseDTO colleague1 = new UserFavoriteColleagesResponseDTO();
        colleague1.setId(colleagueId1.toString());
        UserFavoriteColleagesResponseDTO colleague2 = new UserFavoriteColleagesResponseDTO();
        colleague2.setId(colleagueId2.toString());

        List<UserFavoriteColleagesResponseDTO> favoriteColleagues = new ArrayList<>();
        favoriteColleagues.add(colleague1);
        favoriteColleagues.add(colleague2);

        when(userDao.getFavoritesOfUser(employeeId)).thenReturn(favoriteColleagues);

        List<UserFavoriteColleagesResponseDTO> result = favoriteColleagueService.getFavoriteColleaguesFromEmployee(employeeId);

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(user -> user.getId().equals(colleagueId1.toString())));
        assertTrue(result.stream().anyMatch(user -> user.getId().equals(colleagueId2.toString())));
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
        when(userDao.getFavoritesOfUser(employeeId)).thenReturn(new ArrayList<>());

        List<UserFavoriteColleagesResponseDTO> result = favoriteColleagueService.getFavoriteColleaguesFromEmployee(employeeId);

        assertTrue(result.isEmpty());
    }
}
