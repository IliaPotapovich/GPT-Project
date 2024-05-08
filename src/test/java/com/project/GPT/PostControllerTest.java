package com.project.GPT;

import com.project.GPT.socialmedia.controller.PostController;
import com.project.GPT.socialmedia.service.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

public class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    public PostControllerTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void deletePost_Success() {
        // Mocking PostService behavior
        doNothing().when(postService).deletePost(1L);

        // Testing deletePost method
        ResponseEntity<Void> response = postController.deletePost(1L);

        // Verifying response
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
