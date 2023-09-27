package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.dto.model.AddPostRequest;
import flab.project.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostMapper postMapper;

    public void addPost(long userId, AddPostRequest post) {
        validateUserIdPositive(userId);

        postMapper.save(userId, post);
    }

    private void validateUserIdPositive(long userId) {
        if (userId <= 0) {
            throw new InvalidUserInputException();
        }
    }
}