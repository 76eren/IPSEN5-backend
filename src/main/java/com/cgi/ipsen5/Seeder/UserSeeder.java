package com.cgi.ipsen5.Seeder;

import com.cgi.ipsen5.Dao.UserDao;
import com.cgi.ipsen5.Model.Role;
import com.cgi.ipsen5.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class UserSeeder {
    // All of this is temporary and for testing purposes
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;

    public void seed() {
        for (User user : getUsers()) {
            this.userDao.save(user);
        }
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();

        users.add(createUser("Eren", "e.koning@cgi.com", "de Koning", "password", "0612345678"));
        users.add(createUser("Henk", "h.jong@cgi.com", "de Jong", "cgffknn15!#", "0612345678"));
        users.add(createUser("Lara", "l.croft@cgi.com", "Croft", "Archeologist22", "0612345680"));
        users.add(createUser("Mohamed", "m.abdellaoui@cgi.com", "Abdellaoui", "Autumn11", "0612345678"));
        users.add(createUser("Taylor", "t.swift@cgi.com", "Swift", "Winter10", "0612345678"));
        users.add(createUser("Martin", "m.vliet@cgi.com", "van Vliet", "snail54", "0612345687"));
        users.add(createUser("Tom", "t.nook@cgi.com", "Nook", "bell514", "0612345687"));

        return users;
    }

    public User createUser(String username, String email, String lastName, String password, String phoneNumber) {
        return User
                .builder()
                .id(UUID.randomUUID())
                .username(username)
                .role(Role.USER)
                .email(email)
                .lastName(lastName)
                .password(passwordEncoder.encode(password))
                .phoneNumber(phoneNumber)
                .build();
    }
}
