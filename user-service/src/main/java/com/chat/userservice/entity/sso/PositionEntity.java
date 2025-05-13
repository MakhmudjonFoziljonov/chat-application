package com.chat.userservice.entity.sso;

import com.chat.userservice.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static com.chat.userservice.enums.Status.ACTIVE;
import static jakarta.persistence.EnumType.STRING;

@Getter
@Setter
@Entity
@Table(name = "mia_cd_positions")
public class PositionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_uz")
    private String nameUz;

    @Column(name = "name_cr")
    private String nameCr;

    @Column(name = "name_qr")
    private String nameQr;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "code")
    private String code;

    @Enumerated(STRING)
    @Column(name = "status")
    private Status status = ACTIVE;
}
