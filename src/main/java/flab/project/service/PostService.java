package flab.project.service;

import flab.project.data.dto.model.AddBasicPostRequest;
import flab.project.data.dto.model.AddPostRequest;
import flab.project.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostMapper postMapper;

    public void addPost(long userId, AddPostRequest basicPost) {
        int numberOfAffectedRow = postMapper.addPost(userId, basicPost);

        if (numberOfAffectedRow != 1) {
            throw new RuntimeException();
        }
    }
}