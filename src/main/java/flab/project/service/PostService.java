package flab.project.service;

import flab.project.data.dto.model.BasicPost;
import flab.project.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostMapper postMapper;

    public void addBasicPost(BasicPost basicPost) {
        int NumberOfAffectedRow = postMapper.addBasicPost(basicPost);

        if (NumberOfAffectedRow != 1) {
            throw new RuntimeException();
        }
    }
}