package com.hashar.Task_Management_System.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDTO {

    @NotBlank
    private String memberName;
    @Email
    @NotBlank
    @Column(unique = true)
    private String emailId;
    @NotBlank
    @Size(message = "password must be at least 8 characters and contains alphanumeric characters.", min = 8)
    private String password;
}
