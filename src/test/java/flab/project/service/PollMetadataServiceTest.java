package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.domain.post.model.AddPollPostRequest;
import flab.project.domain.post.service.PollMetadataService;
import flab.project.domain.post.mapper.PollMetadataMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class PollMetadataServiceTest {

    @InjectMocks
    PollMetadataService pollMetadataService;
    @Mock
    PollMetadataMapper pollMetadataMapper;

    @DisplayName("poll_metadata를 추가한다.")
    @Test
    void addMetadata(){
        // given
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);
        AddPollPostRequest pollPostRequest = createPollPostRequest(startDate, endDate);

        // when & then
        pollMetadataService.addMetadata(pollPostRequest);
        then(pollMetadataMapper).should().save(pollPostRequest);
    }

    @DisplayName("poll_metadata를 추가할 때, startDate와 EndDate가 같은 날이여도 된다.")
    @Test
    void addMetadata_sameStartDateAndEndDate(){
        // given
        AddPollPostRequest pollPostRequest = createPollPostRequest(LocalDate.now(), LocalDate.now());

        // when & then
        pollMetadataService.addMetadata(pollPostRequest);
        then(pollMetadataMapper).should().save(pollPostRequest);
    }

    @DisplayName("poll_metadata를 추가할 때, endDate가 startDate보다 과거이면 InvalidUserInputException 던진다.")
    @Test
    void addMetadata_EndDateBeforeStartDate(){
        // given
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().minusDays(1);

        AddPollPostRequest pollPostRequest = createPollPostRequest(startDate, endDate);

        // when & then
        assertThatCode(() -> pollMetadataService.addMetadata(pollPostRequest))
                .isExactlyInstanceOf(InvalidUserInputException.class);
    }

    private static AddPollPostRequest createPollPostRequest(LocalDate startDate, LocalDate endDate) {
        return AddPollPostRequest.builder()
                .content("게시물 내용입니다.")
                .hashTagNames(Set.of("#test1", "#test2"))
                .optionContents(Set.of("선택지1", "선택지2"))
                .subject("주제입니다.")
                .startDate(startDate)
                .endDate(endDate)
                .allowMultipleVotes(true)
                .build();
    }
}