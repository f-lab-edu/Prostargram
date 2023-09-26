package flab.project.service;

import flab.project.mapper.PostOptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class PostOptionService {

    private final PostOptionMapper postOptionMapper;

    public void savePostOptions(long postId, Set<String> optionContents) {
        int numberOfAffectedRow = postOptionMapper.saveAll(postId, optionContents);

        if (numberOfAffectedRow != optionContents.size()) {
            throw new RuntimeException();
        }
    }
}
