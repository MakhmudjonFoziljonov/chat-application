package com.chat.userservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;


@Entity
@Getter
@Setter
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pin", length = 14)
    private String pin;

    @Column(name = "unique_id", unique = true, updatable = false, nullable = false)
    private UUID uniqueId;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "parent_name")
    private String parentName;

    @Column(name = "username")
    private String username;

    @Column(name = "password", unique = true)
    private String password;

    @Column(name = "code_for_registration")
    private String codeForRegistration;

    @Column(name = "user_device")
    private String userDevice;

    @Column(name = "last_login")
    private Date lastLogin;

    @Column(name = "joined_date")
    private Date joinedDate;

    @Column(name = "is_active")
    private Boolean isActive = Boolean.TRUE;

    @Column(name = "passport_series")
    private String passportSeries;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "upload_path")
    private String uploadPath;

    @Column(name = "has_role")
    private Boolean hasRole = Boolean.FALSE;

    @Enumerated(STRING)
    @Column(name = "status")
    private UserStatus status = UserStatus.ACTIVE;

    private enum UserStatus {
        ACTIVE, INACTIVE, DELETED
    }
}
