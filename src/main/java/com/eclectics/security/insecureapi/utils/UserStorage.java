package com.eclectics.security.insecureapi.utils;

import com.eclectics.security.insecureapi.models.User;
import lombok.Getter;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class UserStorage {
    @Getter
    private final List<User> users = new ArrayList<>();
    private final List<JSONObject> userDuplicate = new ArrayList<>();

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

        User user5 = new User();
        user5.setUsername("admin");
        user5.setUserid(UUID.randomUUID().toString());
        user5.setEmail("admin@email.com");
        user5.setPassword("admin");

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);

        JSONObject userObject1 = new JSONObject();
        userObject1.put("userid", user1.getUserid());
        userObject1.put("username", user1.getUsername());
        userObject1.put("email", user1.getEmail());
        userObject1.put("password", user1.getPassword());
        userDuplicate.add(userObject1);

        JSONObject userObject2 = new JSONObject();
        userObject2.put("userid", user2.getUserid());
        userObject2.put("username", user2.getUsername());
        userObject2.put("email", user2.getEmail());
        userObject2.put("password", user2.getPassword());
        userDuplicate.add(userObject2);

        JSONObject userObject3 = new JSONObject();
        userObject3.put("userid", user3.getUserid());
        userObject3.put("username", user3.getUsername());
        userObject3.put("email", user3.getEmail());
        userObject3.put("password", user3.getPassword());
        userDuplicate.add(userObject3);

        JSONObject userObject4 = new JSONObject();
        userObject4.put("userid", user4.getUserid());
        userObject4.put("username", user4.getUsername());
        userObject4.put("email", user4.getEmail());
        userObject4.put("password", user4.getPassword());
        userDuplicate.add(userObject4);
    }

    public User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public JSONObject insecureGetUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return new JSONObject(user);
            }
        }
        return null;
    }

    public List<JSONObject> getAllUsers() {
        return userDuplicate;
    }

    public User addUser(User user) {
        users.add(user);
        return user;
    }

    public JSONObject insecureAddUser(JSONObject user) {
        userDuplicate.add(user);
        return user;
    }
}
