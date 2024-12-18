package com.hashar.Task_Management_System.model;

import com.hashar.Task_Management_System.Constants.Roles;
import com.hashar.Task_Management_System.dto.MemberDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId;
    @NotBlank
    @Column(unique = true)
    private String memberName;
    @Email
    @NotBlank
    @Column(unique = true)
    private String emailId;
    @NotBlank
    @Size(message = "password must be at least 8 characters and contains alphanumeric characters.", min = 8)
    private String password;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @OneToMany(mappedBy = "member")
    private List<Token> tokens;

    public Member(MemberDTO memberDTO) {
        this.memberName = memberDTO.getMemberName();
        this.emailId = memberDTO.getEmailId();
        this.password = memberDTO.getPassword();
    }
}



