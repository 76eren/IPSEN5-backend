package com.cgi.ipsen5.Dao;

import com.cgi.ipsen5.Dto.User.UserEditDTO;
import com.cgi.ipsen5.Dto.User.UserResponseDTO;
import com.cgi.ipsen5.Mapper.UserMapper;
import com.cgi.ipsen5.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.cgi.ipsen5.Model.User;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


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
        if(foundUser.isPresent()) {
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
}
