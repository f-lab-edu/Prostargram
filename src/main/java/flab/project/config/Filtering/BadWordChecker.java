package flab.project.config.Filtering;

import static java.nio.file.Files.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class BadWordChecker {

    private final SubStringExtractor subStringExtractor;
    private final String fileName = "badword.txt";
    private final Map<String,String> badwords;

    public BadWordChecker(SubStringExtractor subStringExtractor) {
        // Todo badword를 DB로 부터 받아오는게 나을까? FILE로 관리하는게 나을까?
        try {
            ClassPathResource resource = new ClassPathResource(fileName);
            File file = resource.getFile();

            List<String> words = readAllLines(file.toPath(), StandardCharsets.UTF_8);

            badwords = words.stream()
                .collect(Collectors.toMap(
                    Function.identity(),
                    Function.identity()
                ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.subStringExtractor = subStringExtractor;
    }

    public boolean hasBadWord(List<String> inputs) {
        return inputs.stream()
            .anyMatch(this::hasBadWord);
    }

    private boolean hasBadWord(String inputString) {
        if(!StringUtils.hasText(inputString)){
            return false;
        }

        List<String> words = List.of(inputString.split(" "));

        List<String> subStrings = subStringExtractor.extractSubString(words);

        return subStrings.stream()
            .anyMatch(badwords::containsKey);
    }


}
