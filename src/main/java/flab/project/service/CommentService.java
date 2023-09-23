package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.model.Comment;
import flab.project.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentMapper commentMapper;

    public SuccessResponse<Comment> addComment (long postId, long userId, String content) {
        Comment comment = Comment.builder()
                .postId(postId)
                .userId(userId)
                .content(content)
                .build();

        commentMapper.addComment(comment);

        return new SuccessResponse<>(comment);
    }
}