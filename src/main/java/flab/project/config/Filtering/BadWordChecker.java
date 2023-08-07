package flab.project.config.Filtering;

import static java.nio.file.Files.*;

import flab.project.mapper.BadwordMapper;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class BadWordChecker {
    private Set<String> badwords;
    private final BadwordMapper badwordMapper;

    public BadWordChecker(BadwordMapper badwordMapper) {
        // Todo badword를 DB로 부터 받아오는게 나을까? FILE로 관리하는게 나을까?
        this.badwordMapper = badwordMapper;
        // todo 생성자가 Mapper랑 의존하는 상태가 찜찜함.
        // 특히 test코드에서 when()메서드가 작동하기 전에 초기화 부터 진행되면서
        // badwordMapper.getAll()메서드가 when절을 따르지 않는 문제도 발생함.

        badwords = badwordMapper.getAll();

    }

    public boolean hasBadWord(List<String> inputs) {
        return inputs.stream()
            .anyMatch(this::hasBadWord);
    }

    private boolean hasBadWord(String inputString) {
        if (!StringUtils.hasText(inputString)) {
            return false;
        }
        return badwords.stream()
            .anyMatch(
                badword
                    -> inputString.trim().contains(badword)
            );
    }


}
