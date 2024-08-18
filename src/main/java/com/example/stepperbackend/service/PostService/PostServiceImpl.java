package com.example.stepperbackend.service.PostService;

import com.example.stepperbackend.apiPayload.code.status.ErrorStatus;
import com.example.stepperbackend.apiPayload.exception.handler.*;
import com.example.stepperbackend.converter.ImageConverter;
import com.example.stepperbackend.domain.*;
import com.example.stepperbackend.domain.enums.BodyPart;
import com.example.stepperbackend.domain.mapping.Scrap;
import com.example.stepperbackend.repository.*;
import com.example.stepperbackend.service.S3Service;
import com.example.stepperbackend.service.badgeService.BadgeService;
import com.example.stepperbackend.web.dto.PostDto;
import com.example.stepperbackend.converter.PostConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final WeeklyMissionRepository weeklyMissionRepository;
    private final BadgeService badgeService;
    private final LikeRepository likeRepository;
    private final ScrapRepository scrapRepository;
    private final CommentRepository commentRepository;
    private final S3Service s3Service;
    private final ImageRepository imageRepository;

    @Override
    public PostDto.PostResponseDto createPost(List<MultipartFile> images, PostDto.PostRequestDto postRequestDto, String email) {
        //이미지 url 가져오기
        List<String> imageUrlList = null;
        if (images != null && !images.isEmpty()) {
            imageUrlList = s3Service.saveFiles(images);
        }

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        WeeklyMission weeklyMission = null;
        if (postRequestDto.getWeeklyMissionId() != null) {
            weeklyMission = weeklyMissionRepository.findById(postRequestDto.getWeeklyMissionId()).orElse(null);
        }

        Post post = PostConverter.toEntity(postRequestDto, member, weeklyMission);
        postRepository.save(post);

        //이미지 저장
        List<Image> imageList = imageUrlList.stream()
                .map(imageUrl -> {
                    Image image = ImageConverter.toEntity(imageUrl, post);
                    return imageRepository.save(image);
                })
                .collect(Collectors.toList());
        post.setImageList(imageList);

        // 첫 커뮤니티 게시글 작성 완료
        if (postRepository.getCountByMember(member) == 1) {
            badgeService.putFirstBadge("첫 게시글 작성 완료", member);
        }

        return PostConverter.toDto(post, imageList);
    }

    @Override
    public List<PostDto.PostViewDto> getPostsList(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        List<PostDto.PostViewDto> postList = postRepository.findAllByMember(member).stream()
                .map(post -> {
                    int likes = likeRepository.getCountByPost(post);
                    int scraps = scrapRepository.getCountByPost(post);
                    int comments = commentRepository.getCountByPost(post);
                    List<Image> imageList = imageRepository.findAllByPost(post);
                    return PostConverter.toViewDto(post, scraps, likes, comments, imageList);
                })
                .collect(Collectors.toList());

        if (postList.isEmpty()) {
            throw new PostHandler(ErrorStatus.MY_POST_LIST_NOT_FOUND);
        }

        return postList;
    }

    @Override
    public PostDto.PostViewDto getPost(Long postId, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostHandler(ErrorStatus.POST_NOT_FOUND));

        int likes = likeRepository.getCountByPost(post);

        int scraps = scrapRepository.getCountByPost(post);

        int commentsCount = commentRepository.getCountByPost(post);

        List<Image> imageList = imageRepository.findAllByPost(post);

        return PostConverter.toViewDto(post, likes, scraps, commentsCount, imageList);
    }

    @Override
    public List<PostDto.PostViewDto> getAllPost(String categoryName, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        List<PostDto.PostViewDto> postList = postRepository.findByCategoryId(BodyPart.valueOf(categoryName)).stream()
                .map(post -> {
                    int likes = likeRepository.getCountByPost(post);
                    int scraps = scrapRepository.getCountByPost(post);
                    int comments = commentRepository.getCountByPost(post);
                    List<Image> imageList = imageRepository.findAllByPost(post);
                    return PostConverter.toViewDto(post, scraps, likes, comments, imageList);
                })
                .collect(Collectors.toList());

        if (postList.isEmpty()) {
            throw new PostHandler(ErrorStatus.MY_POST_LIST_NOT_FOUND);
        }

        return postList;
    }

    @Override
    public List<PostDto.PostViewDto> getWeeklyPost(Long weeklyMissionId, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        List<PostDto.PostViewDto> postList = postRepository.findByWeeklyMissionId(weeklyMissionId).stream()
                .map(post -> {
                    int likes = likeRepository.getCountByPost(post);
                    int scraps = scrapRepository.getCountByPost(post);
                    int comments = commentRepository.getCountByPost(post);
                    List<Image> imageList = imageRepository.findAllByPost(post);
                    return PostConverter.toViewDto(post, scraps, likes, comments, imageList);
                })
                .collect(Collectors.toList());

        if (postList.isEmpty()) {
            throw new PostHandler(ErrorStatus.WEEKLY_POST_LIST_NOT_FOUND);
        }

        return postList;
    }

    @Override
    public List<PostDto.PostViewDto> getCommentsList(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        List<Comment> comments = commentRepository.findByMember(member);

        if (comments.isEmpty()) {
            throw new CommentHandler(ErrorStatus.MY_COMMENTS_NOT_FOUND);
        }

        return comments.stream()
                .map(comment -> {
                    Post post = comment.getPost();
                    int likes = likeRepository.getCountByPost(post);
                    int scraps = scrapRepository.getCountByPost(post);
                    int commentsCount = commentRepository.getCountByPost(post);
                    List<Image> imageList = imageRepository.findAllByPost(post);
                    return PostConverter.toViewDto(post, likes, scraps, commentsCount, imageList);
                })
                .distinct()
                .toList();
    }

    @Override
    public List<PostDto.PostViewDto> getScrapList(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        List<Scrap> scraps = scrapRepository.findByMember(member);

        if (scraps.isEmpty()) {
            throw new ScrapHandler(ErrorStatus.SCRAP_NOT_FOUND);
        }

        return scraps.stream()
                .map(scrap -> {
                    Post post = scrap.getPost();
                    int likes = likeRepository.getCountByPost(post);
                    int scrapsCount = scrapRepository.getCountByPost(post);
                    int commentsCount = commentRepository.getCountByPost(post);
                    List<Image> imageList = imageRepository.findAllByPost(post);
                    return PostConverter.toViewDto(post, likes, scrapsCount, commentsCount, imageList);
                })
                .distinct()
                .toList();
    }
}

