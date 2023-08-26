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

    //todo getInterestName대신 getInterestNameWithSharp를 사용하게 강제하고 싶은데 방법이 없겠지..?
    @JsonIgnore
    public String getInterestNameWithSharp() {
        return "#" + interestName;
    }

    // todo 이거 annotation을 직접 만들어서 어노테이션 지정해놓으면 만들어지게 하면 어떨까?
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
