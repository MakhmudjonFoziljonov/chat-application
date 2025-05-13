package com.chat.userservice.entity;



import com.chat.userservice.enums.SmsStatus;
import com.chat.userservice.enums.SmsType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "sms_histories")
public class SmsHistoryEntity {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "sms_code")
    private String smsCode;

    @Enumerated(EnumType.STRING)
    private SmsType smsType;

    @Enumerated(EnumType.STRING)
    private SmsStatus status;

    @Column(name = "visible", nullable = false)
    private boolean visible;

    @Column(name = "pin")
    private String pin;

    @Column(name = "phone_number")
    private String phoneNumber;

}
