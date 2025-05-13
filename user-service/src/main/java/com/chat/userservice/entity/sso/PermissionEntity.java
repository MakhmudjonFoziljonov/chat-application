package com.chat.userservice.entity.sso;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "mia_s_permissions")
public class PermissionEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "type")
    private String type;

    @Column(name = "name_uz")
    private String nameUz;

    @Column(name = "name_cr")
    private String nameCr;

    @Column(name = "name_qr")
    private String nameQr;

    @Column(name = "name_ru")
    private String nameRu;

    @ManyToMany(mappedBy = "permissions")
    private Set<RoleEntity> roles = new HashSet<>();
}
