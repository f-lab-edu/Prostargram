package flab.project.data.dto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Option {

    private Long optionId;

    private String optionContent;

    private long voteCount;
}