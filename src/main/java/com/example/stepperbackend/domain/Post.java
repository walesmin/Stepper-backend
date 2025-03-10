package com.example.stepperbackend.domain;

import com.example.stepperbackend.domain.common.BaseEntity;
import com.example.stepperbackend.domain.enums.BodyPart;
import com.example.stepperbackend.domain.enums.SubCategory;
import com.example.stepperbackend.domain.mapping.Scrap;
import com.example.stepperbackend.domain.mapping.Likes;
import jakarta.persistence.Entity;
import lombok.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;
    private String title;
    private String body;

    @Enumerated(EnumType.STRING)
    private BodyPart bodyPart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private SubCategory subCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weekly_mission_id")
    private WeeklyMission weeklyMission;


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Likes> likesList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Scrap> scrapList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> CommentList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Image> imageList = new ArrayList<>();
}
