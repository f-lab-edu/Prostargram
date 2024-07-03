package flab.project.domain.user.model;

import flab.project.config.exception.InvalidUserInputException;
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

    //todo Converter, Formatter 강의 수강 후, 리펙토링 여부 결정.
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