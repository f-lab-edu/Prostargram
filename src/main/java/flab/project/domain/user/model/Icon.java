package flab.project.domain.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Icon {
    private long iconId;
    private String domain;
    private String iconUrl;

    @Builder
    private Icon(long iconId, String domain, String iconUrl) {
        this.iconId = iconId;
        this.domain = domain;
        this.iconUrl = iconUrl;
    }
}
