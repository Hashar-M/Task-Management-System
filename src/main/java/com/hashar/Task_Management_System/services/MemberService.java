package com.hashar.Task_Management_System.services;

import com.hashar.Task_Management_System.Constants.Roles;
import com.hashar.Task_Management_System.dto.LoginResponseDTO;
import com.hashar.Task_Management_System.dto.MemberDTO;
import com.hashar.Task_Management_System.dto.MemberLoginDTO;
import com.hashar.Task_Management_System.model.Member;
import com.hashar.Task_Management_System.model.Token;
import com.hashar.Task_Management_System.repo.MemberRepo;
import com.hashar.Task_Management_System.repo.TokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    MemberRepo memberRepo;
    @Autowired
    TokenRepo tokenRepo;
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
        Member member = memberRepo.findByMemberName(memberName);
        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(memberName,memberLoginDTO.getPassword()));
        if (authentication.isAuthenticated()){
            String jwt = jwtService.generateToken(memberLoginDTO.getMemberName());

            revokeAllTokenByMember(member.getMemberId()); // set members available tokens as invalid
            saveMemberToken(jwt,null,member); // saving current access and refresh token to token table

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

    private void revokeAllTokenByMember(int memberId){
        List<Token> validTokens = tokenRepo.findAllAccessTokensByUser(memberId);
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });
        tokenRepo.saveAll(validTokens);
    }
    private void saveMemberToken(String accessToken, String refreshToken, Member member) {
        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setMember(member);
        tokenRepo.save(token);
    }
}
