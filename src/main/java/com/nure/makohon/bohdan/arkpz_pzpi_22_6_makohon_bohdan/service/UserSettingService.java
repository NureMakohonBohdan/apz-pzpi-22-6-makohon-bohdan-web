package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.service;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.dto.UserSettingDTO;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.User;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.UserSetting;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.repository.UserRepository;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.repository.UserSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserSettingService {

    @Autowired
    private UserSettingRepository userSettingRepository;


    @Autowired
    private UserRepository userRepository;

    public Optional<UserSetting> findUserSettingById(Integer id) {
        return userSettingRepository.findById(id);
    }

    public UserSetting findUserSettingByUserId(Integer userId) {
        return userSettingRepository.findByUserUserId(userId);
    }

    public UserSetting saveUserSetting(UserSetting userSetting) {
        return userSettingRepository.save(userSetting);
    }

    public void deleteUserSettingById(Integer id) {
        userSettingRepository.deleteById(id);
    }

    public UserSetting createUserSetting(Integer userId, UserSettingDTO userSettingDTO) {
        // Fetch the user
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User with ID " + userId + " not found"));

        // Convert DTO to Entity
        UserSetting userSetting = new UserSetting();
        userSetting.setUser(user);

        // Save the user setting
        return userSettingRepository.save(userSetting);
    }

    public UserSetting updateSettings(Integer userId, UserSetting userSetting) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User not found"));
        userSetting.setUser(user);
        return userSettingRepository.save(userSetting);
    }


}
