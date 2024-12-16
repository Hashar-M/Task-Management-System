package com.hashar.Task_Management_System.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Getter
@Setter
@ToString
public class LoginResponseDTO {
    private String memberName;
    private List<? extends GrantedAuthority> role;
    private String jwt;
}
