package flab.project.config.exception;

import jakarta.mail.MessagingException;

public class FailedToWriteEmailException extends RuntimeException {
    public FailedToWriteEmailException(MessagingException e) {
    }
}
