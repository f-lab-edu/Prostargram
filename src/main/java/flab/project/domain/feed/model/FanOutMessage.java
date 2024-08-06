package flab.project.domain.feed.model;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FanOutMessage {

    private long userId;
    private long postId;
}
