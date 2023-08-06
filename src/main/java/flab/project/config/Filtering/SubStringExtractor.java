package flab.project.config.Filtering;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SubStringExtractor {
    public List<String> extractSubString(List<String> words) {
        List<String> subStrings = new ArrayList<>();

        for (String word : words) {
            int wordSize = word.length();

            for (int len = 1; len <= wordSize; len++) {
                for (int start = 0; start <= wordSize - len; start++) {
                    int end = start + len;
                    subStrings.add(word.substring(start, end));
                }
            }
        }

        return subStrings;
    }
}
