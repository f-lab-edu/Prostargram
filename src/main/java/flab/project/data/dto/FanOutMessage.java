package flab.project.data.dto;

import lombok.*;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FanOutMessage {

    private long userId;
    private long postId;
}
