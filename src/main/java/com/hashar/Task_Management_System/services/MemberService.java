package com.hashar.Task_Management_System.services;

import com.hashar.Task_Management_System.Constants.Roles;
import com.hashar.Task_Management_System.dto.MemberDTO;
import com.hashar.Task_Management_System.dto.MemberLoginDTO;
import com.hashar.Task_Management_System.model.Member;
import com.hashar.Task_Management_System.repo.MemberRepo;
import jakarta.validation.constraints.Null;
import org.hibernate.jdbc.Expectation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    MemberRepo memberRepo;
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    JWTService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Member register(MemberDTO memberDTO){

        memberDTO.setPassword(encoder.encode(memberDTO.getPassword()));

        Member member = new Member(memberDTO);
        member.setRole(Roles.USER);

        return memberRepo.save(member);
    }

    public String verify(MemberLoginDTO memberLoginDTO) {
        /**
         * check for member exists
         * login by email id
         * is available?
         */
        String memberName;
        if (memberLoginDTO.getMemberName() == null && memberLoginDTO.getEmailId() != null){
            memberName = memberRepo.findMemberNameByEmailId(memberLoginDTO.getEmailId());
        } else if (memberLoginDTO.getMemberName() == null) {
            throw new IllegalArgumentException("memberName or emailId is required with password");
        }else {
            memberName = memberLoginDTO.getMemberName();
        }
        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(memberName,memberLoginDTO.getPassword()));
        if (authentication.isAuthenticated()){
            return jwtService.generateToken(memberLoginDTO.getMemberName());
        }
        else {
            throw new BadCredentialsException("Bad credentials");
        }
    }
}
