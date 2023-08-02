package flab.project.data;

import java.util.List;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.CollectionUtils;

@Getter
@ToString
public class InterestsDelta {
    private final int TOTAL_SOCIAL_ACCOUNTS_MAX_SIZE = 3;
    private List<String> toAddInterests;
    private List<String> toDeleteInterests;
    private List<String> existingInterests;
//    private List<String> nonExistingInterestsInHashTag;

    public InterestsDelta(
        List<String> existingInterests,
        List<String> receivedInterests
    ) {
        this.existingInterests = existingInterests;
        this.toAddInterests = extractToAddInterests(receivedInterests);
        this.toDeleteInterests = extractToDeleteInterests(receivedInterests);
    }

    private List<String> extractToAddInterests(List<String> receivedInterests) {
        return receivedInterests.stream()
            .filter(acount -> !existingInterests.contains(acount))
            .toList();
    }

    private List<String> extractToDeleteInterests(List<String> receivedInterests) {
        return existingInterests.stream()
            .filter(accout -> !receivedInterests.contains(accout))
            .toList();
    }

    private void setExistingInterests(List<String> existingInterests) {
        this.existingInterests = existingInterests;
    }

    public boolean hasToAddInterests() {
        return !CollectionUtils.isEmpty(toAddInterests);
    }

    public boolean hasToDelteInterests() {
        return !CollectionUtils.isEmpty(toDeleteInterests);
    }
    // toAddInterest - 실제 존재하는 놈(select existingHashtag where in (toAddInterests))
//    public void setNonExistingHashtags(List<String> nonExistingHashtags) {
//        this.nonExistingHashtags = nonExistingHashtags;
//    }
}
