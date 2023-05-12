package com.adm;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse {
    private String username;
    private String message;
    private String jwtToken;

}
