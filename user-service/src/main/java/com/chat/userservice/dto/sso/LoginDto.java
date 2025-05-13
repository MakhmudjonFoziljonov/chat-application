package com.chat.userservice.dto.sso;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginDto {
   private String scope;
   private Long expires_in;
   private String token_type;
   private String refresh_token;
   private String access_token;
}
