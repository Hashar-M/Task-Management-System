package com.hashar.Task_Management_System.services;

import com.hashar.Task_Management_System.model.Member;
import com.hashar.Task_Management_System.repo.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    public Member register(Member member){
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        member.setPassword(encoder.encode(member.getPassword()));
        System.out.println(member);
        return memberRepo.save(member);
    }

    public String verify(Member member) {
        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(member.getMemberName(),member.getPassword()));
        if (authentication.isAuthenticated()){
            return jwtService.generateToken(member.getMemberName());
        }
        return "failed";
    }
}
