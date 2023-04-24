package com.adm;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetails extends User {
    private Long id;

    // 기존 UserDetails와 관련된 필드 및 메소드를 포함합니다.

    public CustomUserDetails(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

    // 기존 UserDetails와 관련된 생성자 및 메소드를 포함합니다.

    public Long getId() {
        return id;
    }
}
