package com.hashar.Task_Management_System.repo;

import com.hashar.Task_Management_System.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepo extends JpaRepository<Member, Integer> {

    Optional<Member> findByMemberName(String memberName);
    Boolean existsByMemberName(String memberName);
    Boolean existsByEmailId(String emailId);

    @Query("SELECT m.memberName FROM Member m WHERE m.emailId = :emailId")
    String findMemberNameByEmailId(@Param("emailId") String emailId);
}
