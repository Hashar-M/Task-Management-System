package com.hashar.Task_Management_System.dto;

import com.hashar.Task_Management_System.model.Member;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    @NotBlank
    private String memberName;
    @Email
    @NotBlank
    @Column(unique = true)
    private String emailId;
    @NotBlank
    @Size(message = "password must be at least 8 characters and contains alphanumeric characters.", min = 8, max = 25)
    private String password;
    private int roleId;

    public MemberDTO(Member member) {
        this.memberName = member.getMemberName();
        this.emailId = member.getEmailId();
        this.password = member.getPassword();
        this.roleId = member.getRole().getId();
    }
}
