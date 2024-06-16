package com.cgi.ipsen5.Dao;

import com.cgi.ipsen5.Model.User;
import com.cgi.ipsen5.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDaoTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDao userDao;

    @Test
    public void testGetStandardLocation_UserNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userDao.getStandardLocation(userId));
    }

    @Test
    public void testSetStandardLocation_NullWing() {
        UUID userId = UUID.randomUUID();
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userDao.setStandardLocation(userId, null);

        assertNull(user.getStandardLocation());
        verify(userRepository, times(1)).save(user);
    }
}