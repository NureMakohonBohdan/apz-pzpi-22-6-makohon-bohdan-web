package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.service;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.config.PasswordUtil;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.dto.LoginRequestDTO;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.dto.UserDTO;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.dto.UserSettingDTO;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.NotificationPreference;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.User;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO signup(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new IllegalArgumentException("Email is already registered");
        }
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPasswordHash(PasswordUtil.hashPassword(userDTO.getPassword())); // Hash the password

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Email is already registered");
        }

        return convertToDTO(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    // Handle User Login
    public UserDTO login(LoginRequestDTO loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user != null && user.getPasswordHash().equals(PasswordUtil.hashPassword(loginRequest.getPassword()))) {
            return convertToDTO(user);
        }
        return null; // Login failed
    }

    // Convert Entity to DTO
    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getUserId());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    public UserSettingDTO getUserSettings(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User not found"));
        UserSettingDTO settings = new UserSettingDTO();
        settings.setNotificationPreference(user.getNotificationPreference().toString());
        settings.setTemperatureUnit(user.getTemperatureUnit().toString());
        return settings;
    }

    public void updateUserSettings(Integer userId, UserSettingDTO settings) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User not found"));
        user.setNotificationPreference(NotificationPreference.valueOf(settings.getNotificationPreference()));
        user.setTemperatureUnit(User.TemperatureUnit.valueOf(settings.getTemperatureUnit()));
        userRepository.save(user);
    }

}

