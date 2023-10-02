package flab.project.service;

import flab.project.data.dto.model.Comment;
import flab.project.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentMapper commentMapper;

    public Comment addComment (long postId, long userId, String content) {
        LocalDateTime todayTime = LocalDateTime.now();

        Comment comment = Comment.builder()
                .postId(postId)
                .userId(userId)
                .content(content)
                .createdAt(todayTime)
                .build();

        commentMapper.addComment(comment);

        return comment;
    }
}