package com.project.GPT.socialmedia.controller;

import com.project.GPT.socialmedia.controller.dto.PostDTO;
import com.project.GPT.socialmedia.controller.dto.UserDTO;
import com.project.GPT.socialmedia.model.Post;
import com.project.GPT.socialmedia.model.User;
import com.project.GPT.socialmedia.repository.PostRepository;
import com.project.GPT.socialmedia.service.PostService;
import com.project.GPT.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService = new PostService();
    private final UserService userService = new UserService();
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        return post.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        try {
            Post post = new Post();
            post.setTitle(postDTO.getTitle());
            post.setBody(postDTO.getBody());
            User author = userService.getUserById(postDTO.getAuthorId());
            if (author == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            post.setAuthor(author);
            post = postService.savePost(post);

            // Convert the saved Post entity back to PostDTO and return it
            PostDTO savedPostDTO = new PostDTO();
            savedPostDTO.setTitle(post.getTitle());
            savedPostDTO.setBody(post.getBody());
            savedPostDTO.setAuthorId(post.getAuthor().getId());
            return new ResponseEntity<>(savedPostDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> putUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        try {
            User existingUser = userService.getUserById(userId);
            if (existingUser == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Update existingUser with data from userDTO
            existingUser.setUsername(userDTO.getUsername());
            // Update other fields as needed

            User updatedUser = userService.saveUser(existingUser);

            UserDTO updatedUserDTO = new UserDTO();
            updatedUserDTO.setId(updatedUser.getId());
            updatedUserDTO.setUsername(updatedUser.getUsername());
            // Set other fields from the updated User entity to the updatedUserDTO
            // For example:
            // updatedUserDTO.setFirstName(updatedUser.getFirstName());
            // updatedUserDTO.setLastName(updatedUser.getLastName());

            return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> putPost(@PathVariable Long postId, @RequestBody PostDTO postDTO) {
        try {
            Post existingPost = postService.getPostById(postId);
            if (existingPost == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Update existingPost with data from postDTO
            existingPost.setTitle(postDTO.getTitle());
            existingPost.setBody(postDTO.getBody());
            // Update other fields as needed

            Post updatedPost = postService.savePost(existingPost);

            PostDTO updatedPostDTO = new PostDTO();
            updatedPostDTO.setTitle(updatedPost.getTitle());
            updatedPostDTO.setBody(updatedPost.getBody());
            // Set other fields from the updated Post entity to the updatedPostDTO
            // For example:
            // updatedPostDTO.setAuthorId(updatedPost.getAuthor().getId());

            return new ResponseEntity<>(updatedPostDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        try {
            postService.deletePost(postId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Other CRUD operations for posts
}
