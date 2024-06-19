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

        users.add(createUser("Eren", "e.koning@cgi.com", "de Koning", "Password#1234", "0612345678"));
        users.add(createUser("Henk", "h.jong@cgi.com", "de Jong", "Cgffknn15!#", "0612345678"));
        users.add(createUser("Lara", "l.croft@cgi.com", "Croft", "Archeologist22!", "0612345680"));
        users.add(createUser("Mohamed", "m.abdellaoui@cgi.com", "Abdellaoui", "Autumn11!", "0612345678"));
        users.add(createUser("Taylor", "t.swift@cgi.com", "Swift", "Winter10!", "0612345678"));
        users.add(createUser("Martin", "m.vliet@cgi.com", "van Vliet", "snail54!", "0612345687"));
        users.add(createUser("Tom", "t.nook@cgi.com", "Nook", "bell514!", "0612345687"));
        users.add(createUser("s1136644@student.hsleiden.nl", "s1136644@student.hsleiden.nl", "Smits", "Password#1234", "0620255944"));
        users.add(createAdmin("admin", "admin@cgi.com", "admin", "Admin#1234", "0612345678"));
        users.add(createUser("Maya", "m.bee@cgi.com", "Bee", "Maya#1234", "0612345678"));
        users.add(createUser("Jill", "j.valentine@cgi.com", "Valentine", "Apple#1234", "0612345678"));
        users.add(createUser("Jin", "j.sakai@cgi.com", "Sakai", "Sleep#1234", "0612345678"));
        users.add(createUser("Elle", "e.white@cgi.com", "White", "Password#1234", "0612345678"));
        users.add(createUser("Liam", "l.Hemsworth@cgi.com", "Hemsworth", "Password#1234", "0612345678"));
        users.add(createUser("Simon", "s.says@cgi.com", "Says", "Password#1234", "0612345678"));
        users.add(createUser("Alice", "alice@cgi.com", "Johnson", "Password#1234", "0612345678"));
        users.add(createUser("Bob", "bob@cgi.com", "Smith", "Password#1234", "0612345678"));
        users.add(createUser("Charlie", "charlie@cgi.com", "Brown", "Password#1234", "0612345678"));
        users.add(createUser("David", "david@cgi.com", "Davis", "Password#1234", "0612345678"));
        users.add(createUser("Eve", "eve@cgi.com", "Evans", "Password#1234", "0612345678"));
        users.add(createUser("Frank", "frank@cgi.com", "Franklin", "Password#1234", "0612345678"));
        users.add(createUser("Grace", "grace@cgi.com", "Griffin", "Password#1234", "0612345678"));
        users.add(createUser("Helen", "helen@cgi.com", "Hudson", "Password#1234", "0612345678"));
        users.add(createUser("Ivan", "ivan@cgi.com", "Iverson", "Password#1234", "0612345678"));
        users.add(createUser("Jack", "jack@cgi.com", "Jackson", "Password#1234", "0612345678"));
        users.add(createUser("Karen", "karen@cgi.com", "King", "Password#1234", "0612345678"));
        users.add(createUser("Leo", "leo@cgi.com", "Lion", "Password#1234", "0612345678"));
        users.add(createUser("Mia", "mia@cgi.com", "Miller", "Password#1234", "0612345678"));
        users.add(createUser("Nick", "nick@cgi.com", "Nelson", "Password#1234", "0612345678"));
        users.add(createUser("Olivia", "olivia@cgi.com", "Olsen", "Password#1234", "0612345678"));
        users.add(createUser("Paul", "paul@cgi.com", "Peterson", "Password#1234", "0612345678"));
        users.add(createUser("Quincy", "quincy@cgi.com", "Quinn", "Password#1234", "0612345678"));
        users.add(createUser("Rachel", "rachel@cgi.com", "Roberts", "Password#1234", "0612345678"));
        users.add(createUser("Sam", "sam@cgi.com", "Samson", "Password#1234", "0612345678"));
        users.add(createUser("Tina", "tina@cgi.com", "Thompson", "Password#1234", "0612345678"));
        users.add(createUser("Amber", "s1143282@student.hsleiden.nl", "Test", "Test#1234", "0612345678"));



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
