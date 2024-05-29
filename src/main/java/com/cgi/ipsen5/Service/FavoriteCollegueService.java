package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Dao.UserDao;
import com.cgi.ipsen5.Exception.UserNotFoundException;
import com.cgi.ipsen5.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavoriteCollegueService {
    private final UserDao userDao;
    public void addFavoriteCollegue(UUID employeeId, UUID favoritedCollegueId){
        Optional<User> optionalUser = this.userDao.findById(employeeId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        User employee = optionalUser.get();
        userDao.addFavorite(employee, favoritedCollegueId);
    }
}
