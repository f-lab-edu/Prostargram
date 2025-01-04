package flab.project.domain.comment.exception;

public class CommentNotFoundException extends RuntimeException {

    private static final String MESSAGE = "존재하지 않는 댓글입니다.";

    public CommentNotFoundException() {
        super(MESSAGE);
    }

}
