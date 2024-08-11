package flab.project.domain.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddSocialAccountDto {

    private String socialAccountUrl;
}
