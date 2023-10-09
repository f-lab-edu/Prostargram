package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.dto.model.Comment;
import flab.project.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentMapper commentMapper;

    public Comment addComment(long postId, long userId, Long parentId, String content) {
        validateComment(postId, userId, parentId);

        Comment comment = Comment.builder()
                .postId(postId)
                .userId(userId)
                .parentId(parentId)
                .content(content)
                .build();

        // Todo comment가 null이라는 건 너무 추상적으로 이해가 되는데.., 댓글일 경우 null을 가지는 parentId + Primitive Type인 postId 및 userId를 제외하고는 content에 null이 담겼을 경우밖에 없을 것 같은데?
        // Todo 이건 controller에서 해줄 수 있는 검증인데, service에서도 존재하지 않는 댓글이라는 어색한 context로 검증을 해줘야 할까?

        commentMapper.addComment(comment);

        // setRootId(comment, parentId);

        return comment;
    }

    private void validateComment(long postId, long userId, Long parentId) {
        // Todo userId는 추후 삭제 예정이므로 합쳐서 작성
        validatePostIdAndUserId(postId, userId);
        validateParentId(parentId);
    }

    private void validatePostIdAndUserId(long postId, long userId) {
        if (postId <= 0) {
            throw new InvalidUserInputException("Invalid postId.");
        }

        if (userId <= 0) {
            throw new InvalidUserInputException("Invalid userId.");
        }
    }

    private void validateParentId(Long parentId) {
        if (parentId != null && parentId <= 0) {
            throw new InvalidUserInputException("Invalid parentId.");
        }
    }

    // Todo (CommentController 26L) parentId에 commentId를 넣지 않고, null을 그대로 넣는 방식으로 회의 후 수정 예정 / builder에 parentId 추가
    // private void setRootId(Comment comment, Long parentId) {
    //    if (parentId == null) {
    //        parentId = comment.getCommentId();
    //    }
    //    comment.setParentId(parentId);
    // }
}