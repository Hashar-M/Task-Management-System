package com.hashar.Task_Management_System.services;

import com.hashar.Task_Management_System.model.Member;
import com.hashar.Task_Management_System.model.UserPrinciple;
import com.hashar.Task_Management_System.repo.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    MemberRepo memberRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepo.findByMemberName(username).orElseThrow(()->new UsernameNotFoundException("User not found."));

        return new UserPrinciple(member);
    }
}
