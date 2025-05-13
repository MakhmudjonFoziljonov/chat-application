package com.chat.userservice.entity.sso;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "mia_military_certificates")
public class MilitaryCertificateEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "series")
    private String series;

    @Column(name = "number")
    private String number;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.YYY", timezone = "Asia/Tashkent")
    @Column(name = "begin_date")
    private Date beginDate;

    @Column(name = "end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Tashkent")
    private Date endDate;

}
