package flab.project.common;

import flab.project.mapper.BadWordMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

@Component
public class BadWordChecker {

    private Set<String> badWords;
    private final BadWordMapper badWordMapper;

    public BadWordChecker(BadWordMapper badWordMapper) {
        this.badWordMapper = badWordMapper;
//        badWords = badWordMapper.findAll();
    }

    public boolean hasBadWord(List<String> inputs) {
        return inputs.stream()
                .anyMatch(this::hasBadWord);
    }

    private boolean hasBadWord(String inputString) {
        if (!StringUtils.hasText(inputString)) {
            return false;
        }
        return badWords.stream()
                .anyMatch(badWord -> inputString.trim().contains(badWord));
    }
}
