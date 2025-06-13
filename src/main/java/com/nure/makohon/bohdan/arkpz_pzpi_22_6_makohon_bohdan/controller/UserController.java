package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.controller;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.dto.LoginRequestDTO;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.dto.UserDTO;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.dto.UserSettingDTO;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.User;

import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(
        origins = "*",             
        methods = {
                RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS
        },
        allowedHeaders = "*"
)
public class UserController {

    @Autowired
    private UserService userService;

    // GET: Retrieve all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    // GET: Retrieve a user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return userService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: Create a new user
    @PostMapping
    public ResponseEntity<?> signup(@RequestBody UserDTO userDTO) {
        try {
            UserDTO savedUser = userService.signup(userDTO);
            return ResponseEntity.ok(savedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // PUT: Update an existing user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User userDetails) {
        return userService.findUserById(id).map(user -> {
            user.setEmail(userDetails.getEmail());
            user.setPasswordHash(userDetails.getPasswordHash());
            user.setTemperatureUnit(userDetails.getTemperatureUnit());
            user.setNotificationPreference(userDetails.getNotificationPreference());
            User updatedUser = userService.saveUser(user);
            return ResponseEntity.ok(updatedUser);
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Remove a user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        if (userService.findUserById(id).isPresent()) {
            userService.deleteUserById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        UserDTO userDTO = userService.login(loginRequest);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.status(401).build(); // Unauthorized
    }

    // GET: Retrieve user settings
    @GetMapping("/{id}/settings")
    public ResponseEntity<UserSettingDTO> getUserSettings(@PathVariable Integer id) {
        UserSettingDTO settings = userService.getUserSettings(id);
        return ResponseEntity.ok(settings);
    }

    // POST: Update user settings
    @PostMapping("/{id}/settings")
    public ResponseEntity<Void> updateUserSettings(@PathVariable Integer id, @RequestBody UserSettingDTO settings) {
        userService.updateUserSettings(id, settings);
        return ResponseEntity.noContent().build();
    }

}

