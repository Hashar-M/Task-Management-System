package com.hashar.Task_Management_System.repo;

import com.hashar.Task_Management_System.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface memberRepo extends JpaRepository<Member, Integer> {
}
