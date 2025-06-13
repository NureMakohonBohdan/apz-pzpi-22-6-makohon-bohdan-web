package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.repository;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.UserSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSettingRepository extends JpaRepository<UserSetting, Integer> {
    UserSetting findByUserUserId(Integer userId);
}
