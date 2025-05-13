package com.chat.userservice.entity.sso;

import com.chat.userservice.enums.CandidateType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Table(name = "mia_candidates")
public class CandidateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "parent_name")
    private String parentName;

    @Column(name = "last_name_cr")
    private String lastNameCr;

    @Column(name = "first_name_cr")
    private String firstNameCr;

    @Column(name = "parent_name_cr")
    private String parentNameCr;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "passport_bagin_date")
    private Date passportBeginDate;

    @Column(name = "passport_end_date")
    private Date passportEndDate;

    @Column(name = "passport_series")
    private String passportSeries;

    @Column(name = "passport_given_by")
    private String passportGivenBy;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "certificate_series")
    private String certificateSeries;

    @Column(name = "certificate_number")
    private String certificateNumber;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "pinfl")
    private String pinfl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @Column(name = "birth_date")
    private Date birthDate;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "state_id", referencedColumnName = "id")
    private StateEntity state;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private RegionEntity region;

    @Column(name = "address")
    private String address;

    @Column(name = "upload_path")
    private String uploadPath;

    @Column(name = "photo_exist")
    private Boolean photoExist = Boolean.FALSE;

    @Enumerated(STRING)
    @Column(name = "type")
    private CandidateType type = CandidateType.EMPLOYEE;

}
