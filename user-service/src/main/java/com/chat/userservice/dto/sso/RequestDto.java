package com.chat.userservice.dto.sso;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestDto {
   private String grant_type;
   private String client_id;
   private String client_secret;

   private String redirect_uri;
   private String code;

   private String scope;
   private String access_token;

   private String pinfl;

   private String username;
   private String password;
}
