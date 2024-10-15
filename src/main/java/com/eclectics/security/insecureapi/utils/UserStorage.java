package com.eclectics.security.insecureapi.utils;

import com.eclectics.security.insecureapi.models.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class UserStorage {
    private final List<User> users = new ArrayList<>();

    public UserStorage() {
        User user1 = new User();
        user1.setUsername("admin_one");
        user1.setUserid(UUID.randomUUID().toString());
        user1.setEmail("admin_one@email.com");
        user1.setPassword("admin_one");

        User user2 = new User();
        user2.setUsername("admin_two");
        user2.setUserid(UUID.randomUUID().toString());
        user2.setEmail("admin_two@email.com");
        user2.setPassword("admin_two");

        User user3 = new User();
        user3.setUsername("admin_three");
        user3.setUserid(UUID.randomUUID().toString());
        user3.setEmail("admin_three@email.com");
        user3.setPassword("admin_three");

        User user4 = new User();
        user4.setUsername("admin_four");
        user4.setUserid(UUID.randomUUID().toString());
        user4.setEmail("admin_four@email.com");
        user4.setPassword("admin_four");

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
