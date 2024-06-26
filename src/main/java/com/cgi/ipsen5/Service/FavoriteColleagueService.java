package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Dao.UserDao;
import com.cgi.ipsen5.Dto.User.UserFavoriteColleagesResponseDTO;
import com.cgi.ipsen5.Dto.User.UserFavoriteColleaguesDTO;
import com.cgi.ipsen5.Exception.UserNotFoundException;
import com.cgi.ipsen5.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteColleagueService {
    private final UserDao userDao;
    public void addFavoriteColleague(UUID employeeId, UUID favoritedColleagueId){
        Optional<User> optionalUser = this.userDao.findById(employeeId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        User employee = optionalUser.get();
        userDao.addFavorite(employee, favoritedColleagueId);
    }


    public List<UserFavoriteColleagesResponseDTO> getFavoriteColleaguesFromEmployee(UUID employeeId){
        return userDao.getFavoritesOfUser(employeeId);
    }

    public void removeColleagueFromFavorites(UUID employeeId, UUID favoritedColleagueId){
        Optional<User> optionalUser = this.userDao.findById(employeeId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        User employee = optionalUser.get();
        userDao.removeFavorite(employee, favoritedColleagueId);
    }
}
