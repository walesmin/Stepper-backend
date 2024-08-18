package com.example.stepperbackend.converter;

import com.example.stepperbackend.domain.Comment;
import com.example.stepperbackend.domain.Member;
import com.example.stepperbackend.domain.Post;
import com.example.stepperbackend.web.dto.CommentDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CommentConverter {

    public static Comment toEntity(CommentDto.CommentRequestDto dto, Member member, Post post, String memberName) {
        return Comment.builder()
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .member(member)
                .post(post)
                .anonymous(dto.isAnonymous())
                .anonymousName(memberName)
                .build();
    }

    public static CommentDto.CommentResponseDto toDto(Comment comment) {
        return CommentDto.CommentResponseDto.builder()
                .postId(comment.getPost().getId())
                .commentId(comment.getId())
                .memberName(comment.getAnonymousName())
                .profileImage(comment.getMember().getProfileImage())
                .content(comment.getContent())
                .dateTime(comment.getCreatedAt())
                .build();
    }

    public static CommentDto.ReplyResponseDto toReplyDto(Comment comment) {
        return CommentDto.ReplyResponseDto.builder()
                .postId(comment.getPost().getId())
                .parentCommentId(comment.getParentComment().getId())
                .content(comment.getContent())
                .anonymous(comment.isAnonymous())
                .build();

    }

    public static CommentDto.CommentResponseDto toCommentResponseDto(List<CommentDto.ReplyResponseDto> replyList, Comment comment){

        /*List<CommentDto.ReplyResponseDto> replyDtoList = replyList.stream()
                .map(CommentConverter::toReplyDto).collect(Collectors.toList());*/

        return CommentDto.CommentResponseDto.builder()
                .postId(comment.getPost().getId())
                .commentId(comment.getId())
                .memberName(comment.getAnonymousName())
                .profileImage(comment.getMember().getProfileImage())
                .content(comment.getContent())
                .dateTime(comment.getCreatedAt())
                .replyList(replyList)
                .build();
    }

    public static Comment toReplyEntity(CommentDto.ReplyRequestDto dto, Member member, Post post, String memberName, Comment parentComment) {
        return Comment.builder()
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .member(member)
                .post(post)
                .anonymous(dto.isAnonymous())
                .anonymousName(memberName)
                .parentComment(parentComment)
                .build();
    }
}
