package com.example.stepperbackend.web.controller;

import com.example.stepperbackend.apiPayload.ApiResponse;
import com.example.stepperbackend.jwt.JWTUtil;
import com.example.stepperbackend.service.PostService.PostService;
import com.example.stepperbackend.service.scrapService.ScrapService;
import com.example.stepperbackend.service.likeService.LikeService;
import com.example.stepperbackend.web.dto.LikeDto;
import com.example.stepperbackend.web.dto.PostDto;
import com.example.stepperbackend.web.dto.ScrapDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class PostController {

    private final JWTUtil jwtUtil;
    private final PostService postService;
    private final ScrapService scrapService;
    private final LikeService likeService;
  

    @Operation(summary = "게시글 작성 API", description = "사용자 게시글 작성")
    @PostMapping("/write")
    public ApiResponse<PostDto.PostResponseDto> createPost(@RequestBody PostDto.PostRequestDto postRequestDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.getUsername(token);
        PostDto.PostResponseDto response = postService.createPost(postRequestDto, email);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "내가 작성한 글 조회 API", description = "내가 작성한 글 조회")
    @GetMapping("/my_posts")
    public ApiResponse<List<PostDto.PostViewDto>> getPostsList(HttpServletRequest request) {

        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.getUsername(token);
        List<PostDto.PostViewDto> response = postService.getPostsList(email);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "게시글 상세 조회 API", description = "게시글 상세 조회")
    @GetMapping("/{postId}")
    public ApiResponse<PostDto.PostViewDto> getPost(@PathVariable Long postId, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.getUsername(token);
        PostDto.PostViewDto response = postService.getPost(postId, email);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "게시글 목록 조회 API", description = "게시글 목록 조회")
    @GetMapping("/{categoryName}/posts")
    public ApiResponse<List<PostDto.PostViewDto>> getAllPost(@PathVariable String categoryName, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.getUsername(token);
        List<PostDto.PostViewDto> response = postService.getAllPost(categoryName, email);
        return ApiResponse.onSuccess(response);
    }


    @Operation(summary = "좋아요 등록 API", description = "게시글 좋아요 등록")
    @PostMapping("/{postId}/like")
    public ApiResponse<LikeDto.likeResponseDto> createLike(@PathVariable(name = "postId") Long postId, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.getUsername(token);
        LikeDto.likeResponseDto response = likeService.createLike(email, postId);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "좋아요 취소 API", description = "게시글 좋아요 취소")
    @DeleteMapping("/{postId}/like")
    public ApiResponse<String> deleteLike(@PathVariable(name = "postId") Long postId, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.getUsername(token);
        likeService.deleteLike(email, postId);
        return ApiResponse.onSuccess("좋아요 취소 성공");
    }
  
    @Operation(summary = "스크랩 등록 API", description = "스크랩 등록")
    @PostMapping("/{postId}/scrap")
    public ApiResponse<ScrapDto.ScrapResponseDto> createScrap(@PathVariable(name = "postId") Long postId, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.getUsername(token);
        ScrapDto.ScrapResponseDto response = scrapService.creatScrap(email, postId);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "스크랩 취소 API", description = "게시글 스크랩 취소")
    @DeleteMapping("/{postId}/scrap")
    public ApiResponse<String> deleteScrap(@PathVariable(name = "postId") Long postId, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.getUsername(token);
        scrapService.deleteScrap(email, postId);
        return ApiResponse.onSuccess("스크랩 취소 성공");
    }
}