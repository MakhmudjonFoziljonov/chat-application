package com.chat.userservice.dto.sso;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseDto {
   private Boolean success;
   private String status;
   private String message;
   private Integer code;

   private LoginDto result;
}
