package flab.project.service;

import flab.project.data.dto.model.AddPollPostRequest;
import flab.project.mapper.PollMetadataMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.BDDMockito.given;

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
        int numberOfAffectedRow = 1;

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);
        AddPollPostRequest pollPostRequest = createPollPostRequest(startDate, endDate);

        given(pollMetadataMapper.save(pollPostRequest))
                .willReturn(numberOfAffectedRow);

        // when & then
        assertThatCode(() -> pollMetadataService.addMetadata(pollPostRequest))
                .doesNotThrowAnyException();
    }

    @DisplayName("poll_metadata를 추가할 때, DB에 반영하는데 실패하면 RuntimeException을 던진다.")
    @Test
    void addMetadata_failReflectToDB(){
        // given
        int numberOfAffectedRow = -1;

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);
        AddPollPostRequest pollPostRequest = createPollPostRequest(startDate, endDate);

        given(pollMetadataMapper.save(pollPostRequest))
                .willReturn(numberOfAffectedRow);

        // when & then
        assertThatCode(() -> pollMetadataService.addMetadata(pollPostRequest))
                .isExactlyInstanceOf(RuntimeException.class);
    }

    @DisplayName("poll_metadata를 추가할 때, startDate와 EndDate가 같은 날이여도 된다.")
    @Test
    void addMetadata_sameStartDateAndEndDate(){
        // given
        int numberOfAffectedRow = 1;

        LocalDate targetDay = LocalDate.now();
        LocalDate startDate = targetDay;
        LocalDate endDate = targetDay;

        AddPollPostRequest pollPostRequest = createPollPostRequest(startDate, endDate);

        given(pollMetadataMapper.save(pollPostRequest))
                .willReturn(numberOfAffectedRow);

        // when & then
        assertThatCode(() -> pollMetadataService.addMetadata(pollPostRequest))
                .doesNotThrowAnyException();
    }

    @DisplayName("poll_metadata를 추가할 때, endDate가 startDate보다 과거이면 InvalidUserInputException 던진다.")
    @Test
    void addMetadata_EndDateBeforeStartDate(){
        // given
        int numberOfAffectedRow = 1;

        LocalDate targetDay = LocalDate.now();
        LocalDate startDate = targetDay;
        LocalDate endDate = targetDay;

        AddPollPostRequest pollPostRequest = createPollPostRequest(startDate, endDate);

        given(pollMetadataMapper.save(pollPostRequest))
                .willReturn(numberOfAffectedRow);

        // when & then
        assertThatCode(() -> pollMetadataService.addMetadata(pollPostRequest))
                .doesNotThrowAnyException();
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