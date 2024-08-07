package flab.project.mapper;

import flab.project.domain.comment.mapper.CommentMapper;
import flab.project.domain.comment.model.Comment;
import flab.project.domain.comment.model.CommentWithUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("classpath:tableInit.sql")
@ActiveProfiles("test")
@MybatisTest
class CommentMapperTest {

    @Autowired
    private CommentMapper commentMapper;

    @DisplayName("댓글을 추가할 수 있다.")
    @Test
    void addComment() {
        // given
        Comment comment = Comment.builder()
                .postId(1L)
                .userId(1L)
                .content("This is a comment.")
                .build();

        // when
        commentMapper.addComment(comment);

        // then
        List<CommentWithUser> comments = commentMapper.getComments(1L, 1L, null, 10);
        assertThat(comments).hasSize(1);
        assertThat(comments.get(0).getComment().getCommentId()).isEqualTo(comment.getCommentId());
    }
}
