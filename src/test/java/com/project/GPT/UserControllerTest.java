package com.project.GPT;

import com.project.GPT.socialmedia.controller.UserController;
import com.project.GPT.socialmedia.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    public UserControllerTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void deleteUser_Success() {
        // Mocking UserService behavior
        doNothing().when(userService).deleteUser(1L);

        // Testing deleteUser method
        ResponseEntity<Void> response = userController.deleteUser(1L);

        // Verifying response
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
