package com.d5assignment3506.localmessagingsystem.service;

import com.d5assignment3506.localmessagingsystem.entity.User;
import com.d5assignment3506.localmessagingsystem.repo.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User editUser(String username, String firstName, String lastName, String email, String title) throws UserNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setTitle(title);
            return userRepository.save(user);
        } else {
            throw new UserNotFoundException("User not found with username " + username);
        }
    }

    public User createUser(String firstName, String lastName, String username, String email, String title) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setTitle(title);
        return userRepository.save(user);
    }
}




