package flab.project.service;

import flab.project.data.dto.model.AddBasicPostRequest;
import flab.project.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostMapper postMapper;

    public void addBasicPost(long userId, AddBasicPostRequest basicPost) {
        int numberOfAffectedRow = postMapper.addBasicPost(userId, basicPost);

        if (numberOfAffectedRow != 1) {
            throw new RuntimeException();
        }
    }
}