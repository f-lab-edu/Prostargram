package flab.project.data.dto.model;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.dto.UpdateSocialAccountRequestDto;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class SocialAccount {

    private long socialAccountId;
    private Long iconId;
    private long userId;
    private String socialAccountUrl;

    private static String urlRegex = "(?:https?://)?(?:www\\.)?([^/.]+)";
    private static Pattern urlPatten = Pattern.compile(urlRegex);

    public SocialAccount(UpdateSocialAccountRequestDto updateSocialAccount) {
        this.userId = updateSocialAccount.getUserId();
        this.socialAccountUrl = updateSocialAccount.getSocialAccountUrl();
    }

    //todo 얘는 Converter? Formatter? 그쪽 강의 들으면 더 좋은 방법이 나올듯?
    public String getDomain() {
        String linkUrl = getSocialAccountUrl();
        Matcher matcher = urlPatten.matcher(linkUrl);

        if (matcher.find()) {
            String group = matcher.group(1);
            return group;
        } else {
            throw new InvalidUserInputException();
        }
    }

    public void setIconId(Long iconId) {
        this.iconId = iconId;
    }
}
