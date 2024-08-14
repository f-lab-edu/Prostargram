package flab.project.domain.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class EmailTokenReturnDto {

    private String emailToken;

    public EmailTokenReturnDto(String emailToken) {
        this.emailToken = emailToken;
    }
}
