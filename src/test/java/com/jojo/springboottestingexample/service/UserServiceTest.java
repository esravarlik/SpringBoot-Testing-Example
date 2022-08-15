package com.jojo.springboottestingexample.service;

import com.jojo.springboottestingexample.exception.UserApplicationException;
import com.jojo.springboottestingexample.model.User;
import com.jojo.springboottestingexample.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test getUser called repository")
    void should_get_user_by_id() {
        String id = "someId";

        User result = new User();
        result.setValue("someValue");

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(result));

        userService.getUser(id);

        verify(userRepository).findById(id);
    }

    @Test
    @DisplayName("Test getUser throws an exception")
    void should_throw_exception_get_user() {
        String id = "someId";

        User result = new User();
        result.setValue("someValue");

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());

        UserApplicationException cx = assertThrows(UserApplicationException.class, () -> {
            userService.getUser(id);
        });

        assertEquals("Exception occurred while get demo by id", cx.getMessage());

    }

    @Test
    @DisplayName("Test create user")
    void should_create_user() {
        String userValue = "someUserValue";

        userService.createUser(userValue);

        verify(userRepository).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();

        assertNotNull(capturedUser);
        assertNotNull(capturedUser.getId());
        assertEquals(userValue, capturedUser.getValue());
    }

    @Test
    @DisplayName("Test get users as list")
    void should_return_users_as_list() {
        String userValue = "someUserValue";

        userService.getUser(userValue);

        verify(userRepository).findByValue(userValue);
    }
}