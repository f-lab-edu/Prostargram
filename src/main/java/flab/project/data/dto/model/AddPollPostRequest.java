package flab.project.data.dto.model;

import flab.project.data.enums.PostType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

import static flab.project.data.enums.PostType.POLL;

@Getter
@SuperBuilder
@NoArgsConstructor
public class AddPollPostRequest extends AddPostRequest {

    private static final PostType postType = POLL;

    @Length(max = 35)
    @NotBlank
    private String subject;

    @FutureOrPresent
    private LocalDate startDate;

    @FutureOrPresent
    private LocalDate endDate;

    @Size(min = 2, max = 5)
    private Set<@NotBlank @Length(max = 15) String> optionContents;

    private boolean allowMultipleVotes;
}