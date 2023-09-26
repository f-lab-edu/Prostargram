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
        // Todo 대댓글이 아닌 최상단 댓글을 작성할 경우, rootId를 어떻게 처리할지 세션에서 질문할 예정
        // Todo 1) Controller에서 0인 rootId를 Service로 넘기는 방법
        // Todo 2) rootId를 reference type으로 수정하여 null checking하는 방법
        // Todo 그런데, 1)과 2)는 서버 입장에서 정확히 어떤 연유로 0 또는 null 값을 가지게 되었는지를 알 수 없어서 별로인 것 같음
        // Todo 3) 최상단 댓글을 작성할 경우와 대댓글을 작성할 경우 엔드 포인트를 분리하는 방법은 어떨까?
        Comment comment = Comment.builder()
                .postId(postId)
                .userId(userId)
                .content(content)
                .build();

        commentMapper.addComment(comment);

        return new SuccessResponse<>(comment);
    }
}