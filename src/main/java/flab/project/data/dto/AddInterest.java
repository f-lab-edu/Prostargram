package flab.project.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Getter
public class AddInterest {

    @Positive
    private long userId;

    @Length(min = 1, max = 15)
    private String interestName;

    //todo 항상 클라이언트는 getInterestName을 사용하지 못 하고, getInterestNameWithSharp를 사용할 수 밖에 없도록 리펙토링 예정.
    @JsonIgnore
    public String getInterestNameWithSharp() {
        return "#" + interestName;
    }

    // todo 이거 annotation을 직접 만들어서 마치 Lombok처럼 Annotation을 작성하면 Compile시점에 해당 메서드가 만들어 지도록 리펙토링 예정.
    @JsonIgnore
    public List<String> getStringFields() {
        return List.of(interestName);
    }

    public AddInterest(
            @JsonProperty("userId") long userId,
            @JsonProperty("interestName") String interestName
    ) {
        this.userId = userId;
        this.interestName = HtmlUtils.htmlEscape(interestName);
    }

    private String convertEscapeCharacter() {
        return HtmlUtils.htmlEscape(interestName);
    }
}
