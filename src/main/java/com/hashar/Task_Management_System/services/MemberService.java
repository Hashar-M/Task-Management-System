package com.hashar.Task_Management_System.services;

import com.hashar.Task_Management_System.Constants.Roles;
import com.hashar.Task_Management_System.dto.LoginResponseDTO;
import com.hashar.Task_Management_System.dto.MemberDTO;
import com.hashar.Task_Management_System.dto.MemberLoginDTO;
import com.hashar.Task_Management_System.model.Member;
import com.hashar.Task_Management_System.repo.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        Boolean isMemberNameExist = memberRepo.existsByMemberName(memberDTO.getMemberName());
        Boolean isEmailIdExist = memberRepo.existsByEmailId(memberDTO.getEmailId());
        if(isMemberNameExist){
            throw new DataIntegrityViolationException("Username already exists.");
        }
        if(isEmailIdExist){
            throw new DataIntegrityViolationException("emailId already exists.");
        }
        memberDTO.setPassword(encoder.encode(memberDTO.getPassword()));

        Member member = new Member(memberDTO);
        member.setRole(Roles.USER);

        return memberRepo.save(member);
    }

    public LoginResponseDTO verify(MemberLoginDTO memberLoginDTO) {
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
            String jwt = jwtService.generateToken(memberLoginDTO.getMemberName());
            System.out.println(authentication.getAuthorities().stream().toList());
            System.out.println(authentication.getCredentials());
            System.out.println(authentication.getDetails());
            System.out.println(authentication.getName());
            LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
            loginResponseDTO.setJwt(jwt);
            loginResponseDTO.setMemberName(authentication.getName());
            loginResponseDTO.setRole(authentication.getAuthorities().stream().toList());
            return loginResponseDTO;
        }
        else {
            throw new BadCredentialsException("Bad credentials");
        }
    }
}
