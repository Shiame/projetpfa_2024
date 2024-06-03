package com.example.pfaproject.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthenticationResponse {
   private String token;
   private String type="Bearer";
   private Long id;
   private String email;
   private List<String> roles;

   public AuthenticationResponse(String accesstoken,Long id, String email, List<String> roles) {
       this.token = accesstoken;
       this.id = id;
       this.email = email;
       this.roles = roles;

   }
}
