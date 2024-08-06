package flab.project.domain.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.util.HtmlUtils;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSocialAccountRequestDto {

    @Positive
    private long userId;

    @Schema(example = "http://github.com")
    private String socialAccountUrl;

    public void convertEscapeCharacter() {
        this.socialAccountUrl = HtmlUtils.htmlEscape(socialAccountUrl);
    }
}