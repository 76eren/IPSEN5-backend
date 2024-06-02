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
        users.add(createAdmin("admin", "admin@cgi.com", "admin", "admin", "0612345678"));
        users.add(createUser("Maya", "m.bee@cgi.com", "Bee", "Maya", "0612345678"));
        users.add(createUser("Jill", "j.valentine@cgi.com", "Valentine", "apple", "0612345678"));
        users.add(createUser("Jin", "j.sakai@cgi.com", "Sakai", "sleep", "0612345678"));
        users.add(createUser("Elle", "e.white@cgi.com", "White", "password", "0612345678"));
        users.add(createUser("Liam", "l.Hemsworth@cgi.com", "Hemsworth", "password", "0612345678"));
        users.add(createUser("Simon", "s.says@cgi.com", "Says", "password", "0612345678"));
        users.add(createUser("Alice", "alice@cgi.com", "Johnson", "password1", "0612345678"));
        users.add(createUser("Bob", "bob@cgi.com", "Smith", "password2", "0612345678"));
        users.add(createUser("Charlie", "charlie@cgi.com", "Brown", "password3", "0612345678"));
        users.add(createUser("David", "david@cgi.com", "Davis", "password4", "0612345678"));
        users.add(createUser("Eve", "eve@cgi.com", "Evans", "password5", "0612345678"));
        users.add(createUser("Frank", "frank@cgi.com", "Franklin", "password6", "0612345678"));
        users.add(createUser("Grace", "grace@cgi.com", "Griffin", "password7", "0612345678"));
        users.add(createUser("Helen", "helen@cgi.com", "Hudson", "password8", "0612345678"));
        users.add(createUser("Ivan", "ivan@cgi.com", "Iverson", "password9", "0612345678"));
        users.add(createUser("Jack", "jack@cgi.com", "Jackson", "password10", "0612345678"));
        users.add(createUser("Karen", "karen@cgi.com", "King", "password11", "0612345678"));
        users.add(createUser("Leo", "leo@cgi.com", "Lion", "password12", "0612345678"));
        users.add(createUser("Mia", "mia@cgi.com", "Miller", "password13", "0612345678"));
        users.add(createUser("Nick", "nick@cgi.com", "Nelson", "password14", "0612345678"));
        users.add(createUser("Olivia", "olivia@cgi.com", "Olsen", "password15", "0612345678"));
        users.add(createUser("Paul", "paul@cgi.com", "Peterson", "password16", "0612345678"));
        users.add(createUser("Quincy", "quincy@cgi.com", "Quinn", "password17", "0612345678"));
        users.add(createUser("Rachel", "rachel@cgi.com", "Roberts", "password18", "0612345678"));
        users.add(createUser("Sam", "sam@cgi.com", "Samson", "password19", "0612345678"));
        users.add(createUser("Tina", "tina@cgi.com", "Thompson", "password20", "0612345678"));



        return users;
    }

    public User createUser(String firstname, String email, String lastName, String password, String phoneNumber) {
        return User
                .builder()
                .username(email)
                .role(Role.USER)
                .lastName(lastName)
                .firstName(firstname)
                .password(passwordEncoder.encode(password))
                .phoneNumber(phoneNumber)
                .build();
    }


    public User createAdmin(String firstname, String email, String lastName, String password, String phoneNumber) {
        return User
                .builder()
                .username(email)
                .role(Role.ADMIN)
                .lastName(lastName)
                .firstName(firstname)
                .password(passwordEncoder.encode(password))
                .phoneNumber(phoneNumber)
                .build();
    }
}
