package com.hashar.Task_Management_System.repo;

import com.hashar.Task_Management_System.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<Token, Integer> {

    @Query("""
select t from Token t inner join Member m on t.member.memberId = m.memberId
where t.member.memberId = :memberId and t.loggedOut = false
""")
    List<Token> findAllAccessTokensByUser(Integer memberId);

    Optional<Token> findByAccessToken(String token);

    Optional<Token > findByRefreshToken(String token);
}
