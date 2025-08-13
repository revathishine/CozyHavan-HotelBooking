
//date 4/8/25

package com.hexaware.cozyhavenproject.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hexaware.cozyhavenproject.dto.UserDto;
import com.hexaware.cozyhavenproject.entities.User;
import com.hexaware.cozyhavenproject.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDto userDto) {
        log.info("Creating user: {}", userDto.getEmail());
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setContactNumber(userDto.getContactNumber());
        user.setAddress(userDto.getAddress());
        user.setGender(userDto.getGender());
        user.setRole(userDto.getRole());
        User created = userService.createUser(user);
        return ResponseEntity.created(URI.create("/api/users/" + created.getUserId())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        log.info("Get user: {}", id);
        User u = userService.getUserById(id);
        return ResponseEntity.ok(u);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Get all users");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @Valid @RequestBody UserDto userDto) {
        log.info("Update user: {}", id);
        User user = userService.getUserById(id); // will throw if not found
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setContactNumber(userDto.getContactNumber());
        user.setAddress(userDto.getAddress());
        user.setGender(userDto.getGender());
        user.setRole(userDto.getRole());
        User updated = userService.updateUser(user);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        log.info("Delete user: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
