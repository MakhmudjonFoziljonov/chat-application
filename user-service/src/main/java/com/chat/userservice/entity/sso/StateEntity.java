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
@Table(name = "mia_states")
public class StateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "remote_id")
    private Long remoteId;

    @Column(name = "code_exists")
    private Boolean codeExists = false;

    @Column(name = "name_uz")
    private String nameUz;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "name_qr")
    private String nameQr;

    @Column(name = "name_cr")
    private String nameCr;

    @Column(name = "code")
    private String code;

    @Column(name = "soato")
    private Long soato;

    @Column(name = "code_name")
    private String codeName;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "order_number")
    private Long orderNumber;

    @Enumerated(STRING)
    @Column(name = "status")
    private Status status = ACTIVE;
}
