package flab.project.data;

import java.util.List;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.CollectionUtils;

@Getter
@ToString
public class SocialAccountsDelta {
    private List<String> toAddSocialAccounts;
    private List<String> toDeleteSocialAccounts;
    private List<String> existingSocialAccounts;

    public SocialAccountsDelta(
        List<String> existingSocialAccounts,
        List<String> receivedSocialAccounts
    ) {
        this.existingSocialAccounts = existingSocialAccounts;
        this.toAddSocialAccounts = extractToAddSocialAccounts(receivedSocialAccounts);
        this.toDeleteSocialAccounts = extractToDeleteSocialAccounts(receivedSocialAccounts);
    }

    public boolean hasToAddSocialAccounts() {
        return !CollectionUtils.isEmpty(toAddSocialAccounts);
    }

    public boolean hasToDeleteSocialAccounts() {
        return !CollectionUtils.isEmpty(toDeleteSocialAccounts);
    }

    private List<String> extractToAddSocialAccounts(List<String> receivedSocialAccounts) {
        return receivedSocialAccounts.stream()
            .filter(acount -> !existingSocialAccounts.contains(acount))
            .toList();
    }

    private List<String> extractToDeleteSocialAccounts(List<String> receivedSocialAccounts) {
        return existingSocialAccounts.stream()
            .filter(accout -> !receivedSocialAccounts.contains(accout))
            .toList();
    }
}