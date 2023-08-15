package flab.project.data.dto.model;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.dto.UpdateSocialAccountRequestDto;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class SocialAccount {
    private static final long DEFAULT_ICON_ID = 1L;

    private long socialAccountId;
    private Long iconId;
    private long userId;
    private String socialAccountUrl;

    public SocialAccount(UpdateSocialAccountRequestDto updateSocialAccount) {
        this.userId = updateSocialAccount.getUserId();
        this.socialAccountUrl = updateSocialAccount.getSocialAccountUrl();
    }

    //todo 얘는 Converter? Formatter? 그쪽 강의 들으면 더 좋은 방법이 나올듯?
    //todo 이것도 프론트에서 한 번 Filtering을 해주는게 좋겠지?
    public String getDomain() {
        String linkUrl = getSocialAccountUrl();
        String regex = "(?:https?://)?(?:www\\.)?([^/.]+)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(linkUrl);
        if (matcher.find()) {
            String group = matcher.group(1);
            return group;
        } else {
            throw new InvalidUserInputException();
        }
    }

    public void setIconId(Long iconId) {
        if (iconId == null) {
            this.iconId = DEFAULT_ICON_ID;
            return;
        }
        this.iconId = iconId;
    }
}
