package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_settings")
public class UserSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer settingId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Double minValue;
    private Double maxValue;


}

