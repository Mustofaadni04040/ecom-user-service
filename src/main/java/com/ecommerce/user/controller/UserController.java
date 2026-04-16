package com.ecommerce.user.controller;

import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest) {
        userService.addUser(userRequest);
        return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        boolean updated = userService.updateUser(id, userRequest);

        if (updated) {
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        Optional<UserResponse> user = userService.fetchUser(id);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }

        return ResponseEntity.ok(user.get());
    }
}
