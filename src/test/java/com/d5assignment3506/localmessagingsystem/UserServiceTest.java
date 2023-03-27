package com.d5assignment3506.localmessagingsystem;

import com.d5assignment3506.localmessagingsystem.entity.User;
import com.d5assignment3506.localmessagingsystem.repo.UserRepository;
import com.d5assignment3506.localmessagingsystem.service.UserNotFoundException;
import com.d5assignment3506.localmessagingsystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void createUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("john.doe@example.com");
        user.setTitle("Mr.");

        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.createUser("John", "Doe", "johndoe", "john.doe@example.com", "Mr.");

        assertEquals(user, result);
        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    void editUser() throws UserNotFoundException {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("john.doe@example.com");
        user.setTitle("Mr.");

        when(userRepository.findByUsername("johndoe")).thenReturn(user);

        User updatedUser = new User();
        updatedUser.setFirstName("John");
        updatedUser.setLastName("Smith");
        updatedUser.setUsername("johndoe");
        updatedUser.setEmail("john.smith@example.com");
        updatedUser.setTitle("Dr.");

        when(userRepository.save(user)).thenReturn(updatedUser);

        User result = userService.editUser("johndoe", "John", "Smith", "john.smith@example.com", "Dr.");

        assertEquals(updatedUser, result);
        verify(userRepository, times(1)).findByUsername("johndoe");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void editUser_throws_UserNotFoundException() {
        when(userRepository.findByUsername("unknown")).thenReturn(null);

        assertThrows(UserNotFoundException.class, () ->
                userService.editUser("unknown", "John", "Smith", "john.smith@example.com", "Dr."));
        verify(userRepository, times(1)).findByUsername("unknown");
    }
}
