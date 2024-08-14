package flab.project.domain.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsernameTokenReturnDto {

    private String usernameToken;

    public UsernameTokenReturnDto(String usernameToken) {
        this.usernameToken = usernameToken;
    }
}
