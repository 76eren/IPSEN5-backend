package com.cgi.ipsen5.Dao;

import com.cgi.ipsen5.Dto.User.UserEditDTO;
import com.cgi.ipsen5.Dto.User.UserFavoriteColleagesResponseDTO;
import com.cgi.ipsen5.Dto.User.UserResponseDTO;
import com.cgi.ipsen5.Mapper.UserMapper;
import com.cgi.ipsen5.Model.Wing;
import com.cgi.ipsen5.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.cgi.ipsen5.Model.User;

import java.util.*;


@Component
@RequiredArgsConstructor
public class UserDao implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponseDTO> findAllDto() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::fromEntity)
                .toList();
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<Set<User>> findAllByUsername(String username) {
        return userRepository.findAllByUsername(username);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow();
    }

    public User updateUser(UUID id, UserEditDTO userEditDTO) {
        Optional<User> foundUser = findById(id);
        if (foundUser.isPresent()) {
            User user = foundUser.get();

            if (userEditDTO.getUsername() != null) {
                user.setUsername(userEditDTO.getUsername());
            }

            if (userEditDTO.getRole() != null) {
                user.setRole(userEditDTO.getRole());
            }

            return save(user);
        }

        return null;
    }

    public void addFavorite(User employee, UUID favoriteCollegueId) {
        List<User> currentFavorites = employee.getFavoriteCollegues();

        boolean alreadyFavorited = currentFavorites.stream()
                .anyMatch(user -> user.getId().equals(favoriteCollegueId));

        if (!alreadyFavorited) {
            Optional<User> favoriteColleague = findById(favoriteCollegueId);
            if (!favoriteColleague.isPresent()) {
                throw new UsernameNotFoundException("Favorite colleague not found with id: " + favoriteCollegueId);
            }
            currentFavorites.add(favoriteColleague.get());
            employee.setFavoriteCollegues(currentFavorites);
            save(employee);

        }
    }

    public void removeFavorite(User employee, UUID favoriteCollegueId){
        List<User> currentFavorites = employee.getFavoriteCollegues();
        Optional<User> foundUser = this.findById(favoriteCollegueId);
        if (!foundUser.isPresent()) {
            return;
        }
        User favoriteToBeDeleted = foundUser.get();
        currentFavorites.remove(favoriteToBeDeleted);
        employee.setFavoriteCollegues(currentFavorites);
        save(employee);
    }

    public List<UserFavoriteColleagesResponseDTO> getFavoritesOfUser(UUID employeeId) {
        Optional<User> foundUser = this.findById(employeeId);
        List<UserFavoriteColleagesResponseDTO> favorites = new ArrayList<>();
        if (foundUser.isEmpty()) {
            return favorites;
        }
        User user = foundUser.get();
        List<User> favoriteUsers = user.getFavoriteCollegues();
        for (User favoriteUser : favoriteUsers) {
            favorites.add(mapUserToUserFavoriteColleagesResponseDTO(favoriteUser));
        }
        return favorites;
    }

    public Wing getStandardLocation(UUID userId) {
        Optional<User> foundUser = this.findById(userId);
        if (!foundUser.isPresent()) {
            throw new UsernameNotFoundException("No user was found with id" + userId);
        }
        User user = foundUser.get();
        return user.getStandardLocation();
    }

    public void setStandardLocation(UUID userId, Wing standardLocation) {
        Optional<User> foundUser = this.findById(userId);
        if (!foundUser.isPresent()) {
            throw new UsernameNotFoundException("No user was found with id" + userId);
        }
        User user = foundUser.get();

        user.setStandardLocation(standardLocation);
        save(user);
    }

    public UserFavoriteColleagesResponseDTO mapUserToUserFavoriteColleagesResponseDTO(User user) {
        UserFavoriteColleagesResponseDTO dto = new UserFavoriteColleagesResponseDTO();
        dto.setId(user.getId().toString());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        if (user.getStandardLocation() != null) {
            dto.setStandardLocation(user.getStandardLocation().toString());
        } else {
            dto.setStandardLocation("");
        }
        return dto;
    }
}
