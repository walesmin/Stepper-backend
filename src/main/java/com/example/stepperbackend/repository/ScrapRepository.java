package com.example.stepperbackend.repository;

import com.example.stepperbackend.domain.Comment;
import com.example.stepperbackend.domain.Member;
import com.example.stepperbackend.domain.Post;
import com.example.stepperbackend.domain.mapping.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    boolean existsByMemberAndPost(Member member, Post post);

    void deleteByMemberAndPost(Member member, Post post);

    @Query("SELECT count(e) FROM Scrap e WHERE e.post = :post")
    int getCountByPost(Post post);

    List<Scrap> findByMember(Member member);
}
