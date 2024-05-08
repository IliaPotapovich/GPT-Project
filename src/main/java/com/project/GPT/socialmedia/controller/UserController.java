package com.project.GPT.socialmedia.controller;

import com.project.GPT.socialmedia.controller.dto.UserDTO;
import com.project.GPT.socialmedia.model.User;
import com.project.GPT.socialmedia.repository.UserRepository;
import com.project.GPT.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        Optional<User> user = Optional.ofNullable(userService.getUserById(userId));
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        try {
            User user = new User();
            user.setId(userDTO.getId());
            user.setUsername(userDTO.getUsername());
            // Set other fields from the UserDTO as needed
            // For example:
            // user.setFirstName(userDTO.getFirstName());
            // user.setLastName(userDTO.getLastName());

            User savedUser = userService.saveUser(user);

            UserDTO savedUserDTO = new UserDTO();
            savedUserDTO.setId(savedUser.getId());
            savedUserDTO.setUsername(savedUser.getUsername());


            return new ResponseEntity<>(savedUserDTO, HttpStatus.CREATED);
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

            return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
