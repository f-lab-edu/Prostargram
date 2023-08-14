package flab.project.data.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInterest {
    @Positive
    private long userId;

    @Length(min = 1, max = 15)
    private String interestName;

    public long getUserId() {
        return userId;
    }

    //todo getInterestName대신 getInterestNameWithSharp를 사용하게 강제하고 싶은데 방법이 없겠지..?
    public String findInterestNameWithSharp() {
        return "#"+interestName;
    }

    // todo 이거 annotation을 직접 만들어서 어노테이션 지정해놓으면 만들어지게 하면 어떨까?
    public List<String> findStringFields() {
        return List.of(interestName);
    }

    public void convertEscapeCharacter() {
        this.interestName=HtmlUtils.htmlEscape(interestName);
    }
}
