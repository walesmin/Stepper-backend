package com.example.stepperbackend.repository;

import com.example.stepperbackend.domain.Image;
import com.example.stepperbackend.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findAllByPost(Post post);
}
