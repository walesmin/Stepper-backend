package com.example.stepperbackend.service.PostService;

import com.example.stepperbackend.repository.LikeRepository;
import com.example.stepperbackend.web.dto.PostDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    PostDto.PostResponseDto createPost(List<MultipartFile> image, PostDto.PostRequestDto postRequestDto, String email);

    List<PostDto.PostViewDto> getPostsList(String email);

    PostDto.PostViewDto getPost(Long postId, String email);

    List<PostDto.PostViewDto> getAllPost(String category, String email);

    List<PostDto.PostViewDto> getCommentsList(String email);

    List<PostDto.PostViewDto> getScrapList(String email);

    List<PostDto.PostViewDto> getWeeklyPost(Long weeklyMissionId, String email);
}
