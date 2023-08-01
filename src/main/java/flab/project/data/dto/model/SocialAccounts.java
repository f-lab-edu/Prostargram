package flab.project.data.dto.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;

@Getter
public class SocialAccounts {
    private long linkId;
    private long iconId;
    private long userId;
    private String linkUrl;

    public SocialAccounts(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    //todo 얘는 Converter? Formatter? 그쪽 강의 들으면 더 좋은 방법이 나올듯?
    public String getDomain() {
        String linkUrl = getLinkUrl();
        String regex = "(?:https?://)?(?:www\\.)?([^/.]+)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(linkUrl);
        if(matcher.find()){
            String group = matcher.group(1);
            return group;
        }else{
            System.out.println("어이가 없네");
            throw new RuntimeException();
        }
    }

    public void setIconId(long iconId) {
        this.iconId = iconId;
    }
}