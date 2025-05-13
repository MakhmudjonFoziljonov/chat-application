package com.chat.userservice.entity.sso;


import com.chat.userservice.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Table(name = "mia_s_roles")
public class RoleEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_uz")
    private String nameUz;

    @Column(name = "name_cr")
    private String nameCr;

    @Column(name = "name_qr")
    private String nameQr;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "code", unique = true)
    private String code;

    @ManyToMany(fetch = LAZY)
    @JoinTable(name = "s_role_permissions",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")})
    private Set<PermissionEntity> permissions = new HashSet<>();


}
