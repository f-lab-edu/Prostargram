package flab.project.domain.comment.exception;

public class PostNotFoundException extends RuntimeException {

    private static final String MESSAGE ="존재하지 않는 게시물입니다.";


    public PostNotFoundException() {
        super(MESSAGE);
    }
}
