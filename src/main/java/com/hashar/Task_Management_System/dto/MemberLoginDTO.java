package com.hashar.Task_Management_System.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberLoginDTO {

    private String memberName;
    private String emailId;
    @NotBlank
    private String password;
}
